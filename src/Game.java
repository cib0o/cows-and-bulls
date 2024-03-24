import javax.swing.*;
import java.awt.List;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Game {

    //playerGameMapping;
    String gameType;
    Player player;
    ArrayList<String> guesses = new ArrayList<>();
    String code;
    char[] buffer = new char[4];
    public int length;

    Game(Player p, String codeType, int length) {
        this.player = p;
        this.gameType = codeType;
        this.length = length;
        this.buffer = new char[this.length];
        p.setCodeLength(length);
    }
    Game(Player p) { player = p;   }

    public Game() {

    }

    public void getHint() {}
    protected void loadPlayer() {}
    public static void playGame() {}

    public String lastHint = "";

    public int revealCount = 0;
    public void revealHint() {

        String solutionStr = getSolution();
        if (solutionStr != null && !solutionStr.isEmpty() && revealCount < solutionStr.length()) {
            char nextChar = solutionStr.charAt(revealCount);
            lastHint = String.valueOf(nextChar);
            JOptionPane.showMessageDialog(null, "Hint: " + nextChar, "Hint", JOptionPane.INFORMATION_MESSAGE);
            revealCount++;
        } else {
            // when all hints have been revealed
            JOptionPane.showMessageDialog(null, "No more hints left!!", "Hint", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void showSolution() {

        JOptionPane.showMessageDialog(null, "The solution was: " + code + " ...you lost!", "Solution", JOptionPane.INFORMATION_MESSAGE);

    }

    public String getLastHint() {
        return lastHint;
    }

    public Object requestCode(String gameType) {

            SecretCode secretCode;
            if ("nc".equals(gameType)) {
               SecretCode c = new SecretCode(player, "nc",length);
               code = c.code;
            } else if ("lc".equals(gameType)) {
                SecretCode c = new SecretCode(player, "lc",length);
                code = c.code;
            }
                return null;


    }
    public String getSolution(){
        return this.code;
    }

    public int[] enterGuess(String guessStr, String gameType) {

        /**
         * IMPORTANT: the output of this method is [cows, bulls], a won game would be [0,4]
         */

        if(guessStr.length() !=length){
            JOptionPane.showMessageDialog(null, "Invalid guess length. Please try again", ("Invalid guess:" + guessStr), JOptionPane.ERROR_MESSAGE); //pop up window of error
            throw new IndexOutOfBoundsException();
        } else if(gameType == "lc" && guessStr.matches(".*\\d.*")){ //regex for "contains a number"
            JOptionPane.showMessageDialog(null, "Entered a number guess for a Letter Code", ("Invalid guess:" + guessStr), JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException();
        } else if(gameType == "nc"){
            try { Integer.parseInt(guessStr);
            } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Entered a letter guess for a Number Code", ("Invalid guess:" + guessStr), JOptionPane.ERROR_MESSAGE);
            System.out.println("Game type was " + gameType);
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
        System.out.println(guessStr + "c: " + cows + "|b: " + bulls);

        guesses.add(guessStr + cows + bulls);
        player.incrementGuesses();

        player.updateCows(cows);
        player.updateBulls(bulls);

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
                System.out.println("Game type was " + gameType);
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
                player.updateCows(cow);
            }

            System.out.println("GAME.java buffer: " + String.valueOf(buffer));
            return buffer;
        }

    public void saveGame(boolean won) throws IOException {
        if (won){
            return;
        }
        String saveData = length + code;
        for(int i = 0; i < 5; i++){
            saveData += guesses.get(guesses.size() - 5 + i);
        }
        System.out.println(saveData);
        System.out.println("THE PLAYER PLAYING IS " + player.username);
        updatePlayerData("src/players.txt", player.username, saveData);
    }

    public static void updatePlayerData(String filePath, String playerName, String newGameData) throws IOException {
        Path path = Paths.get(filePath);
        System.out.println("Updating player data in file: " + filePath); // Debugging

        ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(path, StandardCharsets.UTF_8);
        boolean foundPlayer = false; // Debugging

        for (String line : lines) {
            System.out.println("Checking line: " + line); // Debugging
            if (line.contains(playerName)) {
                foundPlayer = true; // Debugging
                System.out.println("Found player line: " + line); // Debugging
                String[] parts = line.split(" ", 7);
                if (parts.length > 6) {
                    parts[6] = newGameData;
                    String updatedLine = String.join(" ", parts);
                    System.out.println("Updated line to: " + updatedLine); // Debugging
                    lines.set(lines.indexOf(line), updatedLine);
                }
            }
        }

        if (!foundPlayer) {
            System.out.println("Player not found in file."); // Debugging
        } else {
            Files.write(path, lines, StandardCharsets.UTF_8);
            System.out.println("File updated."); // Debugging
        }
    }



    public void loadGame(String allGuesses) {
        if (allGuesses == null || allGuesses.isEmpty()) {
            System.out.println("Invalid input string.");
            return;
        }


        int gameLengthIndicator = Character.getNumericValue(allGuesses.charAt(0));
        int codeLength, guessLength;
        if (gameLengthIndicator == 8) {
            codeLength = 8;
            guessLength = 10;
        } else {
            codeLength = 4;
            guessLength = 6;
        }

        if (allGuesses.length() < 1 + codeLength) {
            System.out.println("Input string too short for code.");
            return;
        }


        this.code = allGuesses.substring(1, 1 + codeLength);


        allGuesses = allGuesses.substring(1 + codeLength);

        this.guesses.clear();


        while (!allGuesses.isEmpty() && allGuesses.length() >= guessLength) {
            String guess = allGuesses.substring(0, guessLength);
            guesses.add(guess);
            allGuesses = allGuesses.substring(guessLength);
            System.out.println("Looping in the load");
            System.out.println(allGuesses);
        }
        System.out.println("Loaded code: " + this.code);
        System.out.println("Loaded guesses: " + String.join(", ", this.guesses));
    }



    public void showHint(){}





}
