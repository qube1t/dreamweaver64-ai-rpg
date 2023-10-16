package nz.ac.auckland.se206;

import java.util.HashSet;
import java.util.List;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.controllers.MainGameController;

/** Represents the state of the game. */
public class GameState {

  /** Indicates whether the riddle has been resolved. */
  public static boolean isRiddleResolved = false;

  /** Indicates 10 seconds left. */
  public static boolean tenSecondsLeft = false;

  /** Indicates whether the event filter is on. */
  public static boolean isEventFilter = false;

  // start menu
  /** Indicates the difficulty level and time limit the user selected. */
  public static String[] gameMode;

  public static boolean isMuted = false;

  /** Indicates the number of hints remaining. */
  public static int hintsRemaining;

  /** Indicates the character index. */
  public static int characterIndex = 1;

  // main game
  /** Indicates the main game. */
  public static MainGameController mainGame;

  /** Indicates whether the time limit has reached. */
  public static boolean timeLimitReached = false;

  /** Indicates the current room number. */
  public static int prevRoom = 1;

  /** The GPT-3 engine. */
  public static GptEngine eleanorAi = new GptEngine();
  public static GptEngine eleanorAi2 = new GptEngine();

  public static AudioClip backgroundMusic = new AudioClip(
      (new Media(App.class.getResource(
          "/sounds/Bogart VGM - 8Bit Action- Menu_Select.mp3").toString()))
          .getSource());

  public static AudioClip doorSound = new AudioClip(
      (new Media(App.class.getResource("/sounds/doorSound.mp3").toString()))
          .getSource());

  public static HashSet<AudioClip> soundFx = new HashSet<AudioClip>();

  // instruction loading stage
  /** Indicates the instruction message that used in the loading stage. */
  public static String instructionMsg = null;

  /** Indicates the fact about DW64. */
  public static List<String> factsAboutDW64;

  // room1

  public static boolean booksLoaded;

  /** Indicates the book array that stored in the room1. */
  public static String[] booksInRoom1 = new String[7];

  /** Indicates whether the right book has found to trade with pirate. */
  public static boolean isBookFound = false;

  /** Indicates the correct book. */
  public static String trueBook = null;

  /** Indicates the book that the player has taken. */
  public static String takenBook;

  /** Indicates the room1 gpt is done. */
  public static boolean isRoom1GptDone = false;

  public static boolean isRoom2FirstEntered = false;

  // room2
  /** Indicates the riddle that used in the room2. */
  public static String pirateRiddle = null;

  /** Indicates whether the box key has found to open the treasure box. */
  public static boolean isBoxKeyFound = false;

  /** Indicates the decrypted message that will be placed in end menu. */
  public static String finalMsg;

  /** Indicates the encrypted message that will be placed in treasure box. */
  public static String encryptedFinalMsg;

  public static boolean hasPuzzleSolved = false;

  /** Indicates whether the player has found the encrypted message. */
  public static boolean isEncryptedMessageFound = false;

  /** Indicates the pirate's right response. */
  public static String pirateRightResponse = null;

  /** Indicates the pirate's wrong response. */
  public static String pirateWrongResponse = null;

  /** Indicates the pirate's responses are printed. */
  public static boolean isPirateResponsePrinted = false;

  /** Indicates the room2 gpt is done. */
  public static boolean isRoom2GptDone = false;

  /** Indicates whether the player has clicked the wrong box first. */
  public static boolean isWrongBoxFirstClicked = false;

  // room3
  /** Indicates whether the player has entered the room3 first time. */
  public static boolean isRoom3FirstEntered = false;

  /** Indicates wheter the aircraft code has been found. */
  public static boolean isAircraftCodeFound = false;

  /** Indicates the location of the treasure box. */
  public static int currentBox = -1;

  /** Stored the randomly generated destnation cities. */
  public static String[] destnationCities = new String[8];

  /** Indicates the destnation city index in the array. */
  public static int destnationCityIndex = -1;

  /** Indicates the arranged city name for the puzzle game. */
  public static String arrangedDestnation = "";

  /** Indicates the unarranged city name for puzzle game. */
  public static String unarrangedDestnation = "";

  /** Indicates the correct city index. */
  public static int currentCityIndex = -1;

  /** The list of current locations. */
  public static Text[] currentCities;

  public static boolean isMachineOpen = false;

  public static boolean isPuzzleLoaded = false;

  public static String currentDraggedItemId = "";

  public static boolean computerInIt = false;

  /** Indicates the room3 gpt is done. */
  public static boolean isRoom3GptDone = false;

  /**
   * Indicates the introduction message when first enter the puzzle game in Room3.
   */
  public static String puzzleIntroMessageRoom3 = "";

  /** Indicates whether the player has decrypted the message. */
  public static boolean hasDecrypted = false;

  // end menu
  /** Indicates whether the player exit the game. */
  public static boolean winTheGame = false;

  /**
   * Resets the game state by resetting all game variables and variables for each
   * room.
   */
  public static void reset() {
    resetGameVariables();

    resetRoom1Variables();
    resetRoom2Variables();
    resetRoom3Variables();
  }

  /**
   * Resets all game variables to their default values.
   */
  private static void resetGameVariables() {
    // Reset the event filter for character selection
    isEventFilter = false;
    isRiddleResolved = false;
    tenSecondsLeft = false;
    gameMode = null;
    // Reset mute setting
    isMuted = false;
    hintsRemaining = 0;
    mainGame = null;
    timeLimitReached = false;
    prevRoom = 1;
    // Reset win the game to false.
    winTheGame = false;
    characterIndex = 1;
  }

  /**
   * Resets the variables related to Room 1.
   * This includes resetting the book array, book found status, final message,
   * room1 gpt status, and more.
   */
  private static void resetRoom1Variables() {
    // Reset the book array
    booksInRoom1 = new String[7];
    // Reset the book found status
    isBookFound = false;
    trueBook = null;
    takenBook = null;
    // Reset the final message to null.
    finalMsg = null;
    booksLoaded = false;
    // Reset the room1 gpt status
    isRoom1GptDone = false;

  }

  /**
   * Resets all the variables related to room 2 to their default values.
   */
  private static void resetRoom2Variables() {
    // Reset pirate riddle to null.
    pirateRiddle = null;
    pirateRightResponse = null;
    pirateWrongResponse = null;
    isBoxKeyFound = false;
    // Reset the final message to null.
    encryptedFinalMsg = null;
    isEncryptedMessageFound = false;
    isRoom2FirstEntered = false;
    // Reset the pirate response printed status
    isPirateResponsePrinted = false;
    isRoom2GptDone = false;
    isWrongBoxFirstClicked = false;
  }

  /**
   * Resets all the variables related to Room 3 to their default values.
   * This method is called when the player exits Room 3.
   */
  private static void resetRoom3Variables() {
    // Set is puzzle loaded to false
    isPuzzleLoaded = false;
    isRoom3FirstEntered = false;
    // Reset the aircraft code found status
    isAircraftCodeFound = false;
    currentBox = -1;
    // Reset destnation cities array of size 8.
    destnationCities = new String[8];
    destnationCityIndex = -1;
    arrangedDestnation = "";
    unarrangedDestnation = "";
    hasPuzzleSolved = false;
    currentCityIndex = -1;
    currentCities = null;
    // Reset is machine open to false.
    isMachineOpen = false;
    currentDraggedItemId = "";
    puzzleIntroMessageRoom3 = "";
    // Set decryption status to false.
    hasDecrypted = false;
    computerInIt = false;
    isRoom3GptDone = false;
  }
}
