package nz.ac.auckland.se206.controllers;

import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.GptEngine;
import nz.ac.auckland.se206.components.Character;
import nz.ac.auckland.se206.gpt.ChatMessage;
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
      protectRadarComputer,
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
      bound3;
  @FXML private Circle box1, box2, box3, box4, box5;
  @FXML private ImageView lastFlightPlan;
  @FXML private ImageView departureBoard;
  @FXML private ImageView radar_image, radar_computer, map;
  @FXML private Character character;
  @FXML private AnchorPane radarPane;
  @FXML private AnchorPane chatBubblePane;
  @FXML private TextArea npcResponse;

  private boolean isRadarComputerOpen;
  private Timeline radarAnimation;
  private Circle[] radarPoints;
  private ImageView[] radarSystem;
  private ArrayList<Rectangle> obstacles;

  public void initialize() throws ApiProxyException {

    // Set character postion and movement.
    character.enableMobility(obstacles);
    character.setLayoutX(530);
    character.setLayoutY(210);

    // Initialize the radar points and radarObjects to a list.
    this.radarPoints = new Circle[] {box1, box2, box3, box4, box5};
    this.radarSystem = new ImageView[] {radar_image, radar_computer};

    // Set the radar computer boolean to false initially
    isRadarComputerOpen = false;

    // Disable radar system initially
    disableRadarSystem();

    // Configure the chat bubble
    npcResponse.setWrapText(true);
    npcResponse.setEditable(false);

    // Set the chat bubble to invisible initially
    chatBubblePane.setVisible(false);
    npcResponse.setVisible(false);

    GptEngine.runGpt(
        new ChatMessage("user", GptPromptEngineeringRoom3.npcWelcomeMessage()),
        (result) -> {
          System.out.println(result);
          // Handle the result as needed
          System.out.println("first");
          chatBubblePane.setVisible(true);
          npcResponse.setVisible(true);
          npcResponse.setText(result);
        });

    // GptEngine.runGpt(
    // new ChatMessage("user", GptPromptEngineeringRoom3.getAircraftCode()),
    // (result) -> {
    //  System.out.println("second");
    // System.out.println(result);
    // });

    // Start a new thread to get the aircraft code

    // Set the aircaraft code and assign to game state
    // if (GameState.aircraftCode == null) {
    // GameState.aircraftCode =
    // gptManager.getGptResponseAsString(GptPromptEngineeringRoom3.getAircraftCode());
    // }

    // Call the set radar point color method to set the most up to date correct box
    if (GameState.currentBox == -1) {
      // Generate a random number between 1 and 5 if the correct treasure box is not set
      int randomBox = (int) (Math.random() * 5 + 1);
      changeCorrectBox(randomBox);
      GameState.currentBox = randomBox;
    } else {
      changeCorrectBox(GameState.currentBox);
    }

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

    radarPane.setMouseTransparent(true);
    radar_computer.setMouseTransparent(true);
    radar_image.setMouseTransparent(true);
    protectRadarComputer.setMouseTransparent(true);

    // Initialize the radarAnimation timeline
    this.radarAnimation =
        new Timeline(
            new KeyFrame(Duration.seconds(0), event -> fadeInRadarPoints()),
            new KeyFrame(Duration.seconds(1), event -> fadeOutRadarPoints()));
    radarAnimation.setCycleCount(Timeline.INDEFINITE);
    radarAnimation.setOnFinished(
        event -> {
          // Restart the animation when it completes
          radarAnimation.play();
        });
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

  @FXML
  public void onClickComputer() {
    System.out.println("Gate clicked");
    // Set the scene to room3Sub
    // Switch to the chat view to solve the riddle.
    App.setUi("sub3");
  }

  @FXML
  public void onClickProtectRadar() {
    if (isRadarComputerOpen) {
      protectRadarComputer.setVisible(true);
      protectRadarComputer.setDisable(false);
    }
  }

  @FXML
  public void onClickRadar() {
    isRadarComputerOpen = true;
    System.out.println("Radar clicked");
    fadeInRadarComputer();
  }

  @FXML
  /**
   * This method is called when the book is clicked It will open the flight plan if it is not open
   * and if the flight plan is open, then it will close the flight plan
   */
  public void onClickBook() {
    System.out.println("Book clicked");
    if (GameState.isPreviousFlightPlanOpen) {
      GameState.isPreviousFlightPlanOpen = false;
      fadeOutFlightPlan();
    } else {
      if (GameState.isDepBoardOpen) {
        GameState.isDepBoardOpen = false;
        fadeOutDepBoard();
        fadeInFlightPlan();
      } else {
        fadeInFlightPlan();
      }
      GameState.isPreviousFlightPlanOpen = true;
    }
  }

  // protected void handleGuessCity() {
  // guessCity.setDisable(false);
  // String city = guessCity.getText();
  // if (city.equalsIgnoreCase("Tokyo")) {
  // System.out.println("Correct");
  // guessCity.setVisible(false);
  // guessCity.setDisable(true);
  // GameState.isCityFound = true;
  // } else {
  // System.out.println("Wrong");
  // guessCity.setText("Wrong");
  // }
  // }

  @FXML
  public void onCloseObject() {
    System.out.println("Map clicked");
    // If the radar computer is currently open and player clicks on the map, then close the radar
    // computer

    if (isRadarComputerOpen) {
      if (isRadarComputerOpen) {
        radarAnimation.stop(); // Stop the radar animation
        isRadarComputerOpen = false;
      }
    }
    radar_computer.setVisible(false);
    radar_image.setVisible(false);
    box1.setVisible(false);
    box2.setVisible(false);
    box3.setVisible(false);
    box4.setVisible(false);
    box5.setVisible(false);
  }

  /**
   * This method randomly select one of the boxes and change its color to red
   *
   * @return the snumber of the box that has been changed
   */
  public void changeCorrectBox(int currentBox) {
    // Change the color of the current box to green
    box1.setStyle("-fx-fill: #0b941b");
    box2.setStyle("-fx-fill: #0b941b");
    box3.setStyle("-fx-fill: #0b941b");
    box4.setStyle("-fx-fill: #0b941b");
    box5.setStyle("-fx-fill: #0b941b");

    // Change the color of the box
    if (currentBox == 1) {
      box1.setStyle("-fx-fill: red");
    } else if (currentBox == 2) {
      box2.setStyle("-fx-fill: red");
    } else if (currentBox == 3) {
      box3.setStyle("-fx-fill: red");
    } else if (currentBox == 4) {
      box4.setStyle("-fx-fill: red");
    } else if (currentBox == 5) {
      box5.setStyle("-fx-fill: red");
    }
  }

  @FXML
  public void onClickDoor() {
    System.out.println("Door clicked");
    System.out.println(GameState.currentBox);
  }

  protected void disableRadarSystem() {
    radar_computer.setVisible(false);
    radar_image.setVisible(false);
    box1.setVisible(false);
    box2.setVisible(false);
    box3.setVisible(false);
    box4.setVisible(false);
    box5.setVisible(false);
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

  protected void fadeInRadarPoints() {
    for (Circle radarPoint : radarPoints) {
      radarPoint.setVisible(true);
      FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), radarPoint);
      fadeTransition.setFromValue(0.0);
      fadeTransition.setToValue(1.0);
      fadeTransition.setInterpolator(Interpolator.LINEAR); // Use linear interpolation
      fadeTransition.play();
    }
  }

  protected void fadeOutRadarPoints() {
    for (Circle radarPoint : radarPoints) {

      FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), radarPoint);
      fadeTransition.setFromValue(1.0);
      fadeTransition.setToValue(0.0);
      fadeTransition.setInterpolator(Interpolator.LINEAR); // Use linear interpolation
      fadeTransition.play();
    }
  }

  protected void fadeInRadarComputer() {

    for (ImageView obj : radarSystem) {
      obj.setVisible(true);
      FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), obj);
      fadeTransition.setFromValue(0.0);
      fadeTransition.setToValue(1.0);
      fadeTransition.play();
    }
    radarAnimation.play();
  }
}
