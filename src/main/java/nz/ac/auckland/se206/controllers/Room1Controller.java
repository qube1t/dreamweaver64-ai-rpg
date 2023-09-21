package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.Helper;
import nz.ac.auckland.se206.components.Character;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom1;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/** Controller class for the room view. */
public class Room1Controller {

  // @FXML private Rectangle door;
  // @FXML private Rectangle window;
  // @FXML private Rectangle vase;

  @FXML private Character character;
  @FXML private Rectangle rect1;
  @FXML private Rectangle rect2;
  @FXML private Rectangle rect3;
  @FXML private Rectangle rect4;
  @FXML private Rectangle rect5;
  @FXML private Rectangle rect6;
  @FXML private Rectangle rect7;
  @FXML private Rectangle rect8;
  @FXML private Rectangle rect9;
  @FXML private Rectangle rect10;
  @FXML private Rectangle rect11;
  @FXML private Rectangle rect12;
  @FXML private Rectangle rect13;
  @FXML private Rectangle rect14;
  @FXML private Rectangle rect15;
  @FXML private Rectangle rect16;
  @FXML private Rectangle rect17;
  @FXML private Rectangle rect19;
  @FXML private Rectangle rect20;
  @FXML private Rectangle rect21;
  @FXML private Rectangle rect22;
  @FXML private Rectangle shelf_btn;

  static boolean gptInit = false;
  static int gptStage = 0;

  @FXML private Pane interactablePane;

  /** Initializes the room view, it is called when the room loads. */
  public void initialize() throws ApiProxyException {
    ArrayList<Rectangle> obsts =
        new ArrayList<Rectangle>(
            Arrays.asList(
                rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8, rect9, rect10, rect11, rect12,
                 rect13, rect14, rect15, rect16, rect17, rect19, rect20, rect21, rect22));
    // obsts.add(0, rect1);
    // Initialization code goes here
    character.enableMobility(obsts, interactablePane.getChildren());
    
    if (!gptInit) {
      initGpt();
      gptInit = true;
    }
    
    switch(GameState.prevRoom){
      case 2:
        character.setLayoutX(586);
        character.setLayoutY(450);
        break;
      case 3:
        character.setLayoutX(34);
        character.setLayoutY(362);
        break;
      default:
    character.setLayoutX(250);
    character.setLayoutY(250);
    }

    GameState.prevRoom = 1;
  }

  private void initGpt() throws ApiProxyException {
    GameState.eleanorAi.runGpt(
        GptPromptEngineeringRoom1.get7Books(),
        str -> {
          List<String> matchesList = Helper.getTextBetweenChar(str, "\"");
          GameState.booksInRoom1 = matchesList.toArray(new String[matchesList.size()]);

          String ansBook = (matchesList.get(Helper.getRandomNumber(0, matchesList.size() - 1)));
          GameState.trueBook = ansBook;
          System.out.println(ansBook);
          // gptStage++;
          MainGame.enableInteractPane();
          // riddle for book

          try {
            GameState.eleanorAi.runGpt(
                GptPromptEngineeringRoom1.getRiddleForPirate(ansBook),
                (_str) -> {
                  List<String> pirateDialogue = Helper.getTextBetweenChar(_str, "^");
                  if (pirateDialogue.size() > 0) {
                    GameState.pirateRiddle = pirateDialogue.get(0).replaceAll("\"", "");
                  }
                });
          } catch (ApiProxyException e) {
            e.printStackTrace();
          }
        });

    GameState.eleanorAi.runGpt(
        "The user has entered their childhood home. In this room they are encouraged to look"
            + " around. You can talk to the user. Only the chunk of text surrounded with the"
            + " character * before and after will be shown to the user. Keep the message 1"
            + " sentance. .");
  }

  @FXML
  public void goToLeftRoom() throws IOException, InterruptedException {
    InstructionsLoad.setTexts("", 0);
    MainGame.disableInteractPane();
    // Thread.sleep(500);
    MainGame.removeOverlay(true);
    MainGame.addOverlay("room3", true);
  }

  @FXML
  private void goToRightRoom() throws IOException, InterruptedException {
    InstructionsLoad.setTexts("", 0);
    MainGame.disableInteractPane();
    // Thread.sleep(500);
    MainGame.removeOverlay(true);
    MainGame.addOverlay("room2", true);
  }

  @FXML
  private void openBookShelf() throws IOException, ApiProxyException {
    MainGame.addOverlay("book_shelf", false);
    GameState.eleanorAi.runGpt(
        "User update: User has opened book shelf. No reply is needed for this message.");
  }

  @FXML
  private void openMainDoor() throws ApiProxyException, IOException {
    GameState.eleanorAi.runGpt(
        "User update, User has tried to open main exit without solving the mission. No reply"
            + " needed.");
    if (GameState.winTheGame) {
      GameState.mainGame.outer_pane
        .getChildren()
        .add((Region) FXMLLoader.load(App.class.getResource("/fxml/end_menu.fxml")));
    }
  }

  @FXML
  private void onClickCrockeries() throws ApiProxyException, IOException {
    MainGame.addOverlay("crockery_shelf", false);
    GameState.eleanorAi.runGpt(
        "User update, User has opened crockeries, but they are not accessible. No reply needed");
  }

  @FXML
  private void onClickChest() throws ApiProxyException, IOException {
    MainGame.addOverlay("chest", false);
    GameState.eleanorAi.runGpt(
        "User update: opened the chest, but there is nothing to see there. No reply needed.");
  }
}
