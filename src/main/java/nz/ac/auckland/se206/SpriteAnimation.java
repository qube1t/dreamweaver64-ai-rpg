package nz.ac.auckland.se206;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/*
 * TODO do complete attribution
https://netopyr.com/2012/03/09/creating-a-sprite-animation-with-javafx/
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

  protected void interpolate(double k) {
    int index = Math.min((int) Math.floor(k * count), count - 1);

    // if (index == 0) index++;

    if (index != lastIndex) {
      final int x = (index % columns) * width + offsetX + 64;
      final int y = (index / columns) * height + offsetY;
      imageView.setViewport(new Rectangle2D(x, y, width, height));
      lastIndex = index;
    } else {
      //   System.out.println("index: " + index + " lastIndex: " + lastIndex);
    }
  }
}
