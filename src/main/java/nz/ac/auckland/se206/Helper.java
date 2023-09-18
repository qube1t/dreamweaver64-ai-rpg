package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
  public static void setBooksInRoom1() {
    GameState.booksInRoom1 =
        new String[] {
          "The Great Gatsby",
          "The Catcher in the Rye",
          "The Grapes of Wrath",
          "To Kill a Mockingbird",
          "The Color Purple",
          "Ulysses",
          "Beloved"
        };
  }

  public static List<String> getTextBetweenChar(String str, String c) {
    List<String> matchesList = new ArrayList<String>();
    Pattern pattern = Pattern.compile("\\" + c + "(.*?)\\" + c);
    Matcher m1 = pattern.matcher(str);

    while (m1.find()) {
      matchesList.add(m1.group().replace(c, ""));
    }
    return matchesList;
  }

  public static void changeTreasureBox(int currentBoxNumber) {

    // Generate a random number between 1 and 5
    int boxNumber = (int) (Math.random() * 5) + 1;

    // If the random number is the same as the box number, generate a new random number
    while (boxNumber == currentBoxNumber) {
      boxNumber = (int) (Math.random() * 5) + 1;
    }
    GameState.currentBox = boxNumber;
  }
}
