package nz.ac.auckland.se206.components;

import javafx.scene.image.Image;

/**
 * A class representing a custom image set, which contains an original image and
 * an ID.
 */
public class CustomImageSet {
  private Image originalImage;
  private String id;

  /**
   * A class representing a custom image set. It contains an original image and an
   * ID. The ID is used to identify the image set.
   *
   * @param originalImage the original image to be used in the set
   * @param id            the unique identifier for the image set
   */
  public CustomImageSet(Image originalImage, String id) {
    this.originalImage = originalImage;
    this.id = id;

  }

  /**
   * Sets the original image for this CustomImageSet.
   *
   * @param originalImage the original image to be set
   */
  public void setOriginalImage(Image originalImage) {
    this.originalImage = originalImage;
  }

  /**
   * Sets the ID of this CustomImageSet.
   *
   * @param id the ID to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Returns the original image of this CustomImageSet.
   *
   * @return the original image of this CustomImageSet.
   */
  public Image getOriginalImage() {
    return originalImage;
  }

  public String getId() {
    return id;
  }

}
