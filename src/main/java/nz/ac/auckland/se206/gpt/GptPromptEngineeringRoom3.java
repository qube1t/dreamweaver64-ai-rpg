package nz.ac.auckland.se206.gpt;

public class GptPromptEngineeringRoom3 {

  /**
   * Generates a GPT prompt engineering string for a riddle with the given word.
   *
   * @param wordToGuess the word to be guessed in the riddle
   * @return the generated prompt engineering string
   */
  public static String getAircraftCode() {
    return "Generate an aircraft code for the game with 2 letters and 2 numbers, reply directly"
        + " with the code and do not include any other text. Example reply: AC19";
  }

  public static String npcWelcomeMessage() {
    return "The player is in an escape room game and now entered one of the three rooms. When he"
        + " clicks on the radar screen , a radar computer will pop up and the red points on"
        + " the radar indicates the correct treasure box to discover in another room. When"
        + " the player clicks on the departature, a flight departature board will display"
        + " and he needs to find the correct airline matches the aircraft in the scene. And"
        + " the departature tells the player something about the destination. When the"
        + " player clicks on the book on the desk, a previous flight log will pop up and"
        + " tells something about the current location. When the player clicks on the"
        + " computer, a minigame will shown and the player needs to find the correct route"
        + " of the aircraft base on the information given by the departature board and the"
        + " previous flight log";
  }
}
