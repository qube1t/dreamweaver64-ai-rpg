package nz.ac.auckland.se206.controllers;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameState;
import nz.ac.auckland.se206.components.CustomImageSet;

public class MachineController {
    @FXML
    private Rectangle inventory1;
    @FXML
    private Rectangle inventory2;
    @FXML
    private AnchorPane machinePane;

    protected static ArrayList<CustomImageSet> imageSet;
    protected static int position1Taken = 0;
    protected static int position2Taken = 0;

    protected final int POSITION_X_1 = 67;
    protected final int POSITION_Y_1 = 104;
    protected final int POSITION_X_2 = 67;
    protected final int POSITION_Y_2 = 190;

    // Set up the drop event handler

    public void initialize() {

        imageSet = new ArrayList<>();
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
                    dropItem(1);
                } else if (inventory2.getBoundsInParent().contains(x, y) && position2Taken == 0) {
                    dropItem(2);
                }
            }
            event.setDropCompleted(true);
            event.consume();
        });
    }

    private void dropItem(int positionNumber) {
        ImageView item = new ImageView(MainGameController.getImageSet().getOriginalImage());
        imageSet.add(MainGameController.getImageSet());
        item.setFitHeight(40);
        item.setPreserveRatio(true);
        machinePane.getChildren().add(item);
        if (positionNumber == 1) {
            item.setLayoutX(POSITION_X_1);
            item.setLayoutY(POSITION_Y_1);
            System.out.println("Dropped into first inventory box");
            position1Taken = 1;
        } else if (positionNumber == 2) {
            item.setLayoutX(POSITION_X_2);
            item.setLayoutY(POSITION_Y_2);
            System.out.println("Dropped into second inventory box");
            position2Taken = 1;
        }

        MainGameController.removeObtainedItem(GameState.currentDraggedItemIndex);
        System.out.println("item" + GameState.currentDraggedItemIndex + "removed from top bar.");
    }

}
