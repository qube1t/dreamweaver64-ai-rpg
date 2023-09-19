package nz.ac.auckland.se206.gpt;

import nz.ac.auckland.se206.GameState;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineeringRoom2 {

  public static String npcIntro() {
    return "You are the Pirate of this ship, you are greeting the players."
        + "say \"hello\" with pirate colloquial. and if "
        + !GameState.isBoxKeyFound
        + "say \"give me something to trade with me\" with pirate colloquial"
        + "and if "
        + GameState.isBoxKeyFound
        + !GameState.isTreasureFound
        + "say \"find a treasure\" with pirate colloquial";
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
