package nz.ac.auckland.se206;

import java.util.LinkedList;
import java.util.Queue;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

public class GptEngine {
  private static ChatCompletionRequest chatCompletionRequest;
  private static boolean active = false;
  private static int stage = 0;
  private static Thread activeThread;
  private static Queue<ChatMessage> promptQueue = new LinkedList<>();
  private static Queue<GptResultAction> promptFuncQueue = new LinkedList<>();

  public GptEngine() {
    if (chatCompletionRequest == null)
      chatCompletionRequest =
          new ChatCompletionRequest().setN(1).setTemperature(0.2).setTopP(0.5).setMaxTokens(100);
  }

  /**
   * Runs the GPT model with a given chat message.
   *
   * @param msg the chat message to process
   * @return the response chat message
   * @throws ApiProxyException if there is an error communicating with the API proxy
   */
  public static void runGpt(ChatMessage msg, GptResultAction myFunc) throws ApiProxyException {
    promptQueue.add(msg);
    promptFuncQueue.add(myFunc);
    if (!active) startNewThread();
  }

  private static void startNewThread() {
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

  private static void onGptCompletion(
      ChatCompletionResult chatCompletionResult, GptResultAction myFunc) throws Exception {
        stage++;
        Choice result = chatCompletionResult.getChoices().iterator().next();
        System.out.println(result.getChatMessage().getContent());

    chatCompletionRequest.addMessage(result.getChatMessage());

    myFunc.call(result.getChatMessage().getContent());

    // List<String> chatEntry = Helper.getTextBetweenChar(result.getChatMessage().getContent(), "*");
    // if (chatEntry.size() > 0) GameState.mainGame.addChat(chatEntry.get(0));
  }
}
