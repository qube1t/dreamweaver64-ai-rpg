package nz.ac.auckland.se206.components;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.Animation;
import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.mobility.CharacterMovement;
import nz.ac.auckland.se206.mobility.SpriteAnimation;

/**
 * The Character class extends AnchorPane and represents a character in the game.
 * It contains methods for assigning a sprite sheet, enabling mobility, playing footstep sounds,
 * starting and ending animations, and initializing elements.
 * It also has instance variables for the character's action, columns, count, offset x and y,
 * frame width and height, animation, and movement.
 */
public class Character extends AnchorPane {

  private static int action = 0;

  @FXML
  private ImageView activeImg;
  @FXML
  private Rectangle playerBound;
  @FXML
  private Circle proximityBound;

  private int columns;
  private int count;
  private int offsetX;
  private int offsetY;
  private int frameWidth;
  private int frameHeight;

  private SpriteAnimation animation;
  private CharacterMovement movement;

  private boolean animating = false;

  private AudioClip footstepSound = new AudioClip(
      (new Media(App.class.getResource("/sounds/walkingSound.mp3").toString()))
          .getSource());

  private Timer movementTimer = new Timer();

  /**
   * The Character class represents a character in the game. It extends the JavaFX Pane class and
   * contains the character's sprite and movement logic.
   *
   * @param columns the number of columns in the sprite sheet
   * @param count the total number of frames in the sprite sheet
   * @param offsetX the x-coordinate offset of the sprite sheet
   * @param offsetY the y-coordinate offset of the sprite sheet
   * @param frameWidth the width of each frame in the sprite sheet
   * @param frameHeight the height of each frame in the sprite sheet
   */
  public Character(
      @NamedArg("columns") int columns,
      @NamedArg("count") int count,
      @NamedArg("offset_x") int offsetX,
      @NamedArg("offset_y") int offsetY,
      @NamedArg("frame_width") int frameWidth,
      @NamedArg("frame_height") int frameHeight) {

    this.columns = columns;
    this.count = count;
    this.offsetX = offsetX;
    this.offsetY = offsetY;
    this.frameWidth = frameWidth;
    this.frameHeight = frameHeight;

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/character.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    initElements();

    movementTimer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        if (animating) {
          Platform.runLater(() -> {
            movement.movePlayer(action);
          });
        }
      }
    }, 0, 100);
  }

  /**
   * Assigns the sprite sheet for the character based on the current character index in the game state.
   * If the character index is not 1, 2, 3, or 4, the default sprite sheet (char1.png) is assigned.
   */
  public void assignSpriteSheet() {
    // assign character sprite sheet
    switch (GameState.characterIndex) {
      case 1:
        activeImg.setImage(new Image("/images/main_game/character/char1.png"));
        break;
      case 2:
        activeImg.setImage(new Image("/images/main_game/character/char2.png"));
        break;
      case 3:
        activeImg.setImage(new Image("/images/main_game/character/char3.png"));
        break;
      case 4:
        activeImg.setImage(new Image("/images/main_game/character/char4.png"));
        break;
      default:
        activeImg.setImage(new Image("/images/main_game/character/char1.png"));
        break;
    }
  }

  public Rectangle getPlayerBound() {
    return playerBound;
  }

  public boolean isAnimating() {
    return animating;
  }

  public int getAction() {
    return action;
  }

  /**
   * Sets the action of this character to the given integer value.
   * If the character is not currently animating, initializes the character's elements.
   *
   * @param a the integer value representing the action to set
   */
  public void setAction(int a) {
    action = a;

    if (animating != true) {
      initElements();
    }

  }

  /**
   * Enables mobility for the character by creating a new CharacterMovement object.
   * @param obstacles a list of Rectangles representing obstacles that the character must avoid
   * @param observableList an ObservableList of Nodes representing the nodes that the character must avoid
   */
  public void enableMobility(List<Rectangle> obstacles, ObservableList<Node> observableList) {
    // create character movement object
    movement = new CharacterMovement(this, playerBound, proximityBound, obstacles, observableList);
  }

  /**
   * Plays the footstep sound effect if the game is not muted and the sound effect is not already playing.
   * Adds the sound effect to the list of sound effects in the GameState.
   */
  public void playFootSteps() {
    if (!GameState.isMuted && !footstepSound.isPlaying()) {
      footstepSound.setCycleCount(AudioClip.INDEFINITE);
      footstepSound.setVolume(.35);
      footstepSound.play();
      GameState.soundFx.add(footstepSound);
    }

  }

  public void endFootSteps() {
    footstepSound.stop();
  }

  public void move() {
    movement.movePlayer(action);
  }

  /**
   * Starts the animation for the character if it is not already animating.
   * Sets the cycle count to indefinite and plays the animation.
   * Also plays the footstep sound effect.
   */
  public void startAnimation() {
    if (animating != true) {
      animating = true;
      animation.setCycleCount(Animation.INDEFINITE);
      animation.play();
      playFootSteps();
    }
  }

  /**
   * Stops the animation and sets the active image viewport to the last frame of the animation.
   * If endSound is true, it also ends the footstep sound.
   *
   * @param endSound a boolean indicating whether to end the footstep sound or not
   */
  public void endAnimation(boolean endSound) {
    animating = false;
    animation.stop();
    activeImg.setViewport(
        new Rectangle2D(offsetX, offsetY + 64 * action, frameWidth, frameHeight));
    if (endSound) {
      endFootSteps();
    }
  }

  public int getColumns() {
    return columns;
  }

  public void setColumns(int columns) {
    this.columns = columns;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public int getOffsetX() {
    return offsetX;
  }

  public void setOffsetX(int offsetX) {
    this.offsetX = offsetX;
  }

  public int getOffsetY() {
    return offsetY;
  }

  public void setOffsetY(int offsetY) {
    this.offsetY = offsetY;
  }

  public int getFrameWidth() {
    return frameWidth;
  }

  public void setFrameWidth(int frameWidth) {
    this.frameWidth = frameWidth;
  }

  public int getFrameHeight() {
    return frameHeight;
  }

  public void setFrameHeight(int frameHeight) {
    this.frameHeight = frameHeight;
  }

  /**
   * Initializes the character's sprite sheet and animation.
   */
  private void initElements() {
    // init character
    assignSpriteSheet();
    activeImg.setViewport(
        new Rectangle2D(offsetX, offsetY + 64 * action, frameWidth, frameHeight));

    // define animation
    animation = new SpriteAnimation(
        activeImg,
        Duration.millis(800),
        count,
        columns,
        offsetX,
        offsetY + 64 * action,
        frameWidth,
        frameHeight);
  }
}
