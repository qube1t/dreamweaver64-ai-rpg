package nz.ac.auckland.se206.controllers;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.Helper;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom1;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class InstructionsLoad {
  @FXML private Label instruct_txt;
  @FXML private Label time_txt;

  public void initialize() throws ApiProxyException {
    instruct_txt.setText(nz.ac.auckland.se206.GameState.instructionMsg);
    time_txt.setText(
        "You have " + Integer.parseInt(GameState.gameMode[1].replaceAll("[\\D]", "")) + " mins.");

    // loading
    // shelf setup
    GameState.eleanorAi.runGpt(
        GptPromptEngineeringRoom1.get7Books(),
        str -> {
          List<String> matchesList = Helper.getTextBetweenChar(str, "\"");
          GameState.booksInRoom1 = matchesList.toArray(new String[matchesList.size()]);

          String ansBook = (matchesList.get(Helper.getRandomNumber(0, matchesList.size() - 1)));
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
  }
}
