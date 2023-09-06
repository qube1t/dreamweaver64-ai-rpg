package nz.ac.auckland.se206.mobility;

import java.util.List;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class CharacterMovement {
  private AnchorPane player;
  private Rectangle playerBound;
  List<Rectangle> obstacles;

  public CharacterMovement(AnchorPane player, Rectangle playerBound, List<Rectangle> obstacles) {
    this.player = player;
    // this.playerBound = playerBound;
    this.obstacles = obstacles;
  }

  protected boolean checkCollision() {
    for (Rectangle obstacle : obstacles) {
      if (player.getBoundsInParent().intersects(obstacle.getBoundsInParent())) {
        return true;
      }
    }
    return false;
  }

  public void movePlayer(int action) {
    double playerSpeed = 5.0; // Adjust the player's speed as needed
    double dx = 0, dy = 0;

    double oldX = player.getLayoutX();
    double oldY = player.getLayoutY();
    // double oldBoundX = playerBound.getX();
    // double oldBoundY = playerBound.getY();

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
    // playerBound.setX(playerBound.getX() + dx);
    // playerBound.setY(playerBound.getY() + dy);

    // Return to the old position if there is a collision
    // if (checkCollision()) {
    //   player.setLayoutX(oldX);
    //   player.setLayoutY(oldY);
    // playerBound.setX(oldBoundX);
    // playerBound.setY(oldBoundY);
    // }
  }
}
