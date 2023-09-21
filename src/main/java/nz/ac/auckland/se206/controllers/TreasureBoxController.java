package nz.ac.auckland.se206.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.GameState;

public class TreasureBoxController {

    @FXML private ImageView imgTreasure;
    @FXML private ImageView clickedTreasure;
    @FXML private TextArea message;
    @FXML private Button treasure;

    public void initialize() {
        treasure.setDisable(false);
        if (GameState.isTreasureFound) {
            clickedTreasure.setVisible(false);
            message.setVisible(false);
            imgTreasure.setVisible(false);
        }
    }

    @FXML
    void onGetTreasure(ActionEvent event) throws IOException{
        GameState.isTreasureFound = true;
        clickedTreasure.setVisible(true);
        message.setVisible(true);
        message.setText(GameState.encryptedFinalMsg);
        imgTreasure.setVisible(false);
        treasure.setDisable(true);
        Image treasureImage = new Image("/images/treasure.png");
        MainGame.addObtainedItem(treasureImage, "treasure");
        System.out.println("Treasure obtained");
    }
}
