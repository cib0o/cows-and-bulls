public class Player {
    String username;
    int numberOfBulls;
    int numberOfCows;
    int codesAttempted;
    int codesDeciphered;

    Player() {    }

    protected void updateBulls(){}
    protected void updateCows(){}
    public void incrementCodesAttempted(){}
    public void incrementCodesDeciphered(){}
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
