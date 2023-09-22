package nz.ac.auckland.se206.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import nz.ac.auckland.se206.GameState;

public class RadarController {

  @FXML private Circle box1;
  @FXML private Circle box2;
  @FXML private Circle box3;
  @FXML private Circle box4;
  @FXML private Circle box5;

  private Timeline radarAnimation;
  private Circle[] radarPoints;

  public void initialize() {

    // Call the set radar point color method to set the most up to date correct box
    if (GameState.currentBox == -1) {
      // Generate a random number between 1 and 5 if the correct treasure box is not set
      int randomBox = (int) (Math.random() * 5 + 1);
      changeCorrectBox(randomBox);
      GameState.currentBox = randomBox;
    } else {
      changeCorrectBox(GameState.currentBox);
    }

    // Initialize the radar points and radarObjects to a list.
    this.radarPoints = new Circle[] {box1, box2, box3, box4, box5};
    // Initialize the radarAnimation timeline
    this.radarAnimation =
        new Timeline(
            new KeyFrame(Duration.seconds(0), event -> fadeInRadarPoints()),
            new KeyFrame(Duration.seconds(1), event -> fadeOutRadarPoints()));
    radarAnimation.setCycleCount(Timeline.INDEFINITE);
    radarAnimation.setOnFinished(
        event -> {
          // Restart the animation when it completes
          radarAnimation.play();
        });

    // Start the animation
    radarAnimation.play();
  }

  /**
   * This method randomly select one of the boxes and change its color to red
   *
   * @return the snumber of the box that has been changed
   */
  protected void changeCorrectBox(int currentBox) {
    // Change the color of the current box to green
    box1.setStyle("-fx-fill: #0b941b");
    box2.setStyle("-fx-fill: #0b941b");
    box3.setStyle("-fx-fill: #0b941b");
    box4.setStyle("-fx-fill: #0b941b");
    box5.setStyle("-fx-fill: #0b941b");

    // Change the color of the box
    if (currentBox == 1) {
      box1.setStyle("-fx-fill: red");
    } else if (currentBox == 2) {
      box2.setStyle("-fx-fill: red");
    } else if (currentBox == 3) {
      box3.setStyle("-fx-fill: red");
    } else if (currentBox == 4) {
      box4.setStyle("-fx-fill: red");
    } else if (currentBox == 5) {
      box5.setStyle("-fx-fill: red");
    }
  }

  protected void fadeInRadarPoints() {
    for (Circle radarPoint : radarPoints) {
      radarPoint.setVisible(true);
      FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), radarPoint);
      fadeTransition.setFromValue(0.0);
      fadeTransition.setToValue(1.0);
      fadeTransition.setInterpolator(Interpolator.LINEAR); // Use linear interpolation
      fadeTransition.play();
    }
  }

  protected void fadeOutRadarPoints() {
    for (Circle radarPoint : radarPoints) {

      FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), radarPoint);
      fadeTransition.setFromValue(1.0);
      fadeTransition.setToValue(0.0);
      fadeTransition.setInterpolator(Interpolator.LINEAR); // Use linear interpolation
      fadeTransition.play();
    }
  }
}
