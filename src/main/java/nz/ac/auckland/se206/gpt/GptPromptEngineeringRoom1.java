package nz.ac.auckland.se206.gpt;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineeringRoom1 {

  /**
   * Generates a GPT prompt engineering string for a riddle with the given word.
   *
   * @param wordToGuess the word to be guessed in the riddle
   * @return the generated prompt engineering string
   */
  public static String gameIntro() {
    return "I am the computer architecture that runs DREAMWEAVER64, a futuristic technology that"
        + " creates dreams and allows people to relive their past or discover lost truths or"
        + " memories from their pasts. Your are the operator of this machine."
        + "You have to guide the user to finish their task of finding the message of a lost"
        + " letter from their mother that they have forgotten. "
        + "I will update you on the user's actions, and request assistance. No"
        + " reply is needed for this message.";
  }

  public static String get7Books() {
    return "Produce a list of 7 books that have less than 7 characters, surrounded by quotes, to"
        + " show on the shelf";
  }

  public static String getRiddleForPirate(String ansbook) {
    return "Give a riddle in the form of a quote from"
        + ansbook
        + " in 1 sentence. Only this time, say this riddle with a pirate colloquial. surround the"
        + " quote before and after with the character ^. Do not reveal the book name.";
  }
}
