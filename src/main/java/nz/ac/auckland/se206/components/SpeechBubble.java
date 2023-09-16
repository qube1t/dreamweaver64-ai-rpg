package nz.ac.auckland.se206.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class SpeechBubble extends HBox {
  private Label displayedText;

  public SpeechBubble(String message) {
    this(message, Pos.TOP_LEFT);
  }

  public SpeechBubble(String message, Pos position) {
    this.displayedText = new Label(message);
    this.displayedText.setPadding(new Insets(10));
    this.displayedText.setWrapText(true);
    configureSpeechBubble(position);
    this.displayedText.setMaxWidth(100); // Allow the text to expand horizontally

    // Debugging statements
    System.out.println("Text: " + message);
    System.out.println("Visibility: " + displayedText.isVisible());
  }

  private void configureSpeechBubble(Pos position) {
    displayedText.setMaxWidth(Double.MAX_VALUE); // Allow the text to expand horizontally

    // Customize the appearance of the speech bubble based on the position
    if (position == Pos.TOP_LEFT || position == Pos.BOTTOM_LEFT) {
      displayedText.setAlignment(Pos.CENTER_LEFT);
    } else if (position == Pos.TOP_RIGHT || position == Pos.BOTTOM_RIGHT) {
      displayedText.setAlignment(Pos.CENTER_RIGHT);
    } else {
      displayedText.setAlignment(Pos.CENTER);
    }

    getChildren().setAll(displayedText);
    setAlignment(position);
  }
}
