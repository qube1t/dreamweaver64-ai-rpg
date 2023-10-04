package nz.ac.auckland.se206.components;

import javafx.scene.image.Image;

public class CustomImageSet {
    private Image originalImage;

    public CustomImageSet(Image originalImage) {
        this.originalImage = originalImage;

    }

    public Image getOriginalImage() {
        return originalImage;
    }

}
