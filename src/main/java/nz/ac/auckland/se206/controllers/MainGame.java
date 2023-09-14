package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.Helper;
import nz.ac.auckland.se206.components.Character;

public class MainGame {

  @FXML private Pane game_pane;
  @FXML private static Character character;
  @FXML private Pane outer_pane;

  @FXML private ImageView lastFlightPlan;
  @FXML private ImageView depBoard;
  private static MainGame instance;

  @FXML private static Pane initialised_game_pane;

  public void initialize() throws IOException {

    System.out.println(1);
    initialised_game_pane = game_pane;
    addOverlay("room1", true);
    Helper.setBooksInRoom1();
    instance = this;

    // lastFlightPlan.setVisible(false);
    // depBoard.setVisible(false);
    // System.out.println("plan");
  }

  public static void addOverlay(String roomN, boolean isRoom) throws IOException {
    Region room1 = (Region) FXMLLoader.load(App.class.getResource("/fxml/" + roomN + ".fxml"));
    room1.setScaleShape(true);

    if (isRoom) character = (Character) room1.lookup("#character");

    Pane backgroundBlur = new Pane();
    backgroundBlur.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
    backgroundBlur.setOnMouseClicked(
        e -> {
          removeOverlay();
        });
    initialised_game_pane.getChildren().add(backgroundBlur);
    initialised_game_pane.getChildren().add(room1);
  }

  public void fadeInFlightPlan() {
    lastFlightPlan.setVisible(true); // Set to visible before starting the animation

    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), lastFlightPlan);
    fadeTransition.setFromValue(0.0);
    fadeTransition.setToValue(1.0);
    fadeTransition.play();
  }

  public void fadeOutFlightPlan() {
    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), lastFlightPlan);
    fadeTransition.setFromValue(1.0);
    fadeTransition.setToValue(0.0);
    fadeTransition.play();
    fadeTransition.setOnFinished(
        event -> {
          lastFlightPlan.setVisible(false);
        });
  }

  public void fadeInDepBoard() {
    depBoard.setVisible(true); // Set to visible before starting the animation

    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), depBoard);
    fadeTransition.setFromValue(0.0);
    fadeTransition.setToValue(1.0);
    fadeTransition.play();
  }

  public void fadeOutDepBoard() {
    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), depBoard);
    fadeTransition.setFromValue(1.0);
    fadeTransition.setToValue(0.0);
    fadeTransition.play();
    fadeTransition.setOnFinished(
        event -> {
          depBoard.setVisible(false);
        });
  }

  public static MainGame getInstance() {
    return instance;
  }

  public static void removeOverlay() {
    if (initialised_game_pane.getChildren().size() > 2) {
      initialised_game_pane.getChildren().remove(initialised_game_pane.getChildren().size() - 1);
      initialised_game_pane.getChildren().remove(initialised_game_pane.getChildren().size() - 1);
    }
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
      removeOverlay();
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

  @FXML
  private void clickHeader() {
    BookShelfController.returnBook();
  }
}
