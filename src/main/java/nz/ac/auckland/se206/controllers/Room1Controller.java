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

/** Controller class for the room view. */
public class Room1Controller {
  // static fields for gpt
  static boolean gptInit = false;
  static int gptStage = 0;
  static ImageView imgEndSt;

  public static void resetGptRoom1() {
    gptInit = false;
    gptStage = 0;
  }

  public static void initializeMap() {
    imgEndSt.setVisible(true);
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

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() throws ApiProxyException {
    ArrayList<Rectangle> obsts = new ArrayList<Rectangle>(
        Arrays.asList(
            rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8, rect9, rect10, rect11,
            rect12, rect13, rect14, rect15, rect16, rect17, rect18, rect19));

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

    // imgEndSt = imgEnd;

    if (GameState.tenSecondsLeft) {
      initializeMap();
    }
  }

  private void initGpt() throws ApiProxyException {
    // gettng books from gpt
    MainGameController.enableInteractPane();

    // getting room intro from gpt
    GameState.eleanorAi.runGpt(
        "The user has entered their childhood home. In this room they are encouraged to look"
            + " around. You can talk to the user. Only the chunk of text surrounded with the"
            + " character * before and after will be shown to the user. Keep the message 1"
            + " sentance. .");

    GameState.eleanorAi.runGpt(
        GptPromptEngineeringRoom1.get7Books(),
        str -> {
          List<String> matchesList = Helper.getTextBetweenChar(str, "\"");
          GameState.booksInRoom1 = matchesList.toArray(new String[matchesList.size()]);

          String ansBook = (matchesList.get(Helper.getRandomNumber(0, matchesList.size() - 1)));
          GameState.trueBook = ansBook;
          System.out.println(ansBook);

          // enable interact pane
          Helper.enableAccessToItem(shelfBtn, shelfLoaderImg);
          GameState.booksLoaded = true;
          // get riddle from gpt

          Helper.enableAccessToItem(leftDoorBtn, leftDoorLoaderImg);
          Helper.enableAccessToItem(rightDoorBtn, rightDoorLoaderImg);
        });
  }

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

  @FXML
  private void openBookShelf() throws IOException, ApiProxyException {
    // book shelf clicked
    MainGameController.addOverlay("book_shelf", false);
    GameState.eleanorAi.runGpt(
        "User update: User has opened book shelf. No reply is needed for this message.");
  }

  // private void enableAccessToItem(Rectangle btn, ImageView img) {
  //   btn.setDisable(false);
  //   img.setImage(null);
  // }

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

  @FXML
  private void onClickCrockeries() throws ApiProxyException, IOException {
    // crockeries clicked
    MainGameController.addOverlay("crockery_shelf", false);
    GameState.eleanorAi.runGpt(
        "User update, User has opened an empty book shelf. No reply needed");
  }

  @FXML
  private void onClickChest() throws ApiProxyException, IOException {
    // chest clicked
    MainGameController.addOverlay("chest", false);
    GameState.eleanorAi.runGpt(
        "User update: opened the chest, but there is nothing to see there. No reply needed.");
  }
}
