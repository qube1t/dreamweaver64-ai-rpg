package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.Helper;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom1;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class StartMenuController {
  @FXML private ComboBox<String> difficulty;
  @FXML private ComboBox<String> timeLimit;

  @FXML Button startButton;

  public void initialize() throws ApiProxyException {

    difficulty.getItems().addAll("EASY", "MEDIUM", "HARD");
    timeLimit.getItems().addAll("2 minutes", "4 minutes", "6 minutes");
    // new GptEngine();

    GameState.eleanorAi.runGpt(GptPromptEngineeringRoom1.gameIntro());
    GameState.eleanorAi.runGpt(
        GptPromptEngineeringRoom1.gameInstructions(),
        s -> {
          List<String> pirateDialogue = Helper.getTextBetweenChar(s, "#");
          if (pirateDialogue.size() > 0) {
            GameState.instructionMsg = pirateDialogue.get(0);
          }
          Platform.runLater(() -> startButton.setDisable(false));
        });
  }

  @FXML
  public void onClickStartButton() {
    // Get the difficulty and time limit the user selected
    String difficulty = this.difficulty.getValue();
    String timeLimit = this.timeLimit.getValue();

    // If the difficulty or time limit is default, set it to easy and 2 minutes respectively
    if (difficulty == null) {
      difficulty = "EASY";
    }
    if (timeLimit == null) {
      timeLimit = "2 minutes";
    }

    GameState.gameMode = new String[] {difficulty, timeLimit};
    System.out.println(
        "Game started with"
            + GameState.gameMode[0]
            + " difficulty and "
            + GameState.gameMode[1]
            + " time limit");

    // Transition to the main game view
    try {
      App.setRoot("main_game");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
