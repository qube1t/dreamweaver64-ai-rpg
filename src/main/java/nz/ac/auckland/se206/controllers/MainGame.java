package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import nz.ac.auckland.se206.components.Character;

public class MainGame {

  @FXML private Pane game_pane;
  @FXML private Character character;
  @FXML private Pane outer_pane;

  public void initialize() throws IOException {
    // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/room.fxml"));
    // fxmlLoader.setController(new RoomController());

    System.out.println(1);
    Region room1 = (Region) FXMLLoader.load(getClass().getResource("/fxml/room.fxml"));
    room1.setScaleShape(true);
    character = (Character) room1.lookup("#character");

    // game_pane
    //     .widthProperty()
    //     .addListener(
    //         (obsWidth, oldW, newW) -> {
    //           if (oldW != newW) {
    //             // int sca
    //           }
    //         });

    // game_pane.prefWidthProperty().bind(outer_pane.widthProperty());
    // game_pane.prefHeightProperty().bind(outer_pane.heightProperty());

    game_pane.getChildren().add(room1);

    // System.out.println(
    //     ((Pane) ((Pane) game_pane.getChildren().get(0)).getChildren().get(1)).getChildren());
    System.out.println(character);
    //     game_pane
    //         .sceneProperty()
    //         .addListener(
    //             (observableScene, oldScene, newScene) -> {
    //               if (oldScene == null && newScene != null) {
    //                 System.out.println(game_pane.getScene().getWidth());
    //               }
    //             });
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
