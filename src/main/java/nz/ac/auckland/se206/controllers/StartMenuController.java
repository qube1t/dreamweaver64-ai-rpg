package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class StartMenuController {
  @FXML
  private Button startButton;
  @FXML
  private ComboBox<String> timeLimit;
  @FXML
  private Label timeLimitLabel;
  @FXML
  private Label difficultyLabel;
  @FXML
  private ComboBox<String> difficulty;
  @FXML
  private Label title;
  @FXML
  private Label instruction;
  @FXML
  private Pane infoPane;
  @FXML
  private ImageView muteIcon;
  @FXML
  private Pane outPane;
  @FXML
  private Pane startPane;

  @FXML
  private Rectangle mc1;
  @FXML
  private Rectangle mc2;
  @FXML
  private Rectangle mc3;
  @FXML
  private Rectangle mc4;

  private static Rectangle[] characterArray;
  private static ComboBox<String> difficultyStatic;
  private static ComboBox<String> timeLimitStatic;
  private static AudioClip selectSound;
  private static AudioClip startSound;

  public static void selectCharacter(KeyEvent event) {

    String direction = event.getCode().toString();

    if (direction.equals("ENTER")) {
      startSound.play();
      Timer timer = new Timer();
      timer.schedule(new TimerTask() {
        @Override
        public void run() {
          Platform.runLater(() -> {
            startGameSetting();
          });
        }
      }, 160);

    } else if (direction.equals("RIGHT")) {
      if (GameState.characterIndex != 4) {
        selectSound.play();
        GameState.characterIndex++;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
          @Override
          public void run() {
            Platform.runLater(() -> {
              characterArray[GameState.characterIndex - 2].setOpacity(0);
              characterArray[GameState.characterIndex - 1].setOpacity(1);
            });
          }
        }, 120);
      }
    } else if (direction.equals("LEFT")) {
      if (GameState.characterIndex != 1) {
        selectSound.play();
        GameState.characterIndex--;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
          @Override
          public void run() {
            Platform.runLater(() -> {
              characterArray[GameState.characterIndex - 1].setOpacity(1);
              characterArray[GameState.characterIndex].setOpacity(0);
            });
          }
        }, 150);

      }
    }

  }

  public static void startGameSetting() {
    // Get the difficulty and time limit the user selected
    String difficulty = difficultyStatic.getValue();
    String timeLimit = timeLimitStatic.getValue();

    // If the difficulty or time limit is default, set it to easy and 2 minutes
    // respectively
    if (difficulty == null) {
      difficulty = "EASY";
    }
    if (timeLimit == null) {
      timeLimit = "2 minutes";
    }

    GameState.gameMode = new String[] { difficulty, timeLimit };

    // set the hints remaining based on the difficulty
    switch (difficulty) {
      case "EASY":
        GameState.hintsRemaining = -1;
        break;
      case "MEDIUM":
        GameState.hintsRemaining = 5;
        break;
      case "HARD":
        GameState.hintsRemaining = 0;
        break;
    }

    System.out.println(
        "Game started with"
            + GameState.gameMode[0]
            + " difficulty and "
            + GameState.gameMode[1]
            + " time limit");

    // Transition to the main game view
    try {
      App.setRoot("main_game");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** Initialize the start menu. */
  public void initialize() throws ApiProxyException {
    selectSound = new AudioClip(new Media(App.class.getResource("/sounds/selectSound.mp3").toString())
        .getSource());
    startSound = new AudioClip(new Media(App.class.getResource("/sounds/enter.mp3").toString())
        .getSource());

    difficulty.getItems().addAll("EASY", "MEDIUM", "HARD");
    timeLimit.getItems().addAll("2 minutes", "4 minutes", "6 minutes");
    instruction.setText(GameState.instructionMsg);

    characterArray = new Rectangle[] { mc1, mc2, mc3, mc4 };
    difficultyStatic = difficulty;
    timeLimitStatic = timeLimit;

  }

  /**
   * When the player clicks on the start button, the game will be started.
   * 
   * @param event
   * @throws IOException
   */
  @FXML
  public void onClickStartButton(MouseEvent event) throws IOException {
    startSound.play();
    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        Platform.runLater(() -> {
          startGameSetting();
        });
      }
    }, 160);
  }

  @FXML
  private void toggleInfo() {
    infoPane.setVisible(!infoPane.isVisible());
  }

  @FXML
  private void toggleMute() {
    GameState.isMuted = !GameState.isMuted;

    if (GameState.isMuted) {
      muteIcon.setImage(new Image("/images/main_game/icons/music_off.png"));
    } else {
      muteIcon.setImage(new Image("/images/main_game/icons/music_on.png"));
    }
    // Image volIcon = new Image("images/volume.png");
    // muteIcon.setImage(volIcon);
  }

}
