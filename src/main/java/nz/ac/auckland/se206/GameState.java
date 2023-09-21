package nz.ac.auckland.se206;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.controllers.MainGame;

/** Represents the state of the game. */
public class GameState {

  // AI
  public static GptEngine eleanorAi = new GptEngine();

  /** Indicates whether the riddle has been resolved. */
  public static boolean isRiddleResolved = false;

  /** Indicates if room3 has first entered */
  public static boolean isRoom3FirstEntered = false;

  public static boolean isEncryptedMessageFound = false;

  // pirate riddle
  public static String instructionMsg = null;
  public static String pirateRiddle = null;

  /** Indicates whether the key has been found. */
  public static boolean isKeyFound = false;

  /** Indicates the difficulty level and time limit the user selected. */
  public static String[] gameMode;

  public static boolean isWorldMapOpened = false;

  public static MainGame mainGame;

  public static boolean winTheGame = false;

  /** Indicates whether the game has started. */
  public static boolean isGameStarted = false;

  /** Indicates the time limit that player has chosen */
  public static int chosenTime;

  /** Indicates the wrong choice that the player has entered in room3 minigame */
  public static int wrongChoice = 0;

  /** Indicates whether the time limit has reached */
  public static boolean timeLimitReached = false;

  public static int hintsRemaining = -1;

  public static boolean isCorrectRouteFound = false;
  public static boolean isCityFound = false;

  /** Indicates wheter the aircraft code has been found */
  public static boolean isAircraftCodeFound = false;

  /** Indicates the location of the treasure box. */
  public static int currentBox = -1;

  /** Indicates the unarranged city name for puzzle game. */
  public static String unarrangedDestnationCity = "";

  /** Indicates the arranged city name for the puzzle game. */
  public static String arrangedDestnationCity = "";

  /** Indicates the correct city index */
  public static int currentCityIndex = -1;

  /** The list of current locations */
  public static Text[] currentCities;

  /** Indicates whether the puzzle in room 3 solved. */
  public static boolean isPuzzleInRoom3Solved = false;

  /** Indicates the aircraft code */
  public static String aircraftCode = "";

  /** Indicates the introduction message when first enter the puzzle game in Room3 */
  public static String puzzleIntroMessageRoom3 = "";

  /** Stores the created instance of overlay */
  public static Pane overlay;

  public static String[] booksInRoom1 = new String[7];

  /** Indicates whether the book has found to trade with pirate */
  public static boolean isBookFound = true;

  /** Indicates whether the box key has found to open the treasure box */
  public static boolean isBoxKeyFound = false;

  /** Indicates whether the treasure has found */
  public static boolean isTreasureFound = false;

  /** Indicates whether the decrypt key has found to decrypt the memory */
  public static boolean isDecryptKeyFound = false;

  /** Indicates whether the item6 has found */
  public static boolean isItem6Found = false;

  /** Indicates whether the item7 has found */
  public static boolean isItem7Found = false;

  /** Indicates whether the item8 has found */
  public static boolean isItem8Found = false;

  public static ImageView[] items;
}
