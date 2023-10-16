package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/**
 * The controller class for the Treasure Box in the game. This class handles the
 * initialization of the treasure box and the action of getting the treasure.
 * When the player clicks on the treasure box, the encrypted message will be
 * displayed. The class also adds the treasure image to the inventory.
 */
public class TreasureBoxController {

  @FXML
  private ImageView imgTreasure;
  @FXML
  private ImageView clickedTreasure;
  @FXML
  private Label message;
  @FXML
  private Button treasure;

  /**
   * Initializes the TreasureBoxController. If the encrypted message has been
   * found, the treasure box is disabled and its components are hidden.
   */
  public void initialize() {
    if (GameState.isEncryptedMessageFound) {
      clickedTreasure.setVisible(false);
      message.setVisible(false);
      imgTreasure.setVisible(false);
      treasure.setDisable(true);
    }
  }

  /**
   * This method is called when the user clicks on the treasure box. It runs the
   * GPT model to
   * generate a message indicating that the user has obtained the treasure. The
   * treasure box
   * is then disabled, the encrypted message is displayed, and the treasure image
   * is added
   * to the inventory. No reply is required, but a hint can be given if the user
   * asks for one.
   *
   * @param event The event triggered by the user clicking on the treasure box.
   * @throws IOException       If an I/O error occurs while running the GPT model.
   * @throws ApiProxyException If an error occurs while running the GPT model.
   */
  @FXML
  private void onGetTreasure(ActionEvent event) throws IOException, ApiProxyException {
    GameState.eleanorAi.runGpt(
        "User update: User opened the treasure box and got the treasure which is "
            + " the encrypted message. To decrypte the message, user need to use "
            + " engine machine. No reply is required. If the user ask for hint, "
            + " give the hint.");
    treasure.setDisable(true);
    GameState.isEncryptedMessageFound = true;
    imgTreasure.setVisible(false);

    // display the encrypted message
    message.setText(GameState.encryptedFinalMsg);
    clickedTreasure.setVisible(true);
    message.setVisible(true);

    // add treasure image to the inventory
    Image treasureImage = new Image("/images/treasure.png");
    MainGameController.addObtainedItem(treasureImage, "treasure");
    System.out.println("Treasure obtained");
  }
}
