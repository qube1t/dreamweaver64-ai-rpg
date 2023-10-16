package nz.ac.auckland.se206.mobility;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * The CharacterMovement class represents the movement of a character in the
 * game.
 * It contains methods to check for collision between the player and obstacles
 * or interactables,
 * to check the proximity of the player to interactable nodes and update their
 * visibility and style accordingly,
 * and to move the player in the specified direction based on the given action.
 */
public class CharacterMovement {
  private AnchorPane player;
  private Rectangle playerBound;
  private Circle proximityBound;
  private List<Rectangle> obstacles;
  private ObservableList<Node> interactables;

  /**
   * Represents the movement of a character in the game.
   *
   * @param player         the AnchorPane representing the player
   * @param playerBound    the Rectangle representing the player's bounds
   * @param proximityBound the Circle representing the player's proximity bounds
   * @param obstacles      the List of Rectangles representing the obstacles in
   *                       the game
   * @param interactables  the ObservableList of Nodes representing the
   *                       interactable objects in the game
   */
  public CharacterMovement(
      AnchorPane player,
      Rectangle playerBound,
      Circle proximityBound,
      List<Rectangle> obstacles,
      ObservableList<Node> interactables) {
    this.player = player;
    this.playerBound = playerBound;
    this.proximityBound = proximityBound;
    this.obstacles = obstacles;
    this.interactables = interactables;
  }

  /**
   * Checks for collision between the player and obstacles or interactables.
   *
   * @return true if there is a collision, false otherwise.
   */
  protected boolean checkCollision() {
    for (Rectangle obstacle : obstacles) {
      if (player
          .localToParent(playerBound.getBoundsInParent())
          .intersects(obstacle.getBoundsInParent())) {
        return true;
      }
    }

    for (Node interactable : interactables) {
      if (player
          .localToParent(playerBound.getBoundsInParent())
          .intersects(interactable.getBoundsInParent())) {
        if (interactable.getId() != null)
          if (interactable.getId().equals("rightDoorBtn") || interactable.getId().equals("leftDoorBtn")
              || interactable.getId().equals("mainDoorBtn")) {
            if (!interactable.isDisable()) {
              interactable.getOnMouseClicked().handle(null);
            }
          }
      }
    }
    return false;
  }

  /**
   * Checks the proximity of the player to interactable nodes and updates their
   * visibility and style accordingly.
   */
  private void checkProximity() {
    // check intersection of outer circle with rectangles
    for (Node interactable : interactables) {
      if (player
          .localToParent(proximityBound.getBoundsInParent())
          .intersects(interactable.getBoundsInParent())) {
        interactable.setVisible(true);
        // if interactable is a rectangle
        if (interactable instanceof Rectangle) {
          interactable.getStyleClass().add("action-btn");
        }
        // interactable.getStyleClass().add("action-btn");
      } else {
        interactable.setVisible(false);
        if (interactable instanceof Rectangle)
          interactable.getStyleClass().remove("action-btn");
      }
    }
  }

  /**
   * Moves the player in the specified direction based on the given action.
   *
   * @param action an integer representing the direction to move the player in:
   *               0 for up, 1 for left, 2 for down, and 3 for right
   */
  public void movePlayer(int action) {
    double playerSpeed = 8.0; // Adjust the player's speed as needed
    double dx = 0;
    double dy = 0;

    double oldX = player.getLayoutX();
    double oldY = player.getLayoutY();

    // Adjust the character's position based on the keys pressed.
    if (action == 0) {
      dy -= playerSpeed;
    }
    if (action == 2) {
      dy += playerSpeed;
    }
    if (action == 3) {
      dx += playerSpeed;
    }
    if (action == 1) {
      dx -= playerSpeed;
    }
    // Set the new player positon
    player.setLayoutX(player.getLayoutX() + dx);
    player.setLayoutY(player.getLayoutY() + dy);

    // System.out.println();

    // Return to the old position if there is a collision
    if (checkCollision()) {
      player.setLayoutX(oldX);
      player.setLayoutY(oldY);
    }

    checkProximity();
  }
}
