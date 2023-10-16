package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.Helper;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom1;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/**
 * The SplashController class is responsible for initializing the splash screen
 * of the application.
 * It sets the progress bar progress and runs GPT prompts. The final prompt
 * changes the root of the app to "start_menu".
 *
 * @throws ApiProxyException if there is an issue with the API proxy
 */
public class SplashController {
  @FXML
  private ProgressBar progressBar;

  /**
   * Initializes the SplashController by setting the progress bar progress and
   * running GPT prompts.
   * The final prompt changes the root of the app to "start_menu".
   *
   * @throws ApiProxyException if there is an issue with the API proxy
   */
  public void initialize() throws ApiProxyException {
    // gpt prompts and setting progressbar progress
    progressBar.setProgress(.33);

    GameState.eleanorAi.runGpt(
        GptPromptEngineeringRoom1.gameIntro(),
        s -> {
          Platform.runLater(
              () -> {
                progressBar.setProgress(.66);
              });
        });

    GameState.eleanorAi2.runGpt(
        GptPromptEngineeringRoom1.gameIntro(),
        s -> {
          Platform.runLater(
              () -> {
              });
        });

    // final prompt, and let app change root.
    GameState.eleanorAi.runGpt(
        GptPromptEngineeringRoom1.gameInstructions(),
        s -> {
          List<String> pirateDialogue = Helper.getTextBetweenChar(s, "#", false);
          if (pirateDialogue.size() > 0) {
            GameState.instructionMsg = pirateDialogue.get(0);
          }
          Platform.runLater(
              () -> {
                progressBar.setProgress(.99);

                try {
                  App.setRoot("start_menu");
                } catch (IOException e) {
                  e.printStackTrace();
                }
              });
        });
  }
}
