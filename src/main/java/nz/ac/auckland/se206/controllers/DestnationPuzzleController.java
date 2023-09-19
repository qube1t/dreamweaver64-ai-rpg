package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class DestnationPuzzleController {
  @FXML private Label unarrangedCityName;

  public void initialize() throws ApiProxyException {

    System.out.println(GameState.unarrangedCityName);
    unarrangedCityName.setText(GameState.unarrangedCityName);
  }
}
