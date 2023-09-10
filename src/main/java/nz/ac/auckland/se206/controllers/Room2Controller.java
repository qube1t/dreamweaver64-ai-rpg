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
    App.setRoot("password");
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
  }
}
