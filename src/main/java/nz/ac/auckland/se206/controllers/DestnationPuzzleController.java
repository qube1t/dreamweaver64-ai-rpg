package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.components.DraggableLetter;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class DestnationPuzzleController {
  @FXML private Label unarrangedCityName;
  @FXML private AnchorPane puzzlePane;
  @FXML private HBox letterBox;

  public void initialize() throws ApiProxyException {
    String cityName = GameState.unarrangedCityName;
    System.out.println(cityName);
    initializePuzzle(cityName);
    unarrangedCityName.setText(cityName);
  }

  protected void initializePuzzle(String cityName) {

    char[] letters = cityName.toCharArray();
    for (char letter : letters) {
      DraggableLetter draggableLetter = new DraggableLetter(String.valueOf(letter), letterBox);

      // Create a StackPane to add a frame around each letter
      StackPane letterFrame = new StackPane();
      letterFrame.getStyleClass().add("letter-frame");

      letterFrame.getChildren().add(draggableLetter);
      letterBox.getChildren().add(letterFrame);
    }
  }
}
