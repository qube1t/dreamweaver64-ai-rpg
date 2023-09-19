package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.components.Character;
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
  @FXML private ImageView lastFlightPlan;
  @FXML private ImageView departureBoard;
  @FXML private ImageView map;
  @FXML private Character character;
  @FXML private Pane clickPane;

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

    // // Generate the unarranged city name when room3 loads
    // if (GameState.unarrangedCityName == "") {
    //   GptEngine.runGpt(
    //       new ChatMessage("user", GptPromptEngineeringRoom3.getUnarrangedCity()),
    //       (result) -> {
    //         System.out.println(result);
    //         // Make the city name unarranged

    //         GameState.unarrangedCityName = makeUnarrangedCityName(result);
    //         System.out.println(GameState.unarrangedCityName);
    //       });
    // }

    GameState.unarrangedCityName = "TOKYO";

    // Set both images to invisible initially
    lastFlightPlan.setVisible(false);
    departureBoard.setVisible(false);

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

  @FXML
  public void onClickDepBoard() {
    System.out.println("DepBoard clicked");
    // If the departure board is currently open, then close it
    if (GameState.isDepBoardOpen) {
      GameState.isDepBoardOpen = false;
      fadeOutDepBoard();
    } else {
      // If the departure board is not open, then open it
      GameState.isDepBoardOpen = true;
      if (GameState.isPreviousFlightPlanOpen) {
        System.out.println("Previous flight plan is open");
        GameState.isPreviousFlightPlanOpen = false;
        fadeOutFlightPlan();
        fadeInDepBoard();
      } else {
        fadeInDepBoard();
      }
    }
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
    return unarrangedCityName.toUpperCase();
  }

  @FXML
  public void onClickComputer() {
    System.out.println("Computer clicked");
    // Set the scene to room3Sub
    // Switch to the chat view to solve the riddle.
    App.setUi("sub3");
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
  public void onClickDoor() {
    System.out.println("Door clicked");
    System.out.println(GameState.currentBox);
  }

  protected void fadeInFlightPlan() {
    lastFlightPlan.setVisible(true); // Set to visible before starting the animation

    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), lastFlightPlan);
    fadeTransition.setFromValue(0.0);
    fadeTransition.setToValue(1.0);
    fadeTransition.play();
  }

  protected void fadeOutFlightPlan() {
    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), lastFlightPlan);
    fadeTransition.setFromValue(1.0);
    fadeTransition.setToValue(0.0);
    fadeTransition.play();
    fadeTransition.setOnFinished(
        event -> {
          lastFlightPlan.setVisible(false);
        });
  }

  protected void fadeInDepBoard() {
    departureBoard.setVisible(true); // Set to visible before starting the animation

    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), departureBoard);
    fadeTransition.setFromValue(0.0);
    fadeTransition.setToValue(1.0);
    fadeTransition.play();
  }

  protected void fadeOutDepBoard() {
    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), departureBoard);
    fadeTransition.setFromValue(1.0);
    fadeTransition.setToValue(0.0);
    fadeTransition.play();
    fadeTransition.setOnFinished(
        event -> {
          departureBoard.setVisible(false);
        });
  }
}
