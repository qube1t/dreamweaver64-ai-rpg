package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import nz.ac.auckland.se206.App;

/**
 * This class is the controller for the end credits screen.
 * It handles the initialization of the screen
 * and the event when the exit button is clicked,
 * which sets the root of the application to the end menu.
 */
public class EndCreditController {

  @FXML
  private VBox contentVbox;
  @FXML
  private Button btnExit;

  /**
   * This method is called by the FXMLLoader when the EndCredit.fxml file is
   * loaded. It is used to initialize the controller.
   */
  public void initialize() {
  }

  /**
   * This method is called when the exit button is clicked. It sets the root of
   * the application to the end_menu.
   * 
   * @param event The event that triggered the method call.
   * @throws IOException If an I/O error occurs.
   */
  @FXML
  private void onClickExit(ActionEvent event) throws IOException {
    App.setRoot("end_menu");
  }
}
