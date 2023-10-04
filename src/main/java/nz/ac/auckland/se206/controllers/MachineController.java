package nz.ac.auckland.se206.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import nz.ac.auckland.se206.GameState;

public class MachineController {
    @FXML
    private Rectangle inventory1;
    @FXML
    private Rectangle inventory2;
    @FXML
    private AnchorPane machinePane;

    // Set up the drop event handler

    public void initialize() {

        machinePane.setOnDragOver(event -> {

            System.out.println("Dragged over");
            if (event.getGestureSource() != machinePane &&
                    event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.ANY);
            }

            event.consume();
        });

        machinePane.setOnDragDropped(event -> {
            double x = event.getX();
            double y = event.getY();
            if (event.getDragboard().hasImage()) {
                ImageView item = new ImageView(MainGameController.getImageSet().getOriginalImage());
                item.setFitHeight(40);
                item.setPreserveRatio(true);
                machinePane.getChildren().add(item);
                if (inventory1.getBoundsInParent().contains(x, y)) {
                    // Dropped into first inventory box
                    System.out.println("Dropped into first inventory box");
                    item.setLayoutX(58);
                    item.setLayoutY(100);
                } else if (inventory2.getBoundsInParent().contains(x, y)) {
                    // Dropped into first inventory box
                    System.out.println("Dropped into first inventory box");
                    item.setLayoutX(60);
                    item.setLayoutY(184);
                }
                MainGameController.removeObtainedItem(GameState.currentDraggedItemIndex);
                System.out.println("item" + GameState.currentDraggedItemIndex + "removed");

            }

            event.setDropCompleted(true);
            event.consume();
        });
    }

    public Rectangle getInventory() {
        return inventory1;
    }

    public AnchorPane getPane() {
        return machinePane;
    }
}
