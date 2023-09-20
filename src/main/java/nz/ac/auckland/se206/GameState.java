package nz.ac.auckland.se206;

import javafx.scene.image.ImageView;
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

  public static String[] gameMode;

  public static MainGame mainGame;

  public static boolean winTheGame = false;

  /** Indicates whether the game has started. */
  public static boolean isGameStarted = false;

  /** Indicates whether the time limit has reached */
  public static boolean timeLimitReached = false;

  /** These fields are used in room1 */
  public static String[] booksInRoom1 = new String[7];


  /** These fields are used in room3 */
  public static boolean isPreviousFlightPlanOpen = false;
  public static boolean isDepBoardOpen = false;
  public static boolean isCorrectRouteFound = false;
  public static boolean isCityFound = false;

  /** Indicates wheter the aircraft code has been found */
  public static boolean isAircraftCodeFound = false;


  /** These fields are used in room2 */
  /** Indicates the location of the treasure box. */
  public static int currentBox = -1;

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
