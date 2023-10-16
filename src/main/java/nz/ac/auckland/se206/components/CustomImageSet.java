package nz.ac.auckland.se206.components;

import javafx.scene.image.Image;

/**
 * A class representing a custom image set, which contains an original image and an ID.
 */
public class CustomImageSet {
  private Image originalImage;
  private String id;

  /**
   * A class representing a custom image set.
   * 
   * @param originalImage the original image to be used in the set
   * @param id the unique identifier for the image set
   */
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
