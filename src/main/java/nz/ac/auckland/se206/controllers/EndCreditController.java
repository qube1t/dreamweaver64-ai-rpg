package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.VBox;
import nz.ac.auckland.se206.App;

public class EndCreditController {

  @FXML
  private VBox contentVbox;
  @FXML
  private ScrollBar scrollBar;
  @FXML
  private Button btnExit;

  /** Initialize the scroll bar. */
  public void initialize() {
    scrollBar.setMin(0);
    scrollBar.setMax(1);
    scrollBar.setValue(0);
    // When the player scrolls the scroll bar, the content will be scrolled
    scrollBar
        .valueProperty()
        .addListener(
            (observable, oldVale, newValue) -> {
              double scrollPosition = newValue.doubleValue();
              double contentHeight = contentVbox.getBoundsInLocal().getHeight();
              double scrollableHeight = contentHeight - contentVbox.getHeight();
              contentVbox.setTranslateY(-scrollableHeight * (scrollPosition));
            });
  }

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
