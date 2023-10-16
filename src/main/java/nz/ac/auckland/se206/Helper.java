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
   * Returns a list of strings that are between two instances of a given character
   * in a given string.
   * If strict is true, only strings that are between two instances of the given
   * character will be returned.
   * If strict is false and no instances of the given character are found, the
   * original string will be returned.
   *
   * @param str    the string to search for matches in
   * @param c      the character to search for matches between
   * @param strict whether to only return matches that are strictly between two
   *               instances of the given character
   * @return a list of strings that are between two instances of the given
   *         character in the given string
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
   * Changes the treasure box to a new random box number, ensuring that the new
   * box
   * number is not the same as the current box number or the box that was clicked.
   *
   * @param currentBoxNumber The current treasure box number.
   * @param clickBox         The box that was clicked.
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
   * Returns a random integer between the specified minimum and maximum values
   * (inclusive).
   *
   * @param min the minimum value of the range (inclusive)
   * @param max the maximum value of the range (inclusive)
   * @return a random integer between the specified minimum and maximum values
   *         (inclusive)
   */
  public static int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  /**
   * Counts the number of occurrences of a character in a string.
   *
   * @param str the string to search for occurrences of the character
   * @param c   the character to count occurrences of
   * @return the number of occurrences of the character in the string
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
   * Enables access to an item by setting the button to be enabled and the image
   * to be null.
   *
   * @param btn The button to enable access to the item.
   * @param img The image to set to null.
   */
  public static void enableAccessToItem(Rectangle btn, ImageView img) {
    btn.setDisable(false);
    img.setImage(null);
  }

  /**
   * Disable access to an item. This is done by adding the loading gif and setting
   * the button to be disabled.
   *
   * @param btn
   * @param img
   */
  public static void disableAccessToItem(Rectangle btn, ImageView img) {
    btn.setDisable(true);
    img.setImage(new Image("/images/loading.gif"));
  }
}
