package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.Helper;
import nz.ac.auckland.se206.components.Character;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom1;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom2;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class Room2Controller {

  private static boolean gptInit;
  private static ImageView imgEndStRoom2;

  /** Set the end image when the time is up. */
  public static void initializeMap() {
    imgEndStRoom2.setImage(new Image("/images/rooms/room2/end.gif"));
  }

  /** Reset the GPT for room 2. */
  public static void resetGptRoom2() {
    gptInit = false;
  }

  @FXML
  private ImageView imgEnd;

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
  private ImageView box1BlockImg;
  @FXML
  private ImageView box2BlockImg;
  @FXML
  private ImageView box3BlockImg;
  @FXML
  private ImageView box4BlockImg;
  @FXML
  private ImageView box5BlockImg;
  @FXML
  private ImageView pirateLoaderImg;
  @FXML
  private ImageView leftDoorLoaderImg;
  @FXML
  private ImageView rightDoorLoaderImg;

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
  private Rectangle leftDoorBtn;
  @FXML
  private Rectangle rightDoorBtn;

  @FXML
  private Pane piratePane;
  @FXML
  private Label gptResponse;

  private ArrayList<Rectangle> obsts;
  private Rectangle[] treasureBoxes;
  private ImageView[] imgBoxes;
  private Boolean hasKeyRemoved = false;
  private Boolean wrongMsgPrinted = false;
  private AudioClip seaAmbiance;
  private ArrayList<Rectangle> boxs;

  /**
   * Initializes the room 2, it is called when the room loads.
   *
   * @throws ApiProxyException
   */
  public void initialize() throws ApiProxyException {

    if (boxs == null) {
      boxs = new ArrayList<Rectangle>(Arrays.asList(box1, box2, box3, box4, box5));
    }
    imgEndStRoom2 = imgEnd;

    // set the obstacles in the room2
    this.obsts = new ArrayList<Rectangle>(
        Arrays.asList(
            rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8, rect9, rect10, rect11,
            rect12, rect13, rect14, rect15, rect16, rect17, rect19, rect20, rect21, rect22,
            rect23, rect24, rect25, rect26, rect27, rect28, rect29, rect30, rect31, rect32,
            rect33, rect34));

    character.enableMobility(obsts, interactablePane.getChildren());

    if (!gptInit) {
      initGpt();
      gptInit = true;
    } else {
      MainGameController.enableInteractPane();
      Helper.enableAccessToItem(pirate, pirateLoaderImg);
      Helper.enableAccessToItem(leftDoorBtn, leftDoorLoaderImg);
      Helper.enableAccessToItem(rightDoorBtn, rightDoorLoaderImg);
    }

    interactablePane.setOnDragOver(event -> {
      double x = event.getX();
      double y = event.getY();
      System.out.println("Dragged over to pirate");
      if (event.getDragboard().hasImage() &&
          (pirate.getBoundsInParent().contains(x, y) ||
              boxs.stream().anyMatch(box -> box.getBoundsInParent().contains(x, y)))) {
        event.acceptTransferModes(TransferMode.ANY);
      }
      event.consume();
    });

    interactablePane.setOnDragDropped(event -> {
      double x = event.getX();
      double y = event.getY();
      System.out.println("drop to pirate");
      System.out.println("DRAGGING" + MainGameController.getImageSet().getId());
      if (event.getDragboard().hasImage()) {
        if (pirate.getBoundsInParent().contains(x, y) &&
            MainGameController.getImageSet().getId().equals("book")) {
          if (GameState.isBookFound) {
            try {
              tradeCorrectBook();
            } catch (ApiProxyException e) {
              e.printStackTrace();
            }
          } else {
            try {
              tradeWrongBook();
            } catch (ApiProxyException e) {
              e.printStackTrace();
            }
          }
        } else {
          for (int i = 0; i < boxs.size(); i++) {
            if (boxs.get(i).getBoundsInParent().contains(x, y)) {
              try {
                getRandomBox(i + 1);
              } catch (IOException | ApiProxyException e) {
                e.printStackTrace();
              }
            }
          }
        }
      }
      event.setDropCompleted(true);
      event.consume();
    });

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

    treasureBoxes = new Rectangle[] { box1, box2, box3, box4, box5 };
    imgBoxes = new ImageView[] { box1BlockImg, box2BlockImg, box3BlockImg, box4BlockImg,
        box5BlockImg };

    if (GameState.isBoxKeyFound) {
      book.setVisible(true);
      boxKey.setImage(null);
      for (int i = 0; i < treasureBoxes.length; i++) {
        Helper.enableAccessToItem(treasureBoxes[i], imgBoxes[i]);
      }
    }

    if (GameState.currentBox == -1) {
      Helper.changeTreasureBox(GameState.currentBox, -1);
    }

    if (!GameState.isMuted) {
      seaAmbiance = new AudioClip(
          (new Media(App.class.getResource("/sounds/oceanWavesSound.mp3").toString()))
              .getSource());
      seaAmbiance.setCycleCount(AudioClip.INDEFINITE);
      seaAmbiance.setVolume(.25);
      seaAmbiance.play();
      GameState.soundFx.add(seaAmbiance);
    }

    imgEndStRoom2 = imgEnd;
    if (GameState.tenSecondsLeft) {
      initializeMap();
    }
  }

  /**
   * Initialize the GPT.
   * 
   * @throws ApiProxyException
   */
  private void initGpt() throws ApiProxyException {
    MainGameController.enableInteractPane();

    GameState.eleanorAi.runGpt(GptPromptEngineeringRoom2.room2WelcomeMessage(),
        (str) -> {
          Helper.enableAccessToItem(leftDoorBtn, leftDoorLoaderImg);
          Helper.enableAccessToItem(rightDoorBtn, rightDoorLoaderImg);
        });

    // get riddle from GPT
    GameState.eleanorAi.runGpt(
        GptPromptEngineeringRoom1.getRiddleForPirate(GameState.trueBook),
        (str) -> {
          List<String> pirateDialogue = Helper.getTextBetweenChar(str, "^");
          if (pirateDialogue.size() > 0) {
            GameState.pirateRiddle = pirateDialogue.get(0).replaceAll("\"", "");
          }
        });

    // get the pirate response about wrong answer from GPT
    GameState.eleanorAi.runGpt(
        GptPromptEngineeringRoom2.getPirateWrongResponse(),
        (str2) -> {
          List<String> pirateDialogue = Helper.getTextBetweenChar(str2, "^");
          if (pirateDialogue.size() > 0) {
            GameState.pirateWrongResponse = str2.replaceAll("^", "");
            Helper.enableAccessToItem(pirate, pirateLoaderImg);
          }
        });

    // get the pirate response about correct answer from GPT
    GameState.eleanorAi.runGpt(
        GptPromptEngineeringRoom2.getPirateRightResponse(),
        (str1) -> {
          List<String> pirateDialogue = Helper.getTextBetweenChar(str1, "^");
          if (pirateDialogue.size() > 0) {
            GameState.pirateRightResponse = str1.replaceAll("^", "");
          }
        });

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
   * Get the riddle from pirate.
   * 
   * @param event the mouse event
   * @throws IOException
   * @throws ApiProxyException
   */
  @FXML
  private void getTradeWithPirate(MouseEvent event) throws IOException, ApiProxyException {
    if (GameState.takenBook == null) {
      GameState.eleanorAi.runGpt(
          "User update: The pirate has asked the riddle to the user, but has not been solved."
              + " You can give hints if the user asks. No reply is required");
      displayPirateResponse(GameState.pirateRiddle);
    } else if (!GameState.isBookFound) {
      if (!wrongMsgPrinted) {
        wrongMsgPrinted = true;
        tradeWrongBook();
      } else {
        wrongMsgPrinted = false;
        displayPirateResponse(GameState.pirateRiddle);
      }
    } else {
      tradeCorrectBook();
    }
  }

  /**
   * Trade the wrong book.
   * 
   * @throws ApiProxyException
   */
  private void tradeWrongBook() throws ApiProxyException {
    GameState.eleanorAi.runGpt(
        "User update: The user has not solved the book riddle yet. No reply is required");
    if (GameState.pirateWrongResponse != null) {
      displayPirateResponse(GameState.pirateWrongResponse);
    }
  }

  /**
   * Unlock the treasure box.
   * 
   * @throws ApiProxyException
   */
  private void tradeCorrectBook() throws ApiProxyException {
    GameState.eleanorAi.runGpt(
        "User update: The user has solved the book riddle. They have received the key."
            + " To find the treasure box, they needs to look at the radar."
            + " You can give hints if the user asks. No reply is required");

    // if the player get the correct book, the player can trade with pirate
    for (int i = 0; i < treasureBoxes.length; i++) {
      Helper.enableAccessToItem(treasureBoxes[i], imgBoxes[i]);
    }
    boxKey.setVisible(false);
    book.setVisible(true);
    if (GameState.pirateRightResponse != null) {
      displayPirateResponse(GameState.pirateRightResponse);
    }

    // add key image to the inventory
    if (!GameState.isBoxKeyFound) {
      Image keyImage = new Image("/images/key.png");
      MainGameController.removeObtainedItem("book");
      MainGameController.addObtainedItem(keyImage, "key");
    }
    GameState.isBoxKeyFound = true;
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
        if (!hasKeyRemoved) {
          MainGameController.removeObtainedItem("key");
          hasKeyRemoved = true;
        }
        MainGameController.addOverlay("treasure_box", false);
      } else {
        flashBoxes();
        Helper.changeTreasureBox(GameState.currentBox, numOfBox);
      }
    }
  }

  /**
   * Flash the treasure box when the player clicks the wrong box.
   */
  private void flashBoxes() {
    for (Rectangle box : treasureBoxes) {
      box.setVisible(true);
      box.setOpacity(0);
    }

    new Thread(() -> {
      try {
        for (int flashCount = 0; flashCount < 6; flashCount++) {
          Platform.runLater(() -> {
            for (Rectangle box : treasureBoxes) {
              if (box.getOpacity() == 0) {
                box.setOpacity(1);
              } else {
                box.setOpacity(0);
              }
            }
          });
          Thread.sleep(250);
        }

        // After flashing, make the boxes invisible again
        Platform.runLater(() -> {
          for (Rectangle box : treasureBoxes) {
            box.setVisible(false);
            box.setOpacity(1);
          }
        });

      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();
  }

  /**
   * Display the bubble with the message from GPT.
   * 
   * @param result the message from GPT
   */
  private void displayPirateResponse(String result) {
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
    GameState.eleanorAi.runGpt(
        "User update: User has moved from the pirate ship to "
            + "his childhood home. No reply is required");
    if (!GameState.isMuted)
      seaAmbiance.stop();
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
        "User update: User has moved from the pirate ship to his "
            + "workplace the ATC tower. No reply is required");
    if (!GameState.isMuted)
      seaAmbiance.stop();
  }
}
