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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.Helper;
import nz.ac.auckland.se206.components.Character;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom3;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class Room3Controller {

  private static ImageView imgEndSt;

  /**
   * Set the end image when the time is up.
   */
  public static void initializeMap() {
    imgEndSt.setVisible(true);
  }

  @FXML
  private Rectangle computer;
  @FXML
  private Rectangle computer2;
  @FXML
  private Rectangle chair1;
  @FXML
  private Rectangle chair2;
  @FXML
  private Rectangle gate;
  @FXML
  private Rectangle radar;
  @FXML
  private Rectangle boundary1;
  @FXML
  private Rectangle boundary2;
  @FXML
  private Rectangle boundary3;
  @FXML
  private Rectangle boundary4;
  @FXML
  private Rectangle boundary5;
  @FXML
  private Rectangle decrypt;
  @FXML
  private Rectangle bound1;
  @FXML
  private Rectangle bound2;
  @FXML
  private Rectangle bound3;
  @FXML
  private Rectangle machine;
  @FXML
  private Rectangle clickableComputer;
  @FXML
  private Rectangle clickableRadar;
  @FXML
  private Rectangle clickableDoor;
  @FXML
  private Rectangle worldMap;
  @FXML
  private Circle box1;
  @FXML
  private Circle box2;
  @FXML
  private Circle box3;
  @FXML
  private Circle box4;
  @FXML
  private Circle box5;
  @FXML
  private Character character;
  @FXML
  private AnchorPane radarPane;
  @FXML
  private ImageView map;
  @FXML
  private ImageView paperImage;
  @FXML
  private Pane interactablePane;
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
  @FXML
  private ImageView imgEnd;

  private Circle[] cityPoints;
  private Text[] cityLabels;
  private Timeline radarAnimation;
  private int currentCity;
  private Circle currentCityPoint;
  private Text currentCityLabel;

  private ArrayList<Rectangle> obstacles;

  public void initialize() throws ApiProxyException {

    initilizeGpsMap();

    imgEndSt = imgEnd;

    if (GameState.tenSecondsLeft) {
      initializeMap();
    }

    // Generate seven random city destnations and randomly choose one of them
    // for the puzzle game if it is not set

    // Only displays the welcome message to Room3 if the plauyer first enters the
    // room
    if (!GameState.isRoom3FirstEntered) {

      GameState.isRoom3FirstEntered = true;

      GameState.eleanorAi.runGpt(
          GptPromptEngineeringRoom3.room3WelcomeMessage(),
          (result) -> {
            System.out.println(result);
            MainGameController.enableInteractPane();
          });
    } else {
      MainGameController.enableInteractPane();
    }

    if (GameState.arrangedDestnationCity == "") {
      GameState.eleanorAi.runGpt(
          GptPromptEngineeringRoom3.getEightRandomCity(),
          (result) -> {
            System.out.println("GPT:" + result);

            List<String> cities = Helper.getTextBetweenChar(result, "^");
            GameState.destnationCities = cities.toArray(new String[cities.size()]);
            GameState.destnationCityIndex = Helper.getRandomNumber(0, cities.size() - 1);
            GameState.arrangedDestnationCity = (cities.get(GameState.destnationCityIndex));
            // Print the array
            System.out.println("Arranged:" + GameState.destnationCities.toString());
            System.out.println("Destnation city is " + GameState.arrangedDestnationCity);

            GameState.unarrangedDestnationCity = makeUnarrangedCityName(GameState.arrangedDestnationCity);

          });
    }
    // Generate a introduction message for puzzle game when player first enters
    // room.
    if (GameState.puzzleIntroMessageRoom3 == "") {
      GameState.eleanorAi.runGpt(
          GptPromptEngineeringRoom3.getIntroPuzzleMessage(),
          (result) -> {
            System.out.println(result);
            GameState.puzzleIntroMessageRoom3 = result;
          });
    }

    // Initialize the obsts list
    this.obstacles = new ArrayList<Rectangle>();
    Rectangle[] rectangles = {
        computer, boundary1, boundary2, boundary3, boundary4, boundary5, gate,
        bound1, bound2, bound3, computer, computer2,
        radar,
        chair1, chair2, decrypt
    };

    // Add all the obstacles to the list.
    for (Rectangle rectangle : rectangles) {
      this.obstacles.add(rectangle);
    }

    // Enable the character movement.
    character.enableMobility(obstacles, interactablePane.getChildren());

    switch (GameState.prevRoom) {
      case 1:
        character.setLayoutX(527);
        character.setLayoutY(210);

        break;
      case 2:
        character.setLayoutX(90);
        character.setLayoutY(154);

        break;
      default:
        character.setLayoutX(530);
        character.setLayoutY(210);
    }

    GameState.prevRoom = 3;
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
    // if (GameState.isAircraftCodeFound && GameState.isEncryptedMessageFound) {

    // GameState.eleanorAi.runGpt(
    // GptPromptEngineeringRoom2.generateFinalUnencrypted(),
    // s -> {
    // List<String> msg = Helper.getTextBetweenChar(s, "+");
    // if (msg.size() > 0) {
    // GameState.finalMsg = msg.get(0);
    // } else {
    // GameState.finalMsg = s;
    // }
    // });

    // GameState.eleanorAi.runGpt(
    // "User update: User has successfully decrypted the letter based on the objects
    // he got. He"
    // + " can now click the main door to exit. Send a response to user without
    // revealing"
    // + " the exit / main door and surrounded with * .");
    // // Set the aircraft code image to inventory.
    // Image decryptedLetter = new Image("/images/rooms/room3/paper.png");

    // MainGameController.removeObtainedItem("aircraftCode");
    // MainGameController.removeObtainedItem("treasure");
    // MainGameController.addObtainedItem(decryptedLetter, "decryptedLetter");
    // GameState.hasDecrypted = true;
    // } else {

    // GameState.eleanorAi.runGpt(
    // "User update: User has clicked on the encrypted letter and fail to decrypt.
    // He needs to"
    // + " get both encrypted message in pirate ship and aircraft code to decrypt. "
    // + "Send a response to user"
    // + " without revaling any step. If the user ask for hints give him. Only the
    // message"
    // + " surrounded with * will send to user .");
    // }
  }

  @FXML
  public void onClickDoor() throws IOException, ApiProxyException {
    System.out.println("Door clicked");
    System.out.println(GameState.currentBox);
    // go to the right room
    InstructionsLoadController.setText();

    // disable interact pane for transition
    MainGameController.disableInteractPane();
    MainGameController.removeOverlay(true);
    MainGameController.addOverlay("room1", true);
    GameState.eleanorAi.runGpt("User update: User has moved from ATC to his childhood home room.");
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

    // Set invisible at the start
    label1.setVisible(false);
    label2.setVisible(false);
    label3.setVisible(false);
    label4.setVisible(false);
    label5.setVisible(false);
    point1.setVisible(false);
    point2.setVisible(false);
    point3.setVisible(false);
    point4.setVisible(false);
    point5.setVisible(false);

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

  protected void fadeInRadarPoints(Circle city) {

    city.setVisible(true);
    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), city);
    fadeTransition.setFromValue(0.0);
    fadeTransition.setToValue(1.0);
    fadeTransition.setInterpolator(Interpolator.LINEAR); // Use linear interpolation
    fadeTransition.play();
  }

  protected void fadeOutRadarPoints(Circle city) {

    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), city);
    fadeTransition.setFromValue(1.0);
    fadeTransition.setToValue(0.0);
    fadeTransition.setInterpolator(Interpolator.LINEAR); // Use linear interpolation
    fadeTransition.play();
  }

}
