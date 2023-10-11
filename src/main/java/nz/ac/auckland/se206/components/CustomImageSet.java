package nz.ac.auckland.se206.components;

import javafx.scene.image.Image;

public class CustomImageSet {
    private Image originalImage;
    private String id;

    public CustomImageSet(Image originalImage, String id) {
        this.originalImage = originalImage;
        this.id = id;

    }

    public void setOriginalImage(Image originalImage) {
        this.originalImage = originalImage;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Image getOriginalImage() {
        return originalImage;
    }

    public String getId() {
        return id;
    }

}
