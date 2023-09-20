package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

/**
 * This is the entry point of the JavaFX application, while you can change this class, it should
 * remain as the class that runs the JavaFX application.
 */
public class App extends Application {

  private static Scene scene;
  private static Parent Room3Root;
  private static Parent MainGameRoot;
  private static Parent Room3SubRoot;

  public static void main(final String[] args) {
    launch();
  }

  public static void setUi(String fxml) {
    Parent root = null;
    if (fxml.equals("sub3")) {
      // First time loading the game, create a new instance of the game.
      if (Room3SubRoot == null) {
        try {
          Room3SubRoot = loadFxml("sub3");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      root = Room3SubRoot;
    } else if (fxml.equals("room3")) {
      // First time loading the game, create a new instance of the game.
      if (Room3Root == null) {
        try {
          Room3Root = loadFxml("room3");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      root = Room3Root;
    } else if (fxml.equals("main_game")) {
      if (MainGameRoot == null) {
        try {
          MainGameRoot = loadFxml("main_game");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      root = MainGameRoot;
    }

    if (root != null) {
      scene.setRoot(root);
      root.requestFocus();
    }
  }

  public static void setRoot(String fxml) throws IOException {
    Pane root = (Pane) loadFxml(fxml);
    scene.setRoot(root);
    root.requestFocus();
  }

  /**
   * Returns the node associated to the input file. The method expects that the file is located in
   * "src/main/resources/fxml".
   *
   * @param fxml The name of the FXML file (without extension).
   * @return The node of the input file.
   * @throws IOException If the file is not found.
   */
  private static Parent loadFxml(final String fxml) throws IOException {
    return new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml")).load();
  }

  /**
   * This method is invoked when the application starts. It loads and shows the "Canvas" scene.
   *
   * @param stage The primary stage of the application.
   * @throws IOException If "src/main/resources/fxml/canvas.fxml" is not found.
   */
  @Override
  public void start(final Stage stage) throws IOException {

    Pane root = (Pane) loadFxml("start_menu");

    scene = new Scene(root, 800, 700);

    stage.setScene(scene);
    stage.show();

    // Add the style css file
    scene.getStylesheets().add("/css/style.css");
    // letterbox(scene, root);

    // stage.setFullScreen(true);

    root.requestFocus();
  }

  //
  // FOLLOWING CODE BY jewelsea https://stackoverflow.com/a/16608161/8900549
  //

  private void letterbox(final Scene scene, final Pane contentPane) {
    final double initWidth = scene.getWidth();
    final double initHeight = scene.getHeight();
    final double ratio = initWidth / initHeight;

    SceneSizeChangeListener sizeListener =
        new SceneSizeChangeListener(scene, ratio, initHeight, initWidth, contentPane);
    scene.widthProperty().addListener(sizeListener);
    scene.heightProperty().addListener(sizeListener);
  }

  private static class SceneSizeChangeListener implements ChangeListener<Number> {
    private final Scene scene;
    private final double ratio;
    private final double initHeight;
    private final double initWidth;
    private final Pane contentPane;

    public SceneSizeChangeListener(
        Scene scene, double ratio, double initHeight, double initWidth, Pane contentPane) {
      this.scene = scene;
      this.ratio = ratio;
      this.initHeight = initHeight;
      this.initWidth = initWidth;
      this.contentPane = contentPane;
    }

    @Override
    public void changed(
        ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
      final double newWidth = scene.getWidth();
      final double newHeight = scene.getHeight();

      double scaleFactor =
          newWidth / newHeight > ratio ? newHeight / initHeight : newWidth / initWidth;

      if (scaleFactor >= 1) {
        Scale scale = new Scale(scaleFactor, scaleFactor);
        scale.setPivotX(0);
        scale.setPivotY(0);
        scene.getRoot().getTransforms().setAll(scale);

        contentPane.setPrefWidth(newWidth / scaleFactor);
        contentPane.setPrefHeight(newHeight / scaleFactor);
      } else {
        contentPane.setPrefWidth(Math.max(initWidth, newWidth));
        contentPane.setPrefHeight(Math.max(initHeight, newHeight));
      }
    }
  }
}
