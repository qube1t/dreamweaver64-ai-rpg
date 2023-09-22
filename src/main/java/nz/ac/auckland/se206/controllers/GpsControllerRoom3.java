package nz.ac.auckland.se206.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import nz.ac.auckland.se206.GameState;

public class GpsControllerRoom3 {

  @FXML
  private Text label1;
  @FXML
  private Text label2;
  @FXML
  private Text label3;
  @FXML
  private Text label4;
  @FXML
  private Text label5;
  @FXML
  private Circle point1;
  @FXML
  private Circle point2;
  @FXML
  private Circle point3;
  @FXML
  private Circle point4;
  @FXML
  private Circle point5;

  private Circle[] cityPoints;
  private Text[] cityLabels;
  private Timeline radarAnimation;
  private int currentCity;
  private Circle currentCityPoint;
  private Text currentCityLabel;

  public void initialize() {
    // Initialize the radar points and radarObjects to a list.
    this.cityPoints = new Circle[] { point1, point2, point3, point4, point5 };
    this.cityLabels = new Text[] { label1, label2, label3, label4, label5 };

    GameState.currentCities = cityLabels;

    // initalize current city if it is not set
    if (GameState.currentCityIndex == -1) {

      // generate a number between 1 and 5
      int randomCity = (int) (Math.random() * 5 + 1);
      GameState.currentCityIndex = randomCity;
      this.currentCity = randomCity;
    }

    this.currentCity = GameState.currentCityIndex;

    this.currentCityPoint = cityPoints[currentCity - 1];
    this.currentCityLabel = cityLabels[currentCity - 1];

    // Set invisible at the start
    label1.setVisible(false);
    label2.setVisible(false);
    label3.setVisible(false);
    label4.setVisible(false);
    label5.setVisible(false);
    point1.setVisible(false);
    point2.setVisible(false);
    point3.setVisible(false);
    point4.setVisible(false);
    point5.setVisible(false);

    currentCityLabel.setVisible(true);
    // Initialize the radarAnimation timeline
    this.radarAnimation = new Timeline(
        new KeyFrame(Duration.seconds(0), event -> fadeInRadarPoints(currentCityPoint)),
        new KeyFrame(Duration.seconds(1), event -> fadeOutRadarPoints(currentCityPoint)));
    radarAnimation.setCycleCount(Timeline.INDEFINITE);
    radarAnimation.setOnFinished(
        event -> {
          // Restart the animation when it completes
          radarAnimation.play();
        });

    // Start the animation
    radarAnimation.play();

    System.out.println("Current city is " + GameState.currentCities[currentCity - 1].getText());
  }

  protected void fadeInRadarPoints(Circle city) {

    city.setVisible(true);
    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), city);
    fadeTransition.setFromValue(0.0);
    fadeTransition.setToValue(1.0);
    fadeTransition.setInterpolator(Interpolator.LINEAR); // Use linear interpolation
    fadeTransition.play();
  }

  protected void fadeOutRadarPoints(Circle city) {

    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), city);
    fadeTransition.setFromValue(1.0);
    fadeTransition.setToValue(0.0);
    fadeTransition.setInterpolator(Interpolator.LINEAR); // Use linear interpolation
    fadeTransition.play();
  }
}
