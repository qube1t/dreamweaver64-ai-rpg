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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.components.DraggableLetter;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom3;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/**
 * The DestnationPuzzleController class is responsible for controlling the
 * destination puzzle screen.
 * It initializes the puzzle, handles the submit button click event, and handles
 * the close button click event.
 * The class also sets the cursor to a custom cursor and sets mouse hovering
 * effects for the submit button.
 * The class extends the JavaFX Controller class and is used to interact with
 * the FXML file for the destination puzzle screen.
 */
public class DestnationPuzzleController {
  @FXML
  private Label introduction;
  @FXML
  private Pane puzzlePane;
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

  private Cursor custom;

  /**
   * Initializes the Destination Puzzle Controller.
   * Sets the cursor to custom cursor, sets mouse hovering effects for submit
   * button,
   * sets progress bar to invisible, initializes the puzzle with unarranged puzzle
   * string,
   * and sets the introduction text to the puzzle intro message for room 3.
   *
   * @throws ApiProxyException if there is an issue with the API proxy
   */
  public void initialize() throws ApiProxyException {
    // Set the cursor to custom cursor
    Image cursor = new Image("/images/cursor.png", 9,
        20, true, true);
    this.custom = new ImageCursor(cursor);

    puzzlePane.setCursor(custom);
    letterBox.setCursor(Cursor.OPEN_HAND);

    submit.setOnMouseEntered(
        event -> {
          // Mouse hovering effect
          submit.setStyle("-fx-background-color: #0aa9b5;");
        });

    submit.setOnMouseExited(
        event -> {
          // Mouse hovering effect
          submit.setStyle("-fx-background-color: #15dae4;");
        });
    // Set progress bar to invisible
    load.setVisible(false);
    loadText.setVisible(false);

    String unarrangedPuzzle = GameState.unarrangedDestnation;
    System.out.println(unarrangedPuzzle);
    initializePuzzle(unarrangedPuzzle);
    introduction.setText(GameState.puzzleIntroMessageRoom3.toUpperCase());
  }

  /**
   * This method is called when the user clicks the submit button for the
   * destination puzzle.
   * It retrieves the current text of the puzzle and checks if it matches the
   * correct answer.
   * If the answer is correct, it updates the introduction label with a success
   * message and hides the submit button.
   * If the answer is incorrect, it updates the introduction label with a failure
   * message and enables the submit button.
   *
   * @throws ApiProxyException if there is an error with the API proxy
   */
  @FXML
  public void onClickSubmit() throws ApiProxyException {
    System.out.println("submit");
    // Get the current text of the puzzle
    StringBuilder sb = new StringBuilder();
    submit.setOpacity(0.5);
    submit.setDisable(true);

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
    if (GameState.arrangedDestnation.equalsIgnoreCase(currentText)) {
      // GameState.isPuzzleInRoom3Solved = true;
      GameState.eleanorAi2.runGpt(
          GptPromptEngineeringRoom3.correctPuzzleRoom3(),
          (result) -> {
            System.out.println(result);

            Platform.runLater(
                () -> {
                  GameState.hasPuzzleSolved = true;
                  Room3Controller.enableFlightComputer();
                  // Hide the progress bar
                  load.setVisible(false);
                  loadText.setVisible(false);
                  // Update the introduction label with the correct answer message
                  introduction.setText(result.toUpperCase());
                  submit.setVisible(false);
                });
          });
    } else {
      GameState.eleanorAi2.runGpt(
          GptPromptEngineeringRoom3.wrongPuzzleRoom3(),
          (result) -> {
            System.out.println(result);

            Platform.runLater(
                () -> {
                  // Hide the progress bar
                  load.setVisible(false);
                  loadText.setVisible(false);
                  // Update the introduction label with the incorrect answer message
                  introduction.setText(result.toUpperCase());
                  submit.setOpacity(1);
                  submit.setDisable(false);
                });
          });
    }
  }

  /**
   * Handles the event when the close button is clicked. Removes the overlay from
   * the main game controller.
   */
  @FXML
  protected void onClickClose() {
    MainGameController.removeOverlay(false);
  }

  private void initializePuzzle(String cityName) {
    char[] letters = cityName.toCharArray();

    for (char letter : letters) {
      DraggableLetter draggableLetter = new DraggableLetter(
          String.valueOf(letter), letterBox, this);

      // Create a StackPane to add a frame around each letter
      StackPane letterFrame = new StackPane();
      letterFrame.getStyleClass().add("letter-frame");

      letterFrame.getChildren().add(draggableLetter);
      letterBox.getChildren().add(letterFrame);
    }
    // Set the alignment of puzzle to middle
    letterBox.setAlignment(Pos.CENTER);
  }

  /**
   * Sets the cursor of the letter box based on the given status.
   * If the status is 0, the cursor is set to OPEN_HAND.
   * If the status is not 0, the cursor is set to CLOSED_HAND.
   *
   * @param status the status of the cursor to be set
   */
  public void setLetterCursor(int status) {
    if (status == 0) {
      letterBox.setCursor(Cursor.OPEN_HAND);
    } else {
      letterBox.setCursor(Cursor.CLOSED_HAND);
    }
  }
}
