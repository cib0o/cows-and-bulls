import java.util.ArrayList;
import java.util.Objects;

public class Game {

    //playerGameMapping;
    //currentPlayer Player;
    ArrayList<String> guesses = new ArrayList<>();
    String code;

    Game(Player p, String codeType) {}
    Game(Player p) {    }

    public Game() {

    }

    public void getHint() {}
    protected void loadPlayer() {}
    public static void playGame() {}
    public Object requestCode() {
        code = String.valueOf(1234);
        return 1234;
    }
    public int[] enterGuess(int guess) {

        /**
         * IMPORTANT: the output of this method is [cows, bulls], a won game would be [0,4]
         */

        int bulls = 0;
        int cows = 0;
        //converting both to a string to compare easier
        String guessStr = Integer.toString(guess);
        //adding to the array of previous guesses
        guesses.add(guessStr);
        String tempCode = code;
        String tempGuess = guessStr;
        for (int i = 0; i < guessStr.length(); i++) {
            if (i < code.length() && guessStr.charAt(i) == code.charAt(i)) {
                bulls++;

                // the concatenation of x and y is just so they won't be checked again when we're counting the cows

                tempGuess = tempGuess.substring(0, i) + 'X' + tempGuess.substring(i + 1);
                tempCode = tempCode.substring(0, i) + 'Y' + tempCode.substring(i + 1);
            }
        }
        for (int i = 0; i < tempGuess.length(); i++) {
            if (tempGuess.charAt(i) != 'X') {
                int index = tempCode.indexOf(tempGuess.charAt(i));
                if (index != -1) {
                    cows++;
                    tempCode = tempCode.substring(0, index) + 'Y' + tempCode.substring(index + 1);
                }
            }
        }

        return new int[]{cows, bulls};
    }
        // }
    public void undoGuess(int i) {}
    public void saveGame(){}
    public void loadGame(){}
    public void showSolution(){
    }
}
