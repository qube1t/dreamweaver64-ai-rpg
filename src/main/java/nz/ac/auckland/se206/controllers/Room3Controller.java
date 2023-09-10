package nz.ac.auckland.se206.controllers;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class Room3Controller {
  @FXML private ImageView Aircraft;

  // Zooms in the aircraft image when click on it and click again to zoom out.
  /**
   * This method creates the zoom in animation for the aircraft image.
   *
   * @param imageView the image view of the aircraft
   * @return the zoom in animation
   */
  private ScaleTransition createZoomInAnimation(ImageView imageView) {
    ScaleTransition animation = new ScaleTransition(Duration.seconds(1), imageView);
    animation.setFromX(1.0); // Start from the original size
    animation.setFromY(1.0);
    animation.setToX(2.0); // Zoom-in to 2 times the original size
    animation.setToY(2.0);
    animation.setRate(-1);
    return animation;
  }

  /**
   * Handles the click event on the painting.
   *
   * @param event the mouse event
   */
  @FXML
  public void onClickAircraft(MouseEvent event) {
    handleAircraftClick(event.getSource()); // Pass the source of the event
  }

  /**
   * Implments the logic for handling the click event on the painting.
   *
   * @param source the source of the event
   */
  private void handleAircraftClick(Object source) {
    ImageView paintingImageView = null;

    if (source instanceof ImageView) {
      paintingImageView = (ImageView) source;
    }

    if (paintingImageView != null) {
      ScaleTransition zoomInAnimation = null;

      // If the painting is already zoomed-in, zoom-out
      if (zoomInAnimation.getRate() == -1) {
        zoomInAnimation.setRate(1); // Set the animation rate to 1 to zoom-in
        System.out.println("zoom in");
      } else {
        // If the painting is already zoomed-in, zoom-out
        zoomInAnimation.setRate(-1); // Set the animation rate to -1 to zoom-out
        System.out.println("zoom out");
      }

      // Set the onFinished event of the zoomInAnimation to trigger the AI response
      zoomInAnimation.setOnFinished(finishedEvent -> {});

      zoomInAnimation.play();
    }
  }
}
