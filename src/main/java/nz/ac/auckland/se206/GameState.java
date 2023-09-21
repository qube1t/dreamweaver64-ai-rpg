package nz.ac.auckland.se206;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import nz.ac.auckland.se206.controllers.MainGame;

/** Represents the state of the game. */
public class GameState {

  // AI
  public static GptEngine eleanorAi = new GptEngine();

  /** Indicates whether the riddle has been resolved. */
  public static boolean isRiddleResolved = false;

  // pirate riddle
  public static String instructionMsg = null;
  public static String pirateRiddle = null;

  /** Indicates whether the key has been found. */
  public static boolean isKeyFound = false;

  /** Indicates the difficulty level and time limit the user selected. */
  public static String[] gameMode;
  public static int hintsRemaining;

  public static boolean isWorldMapOpened = false;

  public static MainGame mainGame;

  public static boolean winTheGame = false;
  public static long startTime = 0;
  public static long endTime = 0;

  /** Indicates whether the game has started. */
  public static boolean isGameStarted = false;

  /** Indicates the wrong choice that the player has entered in room3 minigame */
  public static int wrongChoice = 0;

  /** Indicates whether the time limit has reached */
  public static boolean timeLimitReached = false;


  public static int hintsRemaining = -1;


  /** These fields are used in room3 */
  public static boolean isPreviousFlightPlanOpen = false;

  public static boolean isDepBoardOpen = false;
  public static boolean isCorrectRouteFound = false;
  public static boolean isCityFound = false;

  /** Indicates wheter the aircraft code has been found */
  public static boolean isAircraftCodeFound = false;

  /** Indicates the location of the treasure box. */
  public static int currentBox = -1;

  public static String unarrangedDestnationCity = "";
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
  public static boolean isBookFound = false;

  /** Indicates whether the box key has found to open the treasure box */
  public static boolean isBoxKeyFound = false;

  /** Indicates whether the treasure has found */
  public static boolean isTreasureFound = false;
}
