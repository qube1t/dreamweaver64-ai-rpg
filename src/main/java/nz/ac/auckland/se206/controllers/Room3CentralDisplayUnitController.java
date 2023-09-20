package nz.ac.auckland.se206.controllers;

import java.util.Arrays;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.components.Character;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom3;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class Room3CentralDisplayUnitController {

  @FXML private Text errorText;

  @FXML private Character character;
  @FXML
  private Rectangle a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z;
  @FXML private Rectangle one1, two2, three3, four4, five5, six6, seven7, eight8, nine9, zero0;
  @FXML private Rectangle slash, clear, delete, execute;
  @FXML private Text displayInput, displayOutput;
  @FXML private ImageView lock, CentralDisplayUnit;
  protected List<Rectangle> allButtons;

  public void initialize() {
    System.out.println("Room3CentralDisplayUnitController initialized");

    List<Rectangle> allButtons =
        Arrays.asList(
            a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, q, r, s, t, u, v, w, x, y, z, slash,
            execute, clear, delete, one1, two2, three3, four4, five5, six6, seven7, eight8, nine9,
            zero0);
    this.allButtons = allButtons;

    if (!GameState.isPuzzleInRoom3Solved) {
      CentralDisplayUnit.setOpacity(0.6);
      displayOutput.setText("LOCKED");

      for (Rectangle button : allButtons) {
        button.setDisable(true);
      }
    } else {
      enableFlightCDU();
    }
  }

  @FXML
  private void test() {
    System.out.println("test");
  }

  @FXML
  private void handleLetterClick(MouseEvent event) {
    Rectangle letterRectangle = (Rectangle) event.getSource();
    String upperLetter =
        letterRectangle.getId().toUpperCase(); // Get the ID of the clicked rectangle
    System.out.println("Letter clicked: " + upperLetter);

    // Append the clicked letter to the existing text
    String currentText = displayOutput.getText();
    displayOutput.setText(currentText + upperLetter);
  }

  @FXML
  private void handleSlashClick(MouseEvent event) {
    String currentText = displayOutput.getText();
    displayOutput.setText(currentText + "/");
  }

  @FXML
  private void handleDeleteClick(MouseEvent event) {
    String currentText = displayOutput.getText();
    if (currentText.length() > 0) {
      displayOutput.setText(currentText.substring(0, currentText.length() - 1));
    }
  }

  @FXML
  private void handleClearClick(MouseEvent event) {
    displayOutput.setText("");
  }

  @FXML
  private void handleMenuClick(MouseEvent event) {
    MainGame.removeOverlay(false);
  }

  @FXML
  private void handleNumberClick(MouseEvent event) {
    Rectangle numberRectangle = (Rectangle) event.getSource();
    String number = numberRectangle.getId(); // Get the ID of the clicked rectangle
    // last character of the string is the number
    String currentText = displayOutput.getText();
    if (currentText.length() > 0) {
      displayOutput.setText(currentText + number.substring(number.length() - 1));
    }
  }

  protected void typeTextEffect(Text text, String message, int delay) {
    text.setText(""); // Clear the text first
    int messageLength = message.length();

    Timeline timeline = new Timeline();

    for (int i = 0; i < messageLength; i++) {
      final int index = i;
      KeyFrame keyFrame =
          new KeyFrame(
              Duration.millis(delay * i),
              new KeyValue(text.textProperty(), message.substring(0, index + 1)));
      timeline.getKeyFrames().add(keyFrame);
    }

    timeline.play();
  }

  protected void enableFlightCDU() {
    CentralDisplayUnit.setOpacity(1);
    lock.setVisible(false);
    lock.setDisable(true);
    for (Rectangle button : allButtons) {
      button.setDisable(false);
    }
    String message = "ENTER THE FIRST THREE LETTER OF DEP / DEST CITY THEN PRESS EXECUTE.g.AUC/SYD";
    int typingDelay = 50;
    typeTextEffect(displayInput, message, typingDelay);
  }

  @FXML
  /**
   * Handle execute button click
   *
   * @param event
   */
  public void handleExecuteClick() throws ApiProxyException {
    String currentInput = displayOutput.getText();
    String firstThreeDestnation = GameState.arrangedDestnationCity.substring(0, 3).toUpperCase();
    String firstThreeDeparture =
        GameState.currentCities[GameState.currentCityIndex - 1].getText().substring(0, 3);
    System.out.println(firstThreeDestnation + "/" + firstThreeDeparture);
    if (currentInput.equalsIgnoreCase(firstThreeDestnation + "/" + firstThreeDeparture)) {
      GameState.eleanorAi.runGpt(
          "User update: User has now correctly entered the destnation city and current city and"
              + " obtained the aircraft code needed to decrypt the letter in another roon. No need"
              + " to response to this message.");
      // Aircraft code has been found.
      GameState.isAircraftCodeFound = true;
      displayInput.setVisible(true);

      GameState.eleanorAi.runGpt(
          GptPromptEngineeringRoom3.getAircraftCode(),
          (result) -> {
            System.out.println(result);
            Platform.runLater(
                () -> {
                  // Find the start and end indices of the aircraft code within single quotes
                  int startIndex = result.indexOf("^");
                  int endIndex = result.indexOf("^", startIndex + 1);

                  if (startIndex != -1 && endIndex != -1) {
                    // Extract the aircraft code
                    GameState.aircraftCode = result.substring(startIndex + 1, endIndex);
                    System.out.println("Aircraft code: " + GameState.aircraftCode);
                  }
                  // Update the introduction label with the correct answer message
                  displayInput.setText(
                      "CONGRATULATIONS! THE AIRCRAFT CODE IS " + GameState.aircraftCode);
                });
          });

      // Clear the input field and disable it along with the execute button
      displayOutput.setText("");
      displayOutput.setDisable(true);
      execute.setDisable(true);
    } else {
      // Display an error message
      displayInput.setStyle("-fx-text-fill: red;");
      displayInput.setText("INCORRECT ANSWER TRY AGAIN");
      displayOutput.setText("");
    }
  }
}
