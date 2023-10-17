package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.Helper;
import nz.ac.auckland.se206.components.Character;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom2;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom3;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/**
 * The Room3Controller class is responsible for controlling the behavior of Room
 * 3 in the game.
 * It initializes the GPS map, enables character movement, and loads the
 * appropriate game state.
 * It also handles click events for various elements in the room, such as doors
 * and computers.
 * Additionally, it contains methods for unarranging city names and setting the
 * end image when the time is up.
 */
public class Room3Controller {

  private static Rectangle flightComputer;
  private static ImageView lockRed;
  private static boolean radarOpened = false;
  private static boolean gptInit = false;

  /**
   * Resets the variables used in the GPT section of Room 3.
   * Sets gptInit to false, flightComputer to null, lockRed to null, and
   * radarOpened to false.
   */
  public static void resetGptRoom3() {
    gptInit = false;
    flightComputer = null;
    lockRed = null;
    radarOpened = false;
  }

  public static void enableFlightComputer() {
    Helper.enableAccessToItem(flightComputer, lockRed);
  }

  @FXML
  private Pane obstalePane;
  @FXML
  private Pane interactablePane;
  @FXML
  private ImageView puzzleLoad;
  @FXML
  private ImageView doorLoad2;
  @FXML
  private ImageView lock;
  @FXML
  private ImageView doorLoad1;
  @FXML
  private Character character;
  @FXML
  private Rectangle machine;
  @FXML
  private Rectangle clickableComputer1;
  @FXML
  private Rectangle clickableComputer2;
  @FXML
  private Rectangle clickableRadar;
  @FXML
  private Rectangle leftDoorBtn;
  @FXML
  private Rectangle rightDoorBtn;
  @FXML
  private Text label1;
  @FXML
  private Text label2;
  @FXML
  private Text label3;
  @FXML
  private Text label4;
  @FXML
  private Text label5;
  @FXML
  private Circle point1;
  @FXML
  private Circle point2;
  @FXML
  private Circle point3;
  @FXML
  private Circle point4;
  @FXML
  private Circle point5;

  private Circle[] cityPoints;
  private Text[] cityLabels;
  private Timeline radarAnimation;
  private int currentCity;
  private Circle currentCityPoint;
  private Text currentCityLabel;

  private ArrayList<Rectangle> obstacles;
  private AudioClip atcSound;

  /**
   * Initializes the Room3Controller by setting up the GPS map, enabling character
   * movement, and loading the appropriate game state.
   * If the puzzle is not loaded, the clickableComputer2 is disabled.
   * If the game is muted, the ATC ambiance sound is played.
   *
   * @throws ApiProxyException if there is an issue with the API proxy.
   */
  public void initialize() throws ApiProxyException {

    flightComputer = clickableComputer1;
    lockRed = lock;

    if (GameState.hasPuzzleSolved) {
      enableFlightComputer();
    }

    initilizeGpsMap();

    GameState.mainGame.clickGamePane();

    if (!GameState.isPuzzleLoaded) {
      // Set the puzzle load image to invisible if the puzzle is not loaded.
      clickableComputer2.setDisable(true);
    }
    Helper.enableAccessToItem(leftDoorBtn, doorLoad2);
    Helper.enableAccessToItem(rightDoorBtn, doorLoad1);
    if (!gptInit) {
      gptInitilize();
      gptInit = true;
      GameState.isRoom3FirstEntered = true;
    } else {
      // If GPT has been initialized, enable all the objects
      MainGameController.enableInteractPane();
      Helper.enableAccessToItem(leftDoorBtn, doorLoad2);
      Helper.enableAccessToItem(rightDoorBtn, doorLoad1);
      Helper.enableAccessToItem(clickableComputer2, puzzleLoad);
    }

    this.obstacles = new ArrayList<Rectangle>();

    // Get all the obstacles and add to obstacles list
    for (Node node : obstalePane.getChildren()) {
      if (node instanceof Rectangle) {
        obstacles.add((Rectangle) node);
      }
    }

    // Enable the character movement.
    character.enableMobility(obstacles, interactablePane.getChildren());

    switch (GameState.prevRoom) {
      case 1:
        // Set the character position if previous room is room1.
        character.setLayoutX(523);
        character.setLayoutY(210);

        break;
      case 2:

        character.setLayoutX(27);
        character.setLayoutY(214);

        break;
      default:
        // Set default position
        character.setLayoutX(530);
        character.setLayoutY(210);
    }

    GameState.prevRoom = 3;
    if (!GameState.isMuted) {
      // Play the atc background music if the game is not muted by user.
      atcSound = new AudioClip(
          (new Media(App.class.getResource("/sounds/atcAmbiance.mp3").toString()))
              .getSource());
      atcSound.setCycleCount(AudioClip.INDEFINITE);
      atcSound.setVolume(.75);
      atcSound.play();
      GameState.soundFx.add(atcSound);
    }
  }

  /**
   * This method makes the city name unarranged.
   *
   * @param cityName the city name to be unarranged
   * @return the unarranged city name
   */
  protected String makeUnarrangedCity(String cityName) {
    int length = cityName.length();
    int[] randomNumbers = new int[length];
    // Initialize the array of random numbers
    for (int i = 0; i < length; i++) {
      randomNumbers[i] = i;
    }
    Random random = new Random();

    // Shuffle the array of random numbers to make the city name unarranged
    for (int i = 0; i < length; i++) {
      int randomIndexToSwap = random.nextInt(length);
      int temp = randomNumbers[randomIndexToSwap];
      randomNumbers[randomIndexToSwap] = randomNumbers[i];
      randomNumbers[i] = temp;
    }

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      sb.append(cityName.charAt(randomNumbers[i]));
    }
    String unarrangedCityName = sb.toString();
    ;

    if (unarrangedCityName.equals(cityName)) {
      return makeUnarrangedCity(cityName);
    }

    return unarrangedCityName.toUpperCase();
  }

  /**
   * Handles the click event for the door in Room 1.
   * This method updates the game state, disables the interact pane for
   * transition,
   * removes the overlay, adds the overlay for Room 1, and runs the GPT model for
   * Eleanor AI.
   * If the game is not muted, it stops the sound from the previous room.
   *
   * @throws IOException       if an I/O error occurs
   * @throws ApiProxyException if an error occurs while calling the GPT API
   */
  @FXML
  public void onClickRoom1() throws IOException, ApiProxyException {
    System.out.println("Door clicked");
    System.out.println(GameState.currentBox);
    // go to the right room
    InstructionsLoadController.setText();

    // disable interact pane for transition
    MainGameController.disableInteractPane();
    MainGameController.removeOverlay(true);
    MainGameController.addOverlay("room1", true);
    GameState.eleanorAi.runGpt("User update: User has moved from ATC to his childhood home room.");
    if (!GameState.isMuted) {
      atcSound.stop();
    }
  }

  /**
   * Handles the click event for the Room2 button.
   * Loads instructions, disables interact pane for transition, removes overlay,
   * adds overlay for room2,
   * runs GPT for Eleanor AI, and stops ATC sound if not muted.
   *
   * @throws IOException       if an I/O error occurs
   * @throws ApiProxyException if an API proxy error occurs
   */
  @FXML
  public void onClickRoom2() throws IOException, ApiProxyException {
    System.out.println("Room2 clicked");
    // go to the right room
    InstructionsLoadController.setText();

    // disable interact pane for transition
    MainGameController.disableInteractPane();
    MainGameController.removeOverlay(true);
    MainGameController.addOverlay("room2", true);
    GameState.eleanorAi.runGpt("User update: User has moved from ATC"
        + "to the pirate ship. No reply is required");

    if (!GameState.isMuted) {
      atcSound.stop();
    }
  }

  /**
   * Handles the event when the computer is clicked.
   * Adds an overlay to the main game controller.
   *
   * @throws IOException       if an I/O error occurs.
   * @throws ApiProxyException if an API proxy error occurs.
   */
  @FXML
  public void onClickComputer() throws IOException, ApiProxyException {
    System.out.println("Computer clicked");
    MainGameController.addOverlay("sub3", false);
  }

  /**
   * Handles the click event for the puzzle button in Room 3. Adds the puzzle game
   * overlay and sends a message to the
   * AI indicating that the user has opened the unarranged word puzzle game. The
   * correct destination city name is
   * GameState.arrangedDestnationCity. If the user asks for hints, give hint
   * without revealing the city name.
   *
   * @throws IOException       if there is an error adding the puzzle game overlay
   * @throws ApiProxyException if there is an error sending the message to the AI
   */
  @FXML
  public void onClickPuzzle() throws IOException, ApiProxyException {
    System.out.println("destnation city is " + GameState.arrangedDestnation);
    // Add the puzzle game overlay
    MainGameController.addOverlay("room3_puzzle", false);
    GameState.eleanorAi.runGpt(
        "User update: User has opened the unarranged word puzzle game. The correct"
            + " destination city name is "
            + GameState.arrangedDestnation
            + ". Only give hints if the user asks for hints."
            + " Do not contain" + GameState.arrangedDestnation
            + "in any response. No response required");
  }

  /**
   * Handles the event when the user clicks on the radar button. Adds an overlay
   * to the main game
   * screen to display the radar computer, and runs the GPT model to generate a
   * message indicating
   * that the user has opened the radar computer and the location of the treasure
   * box. If the user
   * asks for hints, the hints should be given. No need to respond to this
   * message.
   *
   * @throws IOException       if an I/O error occurs
   * @throws ApiProxyException if an error occurs while communicating with the GPT
   *                           API
   */
  @FXML
  public void onClickRadar() throws IOException, ApiProxyException {
    // user clicked radar
    MainGameController.addOverlay("radar_computer", false);
    if (!radarOpened) {
      GameState.eleanorAi.runGpt(
          "User update: User has opened the radar computer and the red point"
              + " indicates the correct"
              + " location for treasure box location in the pirate ship. "
              + "If the user ask for hints give"
              + " the hints. No need to respond to this message.");
      radarOpened = true;
    }
  }

  /**
   * This method is called when the book is clicked It will open the flight plan
   * if it is not open
   * and if the flight plan is open, then it will close the flight plan.
   */
  @FXML
  public void clickMachineEvent() throws IOException, ApiProxyException {
    GameState.isMachineOpen = true;
    MainGameController.addOverlay("decryption_machine", false);

  }

  /**
   * Initializes the GPT-3 API for Room 3 and runs three prompts: the welcome
   * message, the city selection prompt, and the puzzle introduction message.
   * Enables access to the right and left door buttons and the clickable computer
   * 2 after the prompts are completed.
   *
   * @throws ApiProxyException if there is an issue with the API proxy
   */
  private void gptInitilize() throws ApiProxyException {

    // Enable interact pane first
    MainGameController.enableInteractPane();

    GameState.eleanorAi.runGpt(
        // Get the welcome message from GPT.
        GptPromptEngineeringRoom3.room3WelcomeMessage(),
        (result) -> {
          System.out.println(result);
          Helper.enableAccessToItem(rightDoorBtn, doorLoad1);
          Helper.enableAccessToItem(leftDoorBtn, doorLoad2);
          GameState.isRoom3GptDone = true;
        });

    GameState.eleanorAi2.runGpt(
        GptPromptEngineeringRoom3.getEightRandomCity(),
        (randomCities) -> {

          // Get the list of cities from the result
          List<String> cities = Helper.getTextBetweenChar(randomCities, "^", false);
          // Set the list of cities to the game state
          GameState.destnationCities = cities.toArray(new String[cities.size()]);
          GameState.destnationCityIndex = Helper.getRandomNumber(0, cities.size() - 1);
          GameState.arrangedDestnation = (cities.get(GameState.destnationCityIndex));
          // Print the city generated
          System.out.println(randomCities);
          System.out.println("Arranged:" + GameState.destnationCities.toString()
              + "Destnation city is " + GameState.arrangedDestnation);

          GameState.unarrangedDestnation = makeUnarrangedCity(GameState.arrangedDestnation);
        });

    GameState.eleanorAi2.runGpt(
        GptPromptEngineeringRoom3.getIntroPuzzleMessage(),
        (puzzleWelcome) -> {
          System.out.println(puzzleWelcome);
          GameState.puzzleIntroMessageRoom3 = puzzleWelcome;
          GameState.isPuzzleLoaded = true;
          Helper.enableAccessToItem(clickableComputer2, puzzleLoad);
        });
  }

  /**
   * Initializes the GPS map by setting up the radar points and radar objects to a
   * list, setting the current city index if it is not set, initializing the
   * current city point and label, and starting the radar animation.
   */
  private void initilizeGpsMap() {
    // Initialize the radar points and radarObjects to a list.
    this.cityPoints = new Circle[] { point1, point2, point3, point4, point5 };
    this.cityLabels = new Text[] { label1, label2, label3, label4, label5 };

    GameState.currentCities = cityLabels;

    // initalize current city if it is not set
    if (GameState.currentCityIndex == -1) {

      // generate a number between 1 and 5
      GameState.currentCityIndex = (int) (Math.random() * 5 + 1);
      currentCity = GameState.currentCityIndex;
    }

    currentCity = GameState.currentCityIndex;

    this.currentCityPoint = cityPoints[currentCity - 1];
    this.currentCityLabel = cityLabels[currentCity - 1];

    currentCityLabel.setVisible(true);
    // Initialize the radarAnimation timeline
    this.radarAnimation = new Timeline(
        new KeyFrame(Duration.seconds(0), event -> fadeInRadarPoints(currentCityPoint)),
        new KeyFrame(Duration.seconds(1), event -> fadeOutRadarPoints(currentCityPoint)));
    radarAnimation.setCycleCount(Timeline.INDEFINITE);
    radarAnimation.setOnFinished(
        event -> {
          // Restart the animation when it completes
          radarAnimation.play();
        });

    // Start the animation
    radarAnimation.play();

    System.out.println("Current city is " + GameState.currentCities[currentCity - 1].getText());
  }

  /**
   * Fades in the given circle object representing a city on the radar.
   *
   * @param city the circle object representing the city to be faded in
   */
  private void fadeInRadarPoints(Circle city) {

    city.setVisible(true);
    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), city);
    fadeTransition.setFromValue(0.0);
    fadeTransition.setToValue(1.0);
    fadeTransition.setInterpolator(Interpolator.LINEAR); // Use linear interpolation
    fadeTransition.play();
  }

  /**
   * Fades out the given Circle object over a duration of 1.5 seconds using a
   * linear interpolation.
   *
   * @param city The Circle object to fade out.
   */
  private void fadeOutRadarPoints(Circle city) {

    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), city);
    fadeTransition.setFromValue(1.0);
    fadeTransition.setToValue(0.0);
    fadeTransition.setInterpolator(Interpolator.LINEAR); // Use linear interpolation
    fadeTransition.play();
  }

}
