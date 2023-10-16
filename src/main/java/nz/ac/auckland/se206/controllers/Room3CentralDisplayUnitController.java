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

/**
 * This class is the controller for the central display unit in Room 3 of the
 * game.
 * It handles key press events on the display unit, adds the pressed key to the
 * display output if it is a letter or number,
 * removes the last character from the display output if the pressed key is the
 * backspace key,
 * adds a slash to the display output if the pressed key is the slash key ("/"),
 * and initializes the controller by adding an event filter to the TextField to
 * consume key events,
 * setting the visibility of progress to false, setting all buttons to a list,
 * and enabling/disabling buttons based on whether the aircraft code has been
 * found or not.
 */
public class Room3CentralDisplayUnitController {

  private List<Rectangle> allButtons;

  @FXML
  private Text errorText;
  @FXML
  private Character character;
  @FXML
  private Rectangle letterA;
  @FXML
  private Rectangle letterB;
  @FXML
  private Rectangle letterC;
  @FXML
  private Rectangle letterD;
  @FXML
  private Rectangle letterE;
  @FXML
  private Rectangle letterF;
  @FXML
  private Rectangle letterG;
  @FXML
  private Rectangle letterH;
  @FXML
  private Rectangle letterI;
  @FXML
  private Rectangle letterJ;
  @FXML
  private Rectangle letterK;
  @FXML
  private Rectangle letterL;
  @FXML
  private Rectangle letterM;
  @FXML
  private Rectangle letterN;
  @FXML
  private Rectangle letterO;
  @FXML
  private Rectangle letterP;
  @FXML
  private Rectangle letterQ;
  @FXML
  private Rectangle letterR;
  @FXML
  private Rectangle letterS;
  @FXML
  private Rectangle letterT;
  @FXML
  private Rectangle letterU;
  @FXML
  private Rectangle letterV;
  @FXML
  private Rectangle letterW;
  @FXML
  private Rectangle letterX;
  @FXML
  private Rectangle letterY;
  @FXML
  private Rectangle letterZ;
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

  /**
   * Initializes the Room3CentralDisplayUnitController by adding an event filter
   * to the TextField to consume key events,
   * setting the visibility of progress to false, setting all buttons to a list,
   * and enabling/disabling buttons based on
   * whether the aircraft code has been found or not.
   *
   * @throws ApiProxyException if there is an issue with the API proxy
   */
  public void initialize() throws ApiProxyException {

    // Add an event filter to the TextField to consume key events
    displayOutput.addEventFilter(
        KeyEvent.KEY_TYPED,
        keyEvent -> {
          keyEvent.consume();
        });

    progress.setVisible(false);

    List<Rectangle> allButtons = Arrays.asList(
        letterA, letterB, letterC, letterD, letterE, letterF,
        letterG,
        letterH, letterI, letterJ, letterK,
        letterL, letterM, letterN, letterO, letterP, letterQ,
        letterR, letterS, letterT, letterU,
        letterV, letterW, letterX, letterY, letterZ, slash,
        execute, clear, delete, one1, two2, three3, four4, five5,
        six6, seven7, eight8, nine9,
        zero0);
    this.allButtons = allButtons;

    // if (GameState.isPuzzleInRoom3Solved) {
    if (GameState.isAircraftCodeFound) {
      centralDisplayUnit.setOpacity(0.7);
      displayOutput.setText("");
      // Update the introduction label with the correct answer message
      displayInput.setText("AIRCRAFT CODE HAS OBTAINED. CDU IS NOW LOCKED");

      for (Rectangle button : allButtons) {
        button.setDisable(true);
      }
      displayOutput.setDisable(true);
    } else {
      enableFlightComputer(GameState.computerInIt);
    }
  }

  /**
   * This method automatically adds slash if coreect first two letter of current
   * location entered.
   */
  protected void addSlashIfEnteredCurrentCity() {

    String currentText = displayOutput.getText();
    // Convert the first two letters of the current city to uppercase
    String firstThreeDeparture = GameState.currentCities[GameState.currentCityIndex - 1]
        .getText()
        .substring(0, 3)
        .toUpperCase();
    // Add slash if the first two letters of the current city is entered
    if (currentText.equalsIgnoreCase(firstThreeDeparture)) {
      displayOutput.setText(currentText + "/");
    }
  }

  /**
   * Handles key press events on the display unit. Adds the pressed key to the
   * display output if it is a letter or number.
   * If the pressed key is the backspace key, removes the last character from the
   * display output.
   * If the pressed key is the slash key ("/"), adds a slash to the display
   * output.
   *
   * @param event The KeyEvent object representing the key press event.
   */
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

  /**
   * Handles the event when a letter is clicked on the display unit.
   * Gets the ID of the clicked rectangle and appends the corresponding letter to
   * the existing text.
   * If the letter is the first letter of a new city, adds a slash to separate the
   * cities.
   *
   * @param event The MouseEvent that triggered the method call.
   */
  @FXML
  private void handleLetterClick(MouseEvent event) {
    Rectangle letterRectangle = (Rectangle) event.getSource();
    // Get the ID - last character of the clicked rectangle
    String upperLetter = letterRectangle.getId()
        .substring(letterRectangle.getId().length() - 1)
        .toUpperCase();
    System.out.println("Letter clicked: " + upperLetter);

    // Append the clicked letter to the existing text
    String currentText = displayOutput.getText();
    displayOutput.setText(currentText + upperLetter);
    addSlashIfEnteredCurrentCity();
  }

  /**
   * Appends a forward slash to the current text in the display output.
   *
   * @param event The mouse event that triggered the method call.
   */
  @FXML
  private void handleSlashClick(MouseEvent event) {
    String currentText = displayOutput.getText();
    displayOutput.setText(currentText + "/");
  }

  /**
   * Handles the click event for the delete button. Removes the last character
   * from the displayOutput text field.
   *
   * @param event The MouseEvent that triggered this method.
   */
  @FXML
  private void handleDeleteClick(MouseEvent event) {
    String currentText = displayOutput.getText();
    if (currentText.length() > 0) {
      displayOutput.setText(currentText.substring(0, currentText.length() - 1));
    }
  }

  /**
   * Clears the text displayed on the output screen.
   *
   * @param event The mouse event that triggered this method.
   */
  @FXML
  private void handleClearClick(MouseEvent event) {
    displayOutput.setText("");
  }

  /**
   * Handles the click event of a number rectangle. Gets the ID of the clicked
   * rectangle and appends the last character of the ID to the displayOutput text.
   * If the displayOutput text is not empty, the number is appended to the end of
   * the text.
   * Calls addSlashIfEnteredCurrentCity() method after appending the number.
   *
   * @param event The MouseEvent that triggered the method call.
   */
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

  /**
   * This method is called when the user clicks on the execute button. It checks
   * if the entered code is correct or not. If the code is correct, it sets the
   * aircraft code as found, updates the display, adds the aircraft code image to
   * the inventory, and disables the input field and execute button. If the code
   * is incorrect, it displays an error message.
   *
   * @throws ApiProxyException if there is an error with the API proxy
   */
  @FXML
  public void handleExecuteClick() throws ApiProxyException {
    String currentInput = displayOutput.getText();
    String firstThreeDestnation = GameState.arrangedDestnationCity.substring(0, 3).toUpperCase();
    String firstThreeDeparture = GameState.currentCities[GameState.currentCityIndex - 1]
        .getText().substring(0, 3);

    System.out.println("EXECUTED: " + currentInput);
    System.out.println("CORRECT ANSWER: " + firstThreeDeparture + "/" + firstThreeDestnation);

    // Correct code has been entered, i.e., first two letters of the departure city
    // and first two
    // letters of the destination city
    if (currentInput.equalsIgnoreCase(firstThreeDeparture + "/" + firstThreeDestnation)) {
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

                  // Update the introduction label with the correct answer message
                  displayInput.setText(
                      result.toUpperCase());

                  // Set the aircraft code image to inventory.
                  Image aircraftCode = new Image("/images/aircraft_code.png");
                  MainGameController.addObtainedItem(aircraftCode, "code");
                  System.out.println("Aircraft code unlocked");
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
          "User update: the player needs to enter the first three letters of current city /"
              + " destnation city in the flight computer but now  it is incorrect. Do not respond");
      displayInput.setText("INCORRECT ANSWER TRY AGAIN");
      displayOutput.setText("");
    }
  }

  /**
   * This method creates a typing effect on the given Text object by displaying
   * the given message
   * character by character with a specified delay between each character.
   *
   * @param text    The Text object to display the typing effect on.
   * @param message The message to be displayed with the typing effect.
   * @param delay   The delay between each character in milliseconds.
   */
  private void typeTextEffect(Text text, String message, int delay) {
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

  /**
   * Enables the flight computer by setting the opacity of the central display
   * unit to 1, hiding the lock, disabling the lock, enabling the display output,
   * and enabling the key press event. Also sets the message to "ENTER THE FIRST
   * THREE LETTER OF DEP / DEST CITY THEN PRESS EXEC. E.g SYD/MEL" and displays it
   * using a type effect if it is the first time entering. Otherwise, sets the
   * message directly.
   *
   * @param inIt a boolean indicating whether it is the first time entering the
   *             flight computer
   */
  private void enableFlightComputer(boolean inIt) {
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

    String message = "ENTER THE FIRST THREE LETTER OF DEP / DEST CITY THEN PRESS EXEC. E.g SYD/MEL";
    int typingDelay = 50;
    // Only display type effect when first time enters.
    if (inIt == false) {
      typeTextEffect(displayInput, message, typingDelay);
      GameState.computerInIt = true;
    } else {
      displayInput.setText(message);
      System.out.println("true");
    }
  }

}
