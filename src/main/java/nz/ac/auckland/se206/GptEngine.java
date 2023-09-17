package nz.ac.auckland.se206;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

public class GptEngine {
  private static ChatCompletionRequest chatCompletionRequest;
  private static boolean active = false;
  private static Thread activeThread;
  private static Queue<ChatMessage> promptQueue = new LinkedList<>();

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
    if (!active) startNewThread(myFunc);
  }

  private static void startNewThread(GptResultAction myFunc) {
    activeThread =
        new Thread(
            () -> {
              System.out.println(Thread.currentThread().getId());
              active = true;
              ChatMessage nextPrompt = promptQueue.poll();
              while (nextPrompt != null) {
                try {
                  chatCompletionRequest.addMessage(nextPrompt);
                  ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
                  gptCompletion(chatCompletionResult, myFunc);
                } catch (Exception e) {
                  e.printStackTrace();
                }
                nextPrompt = promptQueue.poll();
                if (nextPrompt == null) {
                  try {
                    Thread.sleep(2000);
                    nextPrompt = promptQueue.poll();
                  } catch (InterruptedException e) {
                    e.printStackTrace();
                  }
                }
              }
              active = false;
            });
    activeThread.start();
  }

  private static void gptCompletion(
      ChatCompletionResult chatCompletionResult, GptResultAction myFunc) throws Exception {
    System.out.println(
        chatCompletionResult.getChoices().iterator().next().getChatMessage().getContent());
    Choice result = chatCompletionResult.getChoices().iterator().next();

    chatCompletionRequest.addMessage(result.getChatMessage());

    myFunc.call(result.getChatMessage().getContent());

    List<String> chatEntry = Helper.getTextBetweenChar(result.getChatMessage().getContent(), "*");
    if (chatEntry.size() > 0) GameState.mainGame.addChat(chatEntry.get(0));
  }
}
