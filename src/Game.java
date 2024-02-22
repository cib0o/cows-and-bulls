public class Game {

    //playerGameMapping;
    //currentPlayer Player;

    Game(Player p, String codeType) {}
    Game(Player p) {    }

    public Game() {

    }

    public void getHint() {}
    protected void loadPlayer() {}
    public static void playGame() {}
    public Object requestCode() {return 1234;}
    public boolean enterGuess() {
        //If guess == solution: return true,
        //If guess != solution: return false;
        try {
        } catch (Exception e){
            throw e;
        }

        return true;
    }
        // }
    public void undoGuess() {}
    public void saveGame(){}
    public void loadGame(){}
    public void showSolution(){
    }
}
