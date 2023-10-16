package nz.ac.auckland.se206.controllers;

import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.components.CustomImageSet;
import nz.ac.auckland.se206.gpt.GptPromptEngineeringRoom3;
import nz.ac.auckland.se206.gpt.openai.ApiProxyException;

/**
 * The MachineController class controls the behavior of the machine UI element
 * in the game.
 * It manages the image set, position of items, and event handlers for the
 * machine.
 * The class provides methods to reset the machine, initialize the UI elements
 * and event handlers,
 * and handle user interactions with the machine such as dropping items into
 * inventory slots and obtaining the letter.
 */
public class MachineController {
  private static ArrayList<CustomImageSet> imageSet;
  private static int position1Taken = 0;
  private static int position2Taken = 0;
  private static Cursor custom;
  private static int firstEnter = 0;
  private static boolean hasGotLetter = false;

  /**
   * Resets the machine by setting the imageSet to null and resetting the
   * position1Taken and position2Taken variables to 0.
   */
  public static void resetMachine() {
    imageSet = null;
    position1Taken = 0;
    position2Taken = 0;
  }

  @FXML
  private AnchorPane machinePane;
  @FXML
  private Button decrypt;
  @FXML
  private ImageView machine;
  @FXML
  private ImageView arrowStatic;
  @FXML
  private ImageView arrowAnimation;
  @FXML
  private ImageView item1;
  @FXML
  private ImageView item2;
  @FXML
  private ImageView letter;

  // Set up the drop event handler

  /**
   * Initializes the MachineController by setting up the UI elements and event
   * handlers.
   * Sets custom cursor, sets decrypted, hides arrow animation, disables decrypt
   * button, removes letter if has decrypted,
   * sets items if positions are taken, initializes the image set of size 3 only
   * the first time entering the machine,
   * checks for correct item, sets background color to light purple when hovering
   * over decrypt button,
   * sets up the drag over event handler and only allows drop into 2 inventory
   * boxes,
   * sets up the drag dropped event handler and drops the item into the inventory
   * box only if the box is empty.
   */
  public void initialize() {

    setCustomCursor();
    setDecrypted();

    arrowAnimation.setVisible(false);
    decrypt.setOpacity(0.3);
    decrypt.setDisable(true);

    // If has decrypted, remove the letter
    if (GameState.hasDecrypted) {
      letter.setImage(null);
    }

    if (position1Taken == 1) {
      setItem(1);
    }
    if (position2Taken == 1) {
      setItem(2);
    }

    // Initialise the image set of size 3 only
    // the first time entering the machine.
    if (firstEnter == 0) {
      imageSet = new ArrayList<>(2);
      imageSet.add(0, null);
      imageSet.add(1, null);
      firstEnter = 1;
    }

    checkCorrectItem();

    // Set background color to light purple when hovering
    decrypt.setOnMouseEntered(event -> {
      decrypt.setStyle("-fx-background-color: #e12ddb;");
    });

    // Reset to the default style when not hovering
    decrypt.setOnMouseExited(event -> {
      decrypt.setStyle("-fx-background-color: #d27d2c;");
    });

    // Set up the drag over event handler and only allow drop into 2 inventory
    // boxes.
    machinePane.setOnDragOver(event -> {
      setCustomCursor();
      double x = event.getX();
      double y = event.getY();

      System.out.println("Dragged over");
      if (event.getGestureSource() != machinePane
          && event.getDragboard().hasImage()) {
        if (item1.getBoundsInParent().contains(x, y)
            || item2.getBoundsInParent().contains(x, y)) {
          event.acceptTransferModes(TransferMode.ANY);
        }
      }

      event.consume();
    });

    // Set up the drag dropped event handler and drop the item into the inventory
    // box only if the box is empty.
    machinePane.setOnDragDropped(event -> {
      setCustomCursor();
      double x = event.getX();
      double y = event.getY();
      if (event.getDragboard().hasImage()) {
        if (item1.getBoundsInParent().contains(x, y) && position1Taken == 0) {
          dropItem(1);
        } else if (item2.getBoundsInParent().contains(x, y) && position2Taken == 0) {
          dropItem(2);
        }
        checkCorrectItem();
      }

      event.setDropCompleted(true);
      event.consume();
    });
  }

  /**
   * This method is called when the user clicks on the letter button.
   * It brings the letter to the front and checks if the letter has already been
   * obtained.
   * If the letter has been obtained, it adds the obtained item to the
   * MainGameController and sets the letter image to null.
   * It also sets the GameState.hasDecrypted to true.
   */
  @FXML
  private void onClickLetter() {
    // Set the letter to front of FXML
    letter.toFront();
    System.out.println("Letter clicked");
    if (hasGotLetter == true && letter != null) {
      // If the letter has been obtained, add the obtained
      // item to the decrypted positon
      MainGameController.addObtainedItem(letter.getImage(), "letterMom");
      letter.setImage(null);
      GameState.hasDecrypted = true;
    }
  }

  /**
   * This method is called when the user clicks on the first inventory slot. If an
   * item is present in the slot, it is removed and added to the player's
   * inventory.
   */
  @FXML
  private void onClickInventory1() {
    if (position1Taken == 1) {

      // Remove item from inventory
      item1.setImage(null);
      MainGameController.addObtainedItem(imageSet.get(0).getOriginalImage(),
          imageSet.get(0).getId());
      System.out.println("Removed item " + imageSet.get(0).getId());
      position1Taken = 0;

      // Remove item from image set as well.
      imageSet.get(0).setId(null);
      imageSet.get(0).setOriginalImage(null);

    }
  }

  /**
   * Handles the event when the second inventory slot is clicked. If an item is
   * present in the slot, it is removed and added to the player's inventory.
   */
  @FXML
  private void onClickInventory2() {
    if (position2Taken == 1) {
      // Remove item from inventory 2 if there is an item placed
      item2.setImage(null);
      MainGameController.addObtainedItem(imageSet.get(1).getOriginalImage(),
          imageSet.get(1).getId());
      position2Taken = 0;
      System.out.println("Remove item" + imageSet.get(1).getId());
      // Reset the imageSet arraylist for position 1.
      imageSet.get(1).setId(null);
      imageSet.get(1).setOriginalImage(null);

    }

  }

  /**
   * Handles the onClick event for the Decrypt button.
   * This method sets the visibility of the arrow animation and disables the
   * Decrypt button.
   * It then runs the GPT model to decrypt the letter and sets the decrypted text.
   *
   * @throws ApiProxyException if there is an issue with the API proxy
   */
  @FXML
  private void onClickDecrypt() throws ApiProxyException {
    // Set the arrow animation to invisible.
    arrowAnimation.setVisible(true);
    arrowStatic.setVisible(false);
    // Hide the decrypt button
    decrypt.setDisable(true);
    decrypt.setOpacity(0.6);
    System.out.println("Decrypt button clicked");
    hasGotLetter = true;
    // Update GPT status for decrypted letter.
    GameState.eleanorAi.runGpt(
        GptPromptEngineeringRoom3.decryptedLetter(),
        (result) -> {
          System.out.println(result);
          setDecrypted();
        });

  }

  /**
   * Sets the decrypted state of the machine.
   * If the machine has already received a letter, it sets the decrypted state by
   * hiding the decryption button, arrow animation, and item images.
   * It also sets the position taken variables to 0 and sets the letter image to
   * the decrypted letter image.
   */
  private void setDecrypted() {
    if (hasGotLetter) {
      // Hide the decrypt button

      // Set decrypt button and arrow animationto invisible
      decrypt.setVisible(false);
      arrowAnimation.setVisible(false);
      arrowStatic.setVisible(true);
      // Set the drcrypted images to null.
      item1.setImage(null);
      item2.setImage(null);
      // Set the position taken variables to 0.
      position1Taken = 0;
      position2Taken = 0;
      Image letterMom = new Image("/images/letterMom.png");
      letter.setImage(letterMom);
      letter.setFitHeight(40);
      letter.setPreserveRatio(true);
    }

  }

  /**
   * Sets the custom cursor for the machine pane.
   * The cursor is set to a custom image of a machine.
   * The cursor is applied to all nodes in the machine pane that intersect with
   * the machine bounds.
   */
  private void setCustomCursor() {
    // Set the cursor to custom cursor
    Image cursor = new Image("/images/machineCursor.png", 16,
        27, true, true);
    custom = new ImageCursor(cursor);

    for (javafx.scene.Node node : machinePane.getChildren()) {
      if (node.getBoundsInParent().intersects(machine.getBoundsInParent())) {
        node.setCursor(custom);
      }
    }
  }

  /**
   * Checks if two correct items are in position 1 and 2, and if so, starts the
   * animation of the decrypt button from opacity of 0.5 to 1.
   * The animation is a FadeTransition of 3 seconds duration.
   *
   * @return void
   */
  private void checkCorrectItem() {
    if (position1Taken == 1 &&
        position2Taken == 1) {

      String currentId = imageSet.get(0).getId().toLowerCase()
          + imageSet.get(1).getId().toLowerCase();
      System.out.println("id checked" + currentId);
      // Check if two correct items are here
      if (currentId.equals("treasurecode")
          || currentId.contains("codetreasure")) {
        System.out.println("Correct items");
        // Start animation of button from opacity of 0.5 to 1
        // Create a FadeTransition of 3 seconds duration.
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), decrypt);

        fadeTransition.setFromValue(0.3);
        fadeTransition.setToValue(1);
        // One cycle only
        fadeTransition.setCycleCount(1);

        // Play the animation
        fadeTransition.play();
        decrypt.setDisable(false);

      }
    }

  }

  /**
   * Sets the image of the item at the specified position.
   *
   * @param position the position of the item to set
   */
  private void setItem(int position) {
    System.out.println("set item" + position + imageSet.get(position - 1).getId());
    // Using getOriginalImage() to get the original image in imageSet
    Image item = imageSet.get(position - 1).getOriginalImage();
    if (position == 1) {
      // Set the image of the item at inventory 1
      item1.setImage(item);
      item1.setFitHeight(35);
      item1.setPreserveRatio(true);
      position1Taken = 1;
    } else if (position == 2) {
      // Set the image of the item at inventory 2
      item2.setImage(item);
      item2.setFitHeight(35);
      item2.setPreserveRatio(true);
      position2Taken = 1;
    }
  }

  /**
   * Drops an item into the inventory box at the specified position number.
   *
   * @param positionNumber the position number of the inventory box to drop the
   *                       item into
   */
  private void dropItem(int positionNumber) {
    // Get the current dragged
    // item from the imageSet in main game controller
    Image item = MainGameController.getImageSet().getOriginalImage();
    String id = MainGameController.getImageSet().getId();
    CustomImageSet newSet = new CustomImageSet(item, id);
    System.out.println(item + id);

    if (positionNumber == 1) {
      // Handle the case when the item is dropped into inventory 1
      imageSet.set(0, newSet);
      item1.setImage(item);
      item1.setFitHeight(35);
      item1.setPreserveRatio(true);
      System.out.println("Dropped into first inventory box with id"
          + imageSet.get(positionNumber - 1).getId());
      position1Taken = 1;
    } else if (positionNumber == 2) {
      // Handle the case when the item is dropped into inventory 2
      imageSet.set(1, newSet);
      item2.setImage(item);
      item2.setFitHeight(35);
      item2.setPreserveRatio(true);
      System.out.println("Dropped into second inventory box with id" +
          imageSet.get(positionNumber - 1).getId());
      position2Taken = 1;
    }
    // Remove the item from the top bar in main game controller
    MainGameController.removeObtainedItem(GameState.currentDraggedItemId);
    System.out.println("item" + GameState.currentDraggedItemId + "removed from top bar.");
  }

}
