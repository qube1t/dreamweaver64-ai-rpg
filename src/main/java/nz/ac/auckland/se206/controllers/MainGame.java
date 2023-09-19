package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.components.Character;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class MainGame {

  @FXML private Pane game_pane;
  @FXML private static Character character;
  @FXML private Pane outer_pane;
  @FXML private Label chat_toggle_btn;
  @FXML private Pane aiCharacterPane;
  @FXML private Pane chatPane;
  @FXML private ImageView speechBubble;
  @FXML private ScrollPane bubbleTextPane;
  @FXML private Label bubbleText;
  @FXML private ListView<Label> chat;
  @FXML private TextField chatInput;
  @FXML private Pane interact_pane;

  Text bubbleChatText = new Text("text");

  private static MainGame instance;

  @FXML private static Pane initialised_game_pane;
  private static Pane initialised_interact_pane;

  public void initialize() throws IOException {

    GameState.mainGame = this;
    System.out.println(1);
    initialised_game_pane = game_pane;
    initialised_interact_pane = interact_pane;

    addOverlay("room1", true);

    // Helper.setBooksInRoom1();
    instance = this;
    // setting up bubble chat
    bubbleChatText.wrappingWidthProperty().bind(bubbleTextPane.minWidthProperty());
    bubbleTextPane.setFitToWidth(true);
    bubbleTextPane.setContent(bubbleChatText);

    disableInteractPane();

    // addChat("test");
  }

  public static void addOverlay(String roomN, boolean isRoom) throws IOException {
    Region room1 = (Region) FXMLLoader.load(App.class.getResource("/fxml/" + roomN + ".fxml"));
    room1.setScaleShape(true);

    if (isRoom) character = (Character) room1.lookup("#character");

    Pane backgroundBlur = new Pane();
    backgroundBlur.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
    backgroundBlur.setOnMouseClicked(
        e -> {
          removeOverlay(false);
        });

    initialised_game_pane
        .getChildren()
        .add(initialised_game_pane.getChildren().size() - 2, backgroundBlur);
    initialised_game_pane.getChildren().add(initialised_game_pane.getChildren().size() - 2, room1);
  }

  public static MainGame getInstance() {
    return instance;
  }

  public static void removeOverlay(boolean alsoRooms) {
    int sub = 0;
    if (alsoRooms) sub = 2;
    if (initialised_game_pane.getChildren().size() > 4 - sub) {
      initialised_game_pane
          .getChildren()
          .remove(initialised_game_pane.getChildren().size() - 1 - 2);
      initialised_game_pane
          .getChildren()
          .remove(initialised_game_pane.getChildren().size() - 1 - 2);
      initialised_game_pane.requestFocus();
    }
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
    } else if (letter.equals("ESCAPE")) {
      removeOverlay(false);
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
      chatPane.setOpacity(0.7);
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

  @FXML
  private void keyPressedChatInput(KeyEvent ke) throws ApiProxyException {
    if (ke.getCode().equals(KeyCode.ENTER)) {
      addChat("You: " + chatInput.getText(), false);
      chatInput.setDisable(true);
      GameState.eleanorAi.runGpt(
          "The user has send this message: '"
              + chatInput.getText()
              + "'. Reply as a normal human in 1 or 2 sentences. If the user asks, you can give"
              + " hints to previous riddles, and every hint needs to have the character % before"
              + " the hint. Do not reveal the answer even if the user asks for it.",
          (res) -> {
            Platform.runLater(
                () -> {
                  addChat(res, true);
                  chatInput.setDisable(false);
                });
          });

      ;
      chatInput.setText("");
      outer_pane.requestFocus();
    }
  }

  public void addChat(String text, boolean isEleanor) {

    // adding to bubble
    bubbleChatText.setText(text);
    bubbleTextPane.setContent(bubbleChatText);

    if (chatPane.isDisable()) {
      speechBubble.setVisible(true);
      bubbleTextPane.setVisible(true);
    }

    // adding to chatbox
    String chatPrefix = isEleanor ? "Eleanor: " : "";
    Label label = new Label(chatPrefix + text);
    label.setWrapText(true);
    label.getStyleClass().add("chat-text");
    // label.setMaxWidth(500);
    // label.setMaxWidth(chat.getWidth() - 20);
    label.prefWidthProperty().bind(chat.widthProperty().subtract(50));
    label.setBorder(null);

    List<Label> items = chat.getItems();
    int index = items.size();
    items.add(label);
    chat.scrollTo(index);
  }

  public static void enableInteractPane() {
    initialised_interact_pane.setDisable(false);
    initialised_interact_pane.setOpacity(1);
  }

  public static void disableInteractPane() {
    initialised_interact_pane.setDisable(true);
    initialised_interact_pane.setOpacity(0);
  }

  private void clickHeader() {
    BookShelfController.returnBook();
  }
}
