package nz.ac.auckland.se206.gpt;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineeringRoom2 {

  /**
   * Generate the prompt when the player enters the room 2 first time.
   * 
   * @return the prompt
   */
  public static String room2WelcomeMessage() {
    return "User update: The user now entered a pirate's ship which was his favorite"
        + " childhood video game. You are the"
        + " computer. Do not use pirate colloquite. The player needs to"
        + " interact with different objects including solving a riddle to trade with pirate"
        + " to get the key. Using the key, the player can open the treasure box. You need to"
        + " achknowledge the current state of the game. Give the user intructions on the"
        + " mission with maximum 2 sentences, not including any hints. Surround the response"
        + " to be displayed to the player to the user with *.";
  }

  /**
   * Generate the prompt when the player get wrong answer in the riddle.
   * 
   * @return the prompt
   */
  public static String getPirateWrongResponse() {
    return "This is the loading stage. Reply in one sentence how a pirate would say wrong answer."
        + " Surround the sentence before and after with the character ^";
  }

  /**
   * Generate the prompt when the player get right answer in the riddle.
   * 
   * @return the prompt
   */
  public static String getPirateRightResponse() {
    return "This is the loading stage. Reply in one sentence how a pirate would say correct answer."
        + " Surround the sentence before and after with the character ^";
  }

  /**
   * Generate the prompt when the player get no key but try to open the treasure
   * box.
   * 
   * @return the prompt
   */
  public static String getPirateNoKeyResponse() {
    return "This is the loading stage. Reply in one sentence how a pirate would say no key."
        + " Surround the sentence before and after with the character ^";
  }

  /**
   * Generate the unencrypted message that the player will get when they exit the
   * game.
   * 
   * @return the unencrypted message
   */
  public static String generateFinalUnencrypted() {
    return "This is the loading stage. Give the contents of the lost message to be shown to the"
        + " user. The contents of the letter needs to be between the character +. it has to"
        + " be only 2 sentences long. maybe foretelling some prophecy. should try to reveal"
        + " some fact";
  }

  /**
   * Generate the encrypted message that the player will get when they get the
   * treasure.
   * 
   * @return the encrypted message
   */
  public static String generateFinalEncrypted() {
    return "This is the loading stage. Return meaningless encryption of 2 sentences, and only"
        + " return the encryption with the character + before and after. ";
  }
}
