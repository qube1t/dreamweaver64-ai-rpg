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
  private Rectangle depBoard;
  @FXML
  private Rectangle desk1;
  @FXML
  private Rectangle desk2;
  @FXML
  private Rectangle bound1;
  @FXML
  private Rectangle bound2;
  @FXML
  private Rectangle bound3;
  @FXML
  private Rectangle paper;
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
  private Pane clickPane;

  private ArrayList<Rectangle> obstacles;

  public void initialize() throws ApiProxyException {

    // Generate a random city destnation name for the puzzle game if it is not set
    if (GameState.arrangedDestnationCity == "") {
      GameState.eleanorAi.runGpt(
          GptPromptEngineeringRoom3.getRandomCity(),
          (result) -> {
            System.out.println(result);
            int startIndex = result.indexOf("^");
            int endIndex = result.indexOf("^", startIndex + 1);

            if (startIndex != -1 && endIndex != -1) {
              GameState.arrangedDestnationCity = result.substring(startIndex + 1, endIndex);
            }

            GameState.unarrangedDestnationCity = makeUnarrangedCityName(GameState.arrangedDestnationCity);
          });
    }
    // Only displays the welcome message to Room3 if the plauyer first enters the
    // room
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
        computer, computer2, chair1, chair2, gate, radar, desk1, desk2, depBoard, boundary1,
        boundary2, boundary3, boundary4, boundary5, bound1, bound2, bound3
    };

    // Add all the obstacles to the list.
    for (Rectangle rectangle : rectangles) {
      this.obstacles.add(rectangle);
    }

    // Enable the character movement.
    character.enableMobility(obstacles, clickPane.getChildren());

    switch (GameState.prevRoom) {
      case 1:
        character.setLayoutX(530);
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
    String unarrangedCityName = "";
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
    for (int i = 0; i < length; i++) {
      unarrangedCityName += cityName.charAt(randomNumbers[i]);
    }

    if (unarrangedCityName.equals(cityName)) {
      return makeUnarrangedCityName(cityName);
    }

    return unarrangedCityName.toUpperCase();
  }

  @FXML
  public void onClickRoom2() throws IOException {
    System.out.println("Room2 clicked");
    // go to the right room
    InstructionsLoad.setText();

    // disable interact pane for transition
    MainGame.disableInteractPane();
    MainGame.removeOverlay(true);
    MainGame.addOverlay("room2", true);
  }

  @FXML
  public void onClickComputer() throws IOException, ApiProxyException {
    System.out.println("Computer clicked");
    MainGame.addOverlay("sub3", false);
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
    MainGame.addOverlay("room3_puzzle", false);
    GameState.eleanorAi.runGpt(
        "User update: User has opened the unarranged word puzzle game. The correct city"
            + " name is "
            + GameState.arrangedDestnationCity
            + ". No reply is needed for this message. If the user ask for hints, give hint without"
            + " revealing the city name.");
  }

  @FXML
  public void onClickRadar() throws IOException, ApiProxyException {
    MainGame.addOverlay("radar_computer", false);
    GameState.eleanorAi.runGpt(
        "User update: User has opened the radar computer and the red point indicates the correct"
            + " location for treasure box location in another room. If the user ask for hints give"
            + " the hints. No need to respond to this message.");
  }

  @FXML
  /**
   * This method is called when the map is clicked It will open the map and set
   * the GameState to
   * true
   *
   * @throws IOException
   * @throws ApiProxyException
   */
  public void onClickMap() throws IOException, ApiProxyException {
    if (!GameState.isWorldMapOpened) {
      GameState.isWorldMapOpened = true;
    }

    System.out.println("Location clicked");
    MainGame.addOverlay("gps_current", false);
    // Generate GPT response to keep updated.
    GameState.eleanorAi.runGpt(
        "User update: User has opened the world map and achnowledge the current location. No need"
            + " to respond to this message.");
  }

  @FXML
  /**
   * This method is called when the book is clicked It will open the flight plan
   * if it is not open
   * and if the flight plan is open, then it will close the flight plan
   */
  public void clickPaperEvent() throws IOException, ApiProxyException {
    if (GameState.isAircraftCodeFound && GameState.isEncryptedMessageFound) {
      paperImage.setVisible(false);
      System.out.println("Decrypted letter released");

      GameState.eleanorAi.runGpt(
          "User update: User has successfully decrypted the letter based on the objects he got. He"
              + " can no2 click the main door to exit. Send a response to user without revealing"
              + " the exit / main door and surrounded with * .");
      // Set the aircraft code image to inventory.
      Image decryptedLetter = new Image("/images/rooms/room3/paper.png");

      MainGame.removeObtainedItem("aircraftCode");
      MainGame.removeObtainedItem("treasure");
      MainGame.addObtainedItem(decryptedLetter, "decryptedLetter");
      GameState.hasDecrypted = true;
    } else {

      GameState.eleanorAi.runGpt(
          "User update: User has clicked on the encrypted letter and fail to decrypt. He needs to"
              + " get both encrypted message and aircraft code to decrypt. Send a response to user"
              + " without revaling any step. If the user ask for hints give him. Only the message"
              + " surrounded with * will send to user .");
    }
  }

  @FXML
  public void onClickDoor() throws IOException {
    System.out.println("Door clicked");
    System.out.println(GameState.currentBox);
    // go to the right room
    InstructionsLoad.setText();

    // disable interact pane for transition
    MainGame.disableInteractPane();
    MainGame.removeOverlay(true);
    MainGame.addOverlay("room1", true);
  }
}
