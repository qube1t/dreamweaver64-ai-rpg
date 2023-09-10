package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;

public class Room2Controller {

  @FXML private Rectangle yellowBox;
  @FXML private Rectangle greyBox;
  @FXML private Rectangle purpleBox;
  @FXML private Rectangle whiteBox;
  @FXML private Rectangle turBox;
  @FXML private Rectangle greenBox;
  @FXML private Rectangle blueBox;
  @FXML private Rectangle recL1;
  @FXML private Rectangle recL2;
  @FXML private Rectangle recL3;
  @FXML private Rectangle recL4;
  @FXML private Rectangle recL5;
  @FXML private Rectangle recL51;
  @FXML private Rectangle recL52;
  @FXML private Rectangle recL53;
  @FXML private Rectangle recL54;
  @FXML private Rectangle recL55;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
    showDialog("Info", "Pirate's Ship Dream", "story,,");
  }

  /**
   * Displays a dialog box with the given title, header text, and message.
   *
   * @param title the title of the dialog box
   * @param headerText the header text of the dialog box
   * @param message the message content of the dialog box
   */
  private void showDialog(String title, String headerText, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(message);
    alert.showAndWait();
  }

  /**
   * Handles the click event on the yellow box.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the chat view
   */
  @FXML
  public void onClickYellow(MouseEvent event) throws IOException {
    System.out.println("Yellow treasure box clicked");

    if (!GameState.isRiddleResolved) {
      // App.setRoot("chat");
      if (GameState.isRiddleResolved) {
        // App.setRoot("memory");
      }
    } else {
      // App.setRoot("memory");
    }
    GameState.attemptToOpenTreasure--;
    checkAttempt();
  }

  /**
   * Handles the click event on the grey box.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the chat view
   */
  @FXML
  public void onClickGrey(MouseEvent event) throws IOException {
    System.out.println("Grey treasure box clicked");
    showDialog("Info", "Wrong", "This is an empty box!");
    GameState.attemptToOpenTreasure--;
    checkAttempt();
  }

  /**
   * Handles the click event on the purple box.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the chat view
   */
  @FXML
  public void onClickPurple(MouseEvent event) throws IOException {
    System.out.println("Purple treasure box clicked");
    GameState.attemptToOpenTreasure--;
    checkAttempt();
  }

  /**
   * Handles the click event on the white box.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the chat view
   */
  @FXML
  public void onClickWhite(MouseEvent event) throws IOException {
    System.out.println("White treasure box clicked");
    if (!GameState.isKeyFound) {
        App.setRoot("password");
    } else {
        showDialog("Info", "Key", "Key found!");
    }
    GameState.attemptToOpenTreasure--;
    checkAttempt();
  }

  /**
   * Handles the click event on the tur box.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the chat view
   */
  @FXML
  public void onClickTur(MouseEvent event) throws IOException {
    System.out.println("Tur treasure box clicked");
    GameState.attemptToOpenTreasure--;
    checkAttempt();
  }

  /**
   * Handles the click event on the green box.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the chat view
   */
  @FXML
  public void onClickGreen(MouseEvent event) throws IOException {
    System.out.println("Green treasure box clicked");
    GameState.attemptToOpenTreasure--;
    checkAttempt();
  }

  /**
   * Handles the click event on the blue box.
   *
   * @param event the mouse event
   * @throws IOException if there is an error loading the chat view
   */
  @FXML
  public void onClickBlue(MouseEvent event) throws IOException {
    System.out.println("Blue treasure box clicked");
    GameState.attemptToOpenTreasure--;
    checkAttempt();
  }

  private void checkAttempt() {
    if (GameState.attemptToOpenTreasure == 4) {
        recL1.setVisible(true);
    } else if (GameState.attemptToOpenTreasure == 3) {
        recL2.setVisible(true);
    } else if (GameState.attemptToOpenTreasure == 2) {
        recL3.setVisible(true);
    } else if (GameState.attemptToOpenTreasure == 1) {
        recL4.setVisible(true);
    } else if (GameState.attemptToOpenTreasure == 0) {
        recL5.setVisible(true);
        recL51.setVisible(true);
        recL52.setVisible(true);
        recL53.setVisible(true);
        recL54.setVisible(true);
        recL55.setVisible(true);
    }
  }
}
