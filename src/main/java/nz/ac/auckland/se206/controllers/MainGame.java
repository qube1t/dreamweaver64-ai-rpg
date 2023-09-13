package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.components.Character;

public class MainGame {

  @FXML private Pane game_pane;
  @FXML private static Character character;
  @FXML private Pane outer_pane;
  @FXML private static Pane initialised_game_pane;

  public void initialize() throws IOException {

    System.out.println(1);
    initialised_game_pane = game_pane;
    setGameRoom("room1");

    System.out.println(character);
  }

  public static void setGameRoom(String roomN) throws IOException {
    Region room1 = (Region) FXMLLoader.load(App.class.getResource("/fxml/" + roomN + ".fxml"));
    room1.setScaleShape(true);
    character = (Character) room1.lookup("#character");

    // if (initialised_game_pane.getChildren().size() == 0) {
    //   initialised_game_pane.getChildren().set(0, room1);
    //   // System.out.println(initialised_game_pane.getChildren().size());
    // } else
    initialised_game_pane.getChildren().add(room1);
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
    } else if (letter.equals("A")) {
      character.setAction(1);
    }
    if (letter.equals("S")) {
      character.setAction(2);
    } else if (letter.equals("D")) {
      character.setAction(3);
    } else if (letter.equals("ESCAPE")) {
      if (initialised_game_pane.getChildren().size() > 1)
        initialised_game_pane.getChildren().remove(initialised_game_pane.getChildren().size() - 1);
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

  static double divideByNumber(Number n1, Number n2) {
    return Double.parseDouble(n1.toString()) / (Double.parseDouble(n2.toString()));
  }
}
