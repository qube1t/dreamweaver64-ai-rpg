package nz.ac.auckland.se206.gpt;

/**
 * This class contains methods to generate prompts for the game "Pirate's
 * Treasure Hunt".
 * The prompts include welcome message for room 2, responses for correct and
 * incorrect answers in a riddle,
 * an unencrypted message for when the player exits the game, and an encrypted
 * message for when the player gets the treasure.
 */
public class GptPromptEngineeringRoom2 {

  /**
   * Generate the prompt when the player enters the room 2 first time.
   *
   * @return the prompt
   */
  public static String room2WelcomeMessage() {
    // Ask GPT for the introduction of the game.
    return "I am the computer architecture. The player now entered a pirate's ship "
        + " which was player's favorite childhood video game. The player needs to "
        + " interact with the pirate to get the treasure."
        + " Greet the user without any pirate colloquial, not including any hints. "
        + " Surround the response to be displayed to the player to the user with *.";
  }

  /**
   * Generate the prompt when the player get wrong answer in the riddle.
   *
   * @return the prompt
   */
  public static String getPirateWrongResponse() {
    return "This is the loading stage. Reply in one sentence how a pirate would say wrong "
        + "  answer. Surround the sentence before and after with the character ^";
  }

  /**
   * Generate the prompt when the player get right answer in the riddle.
   *
   * @return the prompt
   */
  public static String getPirateRightResponse() {
    return "This is the loading stage. Reply in one sentence how a pirate would say correct "
        + " answer. Surround the sentence before and after with the character ^";
  }

  /**
   * Generate the unencrypted message that the player will get when they exit the
   * game.
   *
   * @return the unencrypted message
   */
  public static String generateFinalUnencrypted() {
    return "Give the contents of the lost message"
        + " The contents of the letter needs to be between the character +. it has to"
        + " be short and without any salutations. Maybe foretelling some prophecy. "
        + " Should be deep & philosophical";
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
