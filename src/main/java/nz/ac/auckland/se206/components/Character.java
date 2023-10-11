package nz.ac.auckland.se206.components;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.Animation;
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

  private Timer movementTimer;

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
  }

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

  public void setAction(int a) {
    action = a;

    if (animating != true) {
      initElements();
    }
    ;
  }

  public void enableMobility(List<Rectangle> obstacles, ObservableList<Node> observableList) {
    // create character movement object
    movement = new CharacterMovement(this, playerBound, proximityBound, obstacles, observableList);
  }

  public void startMovement() {
    movementTimer = new Timer();
    movementTimer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        movement.movePlayer(action);
      }
    }, 0, 100);
  }

  public void stopMovement() {
    movementTimer.cancel();
  }

  public void move() {
    movement.movePlayer(action);
  }

  public void startAnimation() {
    if (animating != true) {
      animation.setCycleCount(Animation.INDEFINITE);
      animation.play();
      animating = true;
    }
  }

  public void endAnimation() {
    animation.stop();
    activeImg.setViewport(
        new Rectangle2D(offsetX, offsetY + 64 * action, frameWidth, frameHeight));
    animating = false;
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
