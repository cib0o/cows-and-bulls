import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class LettersCode extends SecretCode {

    public LettersCode(Player player) {
        super(player);
    }

    String wordFile = "ListOfWords.txt";
    
    @Override
    public String generateCode() {
        List<String> words = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(wordFile))) {
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
