package nz.ac.auckland.se206;

import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;

/**
 * CustomClipboardContent is a class that extends the ClipboardContent class and
 * represents the content to be copied to the clipboard.
 * It contains an ImageView and a String itemId.
 */
public class CustomClipboardContent extends ClipboardContent {
    private final ImageView imageView;
    private final String itemId;

    /**
     * Represents the content of a custom clipboard item.
     */
    public CustomClipboardContent(ImageView imageView, String itemId) {
        this.imageView = imageView;
        this.itemId = itemId;
    }

    /**
     * Returns the ImageView object associated with this CustomClipboardContent
     * object.
     *
     * @return the ImageView object associated with this CustomClipboardContent
     *         object
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Returns the ID of the item in the clipboard.
     *
     * @return the ID of the item in the clipboard
     */
    public String getItemId() {
        return itemId;
    }
}
