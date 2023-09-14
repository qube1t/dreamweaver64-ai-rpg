package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class StartMenuController {
  @FXML private ComboBox<String> difficulty;
  @FXML private ComboBox<String> timeLimit;

  public void initialize() {
    difficulty.getItems().addAll("Easy", "Medium", "Hard");
    timeLimit.getItems().addAll("2 minutes", "4 minutes", "6 minutes");
  }
}
