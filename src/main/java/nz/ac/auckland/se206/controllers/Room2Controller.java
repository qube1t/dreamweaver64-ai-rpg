package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.components.Character;

public class Room2Controller {

  @FXML private Rectangle box1;
  @FXML private Rectangle box2;
  @FXML private Rectangle box3;
  @FXML private Rectangle box4;
  @FXML private Rectangle box5;
  @FXML private Rectangle pirate;

  @FXML private Rectangle doorToRoom1;
  @FXML private ImageView boxKey;

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

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {

    ArrayList<Rectangle> obsts =
        new ArrayList<Rectangle>(
            Arrays.asList(
                rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8, rect9, rect10, rect11,
                rect12, rect13, rect14, rect15, rect16, rect17, rect19, rect20, rect21, rect22,
                rect23, rect24, rect25, rect26, rect27, rect28, rect29, rect30, rect31, rect32,
                rect33));

    character.enableMobility(obsts);
    character.setLayoutX(60);
    character.setLayoutY(250);
  }

  @FXML
  public void onGetTrade(MouseEvent event) throws IOException {
    if (GameState.isBookFound) {
      GameState.isBoxKeyFound = true;
      boxKey.setVisible(false);
      System.out.println("Box key found");
      // gottenBoxKey.setOpacity(1);
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
    // int noOfTreasure = (int) (Math.random() * 5) + 1;
    int boxLocation = GameState.currentBox;
    System.out.println("Number of treasure box: " + boxLocation);
    if (GameState.isBoxKeyFound) {
      box1.setDisable(false);
      box2.setDisable(false);
      box3.setDisable(false);
      box4.setDisable(false);
      box5.setDisable(false);
      if (numOfBox == boxLocation) {
        System.out.println("Correct treasure box clicked");
        MainGame.addOverlay("treasure_box", false);
      } else {
        // write this sentance in chat box
        System.out.println("Wrong treasure box clicked. Find correct one");
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
