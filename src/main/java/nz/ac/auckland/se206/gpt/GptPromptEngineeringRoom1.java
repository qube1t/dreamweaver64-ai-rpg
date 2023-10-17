package nz.ac.auckland.se206.gpt;

import nz.ac.auckland.se206.GameState;

/**
 * This class represents the GPT prompt engineering for the Engineering Room 1
 * game.
 * It contains methods to generate game introduction, game instructions, a list
 * of 7 books,
 * a pirate-themed riddle, and a message to be sent to the user based on their
 * chat input.
 */
public class GptPromptEngineeringRoom1 {

  /**
   * Generates a GPT prompt engineering string for a riddle with the given word.
   *
   * @return the generated prompt engineering string
   */
  public static String gameIntro() {
    // Ask GPT for the introduction of the game.
    return "I am the computer architecture that runs DREAMWEAVER64, a futuristic technology that"
        + " creates dreams and allows people to relive their past or discover lost truths or"
        + " memories from their pasts. Your are the operator of this machine."
        + "You have to guide the user to finish their mission of finding the message of a lost"
        + " letter from their mother that they have forgotten. "
        + "I will update you on the user's actions, and request assistance. No"
        + " reply is needed for this message.";
  }

  /**
   * Returns the game instructions to be displayed to the user.
   * The user is given a limited time to complete the mission of recovering
   * a lost letter from their mother by revisiting their past memories.
   * If the time runs out, the mission is aborted.
   * The instructions are returned as a string with a maximum of 2 sentences,
   * surrounded by # symbols.
   *
   * @return the game instructions as a formatted string
   */
  public static String gameInstructions() {
    return "Give the user intructions on the mission. The dream will collapses after their"
        + " allocated time, and the mission aborted. They will revisit their past memories"
        + " to recover a lost letter from their mother. Maximum of 2 sentences. Surround the"
        + " sentences to be shown to the user with #";
  }

  /**
   * Returns the introduction message for the user when they enter the childhood
   * home in room 1.
   * The message encourages the user to look around and informs them that only the
   * text surrounded by
   * asterisks will be shown to them. The message is limited to one sentence.
   *
   * @return the introduction message for room 1
   */
  public static String room1Intro() {
    return "The user has entered their childhood home."
        + " In this room they are encouraged to look"
        + " around. You can talk to the user. Only the chunk of text surrounded with the"
        + " character * before and after will be shown to the user. Keep the message 1"
        + " sentance.";
  }

  /**
   * Returns a string containing a message to produce a list of 7 books that have
   * less than 7 characters, surrounded by quotes, to show on the shelf.
   *
   * @return a string containing the message to produce the list of 7 books.
   */
  public static String get7Books() {
    return "Produce a list of 7 book titles that are single word,"
        + " surrounded by quotes in an array, to show on the shelf. No response is needed to user";
  }

  /**
   * Returns a pirate-themed riddle in the form of a quote from the book ansbook
   * in 1 sentence. The quote is surrounded by the character ^ before and after.
   * The user needs to identify the book ansbook in the other room. Hints can be
   * given once the riddle has been asked.
   *
   * @param ansbook the book from which the quote will be taken
   * @return a pirate-themed riddle in the form of a quote from the book ansbook
   */
  public static String getRiddleForPirate(String ansbook) {
    // asking pirate riddle
    return "Give a riddle in the form of a quote from the book "
        + ansbook
        + " in 25 words. Only this time, say this riddle with a pirate colloquial. Surround the"
        + " quote before and after with the character ^ "
        + " You should not reveal the book title. You can give hints if player ask for.";
  }

  /**
   * Returns a message to be sent to the user based on their chat input.
   * If there are no hints remaining, the message reminds the user of the
   * current state of the dream and instructs them to reply as a normal
   * human in one sentence without giving hints or answers to any riddle.
   * If there are hints remaining, the message reminds the user of the
   * current state of the dream and instructs them to reply as a normal
   * human in one sentence. If the user asks for hints, the message inserts
   * the character ~ before every hint so that it is transmitted to them.
   * The message does not reveal the answer even if the user asks for it.
   *
   * @param chatInput the user's chat input
   * @return a message to be sent to the user
   */
  public static String getChatMessageFromUser(String chatInput) {
    // sending chat messages from user to gpt
    if (GameState.hintsRemaining == 0) {
      return "The user has send this message: '"
          + chatInput
          + "'. Reply as a normal human in 1 sentence. You cannot give hints or answers to any"
          + " riddle. But you can remind them of the current state of the dream.";
    }
    return "User sent you you: '"
        + chatInput
        + "' Reply with special character ~ in one sentence if the user asked "
        + "you for hints or help or stuck."
        + " Do not reveal the answer even if the user asks for it.";
  }
}
