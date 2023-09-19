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

  /**
   * Generates a GPT prompt engineering string for the NPC welcome message.
   *
   * @return the generated prompt engineering string
   * @return
   */
  public static String npcWelcomeMessage() {
    return "The player is an escape room game and now entered one of the three rooms that is an"
        + " air traffic control tower. When he clicks on the radar screen , a radar computer"
        + " will pop up and the red points on the radar indicates the correct treasure box"
        + " to discover in another room. When the player clicks on the departature, a flight"
        + " departature board will display and he needs to find the correct airline matches"
        + " the aircraft in the scene. And the departature tells the player something about"
        + " the destination. When the player clicks on the book on the desk, a previous"
        + " flight plan will display and tells something about the current location. When the"
        + " player clicks on the computer, will transition to a mini-game, the player needs to"
        + " find the correct route of the aircraft base on the information given by the"
        + " departature board and the previous flight log. Generate a general welcome message for"
        + " the NPC in the room within 30 words and not include any hint";
  }

  public static String getRandomCity() {
    return "Generate a popular city that is less than 8 characters, only reply with the city name"
        + " example 'Seoul'";
  }

  public static String getIntroPuzzleMessage() {
    return "Now the player enters the puzzle game in room3 after clicking the book which given a"
        + " unarranged destnation city name and the player need to drag and change the"
        + " position of the letter until the correct city name. Write a 10 words welcome"
        + " message.";
  }

  public static String wrongPuzzleRoom3() {
    return "Now the player rearranged the city name but it is not the correct city name, generate"
        + " a 10 words message to tell the player that the city name is wrong and need to"
        + " rearrange again.";
  }
}
