import javax.swing.*;
import java.io.*;
import java.util.*;

public class Players{

    private String pListTxtFile = "src/players.txt";
    public List<Player> leaderboard() throws FileNotFoundException { //sort by deciphered
        List<Player> playersList = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(pListTxtFile));
            String line;
            while ((line = reader.readLine()) != null ) {
                String[] parts = line.split(" ");
                playersList.add(new Player(parts[0]));
                //players[i] = new Player(reader.readLine().split(" ", 1).toString());
            }
            reader.close();
        } catch (IOException e) {
            throw new FileNotFoundException("File path is invalid");
        }

        Player temp;
        for (int i = 0; i < playersList.size()-1; i++) {
            for (int j = 0; j < playersList.size()-i-1; j++) {
                if (playersList.get(j).getCodesDeciphered() < playersList.get(j+1).getCodesDeciphered()) {
                    temp = playersList.get(j);
                    playersList.set(j, playersList.get(j+1));
                    playersList.set(j+1, temp);
                }
            }
        }

        if(playersList.size() >= 10) {
            for (int n=playersList.size()-1; n > 10; n--) {
                playersList.remove(n);
            }
        }

        System.out.println(playersList.get(1));

        return playersList;
    }

    public void createAccount(String username) {
        //write to file
        //then call player object with new file

        try {
            BufferedWriter addtoFile = new BufferedWriter(new FileWriter(pListTxtFile, true));
            addtoFile.write(username + " 0 0 0 0 0 -1");
            addtoFile.close();

            Player p = new Player(username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
