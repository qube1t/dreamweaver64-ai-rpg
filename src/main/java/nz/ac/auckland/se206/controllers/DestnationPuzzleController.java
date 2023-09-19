package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.components.DraggableLetter;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class DestnationPuzzleController {
  @FXML private Label introduction;
  @FXML private AnchorPane puzzlePane;
  @FXML private HBox letterBox;
  @FXML private Button submit;

  public void initialize() throws ApiProxyException {
    String cityName = GameState.unarrangedCityName;
    System.out.println(cityName);
    initializePuzzle(cityName);
    introduction.setText(GameState.introMessage);
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
    // Set the alignment of puzzle to middle
    letterBox.setAlignment(Pos.CENTER);
  }

  @FXML
  protected void onClickSubmit() {
    // Get the current text of the puzzle
    String currentText = "";

    // Loop through each letter in the puzzle
    for (int i = 0; i < letterBox.getChildren().size(); i++) {
      StackPane letterFrame = (StackPane) letterBox.getChildren().get(i);
      DraggableLetter draggableLetter = (DraggableLetter) letterFrame.getChildren().get(0);
      currentText += draggableLetter.getText();
    }
    System.out.println(currentText);
    if (currentText.equalsIgnoreCase(GameState.arrangedCityName)) {
      System.out.println("correct");
    } else {
      System.out.println("incorrect");
    }
  }

  @FXML
  protected void onClickClose() {
    MainGame.removeOverlay();
  }
}
