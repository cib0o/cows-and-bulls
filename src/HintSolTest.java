import static org.junit.Assert.*;
import org.junit.Test;


public class HintSolTest {

    @Test
    public void testRevealHintNum() {
       Player p = new Player();
        Game game = new Game(p, "nc");
        game.code = "1234";
        game.revealHint();
        String LastHint = game.getLastHint();
        assertNotNull("Hint should have been set", LastHint); // im not sure if this is redundant

    }

    @Test
    public void testShowSolutionNum() {
        Player p = new Player();
        Game game = new Game(p, "nc");
        game.code = "1234";
        String solution = game.getSolution();
        assertNotNull(solution, "solution shouldn't be null");
        assertEquals(solution, game.code);
    }

    @Test
    public void testRevealHintLet() {
        Player p = new Player();
        Game game = new Game(p, "lc");
        game.code = "bert";
        game.revealHint();
        String LastHint = game.getLastHint();
        assertNotNull("Hint should have been set", LastHint); // im not sure if this is redundant

    }

    @Test
    public void testShowSolutionLet() {
        Player p = new Player();
        Game game = new Game(p, "lc");
        game.code = "bert";
        String solution = game.getSolution();
        assertNotNull(solution, "solution shouldn't be empty");
        assertEquals(solution, game.code);
    }

}


