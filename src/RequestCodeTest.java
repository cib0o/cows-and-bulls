import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class RequestCodeTest {
	
	@Test
	//tests when request code is called for a word game
	public void requestLetterCode() {
		
		//creation of Player and Game objects
		Player p = new Player();
		Game g = new Game(p, "lc",4);
		
		int codes_attempted = p.getCodesAttempted(); //sets codes_attempted to the value of the player's codes attempted (used to test if incrementCodesAttempted() works)
		g.requestCode("lc"); //sets codes to the value of requestCode() for a word game
		String code = g.code;
		
		assertEquals(code.length(), 4); //checks if code is of length 4
		assertTrue(codeInFile(code)); //checks if code is held within the word codes file
		assertTrue(distinctCharacter(code)); //checks that all individual characters in code are unique
		assertEquals(codes_attempted+1, p.getCodesAttempted()); //checks that the incrementCodesAttempted() method works
		
	}
	
	@Test
	//tests when request code s called for a number game
	public void requestNumberCode() {
		
		//creation of Player and Game objects
		Player p = new Player();
		Game g = new Game(p, "nc",4);
		
		int codes_attempted = p.getCodesAttempted(); //sets codes_attempted to the value of the player's codes attempted (used to test if incrementCodesAttempted() works)
		g.requestCode("nc"); //sets codes to the value of requestCode() for a number game
		String code = g.code;
		
		assertEquals(String.valueOf(code).length(), 4); //checks if code is of length 4, pass if it is 4 (had to turn int into String to use length())
		assertTrue(distinctCharacter(code)); //checks that all individual numbers in code are unique
		assertEquals(codes_attempted+1, p.getCodesAttempted()); //checks that the incrementCodesAttempted() method works
		
	}
	
	@Test
	public void noWordStored() {
		
		Player p = new Player();
		Game g = new Game(p, "lc",4);
		LettersCode l = new LettersCode(p);
		l.wordFile = "noFileExists.txt"; //changes file to a non-existant file (may need to change dependent on how the file is made)
		
		int codes_attempted = p.getCodesAttempted(); 
		String code = l.generateCode(4);
		
		assertEquals(code, ""); //code should equal an empty string (can change this, depends on how request code is written)
		assertEquals(code.length(), 0); //code should be of length 0 (can change this, depends on how request code is written)
		assertEquals(codes_attempted, p.getCodesAttempted()); //incrementCodesAttempted() should not run, meaning codes attempted should be 0
		
	}
	
	// checks if the code contains only distinct character, i.e. 1234 == True. 1123 == False, since 1 appears twice. "able" == True. "pool" == False
	private boolean distinctCharacter(String code) {
		
		Stack<Character> distinct_character = new Stack<>(); //creation of Stack to store all distinct characters in code
		
		//loop to add distinct characters to Stack, stops when o reaches 4 (max length of code) or when a non distinct character is found
		for (int o = 0; o < 4; o++) {
			char curr_char = code.charAt(o); //returns the last character stored in code, i.e. code == 1234, individual_code_int == 4
			//code = code / 10; //divides code by 10 for use in next iteration of loop, i.e. code == 1234 -> 123
			if (distinct_character.contains(curr_char)) { //if statement to see if current character is stored within stack
				return false; //should return false, this means not every character is distinct
			}
			distinct_character.push(curr_char); //adds distinct character to Stack
		}
		
		return true; //only distinct characters
	}
	
	//checks if the code provided is within the codes file containing all valid word codes 
	private boolean codeInFile(String code) {
		
		Stack<String> codes = new Stack<>(); //creates stack that is used to temporarily store all words held within codes file
		
		//reading of text file
		try {
			File myObj = new File("src/ListofWords.txt");
			Scanner myReader = new Scanner(myObj);

			//loop to store all words from file into Stack, stops when the file has no more words left
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine(); //reads word from file into variable data
				codes.push(data); //adds data variable to Stack
			}
			myReader.close();

		//handles errors when no file is found
		} catch (FileNotFoundException e) {
			e.printStackTrace(); //prints details of exception that has occurred
		}
		
		//if statement to find whether it is a valid code or not, i.e. code is held within the Stack
		if (codes.contains(code)) {
			return true;
		} else return false;
		
	}
	
}
