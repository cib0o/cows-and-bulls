import org.junit.
        jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class cibooTests {

        /**
         * Correct code is : 1234
         * or
         * abcd
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
        public void playerStatsPerc() {
            Player pT = new Player();
            Game g = new Game(pT);
            g.code = "1234";
            pT.username = "Cib0o";

            g.buffer[0] = '1';
            g.checkGuess(g.buffer);
            g.buffer[1] = '0';
            g.checkGuess(g.buffer);
            g.buffer[2] = '4';
            g.checkGuess(g.buffer);
            g.buffer[3] = '0';
            g.checkGuess(g.buffer);
            g.buffer = new char[4]; //resets the buffer to simulate a new guess
            //System.out.print("Bulls : " + pT.getBulls() + "\n" + "Cows : " + pT.getCows() + "\n" + "Stats % : " + pT.getStats() + "\n");

            g.enterGuess("1040","nc");
            pT.displayStats(pT);

            g.buffer[0] = '1';
            g.checkGuess(g.buffer);
            g.buffer[1] = '2';
            g.checkGuess(g.buffer);
            g.buffer[2] = '4';
            g.checkGuess(g.buffer);
            g.buffer[3] = '0';
            g.checkGuess(g.buffer);
            g.buffer = new char[4];
            //System.out.print("Bulls : " + pT.getBulls() + "\n" + "Cows : " + pT.getCows() + "\n" + "Stats % : " + pT.getStats() + "\n");
            g.enterGuess("1240","nc");
            pT.displayStats(pT);

            g.buffer[0] = '0';
            g.checkGuess(g.buffer);
            g.buffer[1] = '0';
            g.checkGuess(g.buffer);
            g.buffer[2] = '0';
            g.checkGuess(g.buffer);
            g.buffer[3] = '0';
            g.checkGuess(g.buffer);

            g.enterGuess("0000","nc");
            //System.out.print("Bulls : " + pT.numberOfBulls + "\n" + "Cows : " + pT.numberOfCows + "\n" + "Stats % : " + pT.getStats() + "\n");

            pT.displayStats(pT);

        }

        @Test
        public void updateStats() throws IOException {
            Player pTs = new Player("cib0o");
            Game g = new Game(pTs);

            pTs.numberOfGuesses= 642345;
            pTs.numberOfCows = 42;
            pTs.numberOfBulls = 12;
            pTs.codesAttempted = 1342;
            pTs.codesDeciphered = 2;

            pTs.updateStats();
        }

        @Test
        public void unsuccessfulGuessDispANDUpdate() {
            Player p = new Player();
            Game g = new Game(p);
            String guess = "0000"; //minimum wrong guess
            g.code = "1234";

            System.out.println("Player and Game object have been made " + p + g + " " + guess + "|code:" + g.requestCode("nc"));

            g.enterGuess(guess, "nc");

            g.buffer[0] = '0';
            g.checkGuess(g.buffer);
            g.buffer[1] = '0';
            g.checkGuess(g.buffer);
            g.buffer[2] = '0';
            g.checkGuess(g.buffer);
            g.buffer[3] = '0';
            g.checkGuess(g.buffer);

            assertNotEquals(4, p.getBulls()); //assertFalse(Objects.deepEquals(g.enterGuess(guess, "nc"), new int[]{0, 4})); //checks to make sure guess is wrong


            System.out.print("Comparison should be false | ");
            System.out.println(Objects.deepEquals(g.enterGuess(guess, "nc"), new int[]{0,4}));

            if (!Objects.deepEquals(g.enterGuess(guess, "nc"), new int[]{0,4})) { //if guess is wrong
                System.out.print(p + " Bulls & Cows Count:");

                System.out.println(p.getBulls() + " b | c " + p.getCows());

                assertTrue((p.getBulls() + p.getCows()) <= g.guesses.size()*4); // shouldnt be more than 4

                int dpCA = p.getCodesAttempted(); //storing value of codeAttempted before increment

                p.incrementCodesAttempted(); //p codeAttempted incremented
                System.out.println(p + " Has incremented codeAttempted");
                assertEquals(1, (p.getCodesAttempted() - dpCA)); //difference should == 1 if successful
            } else {
                System.out.println("--- Test FAILED , guess is true ---");
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
            String guess = "1234";
            g2.code = "1234";

            System.out.println("Guess is " + g2.code);

            System.out.println("Player and Game object have been made " + p2 + g2 + " " + guess + " |code:" + g2.code);

            g2.buffer[0] = '1';
            g2.checkGuess(g2.buffer);
            g2.buffer[1] = '2';
            g2.checkGuess(g2.buffer);
            g2.buffer[2] = '3';
            g2.checkGuess(g2.buffer);
            g2.buffer[3] = '4';
            g2.checkGuess(g2.buffer);

            // g2.enterGuess(guess, "nc");

            System.out.print("Guess should be true : ");
            System.out.println(Objects.deepEquals(g2.enterGuess(guess, "nc"), new int[]{0,4})); //compares contents of obj[]
            assertTrue(p2.getBulls() == 4 ); //checks to make sure guess is correct

            if (p2.getBulls() == 4 ) { //if guess is true
                System.out.print(p2 + "Bulls & Cows Count: " );
                System.out.println(p2.getBulls() + " b|c " + p2.getCows());
                assertTrue(p2.getBulls() == 4); // should be 4

                int dpCA = p2.getCodesAttempted(); //storing value of codesAttempted before increment
                int dpCD = p2.getCodesDeciphered(); //storing value of codesDeciphered before inc.

                p2.incrementCodesAttempted(); //p codeAttempted incremented
                p2.incrementCodesDeciphered();  //p codeDeciphered incremented

                System.out.println(p2 + "Has incremented codeAttempted : " + dpCA + "bef.|aft. " + p2.getCodesAttempted());
                System.out.println(p2 + "Has incremented codeDeciphered : " + dpCD + "bef.|aft. " + p2.getCodesDeciphered());

                assertEquals(1, (p2.getCodesAttempted() - dpCA)); //difference should == 1 if successful
                assertEquals(1, (p2.getCodesDeciphered() - dpCD)); //difference should be == 1

            } else {
                System.out.println("--- Test FAILED , guess is wrong---");
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
            String guess1 = "00000"; //unsucc. guess w invalid len
            String guess2 = "12345"; //successful guess but invalid len

            g3.requestCode("nc");

            System.out.println("Player and Game object have been made " + p3 + g3);

            //g3.enterGuess(guess2);

            //assert throws tests throwables in errors
            Throwable exc1 = assertThrows(IndexOutOfBoundsException.class, //Exception.class is the exception being tested, can change to a more specific Exception type
                    ()->{g3.enterGuess(guess1,"nc");} ); //checks if g3.enterGuess() will throw the exception
            Throwable exc2 = assertThrows(IndexOutOfBoundsException.class,
                    () ->{g3.enterGuess(guess2, "nc");} );

            //See if display is shown
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
            String LettersCode = "lc";
            Game g4 = new Game(p4, LettersCode,4);
            String guess = "ab0d";
            g4.requestCode("nc");

            System.out.println("Player and Game object have been made " + p4 + g4);

            //assert throws tests throwables in errors
            Throwable exception = assertThrows(IllegalArgumentException.class, //Exception.class is the exception being tested, can change to a more specific Exception type
                    ()->{//g4.enterGuess(guess, "lc");
                g4.buffer[0] = 'a';
                g4.checkGuess(g4.buffer);
                g4.buffer[1] = 'b';
                g4.checkGuess(g4.buffer);
                g4.buffer[2] = '0';
                g4.checkGuess(g4.buffer);
                g4.buffer[3] = 'd';
                g4.checkGuess(g4.buffer); } ); //checks if g3.enterGuess() will throw the exception
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
            String NumbersCode = "nc";
            Game g5 = new Game(p5, NumbersCode,4);
            String guess = "12d4";
            g5.requestCode("nc");

            System.out.println("Player and Game object have been made " + p5 + g5);

            //assert throws tests throwables in errors
            Throwable exception = assertThrows(IllegalArgumentException.class, //Exception.class is the exception being tested, can change to a more specific Exception type
                    ()->{g5.enterGuess(guess, "nc");
                        g5.buffer[0] = '1';
                        g5.checkGuess(g5.buffer);
                        g5.buffer[1] = '2';
                        g5.checkGuess(g5.buffer);
                        g5.buffer[2] = 'd';
                        g5.checkGuess(g5.buffer);
                        g5.buffer[3] = '4';
                        g5.checkGuess(g5.buffer); } ); //checks if g3.enterGuess() will throw the exception
        }
    }

