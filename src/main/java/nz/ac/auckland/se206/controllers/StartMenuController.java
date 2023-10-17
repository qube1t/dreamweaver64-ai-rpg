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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/**
 * The StartMenuController class is responsible for controlling the start menu
 * view of the game. It handles user input for selecting game settings and
 * character,
 * and starts the game when the start button is clicked.
 */
public class StartMenuController {
  private static Rectangle[] characterArray;
  private static ComboBox<String> difficultyStatic;
  private static ComboBox<String> timeLimitStatic;
  private static MediaPlayer startSoundPlayer;
  private static MediaPlayer selectSoundPlayer;

  /**
   * This method handles the selection of a character in the start menu.
   * It takes a KeyEvent as input and checks the direction of the key pressed.
   * If the direction is "ENTER", it stops and resets the startSoundPlayer if it's
   * currently playing, plays the startSoundPlayer, and schedules a TimerTask to
   * start the game setting after 50 milliseconds. If the direction is "RIGHT", it
   * stops and resets the selectSoundPlayer if it's currently playing,
   * plays the selectSoundPlayer, and changes the character selection to the
   * right.
   * If the direction is "LEFT", it stops and resets the selectSoundPlayer if it's
   * currently playing, plays the selectSoundPlayer, and changes the character
   * selection
   * to the left.
   *
   * @param event the KeyEvent that triggered the character selection
   */
  public static void selectCharacter(KeyEvent event) {

    String direction = event.getCode().toString();

    if (direction.equals("ENTER")) {
      // Check if the player is currently playing
      if (!GameState.isMuted) {
        if (startSoundPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
          // If it's playing, stop and reset it
          startSoundPlayer.stop();
          startSoundPlayer.setStartTime(javafx.util.Duration.ZERO);
        }
        startSoundPlayer.play();
      }
      Timer timer = new Timer();
      timer.schedule(new TimerTask() {
        @Override
        public void run() {
          Platform.runLater(() -> {
            startGameSetting();
          });
        }
      }, 50);

    } else if (direction.equals("RIGHT")) {
      if (!GameState.isMuted) {
        // Check if the player is currently playing
        if (selectSoundPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
          // If it's playing, stop and reset it
          selectSoundPlayer.stop();
          selectSoundPlayer.setStartTime(javafx.util.Duration.ZERO);
        }
        // Play the sound effect
        selectSoundPlayer.play();
      }
      if (GameState.characterIndex != 4) {
        GameState.characterIndex++;
        characterArray[GameState.characterIndex - 2].setOpacity(0);
        characterArray[GameState.characterIndex - 1].setOpacity(1);
      } else {
        GameState.characterIndex = 1;
        characterArray[3].setOpacity(0);
        characterArray[0].setOpacity(1);
      }
    } else if (direction.equals("LEFT")) {
      if (!GameState.isMuted) {
        // Check if the player is currently playing
        if (selectSoundPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
          // If it's playing, stop and reset it
          selectSoundPlayer.stop();
          selectSoundPlayer.setStartTime(javafx.util.Duration.ZERO);
        }
        // Play the sound effect
        selectSoundPlayer.play();
      }

      if (GameState.characterIndex != 1) {
        GameState.characterIndex--;
        characterArray[GameState.characterIndex - 1].setOpacity(1);
        characterArray[GameState.characterIndex].setOpacity(0);
      } else {
        GameState.characterIndex = 4;
        characterArray[0].setOpacity(0);
        characterArray[3].setOpacity(1);
      }
    }

  }

  /**
   * This method sets the game mode and hints remaining based on the user's
   * selected
   * difficulty and time limit. If the user does not select a difficulty or time
   * limit, it defaults to easy and 2 minutes respectively.
   * The method then transitions to the main game view.
   */
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

  @FXML
  private Button startButton;
  @FXML
  private ComboBox<String> timeLimit;
  @FXML
  private ComboBox<String> difficulty;
  @FXML
  private Label timeLimitLabel;
  @FXML
  private Label difficultyLabel;
  @FXML
  private Label title;
  @FXML
  private Label instruction;
  @FXML
  private Pane infoPane;
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
  @FXML
  private ImageView muteIcon;

  /**
   * Initializes the StartMenuController by setting up the sound players, dropdown
   * menus, and instruction message.
   *
   * @throws ApiProxyException if there is an issue with the API proxy
   */
  public void initialize() throws ApiProxyException {
    // Set up the sound players
    Media sound1 = new Media(App.class.getResource("/sounds/char-select.mp3").toString());
    Media sound2 = new Media(App.class.getResource("/sounds/enter.mp3").toString());
    selectSoundPlayer = new MediaPlayer(sound1);
    startSoundPlayer = new MediaPlayer(sound2);
    
    if (GameState.isMuted) {
      muteIcon.setImage(new Image("/images/main_game/icons/music_off.png"));
    } else {
      muteIcon.setImage(new Image("/images/main_game/icons/music_on.png"));
    }

    selectSoundPlayer.setVolume(0.4);
    startSoundPlayer.setVolume(0.7);

    // Set up the dropdown menus
    difficulty.getItems().addAll("EASY", "MEDIUM", "HARD");
    timeLimit.getItems().addAll("2 minutes", "4 minutes", "6 minutes");
    instruction.setText(GameState.instructionMsg);

    // Set up the character selection
    characterArray = new Rectangle[] { mc1, mc2, mc3, mc4 };
    difficultyStatic = difficulty;
    timeLimitStatic = timeLimit;
  }

  /**
   * When the player clicks on the start button, the game will be started.
   *
   * @param event The mouse event that triggered the start button click.
   * @throws IOException If there is an error loading the main game view.
   */
  @FXML
  public void onClickStartButton(MouseEvent event) throws IOException {
    if (!GameState.isMuted) {
      // Check if the player is currently playing
      if (startSoundPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
        // If it's playing, stop and reset it
        startSoundPlayer.stop();
        startSoundPlayer.setStartTime(javafx.util.Duration.ZERO);
      }
      startSoundPlayer.play();
    }
    startGameSetting();

  }

  /**
   * Toggles the visibility of the info pane.
   */
  @FXML
  private void toggleInfo() {
    infoPane.setVisible(!infoPane.isVisible());
  }

  /**
   * Toggles the game's mute state and updates the mute icon accordingly.
   * If the game is currently muted, the mute icon will display a "music off"
   * image.
   * If the game is not muted, the mute icon will display a "music on" image.
   */
  @FXML
  private void toggleMute() {
    GameState.isMuted = !GameState.isMuted;
    // set mute icon
    if (GameState.isMuted) {
      muteIcon.setImage(new Image("/images/main_game/icons/music_off.png"));
    } else {
      muteIcon.setImage(new Image("/images/main_game/icons/music_on.png"));
    }
  }

  /**
   * Handles the event when a character is clicked on the start menu.
   * If the clicked character is different from the current selected character,
   * the character's opacity is set to 1 and the previously selected character's
   * opacity is set to 0. The character index in the GameState is updated to the
   * clicked character's index. If the select sound is currently playing, it is
   * stopped and reset before playing again.
   *
   * @param event The MouseEvent triggered by clicking on a character.
   */
  @FXML
  private void onClickCharacter(MouseEvent event) {

    if (event.getSource() instanceof Rectangle) {
      Rectangle character = (Rectangle) event.getSource();
      int id = Integer.parseInt(character.getId().toString().substring(2));
      if (GameState.characterIndex != id) {
        if (!GameState.isMuted) {
          // Check if the player is currently playing
          if (selectSoundPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            // If it's playing, stop and reset it
            selectSoundPlayer.stop();
            selectSoundPlayer.setStartTime(javafx.util.Duration.ZERO);
          }
          selectSoundPlayer.play();
        }

        character.setOpacity(1);
        characterArray[GameState.characterIndex - 1].setOpacity(0);
        GameState.characterIndex = id;

      }
    }
  }

}
