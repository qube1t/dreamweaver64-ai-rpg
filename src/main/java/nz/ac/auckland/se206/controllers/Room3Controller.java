package nz.ac.auckland.se206.controllers;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.components.Character;

public class Room3Controller {

  @FXML private Rectangle gate;
  @FXML private ImageView flightPlan;
  @FXML private Character character;

  public void initialize() {
    ArrayList<Rectangle> obsts = new ArrayList<Rectangle>();
    obsts.add(0, gate);
    // Initialization code goes here
    character.enableMobility(obsts);
    character.setLayoutX(250);
    character.setLayoutY(250);
  }

  @FXML
  public void onClickGate() {
    System.out.println("Gate clicked");
    // Set the scene to room3Sub
    // Switch to the chat view to solve the riddle.
    App.setUi("sub3");
  }
}
