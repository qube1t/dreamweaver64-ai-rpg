package nz.ac.auckland.se206.gpt;

import nz.ac.auckland.se206.GameState;

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

  public static String gameInstructions() {
    return "Give the user intructions on the mission. The dream will collapses after their"
        + " allocated time, and the mission aborted. They will revisit their past memories"
        + " to recover a lost letter from their mother. Maximum of 2 sentences. Surround the"
        + " sentences to be shown to the user with #";
  }

  public static String get7Books() {
    return "This is the loading stage. Produce a list of 7 books that have less than 7 characters,"
        + " surrounded by quotes, to show on the shelf";
  }

  public static String getRiddleForPirate(String ansbook) {
    return "This is the loading stage. Give a riddle in the form of a quote from"
        + ansbook
        + " in 1 sentence. Only this time, say this riddle with a pirate colloquial. surround the"
        + " quote before and after with the character ^. The user needs to identify the book. Do"
        + " not reveal the book name.";
  }

  public static String getPirateWrongResponse(){
    return "This is the loading stage. Reply in one sentence how a pirate would say wrong answer."
        + " Surround the sentence before and after with the character ^";
  }

  public static String getPirateRightResponse(){
    return "This is the loading stage. Reply in one sentence how a pirate would say correct answer."
        + " Surround the sentence before and after with the character ^";
  }

  public static String getChatMessageFromUser(String chatInput) {
    if (GameState.hintsRemaining == 0) {
      return "The user has send this message: '"
          + chatInput
          + "'. Reply as a normal human in 1 sentence. You cannot give hints or answers to any riddle."
          + " But you can remind them of the current state of the dream.";
    }
    return "The user has send this message: '"
        + chatInput
        + "'. Reply as a normal human in 1 sentence. If the user asks for hints, you should insert the character ~"
        + " before every hint. Do not reveal the answer even if the user asks for it.";
  }
}
