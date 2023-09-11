package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.components.Character;

public class Room3SubController {

  @FXML private Character character;
  @FXML private Rectangle img1;
  @FXML private Rectangle img2;
  @FXML private Rectangle img3;
  @FXML private Rectangle img4;

  protected int currentSelection;

  public void initialize() {
    img1.setVisible(true);
    img2.setVisible(false);
    img3.setVisible(false);
    img4.setVisible(false);
    currentSelection = 1;
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void press(KeyEvent event) {
    // Handle arrow key presses
    switch (event.getCode()) {
      case LEFT:
        System.out.println("left");
        if (currentSelection == 1) {
        } else if (currentSelection == 2) {
          img2.setVisible(false);
          img1.setVisible(true);
          currentSelection = 1;
        } else if (currentSelection == 3) {
          img3.setVisible(false);
          img2.setVisible(true);
          currentSelection = 2;
        } else if (currentSelection == 4) {
          img4.setVisible(false);
          img3.setVisible(true);
          currentSelection = 3;
        }
        break;
      case RIGHT:
        System.out.println("right");
        if (currentSelection == 1) {
          img1.setVisible(false);
          img2.setVisible(true);
          currentSelection = 2;
        } else if (currentSelection == 2) {
          img2.setVisible(false);
          img3.setVisible(true);
          currentSelection = 3;
        } else if (currentSelection == 3) {
          img3.setVisible(false);
          img4.setVisible(true);
          currentSelection = 4;
        } else if (currentSelection == 4) {
          img4.setVisible(false);
          img1.setVisible(true);
          currentSelection = 1;
        }
        break;
      case ENTER:
        if (currentSelection == 1) {
          System.out.println("1");
        } else if (currentSelection == 2) {
          System.out.println("2");
        } else if (currentSelection == 3) {
          System.out.println("3");
        } else if (currentSelection == 4) {
          System.out.println("4");
        }
        break;
      default:
        break;
    }
  }
}
