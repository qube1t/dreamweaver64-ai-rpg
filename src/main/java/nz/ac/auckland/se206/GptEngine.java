package nz.ac.auckland.se206;

import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

public class GptEngine {
  private static ChatCompletionRequest chatCompletionRequest;

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
  public static ChatMessage runGpt(ChatMessage msg) throws ApiProxyException {
    chatCompletionRequest.addMessage(msg);
    try {
      ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
      Choice result = chatCompletionResult.getChoices().iterator().next();

      chatCompletionRequest.addMessage(result.getChatMessage());

      GameState.mainGame.addChat(result.getChatMessage().getContent());
      return result.getChatMessage();
    } catch (ApiProxyException e) {
      // TODO handle exception appropriately
      e.printStackTrace();
      return null;
    }
  }
}
