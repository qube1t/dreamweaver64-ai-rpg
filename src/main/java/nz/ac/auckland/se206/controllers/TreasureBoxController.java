package nz.ac.auckland.se206.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;

public class TreasureBoxController {
    @FXML private ImageView imgTreasure;
    @FXML private Rectangle treasure;
    @FXML private Button btnGoBack;

    /** 
     * Get the treasure
     * 
     * @param event the mouse event
     * @throws IOException
     */
    @FXML
    public void onGetTreasure(MouseEvent event) throws IOException {
        imgTreasure.setVisible(false);
        GameState.isTreasureFound = true;
    }

    /**
     * Go back to room 2
     * 
     * @param event the mouse event
     * @throws IOException
     */
    @FXML
    public void onGoBackRoom2(ActionEvent event) throws IOException {
        App.setRoot("main_game");
    }
}
