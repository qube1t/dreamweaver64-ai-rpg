package nz.ac.auckland.se206;

public class ObtainedItemsWithId {
    private javafx.scene.image.Image image;
    private String id;

    public ObtainedItemsWithId(javafx.scene.image.Image itemImage, String id) {
        this.image = itemImage;
        this.id = id;
    }

    public javafx.scene.image.Image getImage() {
        return image;
    }

    public String getId() {
        return id;
    }
}

