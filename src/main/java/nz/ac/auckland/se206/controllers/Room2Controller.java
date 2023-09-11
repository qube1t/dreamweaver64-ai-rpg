package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;

public class Room2Controller {

  @FXML private Rectangle box1;
  @FXML private Rectangle box2;
  @FXML private Rectangle box3;
  @FXML private Rectangle box4;
  @FXML private Rectangle box5;
  @FXML private Rectangle box6;
  @FXML private Rectangle box7;
  @FXML private Rectangle box8;
  @FXML private Rectangle doorToRoom1;
  @FXML private ImageView foundKey;

  private int[] realTreasures;

  /** 
   * Initializes the room view, it is called when the room loads. 
   */
  public void initialize() {
    setRandomBox();
  }

  /**
   * Set the random treasure box
   */
  private void setRandomBox() {
    realTreasures = new Random().ints(1, 7).distinct().limit(2).toArray();
  }

  /**
   * Get the random treasure box and put the riddle or password in it
   * @param numOfBox
   * @throws IOException
   */
  private void getRandomBox(int numOfBox) throws IOException {
    if (numOfBox == realTreasures[0]) {
      if (!GameState.isTreasureFound) {
        //find the treasure
      } else {
        if (!GameState.isRiddleResolved) {
          App.setRoot("chat");
        } else {
          // App.setRoot("memory");
        }
      }
      
    } else if (numOfBox == realTreasures[1]) {
      App.setRoot("password");
      if (GameState.isKeyFound) {
        foundKey.setVisible(true);
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
    System.out.println("Treasure box1 clicked");
    getRandomBox(1);
    // GameState.attemptToOpenTreasure--;
    // checkAttempt();
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
    // GameState.attemptToOpenTreasure--;
    // checkAttempt();
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
    // GameState.attemptToOpenTreasure--;
    // checkAttempt();
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
    // GameState.attemptToOpenTreasure--;
    // checkAttempt();
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
    // GameState.attemptToOpenTreasure--;
    // checkAttempt();
  }

  /**
   * Handles the click event on the treasure box 6.
   *
   * @param event the mouse event
   * @throws IOException
   */
  @FXML
  public void onClickBox6(MouseEvent event) throws IOException {
    System.out.println("Treasure box6 clicked");
    getRandomBox(6);
    // GameState.attemptToOpenTreasure--;
    // checkAttempt();
  }

  /**
   * Handles the click event on the treasure box 7.
   *
   * @param event the mouse event
   * @throws IOException
   */
  @FXML
  public void onClickBox7(MouseEvent event) throws IOException {
    System.out.println("Treasure box7 clicked");
    getRandomBox(7);
    // GameState.attemptToOpenTreasure--;
    // checkAttempt();
  }

  /**
   * check how many times the player clicked the treasure box
   */
  //private void checkAttempt() {
  //  if (GameState.attemptToOpenTreasure == 4) {
  //    recL1.setVisible(true);
  //  } else if (GameState.attemptToOpenTreasure == 3) {
  //    recL2.setVisible(true);
  //  } else if (GameState.attemptToOpenTreasure == 2) {
  //    recL3.setVisible(true);
  //  } else if (GameState.attemptToOpenTreasure == 1) {
  //    recL4.setVisible(true);
  //  } else if (GameState.attemptToOpenTreasure == 0) {
  //    recL5.setVisible(true);
  //    recL51.setVisible(true);
  //    recL52.setVisible(true);
  //    recL53.setVisible(true);
  //    recL54.setVisible(true);
  //    recL55.setVisible(true);
  //  }
  // }

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
