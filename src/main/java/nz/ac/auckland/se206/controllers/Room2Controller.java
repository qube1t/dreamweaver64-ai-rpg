package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.Helper;
import nz.ac.auckland.se206.components.Character;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom2;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class Room2Controller {

  @FXML private Rectangle box1;
  @FXML private Rectangle box2;
  @FXML private Rectangle box3;
  @FXML private Rectangle box4;
  @FXML private Rectangle box5;
  @FXML private Rectangle pirate;
  @FXML private Rectangle doorToRoom1;
  @FXML private ImageView boxKey;
  @FXML private ImageView speech_bubble;
  @FXML private Label gptResponse;
  @FXML private Pane interactablePane;
  @FXML private Character character;
  @FXML private ScrollPane speechBubbleScrollPane;
  @FXML
  private Rectangle rect1,
      rect2,
      rect3,
      rect4,
      rect5,
      rect6,
      rect7,
      rect8,
      rect9,
      rect10,
      rect11,
      rect12,
      rect13,
      rect14,
      rect15,
      rect16,
      rect17,
      rect18,
      rect19,
      rect20,
      rect21,
      rect22,
      rect23,
      rect24,
      rect25,
      rect26,
      rect27,
      rect28,
      rect29,
      rect30,
      rect31,
      rect32,
      rect33;

  private int wrongBoxClicked = 0;
  private int correctBoxClicked = 0;
  private static boolean gptInit;

  /** Initializes the room view, it is called when the room loads. 
   * @throws ApiProxyException */
  public void initialize() throws ApiProxyException {
    ArrayList<Rectangle> obsts =
        new ArrayList<Rectangle>(
            Arrays.asList(
                rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8, rect9, rect10, rect11,
                rect12, rect13, rect14, rect15, rect16, rect17, rect19, rect20, rect21, rect22,
                rect23, rect24, rect25, rect26, rect27, rect28, rect29, rect30, rect31, rect32,
                rect33));

    character.enableMobility(obsts, interactablePane.getChildren());
    character.setLayoutX(60);
    character.setLayoutY(250);

    speechBubbleScrollPane = (ScrollPane) interactablePane.lookup("#speechBubbleScrollPane");
    if (speechBubbleScrollPane != null) {
      speechBubbleScrollPane.setContent(gptResponse);      
      speechBubbleScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
      speechBubbleScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    }


    if (!gptInit) {
      initGpt();
      gptInit = true;
    }else{

    MainGame.enableInteractPane();
    }
    
    GameState.prevRoom = 2;
    speechBubbleScrollPane.setVisible(false);
    speech_bubble.setVisible(false);
    gptResponse.setVisible(false);
  
    if (GameState.isBoxKeyFound) {
      boxKey.setVisible(false);
    }
  }

  private void initGpt() throws ApiProxyException {
    GameState.eleanorAi.runGpt(GptPromptEngineeringRoom2.generateFinalEncrypted(), s -> {
      List<String> msg = Helper.getTextBetweenChar(s, "+");
    if (msg.size() > 0) GameState.encryptedFinalMsg = msg.get(0); 
    else GameState.encryptedFinalMsg = s;
      Platform.runLater(() -> MainGame.enableInteractPane());
    });
    
    GameState.eleanorAi.runGpt(GptPromptEngineeringRoom2.generateFinalUnencrypted(), s -> {
      List<String> msg = Helper.getTextBetweenChar(s, "+");
    if (msg.size() > 0) GameState.finalMsg = msg.get(0); 
    });


    // MainGame.enableInteractPane();

  }

  @FXML
  public void onGetTrade(MouseEvent event) throws IOException, ApiProxyException {
    if (!GameState.isBookFound && GameState.pirateRiddle != null) { 
      System.out.println("Pirate clicked"); 
      speechBubbleScrollPane.setVisible(true);
      gptResponse.setText(GameState.pirateRiddle);    
      speech_bubble.setVisible(true);
      gptResponse.setVisible(true);
    } else if (GameState.isBookFound && !GameState.isBoxKeyFound) {
      GameState.isBoxKeyFound = true;
      boxKey.setVisible(false);
      GameState.eleanorAi.runGpt(
          "User update: User has found the treasure box key. No reply is needed for this message.");
      System.out.println("Box key found");
      Image keyImage = new Image("/images/key.png");

      MainGame.removeObtainedItem("book");
      MainGame.addObtainedItem(keyImage, "treasure box key");

      System.out.println("Box key obtained");
    }
  }

  /**
   * Get the random treasure box and put the riddle or password in it
   *
   * @param numOfBox
   * @throws IOException
   */
  private void getRandomBox(int numOfBox) throws IOException {
    int boxLocation = GameState.currentBox;
    System.out.println("Number of treasure box: " + boxLocation);
    if (GameState.isBoxKeyFound) {
      box1.setDisable(false);
      box2.setDisable(false);
      box3.setDisable(false);
      box4.setDisable(false);
      box5.setDisable(false);

      if (numOfBox == boxLocation) {
        MainGame.addOverlay("treasure_box", false);
        if (correctBoxClicked == 0) {
          try {
            GameState.eleanorAi.runGpt(
                "User update: User has found the correct treasure box. No reply is needed for this"
                    + " message.");
          } catch (ApiProxyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          correctBoxClicked++;
        }
      } else {
        // write this sentance in chat box
        if (wrongBoxClicked == 0) {
          try {
            GameState.eleanorAi.runGpt(
                "User update: User has found the wrong treasure box. No reply is needed for this"
                    + " message.");
          } catch (ApiProxyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        wrongBoxClicked++;
      }
    }
  }

  /**
   * Handles the click event on the treasure box 1.
   *
   * @param event the mouse event
   * @throws IOException
   */
  @FXML
  public void onClickBox1(MouseEvent event) throws IOException {
    System.out.println("First treasure box clicked");
    getRandomBox(1);
  }

  /**
   * Handles the click event on the treasure box 2.
   *
   * @param event the mouse event
   * @throws IOException
   */
  @FXML
  public void onClickBox2(MouseEvent event) throws IOException {
    System.out.println("Second teasure box clicked");
    getRandomBox(2);
  }

  /**
   * Handles the click event on the treasure box 3.
   *
   * @param event the mouse event
   * @throws IOException
   */
  @FXML
  public void onClickBox3(MouseEvent event) throws IOException {
    System.out.println("Third treasure box clicked");
    getRandomBox(3);
  }

  /**
   * Handles the click event on the treasure box 4.
   *
   * @param event the mouse event
   * @throws IOException
   */
  @FXML
  public void onClickBox4(MouseEvent event) throws IOException {
    System.out.println("Fourth treasure box clicked");
    getRandomBox(4);
  }

  /**
   * Handles the click event on the treasure box 5.
   *
   * @param event the mouse event
   * @throws IOException
   */
  @FXML
  public void onClickBox5(MouseEvent event) throws IOException {
    System.out.println("Fifth treasure box clicked");
    getRandomBox(5);
  }

  /**
   * Handles the click event on the door.
   *
   * @param event the mouse event
   * @throws IOException
   */
  @FXML
  public void onClickDoor(MouseEvent event) throws IOException {
    MainGame.removeOverlay(true);
    MainGame.addOverlay("room1", true);
  }
}
