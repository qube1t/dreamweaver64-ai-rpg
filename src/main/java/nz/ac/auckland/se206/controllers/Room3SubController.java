package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.components.Character;

public class Room3SubController {

  @FXML private Character character;
  @FXML private Rectangle img1, img2, img3, img4;
  @FXML
  private Rectangle a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z;
  @FXML private Rectangle one1, two2, three3, four4, five5, six6, seven7, eight8, nine9, zero0;
  @FXML private Rectangle slash, clear, delete;
  @FXML private Text displayInput;
  protected int currentSelection;

  public void initialize() {
    img1.setVisible(true);
    img2.setVisible(false);
    img3.setVisible(false);
    img4.setVisible(false);
    currentSelection = 1;
  }

  @FXML
  private void handleLetterClick(MouseEvent event) {
    Rectangle letterRectangle = (Rectangle) event.getSource();
    String upperLetter =
        letterRectangle.getId().toUpperCase(); // Get the ID of the clicked rectangle
    System.out.println("Letter clicked: " + upperLetter);

    // Append the clicked letter to the existing text
    String currentText = displayInput.getText();
    displayInput.setText(currentText + upperLetter);
  }

  @FXML
  private void handleSlashClick(MouseEvent event) {
    String currentText = displayInput.getText();
    displayInput.setText(currentText + "/");
  }

  @FXML
  private void handleDeleteClick(MouseEvent event) {
    String currentText = displayInput.getText();
    if (currentText.length() > 0) {
      displayInput.setText(currentText.substring(0, currentText.length() - 1));
    }
  }

  @FXML
  private void handleClearClick(MouseEvent event) {
    displayInput.setText("");
  }

  @FXML
  private void handleNumberClick(MouseEvent event) {
    Rectangle numberRectangle = (Rectangle) event.getSource();
    String number = numberRectangle.getId(); // Get the ID of the clicked rectangle
    // last character of the string is the number
    String currentText = displayInput.getText();
    if (currentText.length() > 0) {
      displayInput.setText(currentText + number.substring(number.length() - 1));
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
      case ENTER:
        if (currentSelection == 1) {
          System.out.println("1");
        } else if (currentSelection == 2) {
          System.out.println("2");
        } else if (currentSelection == 3) {
          System.out.println("3");
        } else if (currentSelection == 4) {
          System.out.println("4");
        }
        break;
      default:
        break;
    }
  }
}
