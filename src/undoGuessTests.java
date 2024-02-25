import org.junit.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class undoGuessTests{

    /**
     * Scenario: player wants to undo a single letter/number in the guess
     * Given a secret code is displayed and a guess has already been entered
     * When a player selects a letter/number to replace
     * Then the letter/number is replaced in the guess and the number of bulls and cows updated
     */

    @Test
    public void undoGuess(){
        Game g = new Game();
        g.requestCode("nc");
        g.enterGuess("1234", "nc");

        g.undoGuess(1243);
        assertTrue(g.guesses.size() == 1 && g.guesses.get(0) == "1243");
    }

    /**
     * Scenario: player wants to undo a single letter/number in the guess when the player hasn’t guessed yet
     * Given a secret code is displayed and a guess hasn’t already been entered
     * When a player selects a letter/number to replace
     * Then an error message is displayed to the player indicating a complete guess hasn’t been made yet
     */

    @Test
    public void undoNothing(){
        Game g = new Game();
        g.requestCode("nc");
        assertThrows(Exception.class, () -> {
            g.undoGuess(1111);
        });

    }

    /**
     * Scenario: player wants to undo an invalid letter/number in the guess
     * Given a secret code is displayed and a guess has already been entered
     * When a player selects an invalid letter/number to replace
     * Then an error message is displayed and they are asked to try again
     */

    @Test
    public void invalidUndo(){
        Game g = new Game();
        g.requestCode("nc");
        //g.enterGuess(1234);

        assertThrows(Exception.class, () -> {
            g.undoGuess(1111);
        });

        g.undoGuess(1243);
        assertTrue(g.guesses.get(0) == "1243");
    }
}

