package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.components.Character;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom3;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class Room3Controller {

  @FXML
  private Rectangle computer,
      computer2,
      chair1,
      chair2,
      gate,
      radar,
      boundary1,
      boundary2,
      boundary3,
      boundary4,
      boundary5,
      depBoard,
      desk1,
      desk2,
      bound1,
      bound2,
      bound3,
      paper,
      clickableComputer,
      clickableRadar,
      clickableDoor,
      worldMap;
  @FXML private Circle box1, box2, box3, box4, box5;
  @FXML private ImageView lastFlightPlan;
  @FXML private ImageView departureBoard;
  @FXML private Character character;
  @FXML private AnchorPane radarPane;
  @FXML private ImageView radar_image, radar_computer, map;
  @FXML private Pane clickPane;

  private ArrayList<Rectangle> obstacles;

  public void initialize() throws ApiProxyException {

    // Generate the arranged city name and make it unarranged when room3 loads
    if ((GameState.arrangedDestnationCity == ""
        || GameState.arrangedDestnationCity.length() > 11)) {
      GameState.eleanorAi.runGpt(
          GptPromptEngineeringRoom3.getRandomCity(),
          (result) -> {
            // Get the city surrounded by #
            // Find the start and end indices of the aircraft code within single quotes
            int startIndex = result.indexOf("^");
            int endIndex = result.indexOf("^", startIndex + 1);

            if (startIndex != -1 && endIndex != -1)
              GameState.arrangedDestnationCity = result.substring(startIndex + 1, endIndex);

            GameState.unarrangedDestnationCity =
                makeUnarrangedCityName(GameState.arrangedDestnationCity);
          });
    }

    // Only displays the welcome message to Room3 if the plauyer first enters the room
    if (!GameState.isRoom3FirstEntered) {

      GameState.isRoom3FirstEntered = true;
      GameState.eleanorAi.runGpt(
          GptPromptEngineeringRoom3.room3WelcomeMessage(),
          (result) -> {
            System.out.println(result);
            MainGame.enableInteractPane();
          });
    } else {
      MainGame.enableInteractPane();
    }

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
      computer, computer2, chair1, chair2, gate, radar, desk1, desk2, depBoard, boundary1,
      boundary2, boundary3, boundary4, boundary5, bound1, bound2, bound3
    };

    for (Rectangle rectangle : rectangles) {
      this.obstacles.add(rectangle);
    }

    character.enableMobility(obstacles, clickPane.getChildren());

    character.setLayoutX(530);
    character.setLayoutY(210);
  }

  protected String makeUnarrangedCityName(String cityName) {
    String unarrangedCityName = "";
    int length = cityName.length();
    int[] randomNumbers = new int[length];
    for (int i = 0; i < length; i++) {
      randomNumbers[i] = i;
    }
    Random random = new Random();
    for (int i = 0; i < length; i++) {
      int randomIndexToSwap = random.nextInt(length);
      int temp = randomNumbers[randomIndexToSwap];
      randomNumbers[randomIndexToSwap] = randomNumbers[i];
      randomNumbers[i] = temp;
    }
    for (int i = 0; i < length; i++) {
      unarrangedCityName += cityName.charAt(randomNumbers[i]);
    }

    if (unarrangedCityName.equals(cityName)) return makeUnarrangedCityName(cityName);

    return unarrangedCityName.toUpperCase();
  }

  @FXML
  public void onClickRoom2() throws IOException {
    System.out.println("Room2 clicked");
    MainGame.removeOverlay(true);
    MainGame.addOverlay("room2", true);
  }

  @FXML
  public void onClickComputer() throws IOException, ApiProxyException {
    System.out.println("Computer clicked");
    MainGame.addOverlay("sub3", false);
  }

  @FXML
  public void onClickPuzzle() throws IOException, ApiProxyException {
    System.out.println("Puzzle clicked");
    MainGame.addOverlay("room3_puzzle", false);
    GameState.eleanorAi.runGpt(
        "User update: User has opened the unarranged word puzzle. The word indicates the destnation"
            + " city. No reply is needed for this message.");
  }

  @FXML
  public void onClickRadar() throws IOException, ApiProxyException {
    MainGame.addOverlay("radar_computer", false);
    GameState.eleanorAi.runGpt(
        "User update: User has opened the radar computer and the red point indicates the correct"
            + " treasure box location in another room. No need to respond to this message.");
  }

  @FXML
  public void onClickMap() throws IOException, ApiProxyException {
    if (!GameState.isWorldMapOpened) GameState.isWorldMapOpened = true;

    System.out.println("Location clicked");
    MainGame.addOverlay("gps_current", false);
    GameState.eleanorAi.runGpt(
        "User update: User has opened the world map and achnowledge the current location. No need"
            + " to respond to this message.");
  }

  @FXML
  /**
   * This method is called when the book is clicked It will open the flight plan if it is not open
   * and if the flight plan is open, then it will close the flight plan
   */
  public void clickPaperEvent() throws IOException, ApiProxyException {
    if (GameState.isAircraftCodeFound && GameState.isEncryptedMessageFound) {
      System.out.println("Decrypted letter released");
      GameState.eleanorAi.runGpt(
          "User update: User has successfully decrypted the letter based on the objects he got."
              + " Send a response to user surrounded by * .");
      // Set the aircraft code image to inventory.
      Image decryptedLetter = new Image("/images/rooms/room3/paper.png");
      MainGame.addObtainedItem(decryptedLetter, "decryptedLetter");
    } else {

      GameState.eleanorAi.runGpt(
          "User update: User has clicked on the encrypted letter and fail to open. He needs to get"
              + " both encrypted message and aircraft code to decrypt. Send a response to user"
              + " surrounded by *.");
    }
  }

  @FXML
  public void onClickDoor() throws IOException {
    System.out.println("Door clicked");
    System.out.println(GameState.currentBox);
    MainGame.removeOverlay(true);
    MainGame.addOverlay("room1", true);
  }
}
