package nz.ac.auckland.se206.gpt;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineeringRoom2 {

  /**
   * Generate the prompt when the player enters the room 2 first time.
   * 
   * @return the prompt
   */
  public static String room2WelcomeMessage() {
    // Ask GPT for the introduction of the game.
    return "I am the computer architecture. The player now entered a pirate's ship which was his favorite"
        + " childhood video game. The player needs to interact with the pirate to get the treasure."
        + " Greet the user without any pirate colloquial, not including any hints. Surround the response"
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
   * Generate the unencrypted message that the player will get when they exit the
   * game.
   * 
   * @return the unencrypted message
   */
  public static String generateFinalUnencrypted() {
    return "This is the loading stage. Give the contents of the lost message to be shown to the"
        + " user. The contents of the letter needs to be between the character +. it has to"
        + " be only 2 sentences long. Maybe foretelling some prophecy. Should try to reveal"
        + " some fact";
  }

  /**
   * Generate the encrypted message that the player will get when they get the
   * treasure.
   * 
   * @return the encrypted message
   */
  public static String generateFinalEncrypted() {
    return "This is the loading stage. Return meaningless encryption of 1 sentence, and only"
        + " return the encryption with the character + before and after. ";
  }
}
