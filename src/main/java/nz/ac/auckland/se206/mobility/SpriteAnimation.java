package nz.ac.auckland.se206.mobility;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/*
 * TODO do complete attribution
https://netopyr.com/2012/03/09/creating-a-sprite-animation-with-javafx/
 */

/**
 * The SpriteAnimation class is used to create an animation from a sprite sheet.
 * It extends the Transition class and overrides the interpolate method to
 * interpolate through the sprite sheet based on the given value.
 */
public class SpriteAnimation extends Transition {

  private final ImageView imageView;
  private final int count;
  private final int columns;
  private final int offsetX;
  private final int offsetY;
  private final int width;
  private final int height;

  private int lastIndex;

  /**
   * Constructs a new SpriteAnimation object.
   *
   * @param imageView the ImageView to apply the animation to
   * @param duration  the duration of the animation
   * @param count     the number of frames in the animation
   * @param columns   the number of columns in the sprite sheet
   * @param offsetX   the x offset of the first frame in the sprite sheet
   * @param offsetY   the y offset of the first frame in the sprite sheet
   * @param width     the width of each frame in the sprite sheet
   * @param height    the height of each frame in the sprite sheet
   */
  public SpriteAnimation(
      ImageView imageView,
      Duration duration,
      int count,
      int columns,
      int offsetX,
      int offsetY,
      int width,
      int height) {
    this.imageView = imageView;
    this.count = count - 1;
    this.columns = columns;
    this.offsetX = offsetX;
    this.offsetY = offsetY;
    this.width = width;
    this.height = height;
    setCycleDuration(duration);
    setInterpolator(Interpolator.LINEAR);
  }

  /**
   * Interpolates through the sprite sheet based on the given value.
   * 
   * @param k The value used to interpolate through the sprite sheet.
   */
  protected void interpolate(double k) {
    // interpolating through teh sprite sheet
    int index = Math.min((int) Math.floor(k * count), count - 1);

    if (index != lastIndex) {
      final int x = (index % columns) * width + offsetX + 64;
      final int y = (index / columns) * height + offsetY;
      imageView.setViewport(new Rectangle2D(x, y, width, height));
      lastIndex = index;
    }
  }
}
