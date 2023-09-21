package nz.ac.auckland.se206.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;

public class EndMenuController {
  @FXML private Button exit;
  @FXML private Text WinOrLose;
  @FXML private Label letter;
  @FXML private Button attribution;

  public void initialize() {
    // Set the text to display based on whether the player won or lost
    if (GameState.winTheGame) {
      WinOrLose.setText("You Escaped!");
      // letter.setText(GameState.encrypMessage);
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
  private void onOpenEndCredit(ActionEvent event) throws IOException {
    App.setRoot("end_credit");;
  }
}
