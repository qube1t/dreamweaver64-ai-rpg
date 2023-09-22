package nz.ac.auckland.se206.controllers;

import java.util.Arrays;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.components.Character;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom3;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class Room3CentralDisplayUnitController {

  @FXML
  private Text errorText;

  @FXML
  private Character character;
  @FXML
  private Rectangle a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z;
  @FXML
  private Rectangle one1;
  @FXML
  private Rectangle two2;
  @FXML
  private Rectangle three3;
  @FXML
  private Rectangle four4;
  @FXML
  private Rectangle five5;
  @FXML
  private Rectangle six6;
  @FXML
  private Rectangle seven7;
  @FXML
  private Rectangle eight8;
  @FXML
  private Rectangle nine9;
  @FXML
  private Rectangle zero0;
  @FXML
  private Rectangle slash;
  @FXML
  private Rectangle clear;
  @FXML
  private Rectangle delete;
  @FXML
  private Rectangle execute;
  @FXML
  private Text displayInput;
  @FXML
  private TextField displayOutput;
  @FXML
  private ImageView lock;
  @FXML
  private ImageView centralDisplayUnit;
  @FXML
  private ProgressIndicator progress;

  protected List<Rectangle> allButtons;

  public void initialize() throws ApiProxyException {

    // Add an event filter to the TextField to consume key events
    displayOutput.addEventFilter(
        KeyEvent.KEY_TYPED,
        keyEvent -> {
          keyEvent.consume();
        });

    progress.setVisible(false);

    List<Rectangle> allButtons = Arrays.asList(
        a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, q, r, s, t, u, v, w, x, y, z, slash,
        execute, clear, delete, one1, two2, three3, four4, five5, six6, seven7, eight8, nine9,
        zero0);
    this.allButtons = allButtons;

    if (GameState.isPuzzleInRoom3Solved && GameState.isWorldMapOpened) {
      if (GameState.isAircraftCodeFound) {
        centralDisplayUnit.setOpacity(0.7);
        displayOutput.setText("");
        // Update the introduction label with the correct answer message
        displayInput.setText("CONGRATULATIONS! AIRCRAFT CODE UNLOCKED: " + GameState.aircraftCode);

        for (Rectangle button : allButtons) {
          button.setDisable(true);
        }
        displayOutput.setDisable(true);
      } else {
        enableFlightComputer();
      }

    } else {
      centralDisplayUnit.setOpacity(0.7);
      displayOutput.setText("LOCKED");

      for (Rectangle button : allButtons) {
        button.setDisable(true);
      }
      displayOutput.setDisable(true);
      GameState.eleanorAi.runGpt(
          "User update: User clicks on the flight computer but it is locked due to either not"
              + " solved the puzzle or not opened the world map to discover the current location."
              + " Give player a short message without revealing any step. Only give hints If the"
              + " user ask for it. Only the response surrounded between * will send to user.");
    }
  }

  /**
   * This method automatically adds slash if coreect first two letter of current
   * location entered.
   */
  protected void addSlashIfEnteredCurrentCity() {

    String currentText = displayOutput.getText();
    // Convert the first two letters of the current city to uppercase
    String firstTwoDeparture = GameState.currentCities[GameState.currentCityIndex - 1]
        .getText()
        .substring(0, 2)
        .toUpperCase();
    // Add slash if the first two letters of the current city is entered
    if (currentText.equalsIgnoreCase(firstTwoDeparture)) {
      displayOutput.setText(currentText + "/");
    }
  }

  @FXML
  private void keyPressHandle(KeyEvent event) {
    System.out.println("Key pressed");
    // Handle letter key press and number key press to the display output
    String currentText = displayOutput.getText();
    String key = event.getText();

    if (event.getCode().isLetterKey()) {
      key = key.toUpperCase();
    }

    if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
      // Add to display output if the key is a letter or number
      displayOutput.setText(currentText + key);
      addSlashIfEnteredCurrentCity();
    } else if (event.getCode().equals(KeyCode.BACK_SPACE)) {
      if (currentText.length() > 0) {
        displayOutput.setText(currentText.substring(0, currentText.length() - 1));
      }

    } else if (event.getCode() == KeyCode.SLASH) {
      // Handle the slash key ("/")
      displayOutput.setText(currentText + "/");
    }
  }

  @FXML
  private void handleLetterClick(MouseEvent event) {
    Rectangle letterRectangle = (Rectangle) event.getSource();
    // Get the ID of the clicked rectangle
    String upperLetter = letterRectangle.getId().toUpperCase();
    System.out.println("Letter clicked: " + upperLetter);

    // Append the clicked letter to the existing text
    String currentText = displayOutput.getText();
    displayOutput.setText(currentText + upperLetter);
    addSlashIfEnteredCurrentCity();
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
  private void handleNumberClick(MouseEvent event) {
    Rectangle numberRectangle = (Rectangle) event.getSource();
    String number = numberRectangle.getId(); // Get the ID of the clicked rectangle
    // last character of the string is the number
    String currentText = displayOutput.getText();
    if (currentText.length() > 0) {
      displayOutput.setText(currentText + number.substring(number.length() - 1));
    }
    addSlashIfEnteredCurrentCity();
  }

  protected void typeTextEffect(Text text, String message, int delay) {
    text.setText(""); // Clear the text first
    int messageLength = message.length();

    Timeline timeline = new Timeline();

    for (int i = 0; i < messageLength; i++) {
      final int index = i;
      KeyFrame keyFrame = new KeyFrame(
          Duration.millis(delay * i),
          new KeyValue(text.textProperty(), message.substring(0, index + 1)));
      timeline.getKeyFrames().add(keyFrame);
    }

    timeline.play();
  }

  protected void enableFlightComputer() {
    centralDisplayUnit.setOpacity(1);
    lock.setVisible(false);
    lock.setDisable(true);
    displayOutput.setDisable(false);

    displayOutput.requestFocus();
    Platform.runLater(
        () -> {
          displayOutput.requestFocus();
        });
    // Enable the key press event

    for (Rectangle button : allButtons) {
      button.setDisable(false);
    }
    String message = "ENTER THE FIRST TWO LETTER OF DEP / DEST CITY THEN PRESS EXEC. E.g SY/ME";
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
    String firstTwoDestnation = GameState.arrangedDestnationCity.substring(0, 2).toUpperCase();
    String firstTwoDeparture = GameState.currentCities[GameState.currentCityIndex - 1]
        .getText().substring(0, 2);

    System.out.println("EXECUTED: " + currentInput);
    System.out.println("CORRECT ANSWER: " + firstTwoDeparture + "/" + firstTwoDestnation);

    // Correct code has been entered, i.e., first two letters of the departure city
    // and first two
    // letters of the destination city
    if (currentInput.equalsIgnoreCase(firstTwoDeparture + "/" + firstTwoDestnation)) {
      // Aircraft code has been found.
      GameState.isAircraftCodeFound = true;
      displayInput.setText("");
      displayInput.setVisible(true);
      progress.setVisible(true);
      progress.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);

      GameState.eleanorAi.runGpt(
          GptPromptEngineeringRoom3.getAircraftCode(),
          (result) -> {
            System.out.println(result);
            Platform.runLater(
                () -> {
                  // Hide the progress indicator
                  progress.setVisible(false);
                  // Find the start and end indices of the aircraft code within single quotes
                  int startIndex = result.indexOf("^");
                  int endIndex = result.indexOf("^", startIndex + 1);

                  if (startIndex != -1 && endIndex != -1) {
                    // Extract the aircraft code
                    GameState.aircraftCode = result.substring(startIndex + 1, endIndex);
                  }
                  // Update the introduction label with the correct answer message
                  displayInput.setText(
                      "CONGRATULATIONS! AIRCRAFT CODE UNLOCKED: " + GameState.aircraftCode);

                  // Set the aircraft code image to inventory.

                  Image aircraftCode = new Image("/images/aircraft_code.png");

                  MainGame.addObtainedItem(aircraftCode, "aircraftCode");

                  System.out.println("Aircraft code unlocked" + GameState.aircraftCode);
                });
          });

      // Clear the input field and disable it along with the execute button
      displayOutput.setText("");
      displayOutput.setDisable(true);
      execute.setDisable(true);
    } else {
      // Display an error message
      displayInput.setStyle("-fx-text-fill: red;");

      GameState.eleanorAi.runGpt(
          "User update: the player needs to enter the first two letters of current city /"
              + " destnation city in the flight computer but now  it is incorrect. Do not"
              + " respond.");
      displayInput.setText("INCORRECT ANSWER TRY AGAIN");
      displayOutput.setText("");
    }
  }
}
