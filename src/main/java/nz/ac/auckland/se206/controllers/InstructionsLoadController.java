package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/**
 * The InstructionsLoadController class is responsible for controlling the
 * loading of instructions and setting the time for the game.
 * It contains methods to set the text for the instructions and time, as well as
 * a method to produce another fact.
 */
public class InstructionsLoadController {

  private static Label initialisedTimeTxt;

  /**
   * Sets the text for the instructions by printing a random fact about DW64.
   */
  public static void setText() {
    // setting random text
    System.out.println(GameState.factsAboutDW64);
  }

  /**
   * Sets the time for the instructions load controller.
   *
   * @param t the time to be set in minutes
   */
  public static void setTime(String t) {
    // setting time, connect to timer
    initialisedTimeTxt.setText("You have " + t + " mins.");
  }

  /**
   * Initializes the InstructionsLoadController by setting the initial time and
   * text.
   *
   * @throws ApiProxyException if there is an issue with the API proxy.
   */
  public void initialize() throws ApiProxyException {
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

  /**
   * Calls the setText method to produce another fact.
   */
  @FXML
  private void produceAnotherFact() {
    setText();
  }
}