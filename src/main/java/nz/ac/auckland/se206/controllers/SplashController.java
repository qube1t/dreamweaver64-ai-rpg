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

public class SplashController {
  @FXML private ProgressBar progressBar;

  public void initialize() throws ApiProxyException {
    // gpt prompts and setting progressbar progress
    progressBar.setProgress(.25);

    GameState.eleanorAi.runGpt(
        GptPromptEngineeringRoom1.gameIntro(),
        s -> {
          Platform.runLater(
              () -> {
                progressBar.setProgress(.50);
              });
        });

    GameState.eleanorAi.runGpt(
        GptPromptEngineeringRoom1.gameInstructions(),
        s -> {
          List<String> pirateDialogue = Helper.getTextBetweenChar(s, "#");
          if (pirateDialogue.size() > 0) {
            GameState.instructionMsg = pirateDialogue.get(0);
          }
          Platform.runLater(
              () -> {
                progressBar.setProgress(.75);
              });
        });

    // final prompt, and let app change root.
    GameState.eleanorAi.runGpt(
        GptPromptEngineeringRoom1.getFacts(),
        s -> {
          GameState.factsAboutDW64 = Helper.getTextBetweenChar(s, "#");
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
