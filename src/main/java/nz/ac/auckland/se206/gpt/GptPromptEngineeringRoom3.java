package nz.ac.auckland.se206.gpt;

public class GptPromptEngineeringRoom3 {

  /**
   * Generates a GPT prompt engineering string for a riddle with the given word.
   *
   * @param wordToGuess the word to be guessed in the riddle
   * @return the generated prompt engineering string
   */
  public static String getAircraftCode() {
    return "User update: The player now has correctly entered the correct departature and"
        + " destnation city and now unlocks the aircraft code. Response with congrats the"
        + " player and generate a aircraft code with 2 letters and 2 numbers, the aircraft"
        + " code must surround with ^.";
  }

  /**
   * Generates a GPT prompt engineering string for room3 welcome message.
   *
   * @return the generated prompt engineering string
   * @return
   */
  public static String room3WelcomeMessage() {
    return "User update: The user now entered room3 that is an air traffic control tower.The player"
        + " needs to interact with different objects including solving a puzzle to get the"
        + " aircraft code to decrypt letter in another rooom.You need to achknowledge the"
        + " current state of the game. Give the user intructions on the mission with maximum"
        + " 2 sentences, not including any hints. Surround the response to be displayed to"
        + " the player to the user with *.";
  }

  public static String getRandomCity() {
    return "Generate a random city that is less than 10 characters, only reply with the city name"
        + " surrounded by ^, example ^Paris^";
  }

  public static String getIntroPuzzleMessage() {
    return "Now the player enters the puzzle game after clicking the book which given a"
        + " unarranged destnation city name and the player need to drag and change the"
        + " position of the letter until the correct destnation city. Write a 10 words welcome"
        + " message and do not surround the message with *.";
  }

  public static String wrongPuzzleRoom3() {
    return "Now the player rearranged the city name but it is not the correct city name, generate"
        + " a 10 words message to tell the player that the city name is wrong and need to"
        + " rearrange again do not surrounded with *.";
  }

  public static String correctPuzzleRoom3() {
    return "Now the player rearranged the city name and it is the correct city name, say correct"
        + " and congrats the player about the achievement and do not surrounded with *";
  }
}
