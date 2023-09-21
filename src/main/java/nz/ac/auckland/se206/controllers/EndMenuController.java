package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.GameState;

public class EndMenuController {
  @FXML private Button exit;
  @FXML private Text WinOrLose;
  @FXML private Text timeLeft;
  @FXML private Label attribution;

  public void initialize() {

    // Set the text to display based on whether the player won or lost
    if (GameState.winTheGame) {
      WinOrLose.setText("You Escaped!");
      long time = (GameState.endTime - GameState.startTime) / 1000;
      timeLeft = new Text("Time Left: " + time);
    } else {
      WinOrLose.setText("Time's Up! You Lose!");
    }
  }

  @FXML
  private void onClickExit() {
    // When the exit button is clicked, exit the game
    System.exit(0);
  }

  @FXML
  void onOpenEndCredit(MouseEvent event) throws IOException {
    // MainGame.addOverlay("end_credit", false);
  }
}
