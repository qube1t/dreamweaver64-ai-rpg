package nz.ac.auckland.se206.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.VBox;

public class EndCreditController {

  @FXML private VBox contentVBox;
  @FXML private ScrollBar scrollbar;
  @FXML private Button btnExit;

  /*
   * Initialize the scrollbar.
   */
  public void initialize() {
    scrollbar.setMin(0);
    scrollbar.setMax(1);
    scrollbar.setValue(0);
    scrollbar
        .valueProperty()
        .addListener(
            (observable, oldVale, newValue) -> {
              double scrollPosition = newValue.doubleValue();
              double contentHeight = contentVBox.getBoundsInLocal().getHeight();
              double scrollableHeight = contentHeight - contentVBox.getHeight();
              contentVBox.setTranslateY(-scrollableHeight * (scrollPosition));
            });
  }

  @FXML
  void onClickExit(ActionEvent event) {
    System.exit(0);
  }
}
