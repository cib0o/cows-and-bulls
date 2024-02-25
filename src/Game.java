import javax.swing.*;
import java.util.ArrayList;

public class Game {

    //playerGameMapping;
    String gameType = "nc"; //for choosing the gameType, defaults on NumbersCode for tests atm
    Player player;
    ArrayList<String> guesses = new ArrayList<>();
    String code;

    Game(Player p, String codeType) {player = p; gameType = codeType;}
    Game(Player p) { player = p;   }

    public Game() {

    }

    public void getHint() {}
    protected void loadPlayer() {}
    public static void playGame() {}
    public Object requestCode() { //temporary codeType selection
        if (gameType == "nc") { //numbers code
            code = "1234";
            return code;
        } else if (gameType == "lc") { //letters code
            code = "abcd";
            return code;
        }
        return null;
    }
    public int[] enterGuess(String guessStr) {

        /**
         * IMPORTANT: the output of this method is [cows, bulls], a won game would be [0,4]
         */

        if(guessStr.length() != 4){
            JOptionPane.showMessageDialog(null, "Invalid guess length. Please try again", "Invalid guess", JOptionPane.ERROR_MESSAGE); //pop up window of error
            throw new IndexOutOfBoundsException();
        } else if(gameType == "lc" && guessStr.matches(".*\\d.*")){ //regex for "contains a number"
            JOptionPane.showMessageDialog(null, "Entered a number guess for a Letter Code", "Invalid guess", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException();
        } else if(gameType == "nc" && !(guessStr.matches(".*\\d.*"))){
            JOptionPane.showMessageDialog(null, "Entered a letter guess for a Number Code", "Invalid guess", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException();
        }

        int bulls = 0;
        int cows = 0;
        //converting both to a string to compare easier
        //adding to the array of previous guesses

        String tempCode = code;
        String tempGuess = guessStr;

        for (int i = 0; i < guessStr.length(); i++) {
            //System.out.println(tempGuess + " | " + tempCode + "  | c,b {" + cows + "," + bulls + "}");
            if (i < code.length() && guessStr.charAt(i) == code.charAt(i)) {
                bulls++;

                // the concatenation of x and y is just so they won't be checked again when we're counting the cows

                tempGuess = tempGuess.substring(0, i) + 'X' + tempGuess.substring(i + 1);
                tempCode = tempCode.substring(0, i) + 'Y' + tempCode.substring(i + 1);


            }
        }
        for (int i = 0; i < tempGuess.length(); i++) {
            //System.out.println(tempGuess + " | " + tempCode + "  | c,b {" + cows + "," + bulls + "}");
            if (tempGuess.charAt(i) != 'X') {
                int index = tempCode.indexOf(tempGuess.charAt(i));
                if (index != -1) {
                    cows++;

                    tempCode = tempCode.substring(0, index) + 'Y' + tempCode.substring(index + 1);
                }
            }
        }

        /**
        * player.updateBulls(bulls); //updates the players, bull and cow count
        * player.updateCows(cows);
         *   *
         *          * this is being put into a comment while player isn't defined
         *         */
        guesses.add(guessStr + cows + bulls);


        return new int[]{cows, bulls};
    }
        // }
    public void undoGuess(int i) {}
    public void saveGame(){}
    public void loadGame(){}
    public void showSolution(){
    }


}
