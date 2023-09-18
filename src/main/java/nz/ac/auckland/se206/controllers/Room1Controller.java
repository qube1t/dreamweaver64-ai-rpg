package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.GptEngine;
import nz.ac.auckland.se206.Helper;
import nz.ac.auckland.se206.components.Character;
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
  @FXML private Rectangle shelf_btn;

  static boolean gptInit = false;
  static int gptStage = 0;

  /**
   * Initializes the room view, it is called when the room loads.
   *
   * @throws ApiProxyException
   */
  public void initialize() throws ApiProxyException {

    ArrayList<Rectangle> obsts =
        new ArrayList<Rectangle>(
            Arrays.asList(
                rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8, rect9, rect10, rect11,
                rect12, rect13, rect14, rect15, rect16, rect17, rect19, rect20, rect21));
    // obsts.add(0, rect1);
    // Initialization code goes here
    character.enableMobility(obsts);
    character.setLayoutX(250);
    character.setLayoutY(250);

    if (!gptInit) {
      initGpt();
      gptInit = true;
    }
  }

  private void initGpt() throws ApiProxyException {
    // shelf setup

    GptEngine.runGpt(
        "Produce a list of 7 books that have less than 7 characters in their title as"
            + " an array",
        str -> {
          List<String> matchesList = Helper.getTextBetweenChar(str, "\"");
          GameState.booksInRoom1 = matchesList.toArray(new String[matchesList.size()]);

          String ansBook = (matchesList.get(Helper.getRandomNumber(0, matchesList.size() - 1)));
          System.out.println(ansBook);
          // gptStage++;
          MainGame.enableInteractPane();
          // riddle for book

          try {
            GptEngine.runGpt(
                "Give a riddle in the form of a quote from"
                    + ansBook
                    + " in 1 sentence. Say this"
                    + " riddle with pirate colloquial. surround the quote with the character %"
                    + " with no quotation marks",
                (_str) -> {
                  List<String> pirateDialogue = Helper.getTextBetweenChar(str, "%");
                });
          } catch (ApiProxyException e) {
            e.printStackTrace();
          }
        });
  }

  @FXML
  public void changeRoot() throws IOException {
    MainGame.addOverlay("room1", true);
  }

  @FXML
  private void openBookShelf() throws IOException, ApiProxyException {
    if (gptStage == 1) MainGame.addOverlay("book_shelf", false);
  }
}
