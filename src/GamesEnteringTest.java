import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GamesEnteringTest {


    public class GuessEnterTests {
        /**
         * Notes:
         * Game(Player p, String codeType)
         * Game(Player p)
         * Player.getBulls()
         * Player.getCows()
         * Game.enterGuess()
         */

        /**
        As a player I want to be able to enter a guess so I can decipher the secret code

        Scenario: player enters a guess
        Given a secret code is displayed
        When the player enters a guess
        Then the number of bulls and cows are displayed and the player stats are updated
        Check:
        display : num. bulls & cows
        update : player stats
         */

        @Test
        public void unsuccessfulGuessDispANDUpdate() {
            Player p = new Player();
            Game g = new Game(p);
            int guess = 0000;

            System.out.println("Player and Game object have been made " + p + g);
            g.playGame();
            g.enterGuess(guess);
            System.out.println("Guess should be false : ");
            assertFalse(g.enterGuess(guess)); //checks to make sure guess is wrong
            if (!g.enterGuess(guess)) { //if guess is wrong
                System.out.println(p + "Bulls & Cows Count:");
                p.getBulls();
                p.getCows();
                assertTrue((p.getBulls() + p.getCows()) <= 4); // shouldnt be more than 4
                int dpCA = p.getCodesAttempted(); //storing value of codeAttempted before increment

                p.incrementCodesAttempted(); //p codeAttempted incremented
                System.out.println(p + "Has incremented codeAttempted");
                assertEquals(1, (p.getCodesAttempted() - dpCA)); //difference should == 1 if successful
            }
        }

        /**
         * Scenario: player enters the correct guess and successfully deciphers the code
         *          Given a secret code is displayed
         *          When the player enters the correct guess
         *          Then a success message is displayed, their stats are updated and the game is finished
         *          Check:
         *          display : success message
         *          update : stats | game status
         */
        @Test
        public void successfulGuessDispANDUpdate(){
            Player p2 = new Player();
            Game g2 = new Game(p2);
            int guess = 0000;

            System.out.println("Player and Game object have been made " + p2 + g2);
            g2.playGame();
            g2.enterGuess(guess);
            System.out.println("Guess should be true : ");
            assertTrue(g2.enterGuess(guess)); //checks to make sure guess is correct
            if (g2.enterGuess(guess)) { //if guess is true
                System.out.println(p2 + "Bulls & Cows Count:");
                p2.getBulls();
                p2.getCows();
                assertTrue(p2.getBulls() == 4); // should be than 4
                int dpCA = p2.getCodesAttempted(); //storing value of codesAttempted before increment
                int dpCD = p2.getCodesDeciphered(); //storing value of codesDeciphered before inc.

                p2.incrementCodesAttempted(); //p codeAttempted incremented
                p2.incrementCodesDeciphered();  //p codeDeciphered incremented

                System.out.println(p2 + "Has incremented codeAttempted");
                System.out.println(p2 + "Has incremented codeDeciphered");
                assertEquals(1, (p2.getCodesAttempted() - dpCA)); //difference should == 1 if successful
                assertEquals(1, (p2.getCodesDeciphered() - dpCD)); //difference should be == 1

                g2.saveGame();  //ends game (i think)

            }

        }

        /**
         * Scenario: player enters a guess with an invalid length
         *         Given a secret code is displayed
         *         When the player enters a guess with an invalid length
         *         Then an error message is displayed and they are asked to try again
         *         Check:
         *             display : error message , try again
         */
        @Test
        public void invalidLength() {
            Player p3 = new Player();
            Game g3 = new Game(p3);
            int guess = 0000;

            System.out.println("Player and Game object have been made " + p3 + g3);
            g3.playGame();
            g3.enterGuess(guess);

            //assert throws tests throwables in errors
            Throwable exception = assertThrows(Exception.class, //Exception.class is the exception being tested, can change to a more specific Exception type
                    ()->{g3.enterGuess(guess);} ); //checks if g3.enterGuess() will throw the exception

            //idk how to test for display without any display code
        }

        /**
         * Scenario: player enters an invalid guess for a letters code
         *          Given a secret code is displayed
         *          When the player enters a guess containing numbers
         *          Then an error message is displayed and they are asked to try again
         *          Check:
         *          display: error, try again
         */

        @Test
        public void invalidGuessforLettersCode() {
            Player p4 = new Player();
            String LettersCode = null;
            Game g4 = new Game(p4, LettersCode);
            int guess = 0000;

            System.out.println("Player and Game object have been made " + p4 + g4);
            g4.playGame();
            g4.enterGuess(guess);

            //assert throws tests throwables in errors
            Throwable exception = assertThrows(IllegalArgumentException.class, //Exception.class is the exception being tested, can change to a more specific Exception type
                    ()->{g4.enterGuess(guess);} ); //checks if g3.enterGuess() will throw the exception

        }

        /**
         * Scenario: player enters an invalid guess for a numbers code
         *          Given a secret code is displayed
         *          When the player enters a guess containing letters
         *          Then an error message is displayed and they are asked to try again
         *          Check:
         *          display: error, try again
         */


        @Test
        public void invalidGuessforNumbersCode() {
            Player p5 = new Player();
            // Game g5 = new Game(p5, NumbersCode);
            //System.out.println("Player and Game object have been made " + p5 + g5);
           // g5.playGame();
            //g5.enterGuess();

            //assert throws tests throwables in errors
            //Throwable exception = assertThrows(IllegalArgumentException.class, //Exception.class is the exception being tested, can change to a more specific Exception type
              //      ()->{g5.enterGuess();} ); //checks if g3.enterGuess() will throw the exception

        }




    }

}
