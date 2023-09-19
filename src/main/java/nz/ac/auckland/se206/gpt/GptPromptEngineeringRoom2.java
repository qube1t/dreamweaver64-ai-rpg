package nz.ac.auckland.se206.gpt;

import nz.ac.auckland.se206.GameState;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineeringRoom2 {

  public static String npcIntro() {
    return "Pirate: Greet player with pirate colloquialism. Your duty is guiding treasure. If "
        + !GameState.isBoxKeyFound
        + "say give me something to trade with me with pirate colloquialism.";
  }

  public static String foundBoxKey() {
    return "Computer: Gave the key to open the treasure box. The players are able to open the"
        + " treasure boxes and get treasure";
  }

  public static String clickWrongBox() {
    return "Computer: Players clicked wrong treasure box. All treasure boxes are not clickable"
               + " again";
  }

  public static String clickCorrectBox() {
    return "Computer: Players clicked correct treasure box. The treasure box is opened and the"
               + " treasure is shown";
  }
}
