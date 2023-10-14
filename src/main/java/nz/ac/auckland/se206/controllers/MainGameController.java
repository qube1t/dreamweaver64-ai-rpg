package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;

import javafx.scene.Node;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.Helper;
import nz.ac.auckland.se206.ObtainedItemsWithId;
import nz.ac.auckland.se206.components.Character;
import nz.ac.auckland.se206.components.CustomImageSet;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom1;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class MainGameController {

  @FXML
  private static Character character;
  private static Pane interactablePane;

  @FXML
  private static Pane initialisedGamePane;
  @FXML
  private static Pane initialisedInventoryPane;
  @FXML
  private static Pane outPane;

  private static MainGameController instance;
  private static Thread timeLimitThread;

  private static List<ObtainedItemsWithId> obtainedItems;
  private static Pane initialisedInteractPane;

  private static Label timerInitiated;
  private static Label hintInitiated;
  @FXML
  private Label statusLbl;
  private static Label statusLblInitiated;
  private static ImageView item1Initiated;
  private static ImageView item2Initiated;
  private static ImageView item3Initiated;
  private static ImageView item4Initiated;
  private static ImageView item5Initiated;
  private static ImageView item6Initiated;
  private static CustomImageSet imageSetDragging;

  public static MainGameController getInstance() {
    return instance;
  }

  private static void updateInventoryUi() {
    // updating inventory ui with initialised images

    List<ImageView> inventoryItems = List.of(
        item1Initiated,
        item2Initiated,
        item3Initiated,
        item4Initiated,
        item5Initiated,
        item6Initiated);

    for (int i = 0; i < inventoryItems.size(); i++) {

      ImageView item = inventoryItems.get(i);
      int id = i;

      item.setOnDragDetected(
          event -> {
            Image originalImage = item.getImage();
            ImageView originalImageView = new ImageView(originalImage);

            originalImageView.setFitHeight(40); // Set the desired height
            originalImageView.setFitWidth(40);

            imageSetDragging = new CustomImageSet(originalImage, obtainedItems.get(id).getId());
            System.out.println("dragging " + obtainedItems.get(id).getId());

            // Create a new ClipboardContent with the custom drag image
            ClipboardContent content = new ClipboardContent();
            Dragboard dragboard = item.startDragAndDrop(TransferMode.ANY);
            // Store the custom image set in the Dragboard
            content.putImage(originalImageView.snapshot(null, null));
            dragboard.setContent(content);
            GameState.currentDraggedItemId = obtainedItems.get(id).getId();
            event.consume();
          });

      if (obtainedItems.size() > i) {
        inventoryItems.get(i).setImage(obtainedItems.get(i).getImage());

        inventoryItems.get(i).setId(obtainedItems.get(i).getId());
        inventoryItems.get(i).setFitWidth(35);
        inventoryItems.get(i).setPreserveRatio(true);
        inventoryItems.get(i).setSmooth(true);
      } else {
        inventoryItems.get(i).setImage(null);
      }
    }

  }

  private static void setMainCursor() {
    // Set the cursor to custom cursor
    Image cursor = new Image("/images/mainCursor.png", 16,
        27, true, true);
    Cursor custom = new ImageCursor(cursor);

    initialisedInventoryPane.setCursor(custom);
    initialisedInteractPane.setCursor(custom);
    outPane.setCursor(custom);
  }

  public static CustomImageSet getImageSet() {
    return imageSetDragging;
  }

  public static void addObtainedItem(Image itemImage, String itemId) {
    // adding obtained item to inventory
    obtainedItems.add(new ObtainedItemsWithId(itemImage, itemId));

    updateInventoryUi();
  }

  public static void removeObtainedItem(String itemId) {
    // removing obtained item from inventory
    for (int i = 0; i < obtainedItems.size(); i++) {
      if (obtainedItems.get(i).getId().equals(itemId)) {
        obtainedItems.remove(i);
        break;
      }
    }
    updateInventoryUi();
  }

  public static void disableInteractPane() {
    // fade out interact pane
    initialisedInteractPane.setDisable(true);
    initialisedInteractPane.setOpacity(0);
    if (!GameState.isMuted) {
      GameState.doorSound.setCycleCount(1);
      GameState.doorSound.setVolume(.15);
      GameState.doorSound.play();
      GameState.soundFx.add(GameState.backgroundMusic);
    }
  }

  public static void enableInteractPane() {
    // fade in interact pane
    initialisedInteractPane.setVisible(true);
    initialisedInteractPane.setDisable(false);
    FadeTransition ft = new FadeTransition();
    ft.setDuration(javafx.util.Duration.millis(500));
    ft.setNode(initialisedInteractPane);
    ft.setFromValue(0);
    ft.setToValue(1);
    ft.play();
  }

  public static void addOverlay(String roomN, boolean isRoom) throws IOException {
    // adds overlay to the game pane
    Region room1 = (Region) FXMLLoader.load(App.class.getResource("/fxml/" + roomN + ".fxml"));
    room1.setScaleShape(true);

    if (isRoom) {
      if (character != null) {
        character.endAnimation(true);
      }
      character = (Character) room1.lookup("#character");
      interactablePane = (Pane) room1.lookup("#interactablePane");

      // setting up interactable pane
      for (Node node : interactablePane.getChildren()) {
        node.hoverProperty()
            .addListener(
                (observable, oldValue, newValue) -> {
                  if (newValue) {
                    statusLblInitiated.setText(node.getAccessibleText());
                  } else {
                    statusLblInitiated.setText("");
                  }
                });
      }
    }

    // adding blur background
    Pane backgroundBlur = new Pane();
    backgroundBlur.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
    backgroundBlur.setOnMouseClicked(
        e -> {
          removeOverlay(false);
        });

    // adding both to game pane
    initialisedGamePane
        .getChildren()
        .add(initialisedGamePane.getChildren().size() - 4, backgroundBlur);
    initialisedGamePane.getChildren().add(initialisedGamePane.getChildren().size() - 4, room1);
    setMainCursor();
  }

  public static void removeOverlay(boolean alsoRooms) {
    GameState.isMachineOpen = false;
    // removing overlay
    int sub = 0;
    if (alsoRooms) {
      // removing rooms as well
      sub = 2;
      character.endAnimation(alsoRooms);
    }
    if (initialisedGamePane.getChildren().size() > 6 - sub) {
      initialisedGamePane
          .getChildren()
          .remove(initialisedGamePane.getChildren().size() - 1 - 4);
      initialisedGamePane
          .getChildren()
          .remove(initialisedGamePane.getChildren().size() - 1 - 4);
      initialisedGamePane.requestFocus();
    }
    setMainCursor();
  }

  @FXML
  private Pane targetItem;

  @FXML
  private Pane gamePane;
  @FXML
  private Pane outerPane;
  @FXML
  private Label timer;
  @FXML
  private Label hintCount;
  @FXML
  private ImageView item1;
  @FXML
  private ImageView item2;
  @FXML
  private ImageView item3;
  @FXML
  private ImageView item4;
  @FXML
  private ImageView item5;
  @FXML
  private ImageView item6;
  @FXML
  private Label chatToggleBtn;
  @FXML
  private Pane aiCharacterPane;
  @FXML
  private Pane chatPane;
  @FXML
  private ImageView speechBubble;
  @FXML
  private ScrollPane bubbleTextPane;
  @FXML
  private Label bubbleText;
  @FXML
  private ListView<Label> chat;
  @FXML
  private TextField chatInput;
  @FXML
  private Pane interactPane;
  @FXML
  private Pane inventoryPane;

  private Text bubbleChatText = new Text("text");
  private String prevLetter;

  public void initialize() throws IOException {
    obtainedItems = new ArrayList<>();
    chatPane.setMouseTransparent(true);
    bubbleTextPane.setMouseTransparent(true);
    aiCharacterPane.setMouseTransparent(true);

    GameState.mainGame = this;

    timerInitiated = timer;
    hintInitiated = hintCount;
    statusLblInitiated = statusLbl;
    item1Initiated = item1;
    item2Initiated = item2;
    item3Initiated = item3;
    item4Initiated = item4;
    item5Initiated = item5;
    item6Initiated = item6;

    System.out.println(1);
    initialisedGamePane = gamePane;
    initialisedInteractPane = interactPane;
    initialisedInventoryPane = interactPane;
    outPane = outerPane;

    // adding instruction overlay to the bottom of the outer pane
    outerPane
        .getChildren()
        .add(0, (Region) FXMLLoader.load(App.class.getResource("/fxml/instruction_load.fxml")));

    addOverlay("room1", true);

    getTimeLimit();
    setHintCount();

    instance = this;
    // setting up bubble chat
    bubbleChatText.wrappingWidthProperty().bind(bubbleTextPane.minWidthProperty());
    bubbleTextPane.setFitToWidth(true);
    bubbleTextPane.setContent(bubbleChatText);
    setMainCursor();

    if (!GameState.isMuted) {
      GameState.backgroundMusic.setCycleCount(3);
      GameState.backgroundMusic.setVolume(.15);
      GameState.backgroundMusic.play();
      GameState.soundFx.add(GameState.backgroundMusic);
    }
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    // on key pressed
    String letter = event.getCode().toString();
    System.out.println("key " + event.getCode() + " pressed");
    if (!letter.equals(prevLetter)) {
      character.endAnimation(false);
    }
    prevLetter = letter;

    // character movement
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
      // overlay removal
      removeOverlay(false);
    }

    // move after animating as it will change direction of character
    if (letter.equals("D") || letter.equals("A") || letter.equals("W") || letter.equals("S")) {
      // if (!character.isAnimating()) {
      character.startAnimation();
      // character.startMovement();
      // }
      // character.move();
    }
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {
    System.out.println("key " + event.getCode() + " released");
    String letter = event.getCode().toString();
    if (letter.equals("D") || letter.equals("A") || letter.equals("W") || letter.equals("S")) {
      if (letter.equals(prevLetter)) {
        character.endAnimation(true);
      }
      // character.endAnimation();
      // character.stopMovement();
    }
  }

  @FXML
  private void toggleChat() {
    System.out.println("toggle chat");
    if (chatPane.isDisable()) {
      // chatPane is hidden -> show it
      chatPane.setDisable(false);
      chatPane.setOpacity(0.7);
      chatToggleBtn.setText("Hide Chat");

      speechBubble.setVisible(false);
      bubbleTextPane.setVisible(false);
      chatPane.setMouseTransparent(false);
      bubbleTextPane.setMouseTransparent(true);
      aiCharacterPane.setMouseTransparent(true);
    } else {
      // hide chatPane
      chatPane.setDisable(true);
      chatPane.setOpacity(0);
      chatToggleBtn.setText("Show Chat");
      outerPane.requestFocus();
      chatPane.setMouseTransparent(true);
    }
    setMainCursor();
  }

  @FXML
  private void keyPressedChatInput(KeyEvent ke) throws ApiProxyException {
    if (ke.getCode().equals(KeyCode.ENTER)) {
      // adding to chat when entered
      addChat("You: " + chatInput.getText(), false);
      chatInput.setDisable(true);

      // getting response from eleanor
      GameState.eleanorAi.runGpt(
          GptPromptEngineeringRoom1.getChatMessageFromUser(chatInput.getText()),
          (res) -> {
            Platform.runLater(
                () -> {
                  // counting hints
                  int noOfHints = Helper.countOccurences(res, "~");
                  String msg = res.replaceAll("~", "");
                  System.out.println("hints contained" + noOfHints);

                  if (noOfHints > 0) {
                    if (GameState.hintsRemaining - 1 >= 0) {
                      GameState.hintsRemaining -= 1;
                      System.out.println("Hints remaining: " + GameState.hintsRemaining);
                    } else {
                      GameState.hintsRemaining = 0;
                    }
                  }
                  // adding chat to chatbox entirely as assumed to be reply to user without *
                  addChat(msg, true);
                  chatInput.setDisable(false);
                });
          });
      chatInput.setText("");
      outerPane.requestFocus();
    }
  }

  public void addChat(String text, boolean isEleanor) {
    System.out.println("add chat");

    // adding to bubble
    bubbleChatText.setText(text);
    bubbleTextPane.setContent(bubbleChatText);

    if (chatPane.isDisable()) {
      speechBubble.setVisible(true);
      bubbleTextPane.setVisible(true);

      bubbleTextPane.setMouseTransparent(false);
      aiCharacterPane.setMouseTransparent(false);
    }

    // adding to chatbox
    String chatPrefix = isEleanor ? "Eleanor: " : "";
    Label label = new Label(chatPrefix + text);
    label.setWrapText(true);
    label.getStyleClass().add("chat-text");
    label.prefWidthProperty().bind(chat.widthProperty().subtract(50));
    label.setBorder(null);

    List<Label> items = chat.getItems();
    int index = items.size();
    items.add(label);
    chat.scrollTo(index + 1);
  }

  @FXML
  private void clickedGamePane() {
    // hide chat bubble
    speechBubble.setVisible(false);
    bubbleTextPane.setVisible(false);

    bubbleTextPane.setMouseTransparent(true);
    aiCharacterPane.setMouseTransparent(true);
  }

  public void clickGamePane() {
    // hide chat bubble
    speechBubble.setVisible(false);
    bubbleTextPane.setVisible(false);

    bubbleTextPane.setMouseTransparent(true);
    aiCharacterPane.setMouseTransparent(true);
  }

  public void getTimeLimit() {
    // getting time limit from game mode
    System.out.println("start game");
    switch (GameState.gameMode[1]) {
      case "2 minutes":
        timerInitiated.setText("120");
        setTimeLimit(120);
        break;
      case "4 minutes":
        timerInitiated.setText("240");
        setTimeLimit(240);
        break;
      case "6 minutes":
        timerInitiated.setText("360");
        setTimeLimit(360);
        break;
      default:
        timerInitiated.setText("120");
        setTimeLimit(120);
        break;
    }
  }

  /**
   * Sets the time limit.
   *
   * @param timeLimit
   */
  private void setTimeLimit(int timeLimit) {
    // setting time limit thread to count down from time limit.
    Task<Void> task = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        // countdown from time limit to 0 using for loop.
        for (int currentTime = timeLimit; currentTime >= 0; currentTime--) {
          if (GameState.winTheGame || GameState.timeLimitReached) {
            break;
          }
          int time = currentTime;
          int minutes = time / 60;
          int seconds = time % 60;
          String formattedTime = String.format("%02d:%02d", minutes, seconds);
          Platform.runLater(
              () -> {
                timerInitiated.setText(formattedTime);
                InstructionsLoadController.setTime(formattedTime);
                // update hint count every cycle
                updateHintCount();
                if (time == 10) {
                  GameState.tenSecondsLeft = true;
                  Room1Controller.initializeMap();
                  Room2Controller.initializeMap();
                  Room3Controller.initializeMap();
                }
              });
          try {
            // sleep for 1 second before next cycle.
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            return null;
          }
        }
        Platform.runLater(
            () -> {
              try {
                // handle time limit reached event when time limit is reached.
                handleTimeLimitReached();
              } catch (IOException e) {
                e.printStackTrace();
              }
            });
        return null;
      }

    };
    timeLimitThread = new Thread(task);
    timeLimitThread.setDaemon(true);
    timeLimitThread.start();
  }

  /**
   * Handles the time limit reached event.
   *
   * @throws IOException
   */
  private void handleTimeLimitReached() throws IOException {
    // time limit reached
    GameState.timeLimitReached = true;
    if (!GameState.winTheGame) {
      App.setRoot("end_menu");
    }
  }

  private void setHintCount() {
    // setting hint count
    switch (GameState.gameMode[0]) {
      case "EASY":
        hintInitiated.setText("Hint: \u221E");
        break;
      case "MEDIUM":
        hintInitiated.setText("Hint: 5");
        break;
      case "HARD":
        hintInitiated.setText("Hint: None");
        break;
      default:
        hintInitiated.setText("Hint: \u221E");
        break;
    }
  }

  /** Updates the hint count. */
  private void updateHintCount() {
    if (GameState.gameMode[0].equals("MEDIUM")) {
      hintInitiated.setText("Hint: " + Integer.toString(GameState.hintsRemaining));
    } else {
      return;
    }
  }

}
