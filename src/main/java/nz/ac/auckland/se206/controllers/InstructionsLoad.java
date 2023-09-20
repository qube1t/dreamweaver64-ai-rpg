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

  private static Label initialised_instruct_txt;
  private static Label initialised_time_txt;

  public void initialize() throws ApiProxyException {
    initialised_instruct_txt = instruct_txt;
    initialised_time_txt = time_txt;

    
    // loading
    // shelf setup
    setTexts(nz.ac.auckland.se206.GameState.instructionMsg, Integer.parseInt(GameState.gameMode[1].replaceAll("[\\D]", "")));
  }
  
  public static void setTexts(String a, int b) {
    initialised_instruct_txt.setText(a);
    initialised_time_txt.setText(
        "You have " + b + " mins.");
  }
}
