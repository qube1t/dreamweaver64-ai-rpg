package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import nz.ac.auckland.se206.App;

public class EndCreditController {

  @FXML
  private VBox contentVbox;
  @FXML
  private Button btnExit;

  public void initialize() { }

  /**
   * When the player clicks on the exit button, the game will be closed.
   * 
   * @param event the action event
   * @throws IOException
   */
  @FXML
  private void onClickExit(ActionEvent event) throws IOException {
    App.setRoot("end_menu");
  }
}
