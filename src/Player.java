public class Player {
    String username;
    private int numberOfBulls;
    private int numberOfCows;
    int codesAttempted;
    int codesDeciphered;

    Player() { numberOfBulls = 0; numberOfCows = 0;   }

    protected void updateBulls(int bulls){ numberOfBulls += bulls; }
    protected void updateCows(int cows){ numberOfCows += cows; }
    public void incrementCodesAttempted(){codesAttempted++;}
    public void incrementCodesDeciphered(){codesDeciphered++;}
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
}
