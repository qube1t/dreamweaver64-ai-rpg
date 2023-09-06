package nz.ac.auckland.se206;

import java.io.FileNotFoundException;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import nz.ac.auckland.se206.mobility.SpriteAnimation;

public class App_c extends Application {

  private static final int COLUMNS = 4;
  private static final int COUNT = 13;
  private static final int OFFSET_X = 18;
  private static final int OFFSET_Y = 25;
  private static final int WIDTH = 374;
  private static final int HEIGHT = 243;

  public static void main(String[] args) {
    launch(args);
  }

  public void start(Stage primaryStage) throws FileNotFoundException {
    primaryStage.setTitle("The Horse in Motion");

    Image input =
        new Image(getClass().getResource("/images/The_Horse_in_Motion.jpg").toExternalForm());
    final ImageView imageView = new ImageView(input);
    imageView.setViewport(new Rectangle2D(OFFSET_X, OFFSET_Y, WIDTH, HEIGHT));

    final Animation animation =
        new SpriteAnimation(
            imageView, Duration.millis(1000), COUNT, COLUMNS, OFFSET_X, OFFSET_Y, WIDTH, HEIGHT);
    animation.setCycleCount(Animation.INDEFINITE);
    animation.play();

    primaryStage.setScene(new Scene(new Group(imageView)));
    primaryStage.show();
  }
}
