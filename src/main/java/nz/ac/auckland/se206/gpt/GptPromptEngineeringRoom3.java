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
        + " destnation city and now unlocks the aircraft code. Produce an aircraft code that is"
        + " 2 letters and 2 numbers, the aircraft code must surround with ^.";
  }

  /**
   * Generates a GPT prompt engineering string for room3 welcome message and
   * introduce the flow of
   * the game.
   *
   * @return the generated prompt engineering string
   * @return
   */
  public static String room3WelcomeMessage() {

    // Ask GPT for the introduction of the game.
    return "User update: Give the user a short welcome message, do not include any information of"
        + " next step and surround the message to be displayed to user with * based on the"
        + " mission below.The user now entered an air traffic"
        + " control tower which was his work in real life. The user needs to interact with"
        + " different objects including"
        + " solving a puzzle to get the aircraft code. The aircraft code combined with"
        + " encrypted message in another room is required to decrypt the letter in this room"
        + " to successfully escape.You need to achknowledge the current state of the game"
        + " and I will give you user action updates.";
  }

  public static String decryptedLetter() {
    return "User update: The user now has successfully decrypted the letter from mom and now the letter is"
        + " decrypted. The user now can escape the room. Produce a short message and do not include any hint."
        + " You must surround the message to the user with *.";
  }

  /**
   * Generates a GPT prompt engineering string for random city for the puzzle
   * game.
   *
   * @return the generated prompt engineering string
   */
  public static String getEightRandomCity() {
    return "Produce a list of 8 random, real city names "
        + "with one word and must be different from the example provided"
        + " and must be less than 11 characters."
        + "Only reply with the"
        + " city name surrounded between ^, example ^Houston^ ^Dubai^ ^Tokyo^"
        + " ^Seattle^ ^Shanghai^ ^Stockholm^ ^Sydney^ ^Paris^";
  }

  public static String getIntroPuzzleMessage() {
    return "Now the player enters the puzzle game after clicking the book which given a"
        + " unarranged destnation city name and the player need to drag and change the"
        + " position of the letter until the correct destnation city. Write a 10 words welcome"
        + " message and do not surround the message with *.";
  }

  public static String wrongPuzzleRoom3() {
    return "User update: Now the user rearranged the city name but it is not the correct city name,"
        + " generate a 10 words message to tell the user that the city name is wrong and"
        + " need to rearrange again do not surrounded with *.";
  }

  public static String correctPuzzleRoom3() {
    return "User update: Now the user rearranged the city name and it is the correct city name, say"
        + " correct and congrats the user about the achievement and do not surrounded with"
        + " *";
  }
}
