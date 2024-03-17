import javax.swing.*;
import java.awt.List;
import java.io.*;
import java.util.*;

public class Game {

    //playerGameMapping;
    String gameType;
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

    public String lastHint = "";

    public int revealCount = 0;
    public void revealHint() {

        String solutionStr = userInterface.g.getSolution();
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
        System.exit(0);

    }

    public String getLastHint() {
        return lastHint;
    }

    public Object requestCode(String gameType) {

            SecretCode secretCode;
            if ("nc".equals(gameType)) {
               SecretCode c = new SecretCode(player, "nc");
               code = c.code;
            } else if ("lc".equals(gameType)) {
                SecretCode c = new SecretCode(player, "lc");
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

        guesses.add(guessStr + cows + bulls);
        player.incrementGuesses();


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
                player.updateCows(-cow);
            }

            System.out.println("GAME.java buffer: " + String.valueOf(buffer));
            return buffer;
        }
    
    public void saveGame(){
    	
    	try {
    		 File file = new File("src/savedGame.txt");
             if (file.exists()) {
                 int option = JOptionPane.showConfirmDialog(null, "A saved game already exists. Do you want to overwrite it?", "Save Game", JOptionPane.YES_NO_OPTION);
                 if (option == JOptionPane.NO_OPTION) {
                     return;
                 }
             }
    		
    		BufferedWriter writer = new BufferedWriter(new FileWriter("src/savedGame.txt"));
    		
    		writer.write(gameType + "\n");
    		writer.write(code + "\n");
    		
    		for(int i = 0; i < guesses.size(); i++) {
    			writer.write(guesses.get(i).substring(0, 4));
    			if (i+1 < guesses.size()) {
    				writer.write("\n");
    			}
    		}
    		
    		writer.close();
    		
    	} catch (IOException e) {
    		
    		e.printStackTrace();
    		
    	}
    	
    }
    
    public void loadGame(String allGuesses){

        String filePath = "src/players.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");

                    String gameData = parts[6];
                    String code = gameData.substring(0, 4);



                    this.code = code;
                    ArrayList<String> guesses = new ArrayList<>();
                    for (int i = 0; i < allGuesses.length(); i += 6) {
                        String guess = allGuesses.substring(i, i + 6);
                        guesses.add(guess);
                    }
                    this.guesses.clear();
                    for (int i = 0; i <4 ; i++){
                        this.guesses.add(guesses.get(i));
                    }
                    break;
                }

        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void showHint(){}





}
