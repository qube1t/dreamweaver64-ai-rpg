package nz.ac.auckland.se206.controllers;

import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.components.Character;

public class Room3Controller {

  @FXML
  private Rectangle computer,
      computer2,
      chair1,
      chair2,
      gate,
      radar,
      protectRadarComputer,
      boundary1,
      boundary2,
      boundary3,
      boundary4,
      boundary5,
      depBoard,
      desk1,
      desk2,
      bound1,
      bound2,
      bound3;
  @FXML private Circle box1, box2, box3, box4, box5;
  private Circle[] radarPoints;
  private ImageView[] radarObjects;
  @FXML private ImageView lastFlightPlan;
  @FXML private ImageView departureBoard;
  @FXML private Character character;
  @FXML private AnchorPane radarPane;
  @FXML private ImageView radar_image, radar_computer, map;
  private boolean isRadarComputerOpen;
  private Timeline radarAnimation;

  public void initialize() {

    // Initialize the radar points and radarObjects to a list.
    this.radarPoints = new Circle[] {box1, box2, box3, box4, box5};
    this.radarObjects = new ImageView[] {radar_image, radar_computer};

    // Set the inital correct treasure box
    GameState.currentBox = 2;
    box2.setStyle("-fx-fill: red");

    // Add all the obstacles to the list
    ArrayList<Rectangle> obsts = new ArrayList<Rectangle>();
    Rectangle[] rectangles = {
      computer, computer2, chair1, chair2, gate, radar, desk1, desk2, depBoard, boundary1,
      boundary2, boundary3, boundary4, boundary5, bound1, bound2, bound3
    };

    for (Rectangle rectangle : rectangles) {
      obsts.add(rectangle);
    }

    character.enableMobility(obsts);
    character.setLayoutX(530);
    character.setLayoutY(210);

    radarPane.setMouseTransparent(true);

    // Set radar computer invisible initially
    radar_computer.setVisible(false);
    radar_image.setVisible(false);
    box1.setVisible(false);
    box2.setVisible(false);
    box3.setVisible(false);
    box4.setVisible(false);
    box5.setVisible(false);

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

    // Set the radar computer boolean to false initially
    isRadarComputerOpen = false;
  }

  public void fadeInRadarPoints() {
    for (Circle radarPoint : radarPoints) {
      radarPoint.setVisible(true);
      FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), radarPoint);
      fadeTransition.setFromValue(0.0);
      fadeTransition.setToValue(1.0);
      fadeTransition.setInterpolator(Interpolator.LINEAR); // Use linear interpolation
      fadeTransition.play();
    }
  }

  public void fadeOutRadarPoints() {
    for (Circle radarPoint : radarPoints) {

      FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), radarPoint);
      fadeTransition.setFromValue(1.0);
      fadeTransition.setToValue(0.0);
      fadeTransition.setInterpolator(Interpolator.LINEAR); // Use linear interpolation
      fadeTransition.play();
    }
  }

  public void fadeInRadar() {

    for (ImageView obj : radarObjects) {
      obj.setVisible(true);
      FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), obj);
      fadeTransition.setFromValue(0.0);
      fadeTransition.setToValue(1.0);
      fadeTransition.play();
    }
    radarAnimation.play();
  }

  @FXML
  public void onClickDepBoard() {
    System.out.println("DepBoard clicked");
    GameState.isDepBoardOpen = true;
    if (GameState.isPreviousFlightPlanOpen) {
      System.out.println("Previous flight plan is open");
      GameState.isPreviousFlightPlanOpen = false;
      fadeOutFlightPlan();
      fadeInDepBoard();
    } else {
      fadeInDepBoard();
    }
  }

  @FXML
  public void onClickComputer() {
    System.out.println("Gate clicked");
    // Set the scene to room3Sub
    // Switch to the chat view to solve the riddle.
    App.setUi("sub3");
  }

  @FXML
  public void onClickProtectRadar() {
    if (isRadarComputerOpen) {
      protectRadarComputer.setVisible(true);
      protectRadarComputer.setDisable(false);
    }
  }

  @FXML
  public void onClickRadar() {
    isRadarComputerOpen = true;
    System.out.println("Radar clicked");
    fadeInRadar();
  }

  @FXML
  public void onClickBook() {
    System.out.println("Book clicked");
    GameState.isPreviousFlightPlanOpen = true;
    if (GameState.isDepBoardOpen) {
      GameState.isDepBoardOpen = false;
      fadeOutDepBoard();
      fadeInFlightPlan();
    } else {
      fadeInFlightPlan();
    }
  }

  @FXML
  public void onCloseObject() {
    System.out.println("Map clicked");
    // If the radar computer is currently open and player clicks on the map, then close the radar
    // computer

    if (isRadarComputerOpen) {
      if (isRadarComputerOpen) {
        radarAnimation.stop(); // Stop the radar animation
        isRadarComputerOpen = false;
      }
    }
    radar_computer.setVisible(false);
    radar_image.setVisible(false);
    box1.setVisible(false);
    box2.setVisible(false);
    box3.setVisible(false);
    box4.setVisible(false);
    box5.setVisible(false);
  }

  /**
   * This method randomly select one of the boxes and change its color to red
   *
   * @return the snumber of the box that has been changed
   */
  protected int changePointColor(int currentBox) {
    // Change the color of the current box to green
    box1.setStyle("-fx-fill: #0b941b");
    box2.setStyle("-fx-fill: #0b941b");
    box3.setStyle("-fx-fill: #0b941b");
    box4.setStyle("-fx-fill: #0b941b");
    box5.setStyle("-fx-fill: #0b941b");

    int random = (int) (Math.random() * 5) + 1;
    // Use while loop to ensure a different box is selected
    while (random == currentBox) {
      random = (int) (Math.random() * 5) + 1;
    }
    // Change the color of the box
    if (random == 1) {
      box1.setStyle("-fx-fill: red");
    } else if (random == 2) {
      box2.setStyle("-fx-fill: red");
    } else if (random == 3) {
      box3.setStyle("-fx-fill: red");
    } else if (random == 4) {
      box4.setStyle("-fx-fill: red");
    } else if (random == 5) {
      box5.setStyle("-fx-fill: red");
    }
    return random;
  }

  @FXML
  public void onClickDoor() {
    System.out.println("Door clicked");
    GameState.currentBox = changePointColor(GameState.currentBox);
    System.out.println(GameState.currentBox);
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
    departureBoard.setVisible(true); // Set to visible before starting the animation

    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), departureBoard);
    fadeTransition.setFromValue(0.0);
    fadeTransition.setToValue(1.0);
    fadeTransition.play();
  }

  public void fadeOutDepBoard() {
    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), departureBoard);
    fadeTransition.setFromValue(1.0);
    fadeTransition.setToValue(0.0);
    fadeTransition.play();
    fadeTransition.setOnFinished(
        event -> {
          departureBoard.setVisible(false);
        });
  }
}
