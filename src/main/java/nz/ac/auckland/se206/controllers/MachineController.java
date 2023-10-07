package nz.ac.auckland.se206.controllers;

import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.components.CustomImageSet;

public class MachineController {
    @FXML
    private Rectangle inventory1;
    @FXML
    private Rectangle inventory2;
    @FXML
    private AnchorPane machinePane;
    @FXML
    private Button decrypt;

    protected static ArrayList<CustomImageSet> imageSet;
    protected static int position1Taken = 0;
    protected static int position2Taken = 0;

    protected final int POSITION_X_1 = 67;
    protected final int POSITION_Y_1 = 104;
    protected final int POSITION_X_2 = 67;
    protected final int POSITION_Y_2 = 190;

    public static void resetMachine() {
        imageSet = null;
        position1Taken = 0;
        position2Taken = 0;
    }

    // Set up the drop event handler

    public void initialize() {
        decrypt.setOpacity(0.3);
        decrypt.setDisable(true);

        // Initialise the image set of size 3 only
        // the first time entering the machine.
        if (imageSet == null) {
            imageSet = new ArrayList<>();
            imageSet.add(0, null);
            imageSet.add(1, null);
        }

        checkCorrectItem();

        if (position1Taken == 1) {
            setItem(0);
        }
        if (position2Taken == 1) {
            setItem(1);
        }

        // Set background color to light purple when hovering
        decrypt.setOnMouseEntered(event -> {
            decrypt.setStyle("-fx-background-color: #e12ddb;");
        });

        // Reset to the default style when not hovering
        decrypt.setOnMouseExited(event -> {
            decrypt.setStyle("-fx-background-color: purple;");
        });

        // Set up the drag over event handler and only allow drop into 2 inventory
        // boxes.
        machinePane.setOnDragOver(event -> {
            double x = event.getX();
            double y = event.getY();

            System.out.println("Dragged over");
            if (event.getGestureSource() != machinePane &&
                    event.getDragboard().hasImage()) {
                if (inventory1.getBoundsInParent().contains(x, y) ||
                        inventory2.getBoundsInParent().contains(x, y)) {
                    event.acceptTransferModes(TransferMode.ANY);
                }
            }

            event.consume();
        });

        // Set up the drag dropped event handler and drop the item into the inventory
        // box only if the box is empty.
        machinePane.setOnDragDropped(event -> {
            double x = event.getX();
            double y = event.getY();
            if (event.getDragboard().hasImage()) {
                if (inventory1.getBoundsInParent().contains(x, y) && position1Taken == 0) {
                    dropItem(0);
                } else if (inventory2.getBoundsInParent().contains(x, y) && position2Taken == 0) {
                    dropItem(1);
                }
            }
            // Put rectangles to the front
            inventory1.toFront();
            inventory2.toFront();
            checkCorrectItem();
            event.setDropCompleted(true);
            event.consume();
        });
    }

    @FXML
    private void onClickBox1() {
        if (position1Taken == 1) {
            System.out.println("Box 1 clicked");
            // Find the ImageView by id
            ImageView itemToRemove = (ImageView) machinePane.lookup("#item1");

            // Remove the image from the machinePane
            machinePane.getChildren().remove(itemToRemove);
            // Add the item to the obtained items
            MainGameController.addObtainedItem(imageSet.get(0).getOriginalImage(),
                    imageSet.get(0).getId());
            position1Taken = 0;
        }
    }

    @FXML
    private void onClickDecrypt() {
        System.out.println("Decrypt button clicked");

    }

    @FXML
    private void onClickBox2() {
        if (position2Taken == 1) {
            System.out.println("return 2");
            // Find the ImageView by id
            ImageView itemToRemove = (ImageView) machinePane.lookup("#item2");
            machinePane.getChildren().remove(itemToRemove);
            MainGameController.addObtainedItem(imageSet.get(1).getOriginalImage(), imageSet.get(1).getId());
            position2Taken = 0;
        }

    }

    private void dropItem(int positionNumber) {
        ImageView item = new ImageView(MainGameController.getImageSet().getOriginalImage());
        imageSet.add(positionNumber, MainGameController.getImageSet());
        item.setFitHeight(40);
        item.setPreserveRatio(true);
        machinePane.getChildren().add(item);
        item.setId("item" + (positionNumber + 1));
        if (positionNumber == 0) {
            item.setLayoutX(POSITION_X_1);
            item.setLayoutY(POSITION_Y_1);
            System.out.println("Dropped into first inventory box");
            position1Taken = 1;
        } else if (positionNumber == 1) {
            item.setLayoutX(POSITION_X_2);
            item.setLayoutY(POSITION_Y_2);
            System.out.println("Dropped into second inventory box");
            position2Taken = 1;
        }

        MainGameController.removeObtainedItem(GameState.currentDraggedItemIndex);
        System.out.println("item" + GameState.currentDraggedItemIndex + "removed from top bar.");
    }

    private void checkCorrectItem() {
        if (imageSet.get(0) != null &&
                imageSet.get(1) != null) {

            String currentId = imageSet.get(0).getId().toLowerCase() +
                    imageSet.get(1).getId().toLowerCase();
            // Check if two correct items are here
            if (currentId.contains("treasureaircraftcode")
                    || currentId.contains("aircraftcodetreasure")) {
                System.out.println("Correct items");
                // Start animation of button from opacity of 0.5 to 1
                // Create a FadeTransition of 3 seconds duration.
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), decrypt);

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
        ImageView item = new ImageView(imageSet.get(position).getOriginalImage());
        item.setFitHeight(40);
        item.setPreserveRatio(true);
        machinePane.getChildren().add(item);
        item.setId("item" + (position + 1));
        if (position == 0) {
            item.setLayoutX(POSITION_X_1);
            item.setLayoutY(POSITION_Y_1);
            position1Taken = 1;
        } else if (position == 1) {
            item.setLayoutX(POSITION_X_2);
            item.setLayoutY(POSITION_Y_2);
            position2Taken = 1;
        }
        inventory1.toFront();
        inventory2.toFront();
    }

}
