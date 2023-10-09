package nz.ac.auckland.se206;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * This is the entry point of the JavaFX application, while you can change this
 * class, it should
 * remain as the class that runs the JavaFX application.
 */
public class App extends Application {

  private static Scene scene;

  public static void main(final String[] args) {
    launch();
  }

  public static void setRoot(String fxml) throws IOException {
    Pane root = (Pane) loadFxml(fxml);
    scene.setRoot(root);
    root.requestFocus();
  }

  /**
   * Returns the node associated to the input file. The method expects that the
   * file is located in
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
   * This method is invoked when the application starts. It loads and shows the
   * "Canvas" scene.
   *
   * @param stage The primary stage of the application.
   * @throws IOException If "src/main/resources/fxml/canvas.fxml" is not found.
   */
  @Override
  public void start(final Stage stage) throws IOException {

    // Font.loadFont(App.class.getResource("/fonts/Abaddon Bold.ttf").toString(),
    // 14);
    // Font.loadFont(App.class.getResource("/fonts/Abaddon Light.ttf").toString(),
    // 14);
    Pane root = (Pane) loadFxml("splash_screen");

    scene = new Scene(root, 700, 650);

    stage.setScene(scene);
    stage.show();

    // Add the style css file
    scene.getStylesheets().add("/css/style.css");

    root.requestFocus();
  }

}
