import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Player extends Players {
    String username;

    protected int numberOfGuesses;
    protected int numberOfBulls;
    protected int numberOfCows;
    int codesAttempted;
    int codesDeciphered;

    Player() {
        numberOfBulls = 0;
        numberOfCows = 0;
        numberOfGuesses = 0;
    }



        public Player(String username) {
            this.username = null;
            String filePath = "src/players.txt";

            System.out.println("ive reached player constructor");

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                boolean notFound = true;

                while ((line = reader.readLine()) != null) {
                    System.out.println("looping");
                    String[] parts = line.split(" ");
                    if (parts[0].equalsIgnoreCase(username)) {
                        this.username = parts[0];
                        this.numberOfBulls = Integer.parseInt(parts[1]);
                        this.numberOfCows = Integer.parseInt(parts[2]);
                        this.codesAttempted = Integer.parseInt(parts[3]);
                        this.codesDeciphered = Integer.parseInt(parts[4]);
                        this.numberOfGuesses = Integer.parseInt(parts[5]);
                        notFound = false;
                        reader.close();
                        break;
                    }
                }

                if (notFound) {
                    System.out.println("Username not found: " + username);
                    createAccount(username);

                } else {
                    System.out.println("Player: " + this.username + " number of guesses: " + this.numberOfGuesses);
                }

            } catch (Exception e) {
                System.out.println("ERROR WITH PLAYER PARSING");
                e.printStackTrace();
            }
        }


        protected void updateBulls(int bulls) {
            numberOfBulls += bulls;
        }

        protected void updateCows(int cows) {
            numberOfCows += cows;
        }

        protected void updateStats() throws IOException {
            Path fpath = Paths.get("src/players.txt");

            System.out.println("Updating player data in file: " + fpath); // Debugging

            ArrayList<String> lines = (ArrayList<String>) Files.readAllLines(fpath, StandardCharsets.UTF_8);
            boolean foundPlayer = false; // Debugging

            for (String line : lines) {
                System.out.println("Checking line: " + line); // Debugging
                if (line.contains(username)) {

                    foundPlayer = true; // Debugging
                    System.out.println("Found player line: " + line); // Debugging

                    String[] parts = line.split(" ");
                    parts[1] = String.valueOf(numberOfBulls);
                    parts[2] = String.valueOf(numberOfCows);
                    parts[3] = String.valueOf(codesAttempted);
                    parts[4] = String.valueOf(codesDeciphered);
                    parts[5] = String.valueOf(numberOfGuesses);

                    String updatedPlayerLine = String.join(" ", parts);
                    lines.set(lines.indexOf(line), updatedPlayerLine);
                    System.out.println("Updated line to: " + updatedPlayerLine); // Debugging

                }
            }

            if (!foundPlayer) {
                System.out.println("Player not found in file."); // Debugging
            } else {
                Files.write(fpath, lines, StandardCharsets.UTF_8);
                System.out.println("File updated."); // Debugging
            }
        }
        public void incrementCodesAttempted() {
            codesAttempted++;
        }

        public void incrementCodesDeciphered() {
            codesDeciphered++;
        }

        public void incrementGuesses() {
            numberOfGuesses++;
        }

        public int getBulls() {
            return numberOfBulls;
        }

        public int getCows() {
            return numberOfCows;
        }

        public int getCodesAttempted() {
            return codesAttempted;
        }

        public int getCodesDeciphered() {
            return codesDeciphered;
        }

        public float getStats() {
            return ((float) (numberOfCows + numberOfBulls) / ((numberOfGuesses)*4)) * 100; //returns the % of cows and bulls per char of guess
        }

    }
