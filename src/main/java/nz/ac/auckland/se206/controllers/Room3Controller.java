package nz.ac.auckland.se206.controllers;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.components.Character;

public class Room3Controller {

  @FXML private Character character;
  @FXML private Rectangle ob1;

  public void initialize() {
    ArrayList<Rectangle> obsts = new ArrayList<Rectangle>();
    obsts.add(0, ob1);
    // Initialization code goes here
    character.enableMobility(obsts);
    character.setLayoutX(250);
    character.setLayoutY(250);
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyPressed(KeyEvent event) {
    // System.out.println("key " + event.getCode() + " pressed");

    String letter = event.getCode().toString();

    if (letter.equals("W")) {
      character.setAction(0);
      System.out.println("W pressed");
    } else if (letter.equals("A")) {
      System.out.println("A pressed");
      character.setAction(1);
    }
    if (letter.equals("S")) {
      System.out.println("S pressed");
      character.setAction(2);
    } else if (letter.equals("D")) {
      character.setAction(3);
    }

    // move after animating as it will change direction of character
    if (letter.equals("D") || letter.equals("A") || letter.equals("W") || letter.equals("S")) {
      if (!character.isAnimating()) character.startAnimation();
      character.move();
    }
  }

  /**
   * Handles the key released event.
   *
   * @param event the key event
   */
  @FXML
  public void onKeyReleased(KeyEvent event) {
    // System.out.println("key " + event.getCode() + " released");
    String letter = event.getCode().toString();
    if (letter.equals("D") || letter.equals("A") || letter.equals("W") || letter.equals("S")) {
      character.endAnimation();
    }
  }
}
