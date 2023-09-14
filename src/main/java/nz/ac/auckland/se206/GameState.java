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

  public static int currentBox;
}
