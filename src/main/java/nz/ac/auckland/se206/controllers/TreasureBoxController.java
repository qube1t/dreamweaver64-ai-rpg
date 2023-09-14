package nz.ac.auckland.se206.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import nz.ac.auckland.se206.GameState;

public class TreasureBoxController {

    @FXML private ImageView imgTreasure;
    @FXML private ImageView clickedTreasure;
    @FXML private TextArea message;
    @FXML private Rectangle treasure;

    public void initialize() {
        if (GameState.isTreasureFound) {
            clickedTreasure.setVisible(false);
            message.setVisible(false);
            imgTreasure.setVisible(false);
        }
    }

    @FXML
    void onGetTreasure(MouseEvent event) throws IOException{
        GameState.isTreasureFound = true;
        clickedTreasure.setVisible(true);
        message.setVisible(true);
        message.setText("mom");
        imgTreasure.setVisible(false);
    }
}
