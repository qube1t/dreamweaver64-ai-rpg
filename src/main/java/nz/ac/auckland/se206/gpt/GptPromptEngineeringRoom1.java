package nz.ac.auckland.se206.gpt;

/** Utility class for generating GPT prompt engineering strings. */
public class GptPromptEngineeringRoom1 {

  /**
   * Generates a GPT prompt engineering string for a riddle with the given word.
   *
   * @param wordToGuess the word to be guessed in the riddle
   * @return the generated prompt engineering string
   */
  public static String get7Books() {
    return "Computer: Produce a list of 7 books that have less than 7 characters in their title as"
        + " an array to show on the shelf";
  }

  public static String getRiddleForPirate(String ansbook) {

    return "Computer: Give a riddle in the form of a quote from"
        + ansbook
        + " in 1 sentence. Say this"
        + " riddle with pirate colloquial. surround the quote with the character ^"
        + " with no quotation marks. Do not reveal the book name.";
  }
}
