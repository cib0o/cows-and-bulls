public class Player extends Players {
    String username;

    protected int numberOfGuesses;
    protected int numberOfBulls;
    protected int numberOfCows;
    int codesAttempted;
    int codesDeciphered;

    Player() { numberOfBulls = 0; numberOfCows = 0;  numberOfGuesses = 0; }

    protected void updateBulls(int bulls){ numberOfBulls += bulls;}
    protected void updateCows(int cows){ numberOfCows += cows;}
    public void incrementCodesAttempted(){codesAttempted++;}
    public void incrementCodesDeciphered(){codesDeciphered++;}
    public void incrementGuesses(){numberOfGuesses++;}
    public int getBulls(){
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

    public float getStats() { return ((float) (numberOfCows + numberOfBulls)/(numberOfGuesses))*100; //returns the % of cows and bulls per guess
    }
}
