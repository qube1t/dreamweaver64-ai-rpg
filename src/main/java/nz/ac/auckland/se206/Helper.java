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

  // https://www.baeldung.com/java-generating-random-numbers-in-range
  public static int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }
}
