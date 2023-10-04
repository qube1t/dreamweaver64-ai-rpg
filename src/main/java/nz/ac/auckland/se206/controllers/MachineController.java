package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.CustomClipboardContent;
import nz.ac.auckland.se206.GameState;

public class MachineController {
    @FXML
    private Rectangle inventory;
    @FXML
    private AnchorPane machinePane;

    // Set up the drop event handler

    public void initialize() {

        machinePane.setOnDragOver(event -> {
            System.out.println("Dragged over");
            if (event.getGestureSource() != machinePane &&
                    event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }

            event.consume();
        });

        machinePane.setOnDragDropped(event -> {
            if (event.getDragboard().hasImage()) {
                System.out.println("Dropped");
                ImageView item = new ImageView(event.getDragboard().getImage());
                item.setFitWidth(50);
                item.setFitHeight(50);
                MainGameController.removeObtainedItem(GameState.currentDraggedItemIndex);
                System.out.println("item" + GameState.currentDraggedItemIndex + "removed");

                machinePane.getChildren().add(item);
            }

            event.setDropCompleted(true);
            event.consume();
        });
    }

    public Rectangle getInventory() {
        return inventory;
    }

    public AnchorPane getPane() {
        return machinePane;
    }
}
