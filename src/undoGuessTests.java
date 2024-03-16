import org.junit.Test;

import java.awt.event.KeyEvent;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class undoGuessTests{

    /**
     * Scenario: player wants to undo a single letter/number in the guess
     * Given a secret code is displayed and a guess has already been entered
     * When a player selects a letter/number to replace
     * Then the letter/number is replaced in the guess and the number of bulls and cows updated
     */

    @Test
    public void undoGuess() {
        Player p = new Player();
        Game g = new Game(p);
        g.code = "1234";
        g.buffer[0] = '1';
        g.checkGuess(g.buffer);
        g.buffer[1] = '5';
        g.checkGuess(g.buffer);
        g.buffer[2] = '3';
        g.checkGuess(g.buffer);
        g.buffer[3] = '2';
        g.checkGuess(g.buffer);
        System.out.println("The code is: " + g.code);
        System.out.println("should be 2 & 1 " + p.getBulls() + p.getCows());
        assertEquals(2,p.getBulls());


        g.undoGuess();

        g.buffer = new char[]{'1','5','3','4'};
        g.checkGuess(g.buffer);
        System.out.println("Bulls: " + p.getBulls() + " Cows: " + p.getCows());
        assertTrue(p.getBulls() == 3 && p.getCows() == 0);
    }


    /**
     * Scenario: player wants to undo a single letter/number in the guess when the player hasn’t guessed yet
     * Given a secret code is displayed and a guess hasn’t already been entered
     * When a player selects a letter/number to replace
     * Then an error message is displayed to the player indicating a complete guess hasn’t been made yet
     */

    @Test
    public void undoNothing() throws IOException {
        userInterface u = new userInterface("nc", new mainMenu());
        Throwable exception = assertThrows(IndexOutOfBoundsException.class, //Exception.class is the exception being tested, can change to a more specific Exception type
                ()->{ KeyEvent backspacePressed = new KeyEvent(u, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_BACK_SPACE, KeyEvent.CHAR_UNDEFINED);
                    u.keyPressed(backspacePressed);
                    KeyEvent backspaceReleased = new KeyEvent(u, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_BACK_SPACE, KeyEvent.CHAR_UNDEFINED);
                    u.keyReleased(backspaceReleased);} );
    }

    /**
     * Scenario: player wants to undo an invalid letter/number in the guess
     * Given a secret code is displayed and a guess has already been entered
     * When a player selects an invalid letter/number to replace
     * Then an error message is displayed and they are asked to try again
     */

    @Test
    public void invalidUndo() throws IOException {
        userInterface u = new userInterface("nc", new mainMenu());
        Game g = new Game(new Player());
        g.code = "1234";

        KeyEvent e;
        e = new KeyEvent(u.getComponent(0), 1, 20, 1, 10, '1');
        u.keyTyped(e);
        e.setKeyChar('2');
        u.keyTyped(e);
        e.setKeyChar('3');
        u.keyTyped(e);
        KeyEvent backspacePressed = new KeyEvent(u, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_BACK_SPACE, KeyEvent.CHAR_UNDEFINED);
        u.keyPressed(backspacePressed);
        KeyEvent backspaceReleased = new KeyEvent(u, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_BACK_SPACE, KeyEvent.CHAR_UNDEFINED);
        u.keyReleased(backspaceReleased);
        e.setKeyChar('3');
        u.keyTyped(e);
        e.setKeyChar('4');
        u.keyTyped(e);

        g.enterGuess(new String(u.inputBuffer).trim(), "nc");
        System.out.println("Guess is " + new String(u.inputBuffer).trim());
        System.out.println("Guess 0: " + g.guesses.get(0));

        assertTrue("123404".equals(g.guesses.get(0)));
    }
}

