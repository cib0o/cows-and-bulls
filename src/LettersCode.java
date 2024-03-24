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



    public String generateCode(int length) {
        System.out.println("I AM AT LEAST TRYING TO GENERATE A LETTER CODE");
        List<String> words = new ArrayList<>();
        try {

            Scanner scanner = new Scanner(new File(length == 4? "src/4LetterIsograms.txt":"src/ListofWords.txt"));
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine().trim();
                if (word.length() == length) {
                    words.add(word);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            return "";
        }

        if (!words.isEmpty()) {
            System.out.println("IF THIS IS NOT BLANK THEN IT WORKS: " + words.get(new Random().nextInt(words.size())));
            return words.get(new Random().nextInt(words.size()));
        } else {
            return ""; // No words of the desired length were found
        }
    }

}
