package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;

public class EndMenuController {
  @FXML private Button restart;
  @FXML private Button exit;
  @FXML private Text WinOrLose;

  public void initialize() {
    GameState.endTime = System.currentTimeMillis();
    try {
      MainGame.addOverlay("end_credit", false);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    // Set the text to display based on whether the player won or lost
    if (GameState.winTheGame) {
      WinOrLose.setText("You Escaped!");
    } else {
      WinOrLose.setText("Time's Up! You Lose!");
    }
  }

  @FXML
  private void onClickRestart() {
    // When the restart button is clicked, reset the game state and go back to the main menu
    GameState.winTheGame = false;
    try {
      App.setRoot("start_menu");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void onClickExit() {
    // When the exit button is clicked, exit the game
    System.exit(0);
  }
}
