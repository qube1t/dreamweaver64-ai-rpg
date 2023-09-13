package nz.ac.auckland.se206.controllers;

import java.util.ArrayList;
import javafx.animation.FadeTransition;
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
      desk2;
  @FXML private Circle box1, box2, box3, box4, box5;
  @FXML private Character character;
  @FXML private AnchorPane radarPane;
  @FXML private ImageView radar_image, radar_computer, map;
  private boolean isRadarComputerOpen;

  public void initialize() {

    // Set the inital correct treasure box
    GameState.currentBox = 2;

    // Add all the obstacles to the list
    ArrayList<Rectangle> obsts = new ArrayList<Rectangle>();
    Rectangle[] rectangles = {
      computer, computer2, chair1, chair2, gate, radar, desk1, desk2, depBoard, boundary1,
      boundary2, boundary3, boundary4, boundary5
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

    // Set the radar computer boolean to false initially
    isRadarComputerOpen = false;
  }

  public void fadeInRadar() {

    Circle[] points = {box1, box2, box3, box4, box5};
    ImageView[] radarObjects = {radar_computer, radar_image};

    for (Circle point : points) {
      point.setVisible(true);

      FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), point);
      fadeTransition.setFromValue(0.0);
      fadeTransition.setToValue(1.0);
      fadeTransition.play();
    }

    for (ImageView imageView : radarObjects) {
      imageView.setVisible(true);
      FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), imageView);
      fadeTransition.setFromValue(0.0);
      fadeTransition.setToValue(1.0);
      fadeTransition.play();
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
    System.out.println("Radar clicked");
    fadeInRadar();
    isRadarComputerOpen = true;
  }

  @FXML
  public void onClickBook() {
    System.out.println("Book clicked");
    MainGame mainGame = MainGame.getInstance();
    mainGame.fadeInFlightPlan();
  }

  @FXML
  public void onCloseObject() {
    System.out.println("Map clicked");
    // If the radar computer is currently open and player clicks on the map, then close the radar
    // computer

    if (isRadarComputerOpen) {
      radar_computer.setVisible(false);
      radar_image.setVisible(false);
      box1.setVisible(false);
      box2.setVisible(false);
      box3.setVisible(false);
      box4.setVisible(false);
      box5.setVisible(false);

      isRadarComputerOpen = false;
    }
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
}
