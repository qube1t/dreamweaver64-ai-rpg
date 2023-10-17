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
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.Helper;
import nz.ac.auckland.se206.components.Character;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom1;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom2;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/**
 * This class is the controller for Room 2 in the game. It handles the
 * initialization of the room, resetting the GPT for the room, setting
 * the end image when the time is up, and enabling the drag and drop
 * functionality for the pirate and treasure boxes. It also contains the
 * obstacles and interactable items in the room, such as the character, boxes,
 * and doors.
 */
public class Room2Controller {

  private static boolean gptInit;

  /** Reset the GPT for room 2. */
  public static void resetGptRoom2() {
    gptInit = false;
  }

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
  private ImageView[] imgBoxes;
  private Boolean hasKeyRemoved = false;
  private Boolean wrongMsgPrinted = false;
  private AudioClip seaAmbiance;
  private ArrayList<Rectangle> treasureBoxes;

  /**
   * Initializes the Room2Controller by setting up the obstacles, enabling
   * mobility for the character, initializing the GPT if the player has not
   * entered the room 2, enabling access to items, setting up drag and drop
   * functionality for the pirate and treasure boxes, setting the location
   * of the character depending on the previous room, and playing the sea
   * ambiance.
   * If the player has the key, the player can open the treasure box.
   * If 10 seconds left, sets the map image on room2.
   *
   * @throws ApiProxyException if there is an issue with the API proxy
   */
  public void initialize() throws ApiProxyException {
    // created treasure box array list and block image array
    treasureBoxes = new ArrayList<Rectangle>(
        Arrays.asList(box1, box2, box3, box4, box5));
    imgBoxes = new ImageView[] { box1BlockImg, box2BlockImg, box3BlockImg, box4BlockImg,
        box5BlockImg };

    // set the obstacles in the room2
    this.obsts = new ArrayList<Rectangle>(
        Arrays.asList(
            rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8, rect9, rect10, rect11,
            rect12, rect13, rect14, rect15, rect16, rect17, rect19, rect20, rect21, rect22,
            rect23, rect24, rect25, rect26, rect27, rect28, rect29, rect30, rect31, rect32,
            rect33, rect34));

    character.enableMobility(obsts, interactablePane.getChildren());

    GameState.mainGame.clickGamePane();

    // if the player has not entered the room 2, initialize the GPT
    if (!gptInit) {
      initGpt();

      GameState.isRoom2FirstEntered = true;
    } else {
      MainGameController.enableInteractPane();
      Helper.enableAccessToItem(rightDoorBtn, rightDoorLoaderImg);
      Helper.enableAccessToItem(leftDoorBtn, leftDoorLoaderImg);
      Helper.enableAccessToItem(pirate, pirateLoaderImg);
    }

    // set the drag and drop functionality for the pirate and treasure boxes
    interactablePane.setOnDragOver(event -> {
      double x = event.getX();
      double y = event.getY();
      if (event.getDragboard().hasImage()) {
        if (pirate.getBoundsInParent().contains(x, y)) {
          event.acceptTransferModes(TransferMode.ANY);
        } else if (treasureBoxes.stream().anyMatch(box -> box.getBoundsInParent().contains(x, y))) {
          event.acceptTransferModes(TransferMode.ANY);
        }
      }
      event.consume();
    });

    interactablePane.setOnDragDropped(event -> {
      double x = event.getX();
      double y = event.getY();
      System.out.println("DRAGGING " + MainGameController.getImageSet().getId());
      if (event.getDragboard().hasImage()) {
        if (pirate.getBoundsInParent().contains(x, y)) {
          if (MainGameController.getImageSet().getId().equals("book")) {
            if (GameState.pirateRiddle != null) {
              try {
                tradeWithPirate();
              } catch (ApiProxyException e) {
                e.printStackTrace();
              }
            }
          }
        } else {
          if (GameState.isBoxKeyFound) {
            for (int i = 0; i < treasureBoxes.size(); i++) {
              if (treasureBoxes.get(i).getBoundsInParent().contains(x, y)
                  && MainGameController.getImageSet().getId().equals("key")) {
                try {
                  getRandomBox(i + 1);
                } catch (IOException | ApiProxyException e) {
                  e.printStackTrace();
                }
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

    // if the player has the key, the player can open the treasure box
    if (GameState.isBoxKeyFound) {
      book.setVisible(true);
      boxKey.setImage(null);
      for (int i = 0; i < treasureBoxes.size(); i++) {
        Helper.enableAccessToItem(treasureBoxes.get(i), imgBoxes[i]);
      }
    }

    if (GameState.isEncryptedMessageFound) {
      pirate.setDisable(true);
      piratePane.setVisible(false);
    }

    if (GameState.currentBox == -1) {
      Helper.changeTreasureBox(GameState.currentBox, -1);
    }

    // play the sea ambiance
    if (!GameState.isMuted) {
      seaAmbiance = new AudioClip(
          (new Media(App.class.getResource("/sounds/oceanWavesSound.mp3").toString()))
              .getSource());
      seaAmbiance.setCycleCount(AudioClip.INDEFINITE);
      seaAmbiance.setVolume(.25);
      seaAmbiance.play();
      GameState.soundFx.add(seaAmbiance);
    }

  }

  /**
   * Initializes the GPT (Generative Pre-trained Transformer) for Room 2.
   * Runs GPT to get the welcome message, riddle for pirate, encrypted message
   * and unencrypted message. Enables access to items and sets pirate response.
   *
   * @throws ApiProxyException if there is an error with the API proxy
   */
  private void initGpt() throws ApiProxyException {
    gptInit = true;
    MainGameController.enableInteractPane();
    Helper.enableAccessToItem(leftDoorBtn, leftDoorLoaderImg);
    Helper.enableAccessToItem(rightDoorBtn, rightDoorLoaderImg);

    // get riddle from GPT
    GameState.isPirateRiddleLoaded = true;
    GameState.eleanorAi.runGpt(
        GptPromptEngineeringRoom1.getRiddleForPirate(GameState.trueBook),
        (str) -> {
          List<String> pirateDialogue = Helper.getTextBetweenChar(str, "^", false);
          if (pirateDialogue.size() > 0) {
            GameState.pirateRiddle = pirateDialogue.get(0).replaceAll("\"", "");
            Helper.enableAccessToItem(pirate, pirateLoaderImg);
          }
        });
    GameState.eleanorAi.runGpt(GptPromptEngineeringRoom2.room2WelcomeMessage(),
        (str) -> {
          GameState.isRoom2GptDone = true;
        });

    setPirateResponse();
    // get the encrypted message from GPT
    GameState.eleanorAi2.runGpt(
        GptPromptEngineeringRoom2.generateFinalEncrypted(),
        s -> {
          List<String> msg = Helper.getTextBetweenChar(s, "+", false);
          if (msg.size() > 0) {
            GameState.encryptedFinalMsg = msg.get(0);
          } else {
            GameState.encryptedFinalMsg = s;
          }
        });

    // get the encrypted message from GPT
    GameState.eleanorAi2.runGpt(
        GptPromptEngineeringRoom2.generateFinalUnencrypted(),
        s -> {
          List<String> msg = Helper.getTextBetweenChar(s, "+", false);
          if (msg.size() > 0) {
            GameState.finalMsg = msg.get(0);
          }
        });

  }

  /**
   * Sets the pirate response for both correct and wrong answers by retrieving
   * them from GPT and updating the GameState. Uses Platform.runLater to ensure
   * the UI is updated on the JavaFX Application Thread.
   *
   * @throws ApiProxyException if there is an error with the API proxy
   */
  private void setPirateResponse() throws ApiProxyException {
    // get the pirate response about wrong answer from GPT
    GameState.eleanorAi2.runGpt(
        GptPromptEngineeringRoom2.getPirateWrongResponse(),
        (str2) -> {
          Platform.runLater(
              () -> {
                List<String> pirateDialogue = Helper.getTextBetweenChar(str2, "^", false);
                if (pirateDialogue.size() > 0) {
                  GameState.pirateWrongResponse = pirateDialogue.get(0).replaceAll("^", "");
                }
              });
        });

    // get the pirate response about correct answer from GPT
    GameState.eleanorAi2.runGpt(
        GptPromptEngineeringRoom2.getPirateRightResponse(),
        (str1) -> {
          Platform.runLater(
              () -> {
                List<String> pirateDialogue = Helper.getTextBetweenChar(str1, "^", false);
                if (pirateDialogue.size() > 0) {
                  GameState.pirateRightResponse = str1.replaceAll("^", "");
                }
              });
        });
  }

  /**
   * This method is called when the player clicks on the "Trade with Pirate"
   * button in Room 2. If the player does not have the book, the player needs
   * to solve the riddle first. If the player has the book, the method tries
   * to trade with the pirate. If the player has the wrong book, the method
   * displays an error message. If the player has the correct book, the method
   * initiates the trade.
   *
   * @param event The mouse event that triggered the method call.
   * @throws IOException       If there is an error with the input/output.
   * @throws ApiProxyException If there is an error with the API proxy.
   */
  @FXML
  private void getTradeWithPirate(MouseEvent event) throws IOException, ApiProxyException {
    // if the player does not have the book, the player need to solve the riddle
    // first
    if (GameState.takenBook == null) {
      // no book in inventory
      GameState.eleanorAi.runGpt(
          "User update: The pirate has asked the riddle to the user, but has not been solved."
              + " You can give hints if the user asks. No reply is required");

      if (GameState.pirateRiddle != null) {
        displayPirateResponse(GameState.pirateRiddle);
      }
      // if the player has the book, try to trade with the pirate
    } else if (GameState.takenBook != null) {
      if (!GameState.isBookFound) {
        if (!wrongMsgPrinted) {
          wrongMsgPrinted = true;
          tradeWrongBook();
        } else {
          wrongMsgPrinted = false;
          if (GameState.pirateRiddle != null) {
            displayPirateResponse(GameState.pirateRiddle);
          }
        }
      } else if (GameState.isBookFound) {
        tradeCorrectBook();
        pirate.setDisable(true);
      }
    }
  }

  /**
   * This method attempts to trade with the pirate if the player has the book.
   * If the player does not have the book, it will call tradeWrongBook() method to
   * display a message. If the player has the correct book, it will call
   * tradeCorrectBook() method to initiate the trade. If the player has already
   * displayed the wrong message, it will display the pirate's response to the
   * riddle.
   *
   * @throws ApiProxyException if there is an issue with the API proxy
   */
  private void tradeWithPirate() throws ApiProxyException {
    // if the player has the book, try to trade with the pirate
    if (!GameState.isBookFound) {
      if (!wrongMsgPrinted) {
        wrongMsgPrinted = true;
        tradeWrongBook();
      } else {
        wrongMsgPrinted = false;
        if (GameState.pirateRiddle != null) {
          displayPirateResponse(GameState.pirateRiddle);
        }
      }
    } else if (GameState.isBookFound) {
      tradeCorrectBook();
      pirate.setDisable(true);
    }
  }

  /**
   * Trades the wrong book with the pirate and displays the pirate's response if
   * available.
   *
   * @throws ApiProxyException if an error occurs while communicating with the
   *                           API.
   */
  private void tradeWrongBook() throws ApiProxyException {
    if (GameState.pirateWrongResponse != null) {
      displayPirateResponse(GameState.pirateWrongResponse);
    }
  }

  /**
   * This method is called when the player has solved the book riddle and obtained
   * the correct book. It enables access to the treasure boxes, hides the box key,
   * displays the correct book, and displays the pirate's response if available.
   * If the box key has not been found before, it adds the key image to the
   * inventory
   * and removes the book image.
   *
   * @throws ApiProxyException if there is an issue with the API proxy
   */
  private void tradeCorrectBook() throws ApiProxyException {
    GameState.eleanorAi.runGpt(
        "User update: The user has solved the book riddle. They have received the key."
            + " To find the treasure box, they needs to look at the radar."
            + " You can give hints if the user asks. No reply is required");

    // if the player get the correct book, the player can trade with pirate
    for (int i = 0; i < treasureBoxes.size(); i++) {
      Helper.enableAccessToItem(treasureBoxes.get(i), imgBoxes[i]);
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
   * Gets a random treasure box and checks if the player has the key to open it.
   * If the player has the key, the treasure box is opened. Otherwise, the
   * treasure
   * boxes are flashed and the current treasure box is changed.
   *
   * @param numOfBox the number of the treasure box to be opened
   * @throws IOException       if an I/O error occurs
   * @throws ApiProxyException if an API proxy error occurs
   */
  private void getRandomBox(int numOfBox) throws IOException, ApiProxyException {
    int boxLocation = GameState.currentBox;
    for (int i = 0; i < treasureBoxes.size(); i++) {
      treasureBoxes.get(i).setDisable(true);
    }
    // if the player has the key, the player can open the treasure box
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
   * Makes the treasure boxes visible and flashes them for a short period of time.
   * After flashing, the boxes are made invisible again and access to them is
   * enabled.
   */
  private void flashBoxes() {
    // Make the treasure boxes visible
    for (Rectangle box : treasureBoxes) {
      box.setVisible(true);
      box.setOpacity(0);
    }

    // Flash the treasure boxes
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
          for (int i = 0; i < treasureBoxes.size(); i++) {
            treasureBoxes.get(i).setVisible(false);
            treasureBoxes.get(i).setOpacity(1);
            Helper.enableAccessToItem(treasureBoxes.get(i), imgBoxes[i]);
          }
        });

      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();
  }

  /**
   * Displays the result of the pirate response on the GUI.
   *
   * @param result The result of the pirate response to be displayed.
   */
  private void displayPirateResponse(String result) {
    gptResponse.setText(result);
    piratePane.setVisible(true);
    GameState.mainGame.addChat("Pirate: " + result, false);
  }

  /**
   * Handles the event when the first treasure box is clicked.
   * Calls the getRandomBox method with parameter 1.
   *
   * @param event The MouseEvent that triggered this method.
   * @throws IOException       If an I/O error occurs.
   * @throws ApiProxyException If an error occurs while communicating with the
   *                           API.
   */
  @FXML
  private void onClickBox1(MouseEvent event) throws IOException, ApiProxyException {
    System.out.println("First treasure box clicked");
    getRandomBox(1);
  }

  /**
   * Handles the event when the first treasure box is clicked.
   * Calls the getRandomBox method with parameter 2.
   *
   * @param event The MouseEvent that triggered this method.
   * @throws IOException       If an I/O error occurs.
   * @throws ApiProxyException If an error occurs while communicating with the
   *                           API.
   */
  @FXML
  private void onClickBox2(MouseEvent event) throws IOException, ApiProxyException {
    System.out.println("Second teasure box clicked");
    getRandomBox(2);
  }

  /**
   * Handles the event when the third treasure box is clicked.
   * Calls the getRandomBox method with the box number as 3.
   *
   * @param event The MouseEvent that triggered this method.
   * @throws IOException       If an I/O error occurs.
   * @throws ApiProxyException If an error occurs while communicating with the
   *                           API.
   */
  @FXML
  private void onClickBox3(MouseEvent event) throws IOException, ApiProxyException {
    System.out.println("Third treasure box clicked");
    getRandomBox(3);
  }

  /**
   * Handles the event when the fourth treasure box is clicked.
   * Calls the getRandomBox method with the parameter 4.
   *
   * @param event The mouse event that triggered this method.
   * @throws IOException       If an I/O error occurs.
   * @throws ApiProxyException If an error occurs while communicating with the
   *                           API.
   */
  @FXML
  private void onClickBox4(MouseEvent event) throws IOException, ApiProxyException {
    System.out.println("Fourth treasure box clicked");
    getRandomBox(4);
  }

  /**
   * Handles the event when the fifth treasure box is clicked.
   * Calls the getRandomBox method with the box number as an argument.
   *
   * @param event The MouseEvent that triggered this method.
   * @throws IOException       If an I/O error occurs.
   * @throws ApiProxyException If an error occurs while communicating with the
   *                           API.
   */
  @FXML
  private void onClickBox5(MouseEvent event) throws IOException, ApiProxyException {
    System.out.println("Fifth treasure box clicked");
    getRandomBox(5);
  }

  /**
   * Handles the event when the user clicks on the button to open Room 1.
   * This method sets the text for the instructions, disables the interact pane
   * for transition, stops the sea ambiance sound if it is playing, runs the GPT
   * model to update the user's status, removes the current overlay and adds the
   * overlay for Room 1.
   *
   * @param event The mouse event that triggered this method.
   * @throws IOException       If an I/O error occurs.
   * @throws ApiProxyException If an error occurs while communicating with the
   *                           API.
   */
  @FXML
  private void onOpenRoom1(MouseEvent event) throws IOException, ApiProxyException {
    // go to the right room
    InstructionsLoadController.setText();
    // disable interact pane for transition
    MainGameController.disableInteractPane();
    if (!GameState.isMuted) {
      if (seaAmbiance != null) {
        seaAmbiance.stop();
      }
    }
    GameState.eleanorAi.runGpt(
        "User update: User has moved from the pirate ship to "
            + "his childhood home. No reply is required");
    MainGameController.removeOverlay(true);
    MainGameController.addOverlay("room1", true);
  }

  /**
   * Handles the event when the user clicks on the door to Room 3.
   * Loads Room 3, disables the interact pane for transition, removes overlay,
   * adds
   * overlay for Room 3, runs GPT-3 to update Eleanor AI, and stops the sea
   * ambiance
   * sound if it is playing.
   *
   * @param event The mouse event that triggered this method.
   * @throws IOException       If there is an error loading Room 3.
   * @throws ApiProxyException If there is an error running GPT-3 to update
   *                           Eleanor AI.
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
    if (!GameState.isMuted) {
      if (seaAmbiance != null) {
        seaAmbiance.stop();
      }
    }
  }
}
