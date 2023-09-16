package nz.ac.auckland.se206;

public class Helper {
  public static void setBooksInRoom1() {
    GameState.booksInRoom1 =
        new String[] {
          "The Great Gatsby",
          "The Catcher in the Rye",
          "The Grapes of Wrath",
          "To Kill a Mockingbird",
          "The Color Purple",
          "Ulysses",
          "Beloved"
        };
  }

  public static void changeTreasureBox(int currentBoxNumber) {

    // Generate a random number between 1 and 5
    int boxNumber = (int) (Math.random() * 5) + 1;

    // If the random number is the same as the box number, generate a new random number
    while (boxNumber == currentBoxNumber) {
      boxNumber = (int) (Math.random() * 5) + 1;
    }
    GameState.currentBox = boxNumber;
  }
}
