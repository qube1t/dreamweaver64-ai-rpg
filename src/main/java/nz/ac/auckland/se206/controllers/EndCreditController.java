package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.VBox;

public class EndCreditController {

  @FXML 
  private VBox contentVBox;
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
              double contentHeight = contentVBox.getBoundsInLocal().getHeight();
              double scrollableHeight = contentHeight - contentVBox.getHeight();
              contentVBox.setTranslateY(-scrollableHeight * (scrollPosition));
            });
  }

  /**
   * When the player clicks on the exit button, the game will be closed.
   * 
   * @param event the action event
   * @throws IOException
   */
  @FXML
  void onClickExit(ActionEvent event) throws IOException {
    System.exit(0);
  }
}
