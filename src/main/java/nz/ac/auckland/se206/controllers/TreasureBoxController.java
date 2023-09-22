package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.GameState;

public class TreasureBoxController {

  @FXML 
  private ImageView imgTreasure;
  @FXML 
  private ImageView clickedTreasure;
  @FXML 
  private Label message;
  @FXML 
  private Button treasure;

  /** Initialize the treasure box. */
  public void initialize() {
    if (GameState.isEncryptedMessageFound) {
      clickedTreasure.setVisible(false);
      message.setVisible(false);
      imgTreasure.setVisible(false);
      treasure.setDisable(true);
    }
  }

  /**
   * When the player clicks on the treasure box, the encrypted message will be displayed.
   *
   * @param event the action event
   * @throws IOException
   */
  @FXML
  void onGetTreasure(ActionEvent event) throws IOException {
    treasure.setDisable(true);
    GameState.isEncryptedMessageFound = true;
    imgTreasure.setVisible(false);

    // display the encrypted message
    message.setText(GameState.encryptedFinalMsg);
    clickedTreasure.setVisible(true);
    message.setVisible(true);

    // add treasure image to the inventory
    Image treasureImage = new Image("/images/treasure.png");
    MainGame.addObtainedItem(treasureImage, "treasure");
    System.out.println("Treasure obtained");
  }
}
