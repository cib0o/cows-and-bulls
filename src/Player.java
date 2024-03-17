import java.io.BufferedReader;
import java.io.FileReader;

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

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                boolean found = false;

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" ");
                    if (parts[0].equalsIgnoreCase(username)) {
                        this.username = parts[0];
                        this.numberOfBulls = Integer.parseInt(parts[1]);
                        this.numberOfCows = Integer.parseInt(parts[2]);
                        this.codesAttempted = Integer.parseInt(parts[3]);
                        this.codesDeciphered = Integer.parseInt(parts[4]);
                        this.numberOfGuesses = Integer.parseInt(parts[5]);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("Username not found: " + username);

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
            return ((float) (numberOfCows + numberOfBulls) / (numberOfGuesses)) * 100; //returns the % of cows and bulls per guess
        }

    }
