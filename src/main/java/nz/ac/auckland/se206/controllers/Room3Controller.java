package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javafx.fxml.FXML;
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
      book,
      clickableComputer,
      clickableRadar,
      clickableDoor;
  @FXML private Circle box1, box2, box3, box4, box5;
  private Circle[] radarPoints;
  private ImageView[] radarObjects;
  private ArrayList<Rectangle> obsts;
  @FXML private ImageView lastFlightPlan;
  @FXML private ImageView departureBoard;
  @FXML private Character character;
  @FXML private AnchorPane radarPane;
  @FXML private ImageView radar_image, radar_computer, map;
  @FXML private Pane clickPane;
  private boolean isRadarComputerOpen;

  private ArrayList<Rectangle> obstacles;

  public void initialize() throws ApiProxyException {

    // //GptEngine.runGpt(
    //    // new ChatMessage("user", GptPromptEngineeringRoom3.npcWelcomeMessage()),
    //     (result) -> {
    //       System.out.println(result);
    //       // Handle the result as needed
    //       System.out.println("result");
    //     });

    // GptEngine.runGpt(
    //  new ChatMessage("user", GptPromptEngineeringRoom3.getAircraftCode()),
    // (result) -> {
    //   System.out.println(result);
    // });

    // Generate the arranged city name and make it unarranged when room3 loads
    if ((GameState.arrangedDestnationCity == ""
        || GameState.arrangedDestnationCity.length() > 11)) {
      GameState.eleanorAi.runGpt(
          GptPromptEngineeringRoom3.getRandomCity(),
          (result) -> {
            System.out.println(result);
            GameState.arrangedDestnationCity = result;
            // Make the city name unarranged
            GameState.unarrangedDestnationCity = makeUnarrangedCityName(result);
            // System.out.println(GameState.unarrangedCityName);
          });
    }

    // Generate the intro message for the puzzle
    if (GameState.puzzleIntroMessageRoom3 == "") {
      GameState.eleanorAi.runGpt(
          GptPromptEngineeringRoom3.getIntroPuzzleMessage(),
          (result) -> {
            System.out.println(result);

            GameState.puzzleIntroMessageRoom3 = result;
            MainGame.enableInteractPane();
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

    if (unarrangedCityName.equals(cityName)) {
      return makeUnarrangedCityName(cityName);
    }
    return unarrangedCityName.toUpperCase();
  }

  @FXML
  public void onClickComputer() throws IOException {
    System.out.println("Computer clicked");
    // System.out.println(GameState.currentLocatiions[GameState.currentCity - 1]);
    // System.out.println(GameState.arrangedCityName);
    // Set the scene to room3Sub
    // Switch to the chat view to solve the riddle.
    MainGame.addOverlay("sub3", false);
  }

  @FXML
  public void onClickLocation() throws IOException {
    System.out.println("Location clicked");
    MainGame.addOverlay("gps_current", false);
  }

  @FXML
  public void onClickRadar() throws IOException {
    MainGame.addOverlay("radar_computer", false);
  }

  @FXML
  /**
   * This method is called when the book is clicked It will open the flight plan if it is not open
   * and if the flight plan is open, then it will close the flight plan
   */
  public void clickBookEvent() throws IOException {

    System.out.println("Book clicked");
    MainGame.addOverlay("room3_puzzle", false);
    // if (GameState.isPreviousFlightPlanOpen) {
    // GameState.isPreviousFlightPlanOpen = false;
    // fadeOutFlightPlan();
    // } else {
    //  if (GameState.isDepBoardOpen) {
    //  GameState.isDepBoardOpen = false;
    //  fadeOutDepBoard();
    //  fadeInFlightPlan();
    // } else {
    //  fadeInFlightPlan();
    // }
    // GameState.isPreviousFlightPlanOpen = true;
    // }
  }

  @FXML
  public void onClickDoor() throws IOException {
    System.out.println("Door clicked");
    System.out.println(GameState.currentBox);
    MainGame.removeOverlay(true);
    MainGame.addOverlay("room1", true);
  }
}
