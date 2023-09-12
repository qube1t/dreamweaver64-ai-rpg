package nz.ac.auckland.se206.controllers;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
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
      boundary1,
      boundary2,
      boundary3,
      boundary4,
      boundary5,
      depBoard,
      desk1,
      desk2;
  @FXML private Character character;

  public void initialize() {
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

    // Initialization code goes here
    character.enableMobility(obsts);
    character.setLayoutX(530);
    character.setLayoutY(210);
  }

  @FXML
  public void onClickComputer() {
    System.out.println("Gate clicked");
    // Set the scene to room3Sub
    // Switch to the chat view to solve the riddle.
    App.setUi("sub3");
  }

  @FXML
  public void onClickBook() {
    System.out.println("Book clicked");
    MainGame mainGame = MainGame.getInstance();
    mainGame.fadeInFlightPlan();
  }
}
