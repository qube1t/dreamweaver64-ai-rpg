package nz.ac.auckland.se206;

/** Represents the state of the game. */
public class GameState {

  /** Indicates whether the riddle has been resolved. */
  public static boolean isRiddleResolved = false;

  /** Indicates whether the key has been found. */
  public static boolean isKeyFound = false;


  public static boolean isPreviousFlightPlanOpen = false;
  public static boolean isDepBoardOpen = false;
  public static boolean isCorrectRouteFound = false;

  /** Indicates the location of the treasure box. */
  public static int currentBox;

  public static String[] booksInRoom1 = new String[7];

  /** Indicates whether the book has found to trade with pirate */
  public static boolean isBookFound = true;

  /** Indicates whether the box key has found to open the treasure box */
  public static boolean isBoxKeyFound = false;

  /** Indicates whether the access key has found to access the computer */
  public static boolean isAccessKeyFound = false;

  /** Indicates whether the treasure has found */
  public static boolean isTreasureFound = false;
}
