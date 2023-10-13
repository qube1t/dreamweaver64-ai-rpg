package nz.ac.auckland.se206;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.controllers.MainGameController;

/** Represents the state of the game. */
public class GameState {

  /** Indicates whether the riddle has been resolved. */
  public static boolean isRiddleResolved = false;

  /** Indicates 10 seconds left */
  public static boolean tenSecondsLeft = false;

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

  public static AudioClip backgroundMusic = new AudioClip(
      (new Media(App.class.getResource("/sounds/Bogart VGM - 8Bit Action- Menu_Select.mp3").toString()))
          .getSource());

  public static List<AudioClip> soundFx = new ArrayList<AudioClip>();

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

  /** Indicates the room1 gpt is done */
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

  /** Indicates whether the player has found the encrypted message. */
  public static boolean isEncryptedMessageFound = false;

  /** Indicates the pirate's right response. */
  public static String pirateRightResponse = null;

  /** Indicates the pirate's wrong response. */
  public static String pirateWrongResponse = null;

  /** Indicates the pirate's responses are printed */
  public static boolean isPirateResponsePrinted = false;

  /** Indicates the room2 gpt is done */
  public static boolean isRoom2GptDone = false;

  // room3
  /** Indicates whether the player has entered the room3 first time. */
  public static boolean isRoom3FirstEntered = false;

  /** Indicates wheter the aircraft code has been found. */
  public static boolean isAircraftCodeFound = false;

  /** Indicates the location of the treasure box. */
  public static int currentBox = -1;

  /** Stored the randomly generated destnation cities */
  public static String[] destnationCities = new String[8];

  /** Indicates the destnation city index in the array. */
  public static int destnationCityIndex = -1;

  /** Indicates the arranged city name for the puzzle game. */
  public static String arrangedDestnationCity = "";

  /** Indicates the unarranged city name for puzzle game. */
  public static String unarrangedDestnationCity = "";

  /** Indicates the correct city index. */
  public static int currentCityIndex = -1;

  /** The list of current locations. */
  public static Text[] currentCities;

  public static boolean isMachineOpen = false;

  public static boolean isPuzzleLoaded = false;

  public static String currentDraggedItemId = "";

  public static boolean computerInIt = false;

  /** Indicates the room3 gpt is done */
  public static boolean isRoom3GptDone = false;

  /**
   *
   * Indicates the introduction message when first enter the puzzle game in Room3.
   */
  public static String puzzleIntroMessageRoom3 = "";

  /** Indicates whether the player has decrypted the message. */
  public static boolean hasDecrypted = false;

  // end menu
  /** Indicates whether the player exit the game. */
  public static boolean winTheGame = false;

  // Method to reset all variables to default values
  public static void reset() {
    resetGameVariables();
    resetRoom1Variables();
    resetRoom2Variables();
    resetRoom3Variables();
  }

  // Reset game-related variables
  private static void resetGameVariables() {
    isRiddleResolved = false;
    tenSecondsLeft = false;
    gameMode = null;
    isMuted = false;
    hintsRemaining = 0;
    mainGame = null;
    timeLimitReached = false;
    prevRoom = 1;
    winTheGame = false;
  }

  // Reset variables related to Room 1
  private static void resetRoom1Variables() {
    Arrays.fill(booksInRoom1, null);
    isBookFound = false;
    trueBook = null;
    takenBook = null;
    finalMsg = null;
    isRoom1GptDone = false;
  }

  // Reset variables related to Room 2
  private static void resetRoom2Variables() {
    pirateRiddle = null;
    pirateRightResponse = null;
    pirateWrongResponse = null;
    isBoxKeyFound = false;
    encryptedFinalMsg = null;
    isEncryptedMessageFound = false;
    isRoom2FirstEntered = false;
    isPirateResponsePrinted = false;
    isRoom2GptDone = false;
  }

  // Reset variables related to Room 3
  private static void resetRoom3Variables() {
    isPuzzleLoaded = false;
    isRoom3FirstEntered = false;
    isAircraftCodeFound = false;
    currentBox = -1;
    Arrays.fill(destnationCities, null);
    destnationCityIndex = -1;
    arrangedDestnationCity = "";
    unarrangedDestnationCity = "";
    currentCityIndex = -1;
    currentCities = null;
    isMachineOpen = false;
    currentDraggedItemId = "";
    puzzleIntroMessageRoom3 = "";
    hasDecrypted = false;
    computerInIt = false;
    isRoom3GptDone = false;
  }
}
