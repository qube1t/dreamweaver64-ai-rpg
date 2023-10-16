package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 * The Helper class contains various static helper methods that can be used
 * throughout the application.
 */
public class Helper {

  /**
   * Get the text between two characters.
   * 
   * @param str
   * @param c
   * @return matchesList
   */
  public static List<String> getTextBetweenChar(String str, String c, boolean strict) {
    // get text between two characters, including the two characters
    List<String> matchesList = new ArrayList<String>();
    Pattern pattern = Pattern.compile("\\" + c + "(.*?)\\" + c);
    Matcher m1 = pattern.matcher(str);

    while (m1.find()) {
      matchesList.add(m1.group().replace(c, ""));
    }

    if (matchesList.size() == 0 && !strict) {
      matchesList.add(str);
    }

    return matchesList;
  }

  /**
   * Change the treasure box.
   * 
   * @param currentBoxNumber
   * @param clickBox
   */
  public static void changeTreasureBox(int currentBoxNumber, int clickBox) {

    // Generate a random number between 1 and 5
    int boxNumber = (int) (Math.random() * 5) + 1;

    // If the random number is the same as the box number, generate a new random
    // number
    while (boxNumber == clickBox || boxNumber == currentBoxNumber) {
      boxNumber = (int) (Math.random() * 5) + 1;
    }
    GameState.currentBox = boxNumber;
  }

  /**
   * Get a random number between two numbers.
   * 
   * @param min
   * @param max
   * @return
   */
  // https://www.baeldung.com/java-generating-random-numbers-in-range
  public static int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  /**
   * Count the number of occurences of a character in a string.
   * 
   * @param str
   * @param c
   * @return count
   */
  public static int countOccurences(String str, String c) {
    // count the number of occurences of a character in a string
    int count = 0;
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == c.charAt(0)) {
        count++;
      }
    }
    return count;
  }

  /**
   * Enable access to an item.
   * 
   * @param btn
   * @param img
   */
  public static void enableAccessToItem(Rectangle btn, ImageView img) {
    btn.setDisable(false);
    img.setImage(null);
  }

  /**
   * Disable access to an item.
   * 
   * @param btn
   * @param img
   */
  public static void disableAccessToItem(Rectangle btn, ImageView img) {
    btn.setDisable(true);
    img.setImage(new Image("/images/loading.gif"));
  }
}
