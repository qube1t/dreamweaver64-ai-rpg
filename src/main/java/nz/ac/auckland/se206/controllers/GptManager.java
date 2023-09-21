package nz.ac.auckland.se206.controllers;

import javafx.concurrent.Task;
import nz.ac.auckland.se206.gpt.ChatMessage;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionRequest;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult;
import nz.ac.auckland.se206.gpt.openai.ChatCompletionResult.Choice;

public class GptManager {

  private static ChatCompletionRequest chatCompletionRequest;

  public GptManager() {
    chatCompletionRequest =
        new ChatCompletionRequest().setN(1).setTemperature(0.2).setTopP(0.5).setMaxTokens(100);
  }

  public String getGptResponseAsString(String message) {
    // Use a Task to perform GPT model execution in the background
    Task<String> task =
        new Task<String>() {
          @Override
          protected String call() throws Exception {
            ChatMessage msg = new ChatMessage("user", message);
            chatCompletionRequest.addMessage(msg);
            ChatCompletionResult chatCompletionResult = chatCompletionRequest.execute();
            Choice result = chatCompletionResult.getChoices().iterator().next();
            chatCompletionRequest.addMessage(result.getChatMessage());
            return result.getChatMessage().getContent();
          }
        };

    // Set the onFailed event of the task to handle exceptions
    task.setOnFailed(
        event -> {
          Throwable exception = task.getException();
          if (exception != null) {
            exception.printStackTrace();
          }
        });

    // Start the task in a new background thread
    Thread thread = new Thread(task);
    thread.setDaemon(true);
    thread.start();

    try {
      // Wait for the task to complete and return the result
      return task.get();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null; // Return null if there was an error
  }
}
