package nz.ac.auckland.se206;

public class ObtainedItemsWithId {

  private javafx.scene.image.Image image;
  private String id;

  /**
   * Constructor for ObtainedItemsWithId.
   * 
   * @param itemImage the image of the item
   * @param id        the id of the item
   */
  public ObtainedItemsWithId(javafx.scene.image.Image itemImage, String id) {
    this.image = itemImage;
    this.id = id;
  }

  /**
   * Get the image of the item.
   * 
   * @return the image of the item
   */
  public javafx.scene.image.Image getImage() {
    return image;
  }

  /**
   * Get the id of the item.
   * 
   * @return the id of the item
   */
  public String getId() {
    return id;
  }
}
