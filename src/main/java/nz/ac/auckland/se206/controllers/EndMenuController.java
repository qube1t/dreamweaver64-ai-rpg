package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.GptEngine;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom1;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/**
 * The EndMenuController class controls the end menu screen of the game. It
 * handles
 * the restart and exit button clicks, displays the final message to the player,
 * and navigates to the start menu or end credits screen based on user input.
 */
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

  /**
   * Initializes the EndMenuController by re-creating the GptEngine, stopping
   * all sound effects, and setting the text to display based on whether the
   * player won or lost.
   *
   * @throws ApiProxyException if there is an issue with the API proxy
   */
  public void initialize() throws ApiProxyException {
    GameState.eleanorAi = new GptEngine(); // Re-create the GptEngine
    GameState.eleanorAi2 = new GptEngine(); // Re-create the second GptEngine
    GameState.eleanorAi.runGpt(GptPromptEngineeringRoom1.gameIntro());
    GameState.eleanorAi2.runGpt(GptPromptEngineeringRoom1.gameIntro());

    // stop all sound effects
    for (AudioClip sound : GameState.soundFx) {
      sound.stop();
    }

    GameState.soundFx.clear();

    // Set the text to display based on whether the player won or lost
    if (GameState.winTheGame) {
      winOrLose.setText("You Escaped!");
      letter.setWrapText(true);
      letter.setText(GameState.finalMsg);
    } else {
      winOrLose.setText("Time's Up! You Lose!");
    }
  }

  /**
   * Resets the game state and all controllers, and returns to the start menu.
   *
   * @param event The mouse event that triggered the restart.
   * @throws IOException If there is an error loading the start menu.
   */
  @FXML
  private void onClickRestart(MouseEvent event) throws IOException {
    // Reset the game state and all controllers
    GameState.reset();
    Room1Controller.resetGptRoom1();
    Room2Controller.resetGptRoom2();
    Room3Controller.resetGptRoom3();
    MachineController.resetMachine();
    App.startMenu = null;
    App.setRoot("start_menu");
  }

  /**
   * Exits the application when the exit button is clicked.
   *
   * @param event The mouse event that triggered the method call.
   * @throws IOException If an I/O error occurs.
   */
  @FXML
  private void onClickExit(MouseEvent event) throws IOException {
    System.exit(0);
  }

  /**
   * Event handler for when the user clicks on the "End Credits" button.
   * Changes the scene to the end credit screen.
   *
   * @param event The mouse event that triggered this method.
   * @throws IOException If there is an error loading the end credit screen.
   */
  @FXML
  private void onOpenEndCredit(MouseEvent event) throws IOException {
    App.setRoot("end_credit");
  }
}
