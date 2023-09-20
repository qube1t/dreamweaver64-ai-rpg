package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.ObtainedItemsWithId;

public class EndCreditController {

  @FXML private Label game_mode;
  @FXML private Label time_limit;
  @FXML private Label time_taken;
  @FXML private Label item1;
  @FXML private Label item2;
  @FXML private Label item3;
  @FXML private Label item4;
  @FXML private ImageView imgItem1;
  @FXML private ImageView imgItem2;
  @FXML private ImageView imgItem3;
  @FXML private ImageView imgItem4;

  public void initialize() {
    getGame();
    getItem();
  }

  private void getGame() {
    long timeTaken = GameState.endTime - GameState.startTime;
    game_mode.setText("Game Mode: " + GameState.gameMode[0]);
    time_limit.setText("Time Limit: " + GameState.gameMode[1]);
    if (GameState.winTheGame) {
        time_taken.setText("Time Taken: " + timeTaken + " seconds");
    } else {
        time_taken.setText("Time Limit Exceeded");
    }
  }

  private void getItem() {
    for (int i = 0; i < MainGame.obtainedItems.size(); i++) {
      ObtainedItemsWithId item = MainGame.obtainedItems.get(i);
      if (i == 0) {
        item1.setText(item.getId());
        imgItem1.setImage(item.getImage());
      } else if (i == 1) {
        item2.setText(item.getId());
        imgItem2.setImage(item.getImage());
      } else if (i == 2) {
        item3.setText(item.getId());
        imgItem3.setImage(item.getImage());
      } else if (i == 3) {
        item4.setText(item.getId());
        imgItem4.setImage(item.getImage());
      }
    }
  }
}
