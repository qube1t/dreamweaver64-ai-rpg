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
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom3;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class Room3Controller {

  private static ImageView imgEndSt;
  private static boolean gptInit = false;

  /**
   * Set the end image when the time is up.
   */
  public static void initializeMap() {
    imgEndSt.setVisible(true);
  }

  @FXML
  private Pane obstalePane;
  @FXML
  private Pane interactablePane;
  @FXML
  private ImageView imgEnd;
  @FXML
  private ImageView puzzleLoad;
  @FXML
  private ImageView doorLoad2;
  @FXML
  private ImageView doorLoad1;
  @FXML
  private Character character;
  @FXML
  private Rectangle machine;
  @FXML
  private Rectangle clickableComputer;
  @FXML
  private Rectangle clickableComputer2;
  @FXML
  private Rectangle clickableRadar;
  @FXML
  private Rectangle doorToRoom1;
  @FXML
  private Rectangle doorToRoom2;
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

  public void initialize() throws ApiProxyException {

    initilizeGpsMap();

    imgEndSt = imgEnd;

    if (GameState.tenSecondsLeft) {
      initializeMap();
    }

    if (!GameState.isPuzzleLoaded) {
      clickableComputer2.setDisable(true);
    }

    if (!gptInit) {
      gptInitilize();
      gptInit = true;
    } else {
      MainGameController.enableInteractPane();
      Helper.enableAccessToItem(doorToRoom2, doorLoad2);
      Helper.enableAccessToItem(doorToRoom1, doorLoad1);
      Helper.enableAccessToItem(clickableComputer2, puzzleLoad);
    }

    this.obstacles = new ArrayList<Rectangle>();

    for (Node node : obstalePane.getChildren()) {
      if (node instanceof Rectangle) {
        obstacles.add((Rectangle) node);
      }
    }

    // Enable the character movement.
    character.enableMobility(obstacles, interactablePane.getChildren());

    switch (GameState.prevRoom) {
      case 1:
        character.setLayoutX(523);
        character.setLayoutY(210);

        break;
      case 2:
        character.setLayoutX(29);
        character.setLayoutY(198);

        break;
      default:
        character.setLayoutX(530);
        character.setLayoutY(210);
    }

    GameState.prevRoom = 3;
    if (!GameState.isMuted) {
      atcSound = new AudioClip(
          (new Media(App.class.getResource("/sounds/atcAmbiance.mp3").toString()))
              .getSource());
      atcSound.setCycleCount(AudioClip.INDEFINITE);
      atcSound.setVolume(.45);
      atcSound.play();
      GameState.soundFx.add(atcSound);
    }
  }

  /**
   * This method makes the city name unarranged
   *
   * @param cityName the city name to be unarranged
   * @return the unarranged city name
   */
  protected String makeUnarrangedCityName(String cityName) {
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
      return makeUnarrangedCityName(cityName);
    }

    return unarrangedCityName.toUpperCase();
  }

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
    if (!GameState.isMuted)
      atcSound.stop();
  }

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

    if (!GameState.isMuted)
      atcSound.stop();
  }

  @FXML
  public void onClickComputer() throws IOException, ApiProxyException {
    System.out.println("Computer clicked");
    MainGameController.addOverlay("sub3", false);
  }

  @FXML
  /**
   * This method is called when the puzzle is clicked It will open the puzzle
   * game.
   *
   * @throws IOException
   * @throws ApiProxyException
   */
  public void onClickPuzzle() throws IOException, ApiProxyException {
    System.out.println("destnation city is " + GameState.arrangedDestnationCity);
    // Add the puzzle game overlay
    MainGameController.addOverlay("room3_puzzle", false);
    GameState.eleanorAi.runGpt(
        "User update: User has opened the unarranged word puzzle game. The correct destnation city"
            + " name is "
            + GameState.arrangedDestnationCity
            + ". No reply is needed for this message. If the user ask for"
            + " hints, give hint without"
            + " revealing the city name.");
  }

  @FXML
  public void onClickRadar() throws IOException, ApiProxyException {
    // user clicked radar
    MainGameController.addOverlay("radar_computer", false);
    GameState.eleanorAi.runGpt(
        "User update: User has opened the radar computer and the red point"
            + " indicates the correct"
            + " location for treasure box location in the pirate ship. "
            + "If the user ask for hints give"
            + " the hints. No need to respond to this message.");
  }

  @FXML
  /**
   * This method is called when the book is clicked It will open the flight plan
   * if it is not open
   * and if the flight plan is open, then it will close the flight plan
   */
  public void clickMachineEvent() throws IOException, ApiProxyException {
    GameState.isMachineOpen = true;
    MainGameController.addOverlay("decryption_machine", false);
  }

  private void gptInitilize() throws ApiProxyException {

    // Enable interact pane first
    MainGameController.enableInteractPane();

    GameState.eleanorAi.runGpt(
        GptPromptEngineeringRoom3.room3WelcomeMessage(),
        (result) -> {
          System.out.println(result);
        });

    GameState.eleanorAi.runGpt(
        GptPromptEngineeringRoom3.getEightRandomCity(),
        (result) -> {

          List<String> cities = Helper.getTextBetweenChar(result, "^");
          GameState.destnationCities = cities.toArray(new String[cities.size()]);
          GameState.destnationCityIndex = Helper.getRandomNumber(0, cities.size() - 1);
          GameState.arrangedDestnationCity = (cities.get(GameState.destnationCityIndex));
          // Print the city
          System.out.println(result);
          System.out.println("Arranged:" + GameState.destnationCities.toString() +
              "Destnation city is " + GameState.arrangedDestnationCity);

          GameState.unarrangedDestnationCity = makeUnarrangedCityName(GameState.arrangedDestnationCity);
        });

    GameState.eleanorAi.runGpt(
        GptPromptEngineeringRoom3.getIntroPuzzleMessage(),
        (result) -> {
          System.out.println(result);
          GameState.puzzleIntroMessageRoom3 = result;
          GameState.isPuzzleLoaded = true;
          Helper.enableAccessToItem(clickableComputer2, puzzleLoad);
          Helper.enableAccessToItem(doorToRoom1, doorLoad1);
          Helper.enableAccessToItem(doorToRoom2, doorLoad2);
        });
  }

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

  private void fadeInRadarPoints(Circle city) {

    city.setVisible(true);
    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), city);
    fadeTransition.setFromValue(0.0);
    fadeTransition.setToValue(1.0);
    fadeTransition.setInterpolator(Interpolator.LINEAR); // Use linear interpolation
    fadeTransition.play();
  }

  private void fadeOutRadarPoints(Circle city) {

    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), city);
    fadeTransition.setFromValue(1.0);
    fadeTransition.setToValue(0.0);
    fadeTransition.setInterpolator(Interpolator.LINEAR); // Use linear interpolation
    fadeTransition.play();
  }

}
