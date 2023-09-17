package nz.ac.auckland.se206.controllers;

import java.util.Arrays;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.components.Character;

public class Room3SubController {
  @FXML private Pane mainScene;

  @FXML private Rectangle errorWindow;
  @FXML private Text errorText;
  @FXML private Button tryAgain;

  @FXML private Character character;
  @FXML private Rectangle img1, img2, img3, img4;
  @FXML
  private Rectangle a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z;
  @FXML private Rectangle one1, two2, three3, four4, five5, six6, seven7, eight8, nine9, zero0;
  @FXML private Rectangle slash, clear, delete, execute;
  @FXML private Text displayInput, displayOutput, mapTxt;
  @FXML private ImageView currentFlightPlan, im1, im2, im3, im4, lock, CentralDisplayUnit;
  @FXML private Button back;
  protected int currentSelection;
  protected List<Rectangle> allButtons;
  protected int finalSelction;

  public void initialize() {

    disableErrorMessage();

    img2.setVisible(false);
    img3.setVisible(false);
    img4.setVisible(false);
    currentFlightPlan.setVisible(false);
    currentSelection = 1;
    CentralDisplayUnit.setOpacity(0.3);

    List<Rectangle> allButtons =
        Arrays.asList(
            a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, q, r, s, t, u, v, w, x, y, z, slash,
            execute, clear, delete, one1, two2, three3, four4, five5, six6, seven7, eight8, nine9,
            zero0);
    this.allButtons = allButtons;
    for (Rectangle letter : allButtons) {
      letter.setDisable(true);
    }
  }

  @FXML
  private void onGoBack() {
    App.setUi("main_game");
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
  private void onClickTryAgain() {
    disableErrorMessage();
    mainScene.requestFocus();
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
    String message = "ENTER DEP / DEST AIRPORT CODE E.g.WLG/AKL THEN PRESS EXEC";
    int typingDelay = 50;
    typeTextEffect(displayInput, message, typingDelay);
  }

  protected void disableMap() {
    currentFlightPlan.setVisible(true);
    mapTxt.setVisible(false);
    im1.setVisible(false);
    im2.setVisible(false);
    im3.setVisible(false);
    im4.setVisible(false);
    img1.setVisible(false);
    img2.setVisible(false);
    img3.setVisible(false);
    img4.setVisible(false);
    im1.setDisable(true);
    im2.setDisable(true);
    im3.setDisable(true);
    im4.setDisable(true);
    img1.setDisable(true);
    img2.setDisable(true);
    img3.setDisable(true);
    img4.setDisable(true);
  }

  @FXML
  /**
   * Handle execute button click
   *
   * @param event
   */
  public void handleExecuteClick() {
    String currentInput = displayOutput.getText();
    if (currentInput.contains("HND/SYD")) {
      // Aircraft code has been found.
      GameState.isAircraftCodeFound = true;
      displayInput.setVisible(true);

      // Create a TextFlow to hold different styled Text elements
      // TextFlow messageFlow = new TextFlow();

      // Create a Text element for the "CONGRATULATIONS! THE AIRCRAFT CODE IS " part
      // Text messagePart1 = new Text("CONGRATULATIONS! THE AIRCRAFT CODE IS ");

      // Create a Text element for the "QR16" part and apply styling
      // Text codePart = new Text("QR16");
      // codePart.setFill(Color.RED);
      // codePart.setFont(Font.font("System", FontWeight.BOLD, 14));

      // Create a Text element for the " GOOD LUCK ON YOUR ESCAPE!" part
      // Text messagePart2 = new Text(" GOOD LUCK ON YOUR ESCAPE!");

      // Add the Text elements to the TextFlow
      // messageFlow.getChildren().addAll(messagePart1, codePart, messagePart2);

      // Set the styled TextFlow as the content of displayInput
      displayInput.setText("CONGRATULATIONS! THE AIRCRAFT CODE IS QR16 GOOD LUCK ON YOUR ESCAPE!");

      // Clear the input field and disable it along with the execute button
      displayOutput.setText("");
      displayOutput.setDisable(true);
      execute.setDisable(true);
    } else {
      // Display an error message
      displayInput.setStyle("-fx-text-fill: red;");
      displayInput.setText("INCORRECT AIRPORT CODE TRY AGAIN");
      displayOutput.setText("");
    }
  }

  /**
   * Handles the key pressed event.
   *
   * @param event the key event
   */
  @FXML
  public void press(KeyEvent event) {
    // Handle arrow key presses
    switch (event.getCode()) {
      case LEFT:
        if (finalSelction == 2) {
          break;
        }
        System.out.println("left");
        if (currentSelection == 2) {
          img2.setVisible(false);
          img1.setVisible(true);
          currentSelection = 1;
        } else if (currentSelection == 3) {
          img3.setVisible(false);
          img2.setVisible(true);
          currentSelection = 2;
        } else if (currentSelection == 4) {
          img4.setVisible(false);
          img3.setVisible(true);
          currentSelection = 3;
        }
        break;
      case RIGHT:
        if (finalSelction == 2) {
          break;
        }
        System.out.println("right");
        if (currentSelection == 1) {
          img1.setVisible(false);
          img2.setVisible(true);
          currentSelection = 2;
        } else if (currentSelection == 2) {
          img2.setVisible(false);
          img3.setVisible(true);
          currentSelection = 3;
        } else if (currentSelection == 3) {
          img3.setVisible(false);
          img4.setVisible(true);
          currentSelection = 4;
        }
        break;
      case UP:
        if (finalSelction == 2) {
          break;
        }
        System.out.println("up");
        if (currentSelection == 3) {
          img3.setVisible(false);
          img1.setVisible(true);
          currentSelection = 1;
        } else if (currentSelection == 4) {
          img4.setVisible(false);
          img2.setVisible(true);
          currentSelection = 2;
        }
        break;
      case DOWN:
        if (finalSelction == 2) {
          break;
        }
        System.out.println("down");
        if (currentSelection == 1) {
          img1.setVisible(false);
          img3.setVisible(true);
          currentSelection = 3;
        } else if (currentSelection == 2) {
          img2.setVisible(false);
          img4.setVisible(true);
          currentSelection = 4;
        }
        break;
      case ENTER:
        if (currentSelection == 1) {
          System.out.println("1");
          enableErrorMessage();
        } else if (currentSelection == 2) {
          if (GameState.isCorrectRouteFound) {
            break;
          }
          GameState.isCorrectRouteFound = true;
          disableMap();
          enableFlightCDU();
          System.out.println("2");

          finalSelction = 2;
        } else if (currentSelection == 3) {
          System.out.println("3");
          enableErrorMessage();
        } else if (currentSelection == 4) {
          System.out.println("4");
          enableErrorMessage();
        }
        break;
      default:
        break;
    }
  }

  private void enableErrorMessage() {
    errorWindow.setVisible(true);
    errorText.setVisible(true);
    tryAgain.setVisible(true);
  }

  private void disableErrorMessage() {
    errorWindow.setVisible(false);
    errorText.setVisible(false);
    tryAgain.setVisible(false);
  }
}
