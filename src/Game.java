import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Game {

    //playerGameMapping;
    String gameType; //for choosing the gameType, defaults on NumbersCode for tests atm
    Player player;
    ArrayList<String> guesses = new ArrayList<>();
    String code;
    char[] buffer = new char[4];

    Game(Player p, String codeType) {player = p; gameType = codeType;}
    Game(Player p) { player = p;   }

    public Game() {

    }

    public void getHint() {}
    protected void loadPlayer() {}
    public static void playGame() {}
    public Object requestCode(String gameType) { //temporary codeType selection
        if (Objects.equals(gameType, "nc")) { //numbers code
            List<Integer> numbers = new ArrayList<>();
            for (int i = 0 ; i < 10 ; i++){
                numbers.add(i);
            }
            Collections.shuffle(numbers);
            code = "" + numbers.get(0)+ numbers.get(1)+ numbers.get(2)+ numbers.get(3);
            return code;
        } else if (Objects.equals(gameType, "lc")) { //letters code
            code = "rats";
            return code;
        }
        return null;
    }
    public int[] enterGuess(String guessStr, String gameType) {

        /**
         * IMPORTANT: the output of this method is [cows, bulls], a won game would be [0,4]
         */

        if(guessStr.length() != 4){
            JOptionPane.showMessageDialog(null, "Invalid guess length. Please try again", "Invalid guess", JOptionPane.ERROR_MESSAGE); //pop up window of error
            throw new IndexOutOfBoundsException();
        } else if(gameType == "lc" && guessStr.matches(".*\\d.*")){ //regex for "contains a number"
            JOptionPane.showMessageDialog(null, "Entered a number guess for a Letter Code", "Invalid guess", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException();
        } else if(gameType == "nc"){
            try { Integer.parseInt(guessStr);
            } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Entered a letter guess for a Number Code", "Invalid guess", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException();
        }}

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

        guesses.add(guessStr + cows + bulls);


        return new int[]{cows, bulls};
    }

    public void checkGuess(char[] c){
        int bull = 0;
        int cow = 0;
        char lastChar = '\u0000';
        int lastCharIndex = -1;
        for (int i = c.length - 1; i >= 0; i--) {
            if (c[i] != '\u0000') {
                lastChar = c[i];
                lastCharIndex = i;
                break;
            }
        }
        if(gameType == "lc" && (("" + lastChar).matches(".*\\d.*"))){ //regex for "contains a number"
            JOptionPane.showMessageDialog(null, "Entered a number guess for a Letter Code", "Invalid guess", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException();
        } else if(gameType == "nc"){
            try { Integer.parseInt("" + lastChar);
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "Entered a letter guess for a Number Code", "Invalid guess", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException();
            }
        }
        if (lastCharIndex == -1) {
            System.out.println("No guess has been made yet.");
            return;
        }
        if (lastChar == code.charAt(lastCharIndex)) {
            bull = 1;
        } else {
            for (int i = 0; i < code.length(); i++) {
                if (i != lastCharIndex && code.charAt(i) == lastChar) {
                    cow = 1;
                    break;
                }
            }
        }

        player.updateBulls(bull);
        player.updateCows(cow);

        System.out.println("The guess was: " + String.valueOf(c) + "player stats: " + player.getCows() + player.getBulls() + "cowBulls " + cow + bull);

    }

        public char[] undoGuess() {
            int bull = 0;
            int cow = 0;

            int lastNonNullIndex = -1;
            for (int i = 0; i < buffer.length; i++) {
                if (buffer[i] != '\0') {
                    lastNonNullIndex = i;
                }
            }
            if (lastNonNullIndex != -1) {
                if (buffer[lastNonNullIndex] == code.charAt(lastNonNullIndex)) {
                    bull++;
                } else if (code.contains(String.valueOf(buffer[lastNonNullIndex]))) {
                    cow++;
                }
                buffer[lastNonNullIndex] = '\0';
                player.updateBulls(-bull);
                player.updateCows(-cow);
            }

            System.out.println("GAME.java buffer: " + String.valueOf(buffer));
            return buffer;
        }
    public void saveGame(){}
    public void loadGame(){}
    public void showSolution(){
    }


}
