package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;

public class EndMenuController {
  @FXML
  private Button exit;
  @FXML
  private Button restart;
  @FXML
  private Text winOrLose;
  @FXML
  private Label attribution;
  @FXML
  private Label letter;

  /** Initialize the end menu. */
  public void initialize() {
    // Set the text to display based on whether the player won or lost
    if (GameState.winTheGame) {
      winOrLose.setText("You Escaped!");
      letter.setWrapText(true);
      letter.setText(GameState.finalMsg);
    } else {
      winOrLose.setText("Time's Up! You Lose!");
    }
  }

  @FXML
  private void onClickRestart(MouseEvent event) throws IOException {
    GameState.reset();
    App.setRoot("splash_screen");
  }

  /**
   * When the player clicks on the exit button, the game will be closed.
   * 
   * @param event the action event
   * @throws IOException
   */
  @FXML
  private void onClickExit(MouseEvent event) throws IOException {
    System.exit(0);
  }

  /**
   * When the player clicks on the attribution, the attribution page will be
   * opened.
   * 
   * @param event the mouse event
   * @throws IOException
   */
  @FXML
  private void onOpenEndCredit(MouseEvent event) throws IOException {
    App.setRoot("end_credit");
  }
}
