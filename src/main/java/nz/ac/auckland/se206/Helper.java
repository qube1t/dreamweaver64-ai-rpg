package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
  public static List<String> getBetweenChar(String str, String c) {
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
