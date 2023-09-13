package nz.ac.auckland.se206.controllers;

import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
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
  @FXML private Character character;
  @FXML private AnchorPane radarPane;
  @FXML private ImageView radar_image, radar_computer, radar_points, map;
  private boolean isRadarComputerOpen;

  public void initialize() {

    // Add all the obstacles to the list
    ArrayList<Rectangle> obsts = new ArrayList<Rectangle>();
    obsts.add(0, computer);
    obsts.add(1, computer2);
    obsts.add(2, chair1);
    obsts.add(3, chair2);
    obsts.add(4, gate);
    obsts.add(5, radar);
    obsts.add(6, desk1);
    obsts.add(7, desk2);
    obsts.add(8, depBoard);
    obsts.add(9, boundary1);
    obsts.add(10, boundary2);
    obsts.add(11, boundary3);
    obsts.add(12, boundary4);
    obsts.add(13, boundary5);

    character.enableMobility(obsts);
    character.setLayoutX(530);
    character.setLayoutY(210);

    radarPane.setMouseTransparent(true);

    // Set radar computer invisible initially
    radar_computer.setVisible(false);
    radar_points.setVisible(false);
    radar_image.setVisible(false);

    // Set the radar computer boolean to false initially
    isRadarComputerOpen = false;
  }

  public void fadeInRadar() {
    radar_computer.setVisible(true);
    radar_points.setVisible(true);
    radar_image.setVisible(true);

    FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(1), radar_computer);
    FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(1), radar_points);
    FadeTransition fadeTransition3 = new FadeTransition(Duration.seconds(1), radar_image);

    fadeTransition1.setFromValue(0.0);
    fadeTransition1.setToValue(1.0);
    fadeTransition2.setFromValue(0.0);
    fadeTransition2.setToValue(1.0);
    fadeTransition3.setFromValue(0.0);
    fadeTransition3.setToValue(1.0);

    fadeTransition1.play();
    fadeTransition2.play();
    fadeTransition3.play();
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
      radar_points.setVisible(false);
      radar_image.setVisible(false);

      isRadarComputerOpen = false;
    }
  }
}
