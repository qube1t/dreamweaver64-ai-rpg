package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;

public class BookShelfController {
  @FXML private Label lbl_book1;
  @FXML private Label lbl_book2;
  @FXML private Label lbl_book3;
  @FXML private Label lbl_book4;
  @FXML private Label lbl_book5;
  @FXML private Label lbl_book6;
  @FXML private Label lbl_book7;

  @FXML private Rectangle book1_rect;
  @FXML private Rectangle book2_rect;
  @FXML private Rectangle book3_rect;
  @FXML private Rectangle book4_rect;
  @FXML private Rectangle book5_rect;
  @FXML private Rectangle book6_rect;
  @FXML private Rectangle book7_rect;

  static Label[] lbl_books;
  static Rectangle[] book_rects;

  public void initialize() {
    lbl_books =
        new Label[] {lbl_book1, lbl_book2, lbl_book3, lbl_book4, lbl_book5, lbl_book6, lbl_book7};

    book_rects =
        new Rectangle[] {
          book1_rect, book2_rect, book3_rect, book4_rect, book5_rect, book6_rect, book7_rect
        };

    for (int i = 0; i < GameState.booksInRoom1.length; i++) {
      Label lbl_book = lbl_books[i];
      if (GameState.booksInRoom1[i] != null) lbl_book.setText(GameState.booksInRoom1[i]);
      else {
        lbl_book.setVisible(false);
        book_rects[i].setVisible(false);
        continue;
      }

      Rectangle book_rect = book_rects[i];
      int index = i;
      lbl_books[i].setOnMouseClicked(
          e -> {
            if (!hasTakenOneBook()) {
              lbl_book.setVisible(false);
              book_rect.setVisible(false);
              if (GameState.booksInRoom1[index] == GameState.trueBook) {
                GameState.isBookFound = true;
              }
              Image bookImage =
                  new Image(App.class.getResource("/images/rooms/room1/book.png").toString());
              MainGame.addObtainedItem(bookImage);
              GameState.booksInRoom1[index] = null;
            } else {
              returnBook();
            }
          });
    }
  }

  private boolean hasTakenOneBook() {
    if (lbl_book1.isVisible()
        && lbl_book2.isVisible()
        && lbl_book3.isVisible()
        && lbl_book4.isVisible()
        && lbl_book5.isVisible()
        && lbl_book6.isVisible()
        && lbl_book7.isVisible()) {
      return false;
    }
    return true;
  }

  public static void returnBook() {
    for (int i = 0; i < GameState.booksInRoom1.length; i++) {
      if (GameState.booksInRoom1[i] == null) {
        lbl_books[i].setVisible(true);
        book_rects[i].setVisible(true);
        GameState.booksInRoom1[i] = lbl_books[i].getText();
        // lbl_book.setText(GameState.booksInRoom1[i]);
        break;
      }
    }
  }
}
