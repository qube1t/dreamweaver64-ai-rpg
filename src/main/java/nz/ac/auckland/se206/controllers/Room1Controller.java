package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.components.Character;

/** Controller class for the room view. */
public class Room1Controller {

  // @FXML private Rectangle door;
  // @FXML private Rectangle window;
  // @FXML private Rectangle vase;

  @FXML private Character character;
  @FXML private Rectangle rect1;
  private MainGame parentController;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() {

    ArrayList<Rectangle> obsts = new ArrayList<Rectangle>();
    obsts.add(0, rect1);
    // Initialization code goes here
    character.enableMobility(obsts);
    character.setLayoutX(250);
    character.setLayoutY(250);
  }

  public void setParentController(MainGame parentController) {
    this.parentController = parentController;
  }

  @FXML
  public void changeRoot() throws IOException {
    MainGame.setGameRoom("room1");
  }
}
