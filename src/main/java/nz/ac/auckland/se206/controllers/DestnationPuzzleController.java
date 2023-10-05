package nz.ac.auckland.se206.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.components.DraggableLetter;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom3;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class DestnationPuzzleController {
  @FXML
  private Label introduction;
  @FXML
  private AnchorPane puzzlePane;
  @FXML
  private HBox letterBox;
  @FXML
  private Button submit;
  @FXML
  private Button exit;
  @FXML
  private ProgressBar load;
  @FXML
  private Label loadText;
  @FXML
  private ImageView screen;
  @FXML
  private ImageView customCursor;
  @FXML
  private Rectangle screenArea;

  private Cursor custom;

  public void initialize() throws ApiProxyException {
    // Set the cursor to custom cursor
    Image cursor = new Image("/images/cursor.png", 16,
        27, true, true);
    this.custom = new ImageCursor(cursor);

    screenArea.setCursor(custom);
    submit.setCursor(custom);
    exit.setCursor(custom);
    letterBox.setCursor(Cursor.OPEN_HAND);
    // Set the custom cursor for the screen
    // letterBox.setCursor(grabCursor);

    // Set progress bar to invisible
    load.setVisible(false);
    loadText.setVisible(false);
    String unarrangedPuzzle = GameState.unarrangedDestnationCity;
    System.out.println(unarrangedPuzzle);
    initializePuzzle(unarrangedPuzzle);
    introduction.setText(GameState.puzzleIntroMessageRoom3);
  }

  public void setLetterCursor(int status) {
    if (status == 0) {
      letterBox.setCursor(Cursor.OPEN_HAND);
    } else {
      letterBox.setCursor(Cursor.CLOSED_HAND);
    }
  }

  protected void initializePuzzle(String cityName) {
    char[] letters = cityName.toCharArray();

    for (char letter : letters) {
      DraggableLetter draggableLetter = new DraggableLetter(String.valueOf(letter), letterBox, this);

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
  public void onClickSubmit() throws ApiProxyException {
    System.out.println("submit");
    // Get the current text of the puzzle
    StringBuilder sb = new StringBuilder();

    // Loop through each letter in the puzzle
    for (int i = 0; i < letterBox.getChildren().size(); i++) {
      StackPane letterFrame = (StackPane) letterBox.getChildren().get(i);
      DraggableLetter draggableLetter = (DraggableLetter) letterFrame.getChildren().get(0);
      sb.append(draggableLetter.getText());
    }
    String currentText = sb.toString();
    System.out.println(currentText);

    introduction.setText("");
    loadText.setVisible(true);
    load.setVisible(true);
    load.setProgress(ProgressBar.INDETERMINATE_PROGRESS);

    // Handle the case where the user has entered the correct answer
    if (GameState.arrangedDestnationCity.equalsIgnoreCase(currentText)) {
      GameState.isPuzzleInRoom3Solved = true;
      GameState.eleanorAi.runGpt(
          GptPromptEngineeringRoom3.correctPuzzleRoom3(),
          (result) -> {
            System.out.println(result);

            Platform.runLater(
                () -> {
                  // Hide the progress bar
                  load.setVisible(false);
                  loadText.setVisible(false);
                  // Update the introduction label with the correct answer message
                  introduction.setText(result);
                });
          });
      GameState.eleanorAi.runGpt(
          "User update: The player now has correctly solved the"
              + "puzzle for destnation city. In other words,"
              + "the user has discovered the destnation city. Do not need to reply");
    } else {
      GameState.eleanorAi.runGpt(
          GptPromptEngineeringRoom3.wrongPuzzleRoom3(),
          (result) -> {
            System.out.println(result);

            Platform.runLater(
                () -> {
                  // Hide the progress bar
                  load.setVisible(false);
                  loadText.setVisible(false);
                  // Update the introduction label with the incorrect answer message
                  introduction.setText(result);
                });
          });
    }
  }

  @FXML
  protected void onClickClose() {
    MainGameController.removeOverlay(false);
  }
}
