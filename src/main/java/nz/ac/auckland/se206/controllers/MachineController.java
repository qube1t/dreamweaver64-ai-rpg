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

public class MachineController {

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

    private static ArrayList<CustomImageSet> imageSet;
    private static int position1Taken = 0;
    private static int position2Taken = 0;
    private static Cursor custom;
    private static int firstEnter = 0;
    private static boolean hasGotLetter = false;

    public static void resetMachine() {
        imageSet = null;
        position1Taken = 0;
        position2Taken = 0;
    }

    // Set up the drop event handler

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
            if (event.getGestureSource() != machinePane &&
                    event.getDragboard().hasImage()) {
                if (item1.getBoundsInParent().contains(x, y) ||
                        item2.getBoundsInParent().contains(x, y)) {
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

    @FXML
    private void onClickLetter() {
        letter.toFront();
        System.out.println("Letter clicked");
        if (hasGotLetter == true && letter != null) {
            MainGameController.addObtainedItem(letter.getImage(), "letterMom");
            letter.setImage(null);
            GameState.hasDecrypted = true;
        }
    }

    @FXML
    private void onClickInventory1() {
        if (position1Taken == 1) {

            item1.setImage(null);
            MainGameController.addObtainedItem(imageSet.get(0).getOriginalImage(),
                    imageSet.get(0).getId());
            System.out.println("Removed item " + imageSet.get(0).getId());
            position1Taken = 0;
            imageSet.get(0).setId(null);
            imageSet.get(0).setOriginalImage(null);

        }
    }

    @FXML
    private void onClickInventory2() {
        if (position2Taken == 1) {
            item2.setImage(null);
            MainGameController.addObtainedItem(imageSet.get(1).getOriginalImage(),
                    imageSet.get(1).getId());
            position2Taken = 0;
            System.out.println("Remove item" + imageSet.get(1).getId());
            imageSet.get(1).setId(null);
            imageSet.get(1).setOriginalImage(null);

        }

    }

    @FXML
    private void onClickDecrypt() throws ApiProxyException {
        arrowAnimation.setVisible(true);
        arrowStatic.setVisible(false);
        decrypt.setDisable(true);
        decrypt.setOpacity(0.6);
        System.out.println("Decrypt button clicked");
        hasGotLetter = true;
        GameState.eleanorAi.runGpt(
                GptPromptEngineeringRoom3.decryptedLetter(),
                (result) -> {
                    System.out.println(result);
                    setDecrypted();
                });

    }

    private void setDecrypted() {
        if (hasGotLetter) {
            decrypt.setVisible(false);
            arrowAnimation.setVisible(false);
            arrowStatic.setVisible(true);
            item1.setImage(null);
            item2.setImage(null);
            position1Taken = 0;
            position2Taken = 0;
            Image letterMom = new Image("/images/letterMom.png");
            letter.setImage(letterMom);
            letter.setFitHeight(40);
            letter.setPreserveRatio(true);
        }

    }

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

    private void checkCorrectItem() {
        if (position1Taken == 1 &&
                position2Taken == 1) {

            String currentId = imageSet.get(0).getId().toLowerCase() +
                    imageSet.get(1).getId().toLowerCase();
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

    private void setItem(int position) {
        System.out.println("set item" + position + imageSet.get(position - 1).getId());
        Image item = imageSet.get(position - 1).getOriginalImage();
        if (position == 1) {
            item1.setImage(item);
            item1.setFitHeight(35);
            item1.setPreserveRatio(true);
            position1Taken = 1;
        } else if (position == 2) {
            item2.setImage(item);
            item2.setFitHeight(35);
            item2.setPreserveRatio(true);
            position2Taken = 1;
        }
    }

    private void dropItem(int positionNumber) {
        Image item = MainGameController.getImageSet().getOriginalImage();
        String id = MainGameController.getImageSet().getId();
        CustomImageSet newSet = new CustomImageSet(item, id);
        System.out.println(item + id);

        if (positionNumber == 1) {
            imageSet.set(0, newSet);
            item1.setImage(item);
            item1.setFitHeight(35);
            item1.setPreserveRatio(true);
            System.out.println("Dropped into first inventory box with id"
                    + imageSet.get(positionNumber - 1).getId());
            position1Taken = 1;
        } else if (positionNumber == 2) {
            imageSet.set(1, newSet);
            item2.setImage(item);
            item2.setFitHeight(35);
            item2.setPreserveRatio(true);
            System.out.println("Dropped into second inventory box with id" +
                    imageSet.get(positionNumber - 1).getId());
            position2Taken = 1;
        }

        MainGameController.removeObtainedItem(GameState.currentDraggedItemId);
        System.out.println("item" + GameState.currentDraggedItemId + "removed from top bar.");
    }

}
