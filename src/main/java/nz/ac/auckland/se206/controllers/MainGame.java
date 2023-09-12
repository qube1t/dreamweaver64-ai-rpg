package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.components.Character;

public class MainGame {

  @FXML private Pane game_pane;
  @FXML private Character character;
  @FXML private Pane outer_pane;
  @FXML private Label chat_toggle_btn;
  @FXML private Pane aiCharacterPane;
  @FXML private Pane chatPane;
  @FXML private ImageView speechBubble;
  @FXML private ScrollPane bubbleTextPane;
  @FXML private Label bubbleText;
  @FXML private ListView<Label> chat;
  Text bubbleChatText = new Text("text");

  public void initialize() throws IOException {
    // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/room.fxml"));
    // fxmlLoader.setController(new RoomController());

    System.out.println(1);
    Region room1 = (Region) FXMLLoader.load(getClass().getResource("/fxml/room1.fxml"));
    room1.setScaleShape(true);
    character = (Character) room1.lookup("#character");

    game_pane.getChildren().add(0, room1);

    System.out.println(character);

    // setting up bubble chat
    bubbleChatText.wrappingWidthProperty().bind(bubbleTextPane.minWidthProperty());
    bubbleTextPane.setFitToWidth(true);
    bubbleTextPane.setContent(bubbleChatText);

    addChat("hello");
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    // System.out.println("key " + event.getCode() + " pressed");

    String letter = event.getCode().toString();

    if (letter.equals("W")) {
      character.setAction(0);
    } else if (letter.equals("A")) {
      character.setAction(1);
    }
    if (letter.equals("S")) {
      character.setAction(2);
    } else if (letter.equals("D")) {
      character.setAction(3);
    }

    // move after animating as it will change direction of character
    if (letter.equals("D") || letter.equals("A") || letter.equals("W") || letter.equals("S")) {
      if (!character.isAnimating()) character.startAnimation();
      character.move();
    }
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {
    // System.out.println("key " + event.getCode() + " released");
    String letter = event.getCode().toString();
    if (letter.equals("D") || letter.equals("A") || letter.equals("W") || letter.equals("S")) {
      character.endAnimation();
    }
  }

  @FXML
  private void toggleChat() {
    if (chatPane.isDisable()) {
      // chatPane is hidden -> show it
      chatPane.setDisable(false);
      chatPane.setOpacity(0.5);
      chat_toggle_btn.setText("Hide Chat");

      speechBubble.setVisible(false);
      bubbleTextPane.setVisible(false);
    } else {
      // hide chatPane
      chatPane.setDisable(true);
      chatPane.setOpacity(0);
      chat_toggle_btn.setText("Show Chat");
      outer_pane.requestFocus();
    }
  }

  private void addChat(String text) {

    bubbleChatText.setText(text);

    Label label = new Label(text);
    label.setWrapText(true);
    label.getStyleClass().add("chat-text-announcement");
    label.setMaxWidth(500);
    chat.getItems().add(0, label);
  }
}
