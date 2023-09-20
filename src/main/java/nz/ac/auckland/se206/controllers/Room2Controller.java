package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.GptEngine;
import nz.ac.auckland.se206.Helper;
import nz.ac.auckland.se206.components.Character;
import nz.ac.auckland.se206.gpt.ChatMessage;
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

  private static boolean gptInit = false;
  private static int gptStage = 0;

  private int wrongBoxClicked = 0;
  private int correctBoxClicked = 0;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {
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

    speech_bubble = new ImageView();
    gptResponse = new Label();
    gptResponse.setText("Pirate : Find the key to open the treasure box");
  }

  @FXML
  public void onGetTrade(MouseEvent event) throws IOException {
    if (gptInit) {
      speech_bubble.setVisible(false);
      gptResponse.setVisible(false);
    }
    if (GameState.isBookFound && !GameState.isBoxKeyFound) {
      GameState.isBoxKeyFound = true;
      boxKey.setVisible(false);
      try {
        GptEngine.runGpt(
            new ChatMessage("user", GptPromptEngineeringRoom2.foundBoxKey()),
            (st) -> {
              List<String> findBoxKey = Helper.getTextBetweenChar(st, "%");
            });
      } catch (ApiProxyException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      System.out.println("Box key found");
      Image keyImage = new Image("/images/key.png");
      MainGame.addObtainedItem(keyImage);
      System.out.println("Box key obtained");
    } else {
      // write this sentance in chat box or pirate's speech bubble
      System.out.println("Find the item to trade with pirate to open the boxes");
    }
  }

  /**
   * Get the random treasure box and put the riddle or password in it
   *
   * @param numOfBox
   * @throws IOException
   */
  private void getRandomBox(int numOfBox) throws IOException {
    if (gptInit) {
      speech_bubble.setVisible(false);
      gptResponse.setVisible(false);
    }
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
            GptEngine.runGpt(
                new ChatMessage("user", GptPromptEngineeringRoom2.clickCorrectBox()),
                (st) -> {
                  List<String> clickCorrectBox = Helper.getTextBetweenChar(st, "/");
                });
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
            GptEngine.runGpt(
                new ChatMessage("user", GptPromptEngineeringRoom2.clickWrongBox()),
                (st) -> {
                  List<String> clickWrongBox = Helper.getTextBetweenChar(st, "/");
                });
          } catch (ApiProxyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        wrongBoxClicked++;
      }
      box1.setDisable(true);
      box2.setDisable(true);
      box3.setDisable(true);
      box4.setDisable(true);
      box5.setDisable(true);
    } else {
      // write this sentance in chat box or pirate's speech bubble
      System.out.println("Find the item to trade with pirate");
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
