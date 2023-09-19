package nz.ac.auckland.se206.components;

import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class DraggableLetter extends Label {

  private HBox hbox;

  public DraggableLetter(String text, HBox hbox) {
    super(text);
    this.hbox = hbox;

    Font font = Font.font("Arial", 40);
    setFont(font);

    setOnDragDetected(
        event -> {
          System.out.println("drag detected");
          Dragboard dragboard = startDragAndDrop(TransferMode.ANY);
          ClipboardContent content = new ClipboardContent();
          content.putString(getText()); // Copy the text of the label
          dragboard.setContent(content);
          event.consume();
        });

    setOnDragOver(
        event -> {
          System.out.println("drag over");
          if (event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
          }
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
          }
          event.consume();
        });
  }
}
