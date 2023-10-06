package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;

public class BookShelfController {
  private static Label[] lblBooks;
  private static ImageView[] bookRects;

  /**
   * Returns the book to the bookshelf.
   * 
   */
  public static void returnBook() {
    // return the book to the bookshelf using for loop.
    for (int i = 0; i < GameState.booksInRoom1.length; i++) {
      if (GameState.booksInRoom1[i] == null) {
        lblBooks[i].setVisible(true);
        bookRects[i].setVisible(true);
        GameState.booksInRoom1[i] = GameState.takenBook;
        GameState.takenBook = null;
        lblBooks[i].setText(GameState.booksInRoom1[i]);
        break;
      }
    }
  }

  @FXML
  private Label lblBook1;
  @FXML
  private Label lblBook2;
  @FXML
  private Label lblBook3;
  @FXML
  private Label lblBook4;
  @FXML
  private Label lblBook5;
  @FXML
  private Label lblBook6;
  @FXML
  private Label lblBook7;

  @FXML
  private ImageView book1Img;
  @FXML
  private ImageView book2Img;
  @FXML
  private ImageView book3Img;
  @FXML
  private ImageView book4Img;
  @FXML
  private ImageView book5Img;
  @FXML
  private ImageView book6Img;
  @FXML
  private ImageView book7Img;

  public void initialize() {
    //
    lblBooks = new Label[] { lblBook1, lblBook2, lblBook3, lblBook4, lblBook5, lblBook6, lblBook7 };

    bookRects = new ImageView[] {
        book1Img, book2Img, book3Img, book4Img, book5Img, book6Img, book7Img
    };

    // setting labels in the bookshelf
    for (int i = 0; i < GameState.booksInRoom1.length; i++) {
      Label lblBook = lblBooks[i];
      if (GameState.booksInRoom1[i] != null) {
        lblBook.setText(GameState.booksInRoom1[i]);
      } else {
        lblBook.setVisible(false);
        bookRects[i].setVisible(false);
        continue;
      }

      ImageView bookRect = bookRects[i];
      int index = i;
      lblBooks[i].setOnMouseClicked(
          e -> {
            boolean oneWasTaken = hasTakenOneBook();
            if (oneWasTaken) {
              returnBook();
            }
            GameState.takenBook = lblBook.getText();

            if (GameState.booksInRoom1[index] == GameState.trueBook) {
              GameState.isBookFound = true;
              System.out.println("true book found");
            } else {
              GameState.isBookFound = false;
            }

            if (!oneWasTaken) {
              Image bookImage = new Image(App.class.getResource("/images/rooms/room1/book.png")
                  .toString());
              MainGameController.addObtainedItem(bookImage, "book");
            }
            lblBook.setVisible(false);
            bookRect.setVisible(false);
            GameState.booksInRoom1[index] = null;
          });
    }
  }

  /**
   * This method checks whether the player has taken one book.
   * 
   * @return true if the player has taken one book, false otherwise
   */
  private boolean hasTakenOneBook() {
    // check whether the player has taken one book using if.
    if (lblBook1.isVisible()
        && lblBook2.isVisible()
        && lblBook3.isVisible()
        && lblBook4.isVisible()
        && lblBook5.isVisible()
        && lblBook6.isVisible()
        && lblBook7.isVisible()) {
      return false;
    }
    return true;
  }

}
