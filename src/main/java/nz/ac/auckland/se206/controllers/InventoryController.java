package nz.ac.auckland.se206.controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import nz.ac.auckland.se206.GameState;

public class InventoryController {

  @FXML private ImageView item1;
  @FXML private ImageView item2;
  @FXML private ImageView item3;
  @FXML private ImageView item4;
  @FXML private ImageView item5;
  @FXML private ImageView item6;
  @FXML private ImageView item7;
  @FXML private ImageView item8;

  @FXML private TextArea timer;
  @FXML private Button btnStart; 

  private Thread timeLimitThread;

  public void initialize() {

  }

  /**
   * Handles the start game button.
   *
   * @param event the key event
   * @throws IOException
   */
  @FXML
  private void onStartGame(ActionEvent event) throws IOException {
    GameState.isGameStarted = true;
    System.out.println("start game");
    if (GameState.chosenTime == 2) {
      timer.setText("120");
      setTimeLimit(120);
    } else if (GameState.chosenTime == 4) {
      timer.setText("240");
      setTimeLimit(240);
    } else if (GameState.chosenTime == 6) {
      timer.setText("360");
      setTimeLimit(360);
    } else {
      timer.setText("5");
      setTimeLimit(5);
    }
  }

  /**
   * Sets the time limit.
   * 
   * @param timeLimit
   */
  private void setTimeLimit(int timeLimit) {
    Task<Void> task =
        new Task<Void>() {
          @Override
          protected Void call() throws Exception {
            for (int currentTime = timeLimit; currentTime >= 0; currentTime--) {
              if (GameState.isPlayerWon || GameState.timeLimitReached) {
                break;
              }
              timer.setText(String.valueOf(currentTime));
              try {
                Thread.sleep(1000);
              } catch (InterruptedException e) {
                return null;
              }
            }
            Platform.runLater(() -> handleTimeLimitReached());
            return null;
          }
        };
    timeLimitThread = new Thread(task);
    timeLimitThread.setDaemon(true);
    timeLimitThread.start();
  }

  /**
   * Handles the time limit reached event.
   */
  private void handleTimeLimitReached() {
    GameState.timeLimitReached = true;
    if (!GameState.isPlayerWon) {
      System.out.println("time limit reached");
    }
  }

  /**
   * Sets the item to be found.
   * 
   * @param item the item to be found
   */
  public void showGottenItem() {
    if (GameState.isBookFound) {
        item1.setVisible(true);
    } else if (GameState.isBoxKeyFound) {
        item2.setVisible(true);
    } else if (GameState.isAccessKeyFound) {
        item3.setVisible(true);
    } else if (GameState.isTreasureFound) {
        item4.setVisible(true);
    } else if (GameState.isDecryptKeyFound) {
        item5.setVisible(true);
    } else if (GameState.isItem6Found) {
        item6.setVisible(true);
    } else if (GameState.isItem7Found) {
        item7.setVisible(true);
    } else if (GameState.isItem8Found) {
        item8.setVisible(true);
    }
  }
}
