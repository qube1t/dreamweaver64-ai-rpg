package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

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

  public void initialize() {
    Label[] lbl_books = {
      lbl_book1, lbl_book2, lbl_book3, lbl_book4, lbl_book5, lbl_book6, lbl_book7
    };
    Rectangle[] book_rects = {
      book1_rect, book2_rect, book3_rect, book4_rect, book5_rect, book6_rect, book7_rect
    };

    for (int i = 0; i < lbl_books.length; i++) {
      Label lbl_book = lbl_books[i];
      Rectangle book_rect = book_rects[i];
      lbl_books[i].setOnMouseClicked(
          e -> {
            if (!hasTakenOneBook()) {
              lbl_book.setVisible(false);
              book_rect.setVisible(false);
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
}
