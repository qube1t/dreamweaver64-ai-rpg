package nz.ac.auckland.se206;

/** Represents the state of the game. */
public class GameState {

  /** Indicates whether the riddle has been resolved. */
  public static boolean isRiddleResolved = false;

  /** Indicates whether the key has been found. */
  public static boolean isKeyFound = false;

  /** These fileds are used in room3 */
  public static boolean isPreviousFlightPlanOpen = false;

  public static boolean isDepBoardOpen = false;
  public static boolean isCorrectRouteFound = false;
  public static boolean isCityFound = false;

  /** Indicates wheter the aircraft code has been found */
  public static boolean isAircraftCodeFound = false;

  public static int currentBox;

  public static String[] booksInRoom1 = new String[7];
}
