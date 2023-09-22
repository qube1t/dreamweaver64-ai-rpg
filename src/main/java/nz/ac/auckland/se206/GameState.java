package nz.ac.auckland.se206;

import java.util.List;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.controllers.MainGame;

/** Represents the state of the game. */
public class GameState {

  /** Indicates whether the riddle has been resolved. */
  public static boolean isRiddleResolved = false;

  // start menu
  /** Indicates the difficulty level and time limit the user selected. */
  public static String[] gameMode;

  /** Indicates the number of hints remaining. */
  public static int hintsRemaining;

  // main game
  /** Indicates the main game. */
  public static MainGame mainGame;

  /** Indicates whether the time limit has reached. */
  public static boolean timeLimitReached = false;

  /** Indicates the current room number. */
  public static int prevRoom = 1;

  /** The GPT-3 engine. */
  public static GptEngine eleanorAi = new GptEngine();

  // instruction loading stage
  /** Indicates the instruction message that used in the loading stage. */
  public static String instructionMsg = null;

  /** Indicates the fact about DW64. */
  public static List<String> factsAboutDW64;

  // room1
  /** Indicates the riddle that used in the room2. */
  public static String pirateRiddle = null;

  /** Indicates the book array that stored in the room1. */
  public static String[] booksInRoom1 = new String[7];

  /** Indicates whether the right book has found to trade with pirate. */
  public static boolean isBookFound = false;

  /** Indicates the correct book. */
  public static String trueBook = null;

  /** Indicates the book that the player has taken. */
  public static String takenBook;

  // room2
  /** Indicates whether the player has entered the room2 first time. */
  public static boolean isRoom2FirstEntered = false;

  /** Indicates whether the box key has found to open the treasure box. */
  public static boolean isBoxKeyFound = false;

  /** Indicates the unencrypted message that the player will get when they exit the game. */
  public static String finalMsg;

  /** Indicates the encrypted message that the player will get when they get the treasure. */
  public static String encryptedFinalMsg;

  /** Indicates whether the player has found the encrypted message. */
  public static boolean isEncryptedMessageFound = false;

  // room3
  /** Indicates whether the player has entered the room3 first time. */
  public static boolean isRoom3FirstEntered = false;

  /** Indicates whether the world map has opened. */
  public static boolean isWorldMapOpened = false;

  /** Indicates wheter the aircraft code has been found. */
  public static boolean isAircraftCodeFound = false;

  /** Indicates the location of the treasure box. */
  public static int currentBox = -1;

  /** Indicates the unarranged city name for puzzle game. */
  public static String unarrangedDestnationCity = "";

  /** Indicates the arranged city name for the puzzle game. */
  public static String arrangedDestnationCity = "";

  /** Indicates the correct city index. */
  public static int currentCityIndex = -1;

  /** The list of current locations. */
  public static Text[] currentCities;

  /** Indicates whether the puzzle in room 3 solved. */
  public static boolean isPuzzleInRoom3Solved = false;

  /** Indicates the aircraft code. */
  public static String aircraftCode = "";

  /** Indicates the introduction message when first enter the puzzle game in Room3. */
  public static String puzzleIntroMessageRoom3 = "";

  /** Indicates whether the player has decrypted the message. */
  public static boolean hasDecrypted = false;

  // end menu
  /** Indicates whether the player exit the game. */
  public static boolean winTheGame = false;
}
