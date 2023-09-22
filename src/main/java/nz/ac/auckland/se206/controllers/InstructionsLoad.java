package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.Helper;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class InstructionsLoad {

  private static Label initialisedInstructTxt;
  private static Label initialisedTimeTxt;

  public static void setText() {
    // setting random text
    System.out.println(GameState.factsAboutDW64);
    initialisedInstructTxt.setText(
        GameState.factsAboutDW64.get(Helper.getRandomNumber(0, GameState.factsAboutDW64.size())));
  }

  public static void setTime(String t) {
    // setting time, connect to timer
    initialisedTimeTxt.setText("You have " + t + " mins.");
  }

  public void initialize() throws ApiProxyException {
    initialisedInstructTxt = instructTxt;
    initialisedTimeTxt = timeTxt;

    // loading
    // shelf setup
    setText();
    setTime(Integer.parseInt(GameState.gameMode[1].replaceAll("[\\D]", "")) + "");
  }

  @FXML
  private Label instructTxt;
  @FXML
  private Label timeTxt;

  @FXML
  private void produceAnotherFact() {
    setText();
  }
}