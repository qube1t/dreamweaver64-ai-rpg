package nz.ac.auckland.se206.components;

import java.io.IOException;
import java.util.List;
import javafx.animation.Animation;
import javafx.beans.NamedArg;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.mobility.CharacterMovement;
import nz.ac.auckland.se206.mobility.SpriteAnimation;

public class Character extends AnchorPane {
  @FXML private ImageView active_img;

  private int columns; // =   4;
  private int count; // =  11;
  private int offset_x; // =  18;
  private int offset_y; // =  25;
  private int frame_width; // = 374;
  private int frame_height; // = 243;

  private String spriteSheet;

  private SpriteAnimation animation;
  private CharacterMovement movement;

  private boolean animating = false;

  public boolean isAnimating() {
    return animating;
  }

  private int action = 0;

  public int getAction() {
    return action;
  }

  public void setAction(int action) {
    this.action = action;
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

    this.spriteSheet = spriteSheet;
    this.columns = columns;
    this.count = count;
    this.offset_x = offset_x;
    this.offset_y = offset_y;
    this.frame_width = frame_width;
    this.frame_height = frame_height;

    double x = this.getLayoutX();

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
    Image sprite_sheet_img = new Image(getClass().getResource(spriteSheet).toExternalForm());
    active_img.setImage(sprite_sheet_img);
    active_img.setViewport(
        new Rectangle2D(offset_x, offset_y + 64 * action, frame_width, frame_height));

    animation =
        new SpriteAnimation(
            active_img,
            Duration.millis(1000),
            count,
            columns,
            offset_x,
            offset_y + 64 * action,
            frame_width,
            frame_height);
  }

  public void enableMobility(Rectangle playerBound, List<Rectangle> obstacles) {
    movement = new CharacterMovement(this, playerBound, obstacles);
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
    // initElements();
  }

  // public ImageView getActive_img() {
  //     return active_img;
  // }

  // public void setActive_img(ImageView active_img) {
  //     this.active_img = active_img;
  // }

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
