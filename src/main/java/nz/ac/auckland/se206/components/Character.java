package nz.ac.auckland.se206.components;

import java.io.IOException;
import java.util.List;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.mobility.CharacterMovement;
import nz.ac.auckland.se206.mobility.SpriteAnimation;

public class Character extends AnchorPane {
  @FXML private ImageView active_img;
  @FXML private Rectangle playerBound;
  @FXML private Circle proximityBound;

  public Rectangle getPlayerBound() {
    return playerBound;
  }

  private int columns;
  private int count;
  private int offset_x;
  private int offset_y;
  private int frame_width;
  private int frame_height;

  private String spriteSheet;

  private SpriteAnimation animation;
  private CharacterMovement movement;

  private boolean animating = false;

  public boolean isAnimating() {
    return animating;
  }

  private static int action = 0;

  public int getAction() {
    return action;
  }

  public void setAction(int a) {
    action = a;
    // this.offset_y = offset_y + 64 * action;

    if (animating != true) initElements();
  }

  public String getSpriteSheet() {
    return spriteSheet;
  }

  public Character(
      @NamedArg("spriteSheet") String spriteSheet,
      @NamedArg("columns") int columns,
      @NamedArg("count") int count,
      @NamedArg("offset_x") int offset_x,
      @NamedArg("offset_y") int offset_y,
      @NamedArg("frame_width") int frame_width,
      @NamedArg("frame_height") int frame_height) {

    // character parameters
    this.spriteSheet = spriteSheet;
    this.columns = columns;
    this.count = count;
    this.offset_x = offset_x;
    this.offset_y = offset_y;
    this.frame_width = frame_width;
    this.frame_height = frame_height;

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/character.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    System.out.println(spriteSheet);
    initElements();
  }

  private void initElements() {
    // init character
    Image sprite_sheet_img = new Image(getClass().getResource(spriteSheet).toExternalForm());
    active_img.setImage(sprite_sheet_img);
    active_img.setViewport(
        new Rectangle2D(offset_x, offset_y + 64 * action, frame_width, frame_height));

    // define animation
    animation =
        new SpriteAnimation(
            active_img,
            Duration.millis(800),
            count,
            columns,
            offset_x,
            offset_y + 64 * action,
            frame_width,
            frame_height);
  }

  public void enableMobility(List<Rectangle> obstacles, ObservableList<Node> observableList) {
    // create character movement object
    movement = new CharacterMovement(this, playerBound, proximityBound, obstacles, observableList);
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
    active_img.setViewport(
        new Rectangle2D(offset_x, offset_y + 64 * action, frame_width, frame_height));
    animating = false;
  }

  public void setSpriteSheet(String sprite_sheet) {
    this.spriteSheet = sprite_sheet;
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

  public int getOffset_x() {
    return offset_x;
  }

  public void setOffset_x(int offset_x) {
    this.offset_x = offset_x;
  }

  public int getOffset_y() {
    return offset_y;
  }

  public void setOffset_y(int offset_y) {
    this.offset_y = offset_y;
  }

  public int getFrame_width() {
    return frame_width;
  }

  public void setFrame_width(int frame_width) {
    this.frame_width = frame_width;
  }

  public int getFrame_height() {
    return frame_height;
  }

  public void setFrame_height(int frame_height) {
    this.frame_height = frame_height;
  }
}
