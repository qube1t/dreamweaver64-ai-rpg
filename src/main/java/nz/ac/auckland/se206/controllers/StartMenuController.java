package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import nz.ac.auckland.se206.App;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.GptEngine;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

public class StartMenuController {
  @FXML private ComboBox<String> difficulty;
  @FXML private ComboBox<String> timeLimit;

  @FXML Button startButton;

  public void initialize() throws ApiProxyException {
    difficulty.getItems().addAll("EASY", "MEDIUM", "HARD");
    timeLimit.getItems().addAll("2 minutes", "4 minutes", "6 minutes");
    new GptEngine();

    GptEngine.runGpt(
        "You are the programme behind DREAMWEAVER64, a futuristic technology that creates dreams"
            + " and allows people to relive their past or discover lost truths or memories from"
            + " their pasts. "
            + //
            "You have to guide the user to finish their task of finding the message of a lost"
            + " letter from their mother that they have forgotten. "
            + //
            "To speak with the user surround the message to transmit with the character ^. All"
            + " other text will be lost."
            + //
            "When asked for hints, surround the hints with the character *."
            + //
            // "As it affects the stability of the dream do not give hints if the character does
            // not"
            // + " have any left."
            // + //
            "The technology will update you on their actions. Always make sure that the above rules"
            + " are followed for proper communication with the person.");
  }

  @FXML
  public void onClickStartButton() {
    // Get the difficulty and time limit the user selected
    String difficulty = this.difficulty.getValue();
    String timeLimit = this.timeLimit.getValue();

    // If the difficulty or time limit is default, set it to easy and 2 minutes respectively
    if (difficulty == null) {
      difficulty = "EASY";
    }
    if (timeLimit == null) {
      timeLimit = "2 minutes";
    }

    GameState.gameMode = new String[] {difficulty, timeLimit};
    System.out.println(
        "Game started with"
            + GameState.gameMode[0]
            + " difficulty and "
            + GameState.gameMode[1]
            + " time limit");

    // Transition to the main game view
    try {
      App.setRoot("main_game");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
