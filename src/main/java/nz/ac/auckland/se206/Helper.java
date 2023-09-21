package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

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

  // https://www.baeldung.com/java-generating-random-numbers-in-range
  public static int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  public static int countOccurences(String str, String c) {
    int count = 0;
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == c.charAt(0)) {
        count++;
      }
    }
    return count;
  }
}
