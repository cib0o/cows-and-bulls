import javax.swing.*;

public class Players{

    public void displayStats(Player p) {
        System.out.println("---------------------------------------------");
        System.out.println("Stats for Player " + p.username + "\n");
        System.out.println("---------------------");
        System.out.print("Bulls : " + p.getBulls() + "\n" + "Cows : " + p.getCows() + "\n" + "Success % : " + p.getStats() + "%\n");
        System.out.println("---------------------------------------------");

        JOptionPane.showMessageDialog(null,
                "Bulls : " + p.getBulls() + "\n" + "Cows : " + p.getCows() + "\n" + "Guesses : " + p.numberOfGuesses/4 + "\n" + "Success % : " + p.getStats() + "%\n",
                p.username + "'s Stats", JOptionPane.PLAIN_MESSAGE);
    }
}
