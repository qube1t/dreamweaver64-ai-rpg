package nz.ac.auckland.se206.components;

import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import nz.ac.auckland.se206.controllers.DestnationPuzzleController;

/**
 * A custom Label class that can be dragged and dropped.
 */
public class DraggableLetter extends Label {

  /**
   * A custom Label class that can be dragged and dropped.
   * It allows the user to drag and drop the label to another location.
   *
   * @param text       the text to be displayed on the label
   * @param hbox       the HBox that the label is in
   * @param controller the controller for the destination puzzle
   */
  public DraggableLetter(String text, HBox hbox, DestnationPuzzleController controller) {
    super(text);

    Font font = Font.font("Arial", 45);
    setFont(font);

    setOnDragDetected(
        event -> {
          System.out.println("drag detected");
          Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
          ClipboardContent content = new ClipboardContent();
          content.putString(getText()); // Copy the text of the label
          dragboard.setContent(content);
          event.consume();
          controller.setLetterCursor(1);
        });

    setOnDragOver(
        event -> {
          System.out.println("drag over");
          if (event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
          }
          controller.setLetterCursor(1);
          event.consume();
        });

    setOnDragDropped(
        event -> {
          if (event.getDragboard().hasString()) {
            String draggedText = event.getDragboard().getString();
            Label sourceLabel = (Label) event.getGestureSource();
            // Swap the text of the labels
            String temp = getText();
            setText(draggedText);
            sourceLabel.setText(temp);
            controller.setLetterCursor(0);
          }
          event.consume();
        });

  }
}
