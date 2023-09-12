package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.components.Character;

public class Room2Controller {

  @FXML private Rectangle box1;
  @FXML private Rectangle box2;
  @FXML private Rectangle box3;
  @FXML private Rectangle box4;
  @FXML private Rectangle box5;

  @FXML private Rectangle doorToRoom1;
  @FXML private ImageView pirate;

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
      rect28, rect29, rect30, rect31, rect32, rect33;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {

    ArrayList<Rectangle> obsts = new ArrayList<Rectangle>();
    obsts.add(0, rect1);
    obsts.add(1, rect2);
    obsts.add(2, rect3);
    obsts.add(3, rect4);
    obsts.add(4, rect5);
    obsts.add(5, rect6);
    obsts.add(6, rect7);
    obsts.add(7, rect8);
    obsts.add(8, rect9);
    obsts.add(9, rect10);
    obsts.add(10, rect11);
    obsts.add(11, rect12);
    obsts.add(12, rect13);
    obsts.add(13, rect14);
    obsts.add(14, rect15);
    obsts.add(15, rect16);
    obsts.add(16, rect17);
    obsts.add(17, rect18);
    obsts.add(18, rect19);
    obsts.add(19, rect20);
    obsts.add(20, rect21);
    obsts.add(21, rect22);
    obsts.add(22, rect23);
    obsts.add(23, rect24);
    obsts.add(24, rect25);
    obsts.add(25, rect26);
    obsts.add(26, rect27);
    obsts.add(27, rect28);
    obsts.add(28, rect29);
    obsts.add(29, rect30);
    obsts.add(30, rect31);
    obsts.add(31, rect32);
    obsts.add(32, rect33);

    // Initialization code goes here
    character.enableMobility(obsts);
    character.setLayoutX(60);
    character.setLayoutY(250);

    box1.disableProperty().setValue(true);
    box2.disableProperty().setValue(true);
    box3.disableProperty().setValue(true);
    box4.disableProperty().setValue(true);
    box5.disableProperty().setValue(true);
  }

  @FXML public void onTradeKey() {
    if (GameState.isItemFound) {
      GameState.isBoxKeyFound = true;
    }
  }

  /**
   * Get the random treasure box and put the riddle or password in it
   *
   * @param numOfBox
   * @throws IOException
   */
  private void getRandomBox(int numOfBox) throws IOException {
    int treasure = new Random().nextInt(5-1+1) + 1;
    if (GameState.isBoxKeyFound) {
      box1.disableProperty().setValue(false);
      box2.disableProperty().setValue(false);
      box3.disableProperty().setValue(false);
      box4.disableProperty().setValue(false);
      box5.disableProperty().setValue(false);
    } else {
      System.out.println("Find the item to trade with pirate");
      return;
    }
    if (numOfBox == treasure) {
      System.out.println("Correct treasure box clicked");
      App.setRoot("treasurebox");
    } else {
      System.out.println("Wrong treasure box clicked. Find correct one");
      box1.disableProperty().setValue(true);
      box2.disableProperty().setValue(true);
      box3.disableProperty().setValue(true);
      box4.disableProperty().setValue(true);
      box5.disableProperty().setValue(true);
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
    System.out.println("Treasure box1 clicked");
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
    System.out.println("Treasure box2 clicked");
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
    System.out.println("Treasure box3 clicked");
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
    System.out.println("Treasure box4 clicked");
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
    System.out.println("Treasure box5 clicked");
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
    App.setRoot("room1");
  }
}
