package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class StartMenuController {
  @FXML
  private Button startButton;
  @FXML
  private ComboBox<String> timeLimit;
  @FXML
  private Label timeLimitLabel;
  @FXML
  private Label difficultyLabel;
  @FXML
  private ComboBox<String> difficulty;
  @FXML
  private Label title;
  @FXML
  private Label instruction;

  /** Initialize the start menu. */
  public void initialize() throws ApiProxyException {
    difficulty.getItems().addAll("EASY", "MEDIUM", "HARD");
    timeLimit.getItems().addAll("2 minutes", "4 minutes", "6 minutes");

    instruction.setText(GameState.instructionMsg);
  }

  /**
   * When the player clicks on the start button, the game will be started.
   * 
   * @param event
   * @throws IOException
   */
  @FXML
  public void onClickStartButton(MouseEvent event) throws IOException {
    // Get the difficulty and time limit the user selected
    String difficulty = this.difficulty.getValue();
    String timeLimit = this.timeLimit.getValue();

    // If the difficulty or time limit is default, set it to easy and 2 minutes
    // respectively
    if (difficulty == null) {
      difficulty = "EASY";
    }
    if (timeLimit == null) {
      timeLimit = "2 minutes";
    }

    GameState.gameMode = new String[] { difficulty, timeLimit };

    // set the hints remaining based on the difficulty
    switch (difficulty) {
      case "EASY":
        GameState.hintsRemaining = -1;
        break;
      case "MEDIUM":
        GameState.hintsRemaining = 5;
        break;
      case "HARD":
        GameState.hintsRemaining = 0;
        break;
    }

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
