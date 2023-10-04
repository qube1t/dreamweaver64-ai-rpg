package nz.ac.auckland.se206;

import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;

public class CustomClipboardContent extends ClipboardContent {
    private final ImageView imageView;
    private final String itemId;

    public CustomClipboardContent(ImageView imageView, String itemId) {
        this.imageView = imageView;
        this.itemId = itemId;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public String getItemId() {
        return itemId;
    }
}
