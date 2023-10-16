package nz.ac.auckland.se206.gpt;

/**
 * This class contains static methods that generate GPT prompt engineering
 * strings for various
 * messages in Room 3 of the game. These messages include the welcome message,
 * puzzle game messages,
 * and messages related to obtaining the aircraft code and decrypting the letter
 * from mom.
 */
public class GptPromptEngineeringRoom3 {

  /**
   * Returns the aircraft code that will be used to decrypt the letter from mom.
   * Produces a 10 word congratulation to player, without any hint.
   *
   * @return the aircraft code as a String
   */
  public static String getAircraftCode() {
    return "User update: The player has now succesfully obtained the aircraft code"
        + "will be used to decrypt the letter from mom. Produce a 10 word congratulation "
        + "to player do not include any hint.";
  }

  /**
   * Generates a GPT prompt engineering string for room3 welcome message and
   * introduce the flow of
   * the game.
   *
   * @return the generated prompt engineering string
   */
  public static String room3WelcomeMessage() {

    // Ask GPT for the introduction of the game.
    return "User update: Give the user a short welcome message, do not include any information of"
        + " next step and surround the message to be displayed to user with * based on the"
        + " mission below.The user now entered an air traffic"
        + " control tower which was his work in real life. The user needs to interact with"
        + " different objects including"
        + " solving a puzzle to get the aircraft code. The aircraft code combined with"
        + " encrypted message in another room is required to decrypt the letter. You need to achknowledge "
        + "the current state of the game"
        + " and I will give you user action updates.";
  }

  /**
   * Returns a message to the user indicating that they have successfully
   * decrypted the letter from mom and need to place it into inventory.
   * The message is surrounded by asterisks and does not include any hints.
   *
   * @return a message to the user indicating that they have successfully
   *         decrypted the letter from mom and need to place it into inventory
   */
  public static String decryptedLetter() {
    return "User update: The user now has successfully decrypted the letter from"
        + "mom and now the letter is"
        + " decrypted. The user needs to place the letter into inventory. "
        + "Produce a short message to user and"
        + "do not include any hint."
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

  /**
   * Returns a welcome message for the puzzle game, which gives an unarranged
   * destination city name and
   * requires the player to drag and change the position of the letters until the
   * correct destination city
   * is formed. The message should be 10 words or less and should not be
   * surrounded by *.
   *
   * @return a welcome message for the puzzle game
   */
  public static String getIntroPuzzleMessage() {
    return "Now the player enters the puzzle game which gives a"
        + " unarranged destnation city name and the player need to drag and change the"
        + " position of the letter until the correct destnation city. Write a 10 words welcome"
        + " message and do not surround the message with *.";
  }

  /**
   * Returns a short message to tell the user that the city name is wrong and
   * needs to be rearranged again.
   * The message must be within 15 words and should not be surrounded with *.
   *
   * @return a short message to prompt the user to rearrange the city name again
   */
  public static String wrongPuzzleRoom3() {
    return "User update: Now the user rearranged the city name but it is not the correct city name,"
        + " generate a short message to tell the user that the city name is wrong and"
        + " need to rearrange again. The message must within 15 words. do not surrounded with *.";
  }

  /**
   * Returns a congratulatory message to the user for correctly rearranging the
   * destination city name in room 3.
   * The message does not contain any asterisks.
   *
   * @return a congratulatory message to the user
   */
  public static String correctPuzzleRoom3() {
    return "User update: Now the user has correctly rearranged the destnation city name say"
        + " correct and congrats the user about the achievement and do not surrounded with"
        + " *";
  }
}
