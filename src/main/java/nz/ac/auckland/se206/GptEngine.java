package nz.ac.auckland.se206;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

public class GptEngine {
  private ChatCompletionRequest chatCompletionRequest;
  private boolean active = false;
  private int stage = 0;
  private Thread activeThread;
  private Queue<ChatMessage> promptQueue = new LinkedList<>();
  private Queue<GptResultAction> promptFuncQueue = new LinkedList<>();

  public GptEngine() {
    if (chatCompletionRequest == null)
      chatCompletionRequest =
          new ChatCompletionRequest().setN(1).setTemperature(0.2).setTopP(0.5).setMaxTokens(100);
          
          Timer timer = new Timer();
          timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
              if (!active && promptQueue.size() > 0) startNewThread();
            }
          }, 1000, 1000);
            }

  /**
   * Runs the GPT model with a given chat message.
   *
   * @param msg the chat message to process
   * @return the response chat message
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  public void runGpt(String msg, GptResultAction myFunc) throws ApiProxyException {
    ChatMessage chatmsg = new ChatMessage("user", msg);
    promptQueue.add(chatmsg);
    promptFuncQueue.add(myFunc);
    
    if (!active) {
      active = true;
      startNewThread();
    }
  }

  private void startNewThread() {
    activeThread =
        new Thread(
            () -> {
              System.out.println("GptEngine Thread " + Thread.currentThread().getId());

              active = true;

              // get the next prompt and function to call
              ChatMessage nextPrompt = promptQueue.poll();
              GptResultAction myFunc = promptFuncQueue.poll();

              while (nextPrompt != null) {
                try {

                  // adds prompt to conversation and execs
                  chatCompletionRequest.addMessage(nextPrompt);
                  ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();

                  stage++;

                  // performs onfinish tasks
                  onGptCompletion(chatCompletionResult, myFunc);
                } catch (Exception e) {
                  e.printStackTrace();
                }

                // get the next prompt and function to call
                nextPrompt = promptQueue.poll();
                myFunc = promptFuncQueue.poll();

                // if there is no next prompt, wait for 2 seconds and check again
                // TODO do as much testing as possible
                if (nextPrompt == null) {
                  try {
                    Thread.sleep(2000);
                    nextPrompt = promptQueue.poll();
                    myFunc = promptFuncQueue.poll();
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                  }
                }
              }
              active = false;
            });
    activeThread.start();
  }

  private void onGptCompletion(ChatCompletionResult chatCompletionResult, GptResultAction myFunc)
      throws Exception {
    stage++;
    Choice result = chatCompletionResult.getChoices().iterator().next();
    System.out.println(result.getChatMessage().getContent());

    chatCompletionRequest.addMessage(result.getChatMessage());

    if (myFunc != null) myFunc.call(result.getChatMessage().getContent());

    List<String> chatEntry = Helper.getTextBetweenChar(result.getChatMessage().getContent(), "*");
    if (chatEntry.size() > 0)
      Platform.runLater(
          () -> {
            String msg = chatEntry.get(0).replaceAll("\"", "");
            int noOfHints = Helper.countOccurences(msg, "%");
            msg = msg.replaceAll("%", "");

            if (noOfHints > 0) {
              if (GameState.hintsRemaining - noOfHints >= 0) GameState.hintsRemaining -= noOfHints;
              else GameState.hintsRemaining = 0;
            }

            GameState.mainGame.addChat(msg, true);
          });
  }

  public void runGpt(String string) throws ApiProxyException {
    runGpt(string, null);
  }
}
