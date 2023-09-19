package nz.ac.auckland.se206.gpt;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineeringRoom2 {

  public static String foundBoxKey() {
    return "Computer: Give the key to player to open any treasure box. The player is able to open"
        + " the treasure boxes and get treasure";
  }

  public static String clickWrongBox() {
    return "Computer: Player clicked wrong treasure box. Player cannot find treasure."
        + " All treasure boxes are now not clickable.";
  }

  public static String clickCorrectBox() {
    return "Computer: Player clicked correct treasure box. The treasure box is now opened and the"
        + " treasure is shown. Move to next stage";
  }
}
