package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class StartMenuController {
  @FXML private ComboBox<String> difficulty;
  @FXML private ComboBox<String> timeLimit;

  @FXML Button startButton;

  public void initialize() {
    difficulty.getItems().addAll("Easy", "Medium", "Hard");
    timeLimit.getItems().addAll("2 minutes", "4 minutes", "6 minutes");
  }

  @FXML
  public void onClickStartButton() {
    // Get the difficulty and time limit the user selected
    String difficulty = this.difficulty.getValue();
    String timeLimit = this.timeLimit.getValue();

    System.out.println(
        "Game started with" + difficulty + " difficulty and " + timeLimit + " time limit");
  }
}
