import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class LettersCode {

    Player player;

    public LettersCode(Player player) {
        this.player = player;
    }

    String wordFile = "ListofWords.txt";
    

    public String generateCode(int length) {
        List<String> words = new ArrayList<>();
        if (length != 4) {
            try (Scanner scanner = new Scanner(new File("src/" + wordFile))) {
                player.incrementCodesAttempted();
                while (scanner.hasNextLine()) {
                    words.add(scanner.nextLine().trim());
                }
            } catch (FileNotFoundException e) {
                System.err.println("File not found: " + e.getMessage());
                return "";
            }
        }
        try (Scanner scanner = new Scanner(new File("src/" + "wordList10Let"))) {
            player.incrementCodesAttempted();
            while (scanner.hasNextLine()) {
                words.add(scanner.nextLine().trim());
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            return "";
        }

        if (!words.isEmpty()) {
            return words.get(new Random().nextInt(words.size()));
        } else {
            return "";
        }
    }
}
