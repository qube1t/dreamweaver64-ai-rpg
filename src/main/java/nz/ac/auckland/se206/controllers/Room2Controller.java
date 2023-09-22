package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.Helper;
import nz.ac.auckland.se206.components.Character;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom1;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom2;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class Room2Controller {

  private static boolean gptInit;

  @FXML
  private Rectangle rect1;
  @FXML
  private Rectangle rect2;
  @FXML
  private Rectangle rect3;
  @FXML
  private Rectangle rect4;
  @FXML
  private Rectangle rect5;
  @FXML
  private Rectangle rect6;
  @FXML
  private Rectangle rect7;
  @FXML
  private Rectangle rect8;
  @FXML
  private Rectangle rect9;
  @FXML
  private Rectangle rect10;
  @FXML
  private Rectangle rect11;
  @FXML
  private Rectangle rect12;
  @FXML
  private Rectangle rect13;
  @FXML
  private Rectangle rect14;
  @FXML
  private Rectangle rect15;
  @FXML
  private Rectangle rect16;
  @FXML
  private Rectangle rect17;
  @FXML
  private Rectangle rect18;
  @FXML
  private Rectangle rect19;
  @FXML
  private Rectangle rect20;
  @FXML
  private Rectangle rect21;
  @FXML
  private Rectangle rect22;
  @FXML
  private Rectangle rect23;
  @FXML
  private Rectangle rect24;
  @FXML
  private Rectangle rect25;
  @FXML
  private Rectangle rect26;
  @FXML
  private Rectangle rect27;
  @FXML
  private Rectangle rect28;
  @FXML
  private Rectangle rect29;
  @FXML
  private Rectangle rect30;
  @FXML
  private Rectangle rect31;
  @FXML
  private Rectangle rect32;
  @FXML
  private Rectangle rect33;
  @FXML
  private Rectangle rect34;
  @FXML
  private ImageView boxKey;
  @FXML
  private ImageView book;
  @FXML
  private Character character;
  @FXML
  private Pane interactablePane;
  @FXML
  private Rectangle box1;
  @FXML
  private Rectangle box2;
  @FXML
  private Rectangle box3;
  @FXML
  private Rectangle box4;
  @FXML
  private Rectangle box5;
  @FXML
  private Rectangle pirate;
  @FXML
  private Rectangle doorToRoom1;
  @FXML
  private Rectangle doorToRoom3;
  @FXML
  private Pane piratePane;
  @FXML
  private ImageView pirateSpeech;
  @FXML
  private ScrollPane speechBubbleScrollPane;
  @FXML
  private Label gptResponse;

  /**
   * Initializes the room 2, it is called when the room loads.
   *
   * @throws ApiProxyException
   */
  public void initialize() throws ApiProxyException {
    // set the obstacles in the room2
    ArrayList<Rectangle> obsts = new ArrayList<Rectangle>(
        Arrays.asList(
            rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8, rect9, rect10, rect11,
            rect12, rect13, rect14, rect15, rect16, rect17, rect19, rect20, rect21, rect22,
            rect23, rect24, rect25, rect26, rect27, rect28, rect29, rect30, rect31, rect32,
            rect33, rect34));

    character.enableMobility(obsts, interactablePane.getChildren());

    // set the location of the character depending on the previous room
    switch (GameState.prevRoom) {
      case 1:
        character.setLayoutX(70);
        character.setLayoutY(250);
        break;
      case 3:
        character.setLayoutX(486);
        character.setLayoutY(242);
        break;
      default:
        character.setLayoutX(70);
        character.setLayoutY(250);
    }

    GameState.prevRoom = 2;

    piratePane.setVisible(false);

    // set pane inside the speech bubble
    speechBubbleScrollPane = (ScrollPane) interactablePane.lookup("#speechBubbleScrollPane");
    if (speechBubbleScrollPane != null) {
      speechBubbleScrollPane.setContent(gptResponse);
      speechBubbleScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
      speechBubbleScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }

    // print the welcome message when first enter the room 2
    if (!GameState.isRoom2FirstEntered) {
      GameState.isRoom2FirstEntered = true;
      GameState.eleanorAi.runGpt(
          GptPromptEngineeringRoom2.room2WelcomeMessage(),
          (result) -> {
            MainGameController.enableInteractPane();
          });
    } else {
      MainGameController.enableInteractPane();
    }

    if (!gptInit) {
      initGpt();
      gptInit = true;
    }

    if (GameState.isBoxKeyFound) {
      boxKey.setVisible(false);
      book.setVisible(true);
    }

    if (!GameState.isBoxKeyFound) {
      box1.setDisable(true);
      box2.setDisable(true);
      box3.setDisable(true);
      box4.setDisable(true);
      box5.setDisable(true);
    }
  }

  /**
   * Initialize the GPT.
   * 
   * @throws ApiProxyException
   */
  private void initGpt() throws ApiProxyException {
    // get the encrypted message from GPT
    GameState.eleanorAi.runGpt(
        GptPromptEngineeringRoom2.generateFinalEncrypted(),
        s -> {
          List<String> msg = Helper.getTextBetweenChar(s, "+");
          if (msg.size() > 0) {
            GameState.encryptedFinalMsg = msg.get(0);
          } else {
            GameState.encryptedFinalMsg = s;
          }
        });

  }

  /**
   * Handles the click event on the pirate.
   * 
   * @param event the mouse event
   * @throws IOException
   * @throws ApiProxyException
   */
  @FXML
  private void onGetTrade(MouseEvent event) throws IOException, ApiProxyException {
    if (!GameState.isBookFound && GameState.pirateRiddle != null) {

      GameState.eleanorAi.runGpt(
          "User update: The pirate has asked the riddle to the user, but has not been solved. You can give hints if the user asks. No reply is required");

      // if the player get wrong book, the message will be displayed
      if (GameState.takenBook != null) {
        GameState.eleanorAi.runGpt(
            GptPromptEngineeringRoom2.getPirateWrongResponse(),
            (result) -> {
              Platform.runLater(
                  () -> {
                    List<String> pirateDialogue = Helper.getTextBetweenChar(result, "^");
                    if (pirateDialogue.size() > 0) {
                      displayBubble(result.replace("^", ""));
                    }
                  });
            });
      } else {
        // if the player has not got any book, the message will be displayed
        gptResponse.setText(GameState.pirateRiddle);
        piratePane.setVisible(true);
      }
    } else if (GameState.isBookFound && !GameState.isBoxKeyFound) {
      GameState.eleanorAi.runGpt(
          "User update: The user has solved the book riddle. They have received the key. To find the treasure box, they needs to look at the radar. No reply is required");

      // if the player get the correct book, the player can trade with pirate
      GameState.isBoxKeyFound = true;
      box1.setDisable(false);
      box2.setDisable(false);
      box3.setDisable(false);
      box4.setDisable(false);
      box5.setDisable(false);
      boxKey.setVisible(false);
      book.setVisible(true);
      GameState.eleanorAi.runGpt(
          GptPromptEngineeringRoom2.getPirateRightResponse(),
          (result) -> {
            Platform.runLater(
                () -> {
                  List<String> pirateDialogue = Helper.getTextBetweenChar(result, "^");
                  if (pirateDialogue.size() > 0) {
                    displayBubble(result.replace("^", ""));
                  }
                });
          });
      // add key image to the inventory
      Image keyImage = new Image("/images/key.png");
      MainGameController.removeObtainedItem("book");
      MainGameController.addObtainedItem(keyImage, "treasure box key");
    }
  }

  /**
   * Get the random treasure box and handle the click event on the treasure box.
   *
   * @param numOfBox the location of the treasure box
   * @throws IOException
   * @throws ApiProxyException
   */
  private void getRandomBox(int numOfBox) throws IOException, ApiProxyException {
    int boxLocation = GameState.currentBox;
    System.out.println("Number of treasure box: " + boxLocation);
    if (GameState.isBoxKeyFound) {

      if (numOfBox == boxLocation) {
        MainGameController.addOverlay("treasure_box", false);
        GameState.eleanorAi.runGpt(
            GptPromptEngineeringRoom2.getPirateRightResponse(),
            (result) -> {
              Platform.runLater(
                  () -> {
                    List<String> pirateDialogue = Helper.getTextBetweenChar(result, "^");
                    if (pirateDialogue.size() > 0) {
                      displayBubble(result.replace("^", ""));
                    }
                  });
            });
      } else {
        // if the player has clicked the wrong box, the player will get the wrong
        // message
        GameState.eleanorAi.runGpt(
            GptPromptEngineeringRoom2.getPirateWrongResponse(),
            (result) -> {
              Platform.runLater(
                  () -> {
                    List<String> pirateDialogue = Helper.getTextBetweenChar(result, "^");
                    if (pirateDialogue.size() > 0) {
                      displayBubble(result.replace("^", ""));
                    }
                  });
            });
        Helper.changeTreasureBox(GameState.currentBox);
      }
    } else {
      // if the player has not got the key, the player will get the message
      box1.setDisable(true);
      box2.setDisable(true);
      box3.setDisable(true);
      box4.setDisable(true);
      box5.setDisable(true);

      GameState.eleanorAi.runGpt(
          GptPromptEngineeringRoom2.getPirateNoKeyResponse(),
          (result) -> {
            Platform.runLater(
                () -> {
                  List<String> pirateDialogue = Helper.getTextBetweenChar(result, "^");
                  if (pirateDialogue.size() > 0) {
                    displayBubble(result.replace("^", ""));
                  }
                });
          });
    }
  }

  /** Display the message from GPT. */
  private void displayBubble(String result) {
    gptResponse.setText(result);
    piratePane.setVisible(true);
  }

  /**
   * Handles the click event on the treasure box 1.
   *
   * @param event the mouse event
   * @throws IOException
   * @throws ApiProxyException
   */
  @FXML
  private void onClickBox1(MouseEvent event) throws IOException, ApiProxyException {
    System.out.println("First treasure box clicked");
    getRandomBox(1);
  }

  /**
   * Handles the click event on the treasure box 2.
   *
   * @param event the mouse event
   * @throws IOException
   * @throws ApiProxyException
   */
  @FXML
  private void onClickBox2(MouseEvent event) throws IOException, ApiProxyException {
    System.out.println("Second teasure box clicked");
    getRandomBox(2);
  }

  /**
   * Handles the click event on the treasure box 3.
   *
   * @param event the mouse event
   * @throws IOException
   * @throws ApiProxyException
   */
  @FXML
  private void onClickBox3(MouseEvent event) throws IOException, ApiProxyException {
    System.out.println("Third treasure box clicked");
    getRandomBox(3);
  }

  /**
   * Handles the click event on the treasure box 4.
   *
   * @param event the mouse event
   * @throws IOException
   * @throws ApiProxyException
   */
  @FXML
  private void onClickBox4(MouseEvent event) throws IOException, ApiProxyException {
    System.out.println("Fourth treasure box clicked");
    getRandomBox(4);
  }

  /**
   * Handles the click event on the treasure box 5.
   *
   * @param event the mouse event
   * @throws IOException
   * @throws ApiProxyException
   */
  @FXML
  private void onClickBox5(MouseEvent event) throws IOException, ApiProxyException {
    System.out.println("Fifth treasure box clicked");
    getRandomBox(5);
  }

  /**
   * Handles the click event on the door.
   *
   * @param event the mouse event
   * @throws IOException
   * @throws ApiProxyException
   */
  @FXML
  private void onOpenRoom1(MouseEvent event) throws IOException, ApiProxyException {
    // go to the right room
    InstructionsLoadController.setText();
    // disable interact pane for transition
    MainGameController.disableInteractPane();
    MainGameController.removeOverlay(true);
    MainGameController.addOverlay("room1", true);
    GameState.eleanorAi
        .runGpt("User update: User has moved from the pirate ship to his childhood home. No reply is required");
  }

  /**
   * Handles the click event on the door.
   *
   * @param event the mouse event
   * @throws IOException
   * @throws ApiProxyException
   */
  @FXML
  private void onOpenRoom3(MouseEvent event) throws IOException, ApiProxyException {
    // go to the right room
    InstructionsLoadController.setText();
    // disable interact pane for transition
    MainGameController.disableInteractPane();
    MainGameController.removeOverlay(true);
    MainGameController.addOverlay("room3", true);
    GameState.eleanorAi.runGpt(
        "User update: User has moved from the pirate ship to his workplace the ATC tower. No reply is required");
  }
}
