package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.Helper;
import nz.ac.auckland.se206.components.Character;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom1;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/**
 * The Room1Controller class is responsible for controlling the behavior of Room
 * 1 in the game.
 * It initializes the map, character mobility, and GPT-3 API for generating
 * prompts for the user to interact with.
 * It also enables/disables buttons and obstacles in the room based on the game
 * state.
 */
public class Room1Controller {
  // static fields for gpt
  static boolean gptInit = false;
  static int gptStage = 0;

  /**
   * Resets the GPT (Generative Pre-trained Transformer) for Room 1.
   * This method sets the gptInit flag to false and the gptStage to 0.
   */
  public static void resetGptRoom1() {
    gptInit = false;
    gptStage = 0;
  }

  @FXML
  private Character character;
  @FXML
  private Rectangle rect1;
  @FXML
  private Rectangle rect2;
  @FXML
  private Rectangle rect3;
  @FXML
  private Rectangle rect4;
  @FXML
  private Rectangle rect5;
  @FXML
  private Rectangle rect6;
  @FXML
  private Rectangle rect7;
  @FXML
  private Rectangle rect8;
  @FXML
  private Rectangle rect9;
  @FXML
  private Rectangle rect10;
  @FXML
  private Rectangle rect11;
  @FXML
  private Rectangle rect12;
  @FXML
  private Rectangle rect13;
  @FXML
  private Rectangle rect14;
  @FXML
  private Rectangle rect15;
  @FXML
  private Rectangle rect16;
  @FXML
  private Rectangle rect17;
  @FXML
  private Rectangle rect18;
  @FXML
  private Rectangle rect19;

  @FXML
  private Rectangle shelfBtn;
  @FXML
  private ImageView shelfLoaderImg;
  @FXML
  private ImageView mainDoorBlockImg;
  @FXML
  private Rectangle mainDoorBtn;

  @FXML
  private Pane interactablePane;
  @FXML
  private Rectangle leftDoorBtn;
  @FXML
  private Rectangle rightDoorBtn;
  @FXML
  private ImageView leftDoorLoaderImg;
  @FXML
  private ImageView rightDoorLoaderImg;

  /**
   * Initializes the Room1Controller by setting up the obstacles,
   * enabling/disabling buttons
   * based on game state, enabling character mobility, initializing GPT, setting
   * character
   * position, and initializing the map if ten seconds are left.
   *
   * @throws ApiProxyException if there is an issue with the API proxy
   */
  public void initialize() throws ApiProxyException {
    ArrayList<Rectangle> obsts = new ArrayList<Rectangle>(
        Arrays.asList(
            rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8, rect9, rect10, rect11,
            rect12, rect13, rect14, rect15, rect16, rect17, rect18, rect19));

    GameState.mainGame.clickGamePane();

    if (!GameState.booksLoaded) {
      // while loading
      shelfBtn.setDisable(true);
    } else {
      Helper.enableAccessToItem(shelfBtn, shelfLoaderImg);
      GameState.booksLoaded = true;
    }

    if (GameState.hasDecrypted) {
      // enable main door
      mainDoorBtn.setDisable(false);
      mainDoorBlockImg.setImage(null);
    }
    // Initialization code goes here
    character.enableMobility(obsts, interactablePane.getChildren());

    // first time initialisation
    if (!gptInit) {
      initGpt();
      gptInit = true;
    } else {
      // enable interact pane
      MainGameController.enableInteractPane();
      Helper.enableAccessToItem(leftDoorBtn, leftDoorLoaderImg);
      Helper.enableAccessToItem(rightDoorBtn, rightDoorLoaderImg);
    }

    // set character position
    switch (GameState.prevRoom) {
      case 2:
        character.setLayoutX(504);
        character.setLayoutY(312);
        break;
      case 3:
        character.setLayoutX(104);
        character.setLayoutY(352);
        break;
      default:
        character.setLayoutX(400);
        character.setLayoutY(256);
    }

    GameState.prevRoom = 1;
  }

  /**
   * Initializes the GPT-3 API for Room 1. Retrieves books from GPT-3 and sets up
   * the interact pane.
   * Only the chunk of text surrounded with the character * before and after will
   * be shown to the user.
   * Keep the message 1 sentence.
   *
   * @throws ApiProxyException if there is an issue with the API proxy
   */
  private void initGpt() throws ApiProxyException {
    // gettng books from gpt
    MainGameController.enableInteractPane();

    GameState.eleanorAi.runGpt(
        GptPromptEngineeringRoom1.room1Intro(),
        str -> {
          System.out.println("111");
        });

    GameState.eleanorAi.runGpt(
        GptPromptEngineeringRoom1.get7Books(),
        str -> {
          // System.out.println("222");
          List<String> matchesList = Helper.getTextBetweenChar(str, "\"", true);
          for (int i = 0; i < 7; i++) {
            GameState.booksInRoom1[i] = matchesList.get(i).replace(",", "");
          }

          String ansBook = matchesList.get(Helper.getRandomNumber(0, matchesList.size() - 1)).replace(",", "");
          GameState.trueBook = ansBook;
          System.out.println(ansBook);

          // enable interact pane
          Helper.enableAccessToItem(shelfBtn, shelfLoaderImg);
          GameState.booksLoaded = true;
          // get riddle from gpt
          GameState.isRoom1GptDone = true;
          Helper.enableAccessToItem(leftDoorBtn, leftDoorLoaderImg);
          Helper.enableAccessToItem(rightDoorBtn, rightDoorLoaderImg);
        });
  }

  /**
   * This method is called when the user wants to go to the left room. It disables
   * the interact pane for transition,
   * removes the overlay, adds the overlay for room3, and runs the GPT model to
   * update the user's location.
   *
   * @throws IOException          if there is an error with input/output
   * @throws InterruptedException if the thread is interrupted
   * @throws ApiProxyException    if there is an error with the API proxy
   */
  @FXML
  public void goToLeftRoom() throws IOException, InterruptedException, ApiProxyException {
    // go to the left room
    InstructionsLoadController.setText();

    // disable interact pane for transition
    MainGameController.disableInteractPane();
    MainGameController.removeOverlay(true);
    MainGameController.addOverlay("room3", true);
    GameState.eleanorAi.runGpt("User update: User has moved from his home to his work place ATC."
        + "No reply is required");
  }

  /**
   * This method is called when the user wants to move to the right room. It
   * disables the interact pane for transition,
   * removes the overlay, adds the overlay for room2, and runs the GPT model to
   * update the game state.
   *
   * @throws IOException          if there is an error with input/output
   * @throws InterruptedException if the thread is interrupted
   * @throws ApiProxyException    if there is an error with the API proxy
   */
  @FXML
  private void goToRightRoom() throws IOException, InterruptedException, ApiProxyException {
    // go to the right room
    InstructionsLoadController.setText();

    // disable interact pane for transition
    MainGameController.disableInteractPane();
    MainGameController.removeOverlay(true);
    MainGameController.addOverlay("room2", true);
    GameState.eleanorAi.runGpt("User update: User has moved from his home to the pirate"
        + " ship. No reply is required");
  }

  /**
   * Opens the book shelf and adds an overlay to the main game controller.
   * Also runs a GPT to update the game state with a message indicating that the
   * user has opened the book shelf.
   *
   * @throws IOException       if an I/O error occurs when opening the book shelf.
   * @throws ApiProxyException if an error occurs when running the GPT to update
   *                           the game state.
   */
  @FXML
  private void openBookShelf() throws IOException, ApiProxyException {
    // book shelf clicked
    MainGameController.addOverlay("book_shelf", false);
    GameState.eleanorAi.runGpt(
        "User update: User has opened book shelf. No reply is needed for this message.");
  }

  /**
   * Opens the main door if the user has decrypted the mission. If the user has
   * not decrypted the
   * mission, a message is sent to the Eleanor AI and no further action is taken.
   *
   * @throws ApiProxyException if there is an issue with the API proxy
   * @throws IOException       if there is an issue with input/output
   */
  @FXML
  private void openMainDoor() throws ApiProxyException, IOException {
    // main door clicked
    GameState.eleanorAi.runGpt(
        "User update, User has tried to open main exit without solving the mission. No reply"
            + " needed.");
    if (GameState.hasDecrypted) {
      GameState.winTheGame = true;
      App.setRoot("end_menu");
    }
  }

  /**
   * Handles the event when the crockeries button is clicked.
   * Adds an overlay for the crockery shelf to the main game controller.
   *
   * @throws ApiProxyException if there is an issue with the API proxy
   * @throws IOException       if there is an issue with IO operations
   */
  @FXML
  private void onClickCrockeries() throws ApiProxyException, IOException {
    // crockeries clicked
    MainGameController.addOverlay("crockery_shelf", false);
  }

  /**
   * Handles the event when the chest is clicked.
   * Adds an overlay to the main game controller to display the chest, and updates
   * the game state.
   *
   * @throws ApiProxyException if there is an issue with the API proxy
   * @throws IOException       if there is an issue with the input/output
   */
  @FXML
  private void onClickChest() throws ApiProxyException, IOException {
    // chest clicked
    MainGameController.addOverlay("chest", false);

  }
}
