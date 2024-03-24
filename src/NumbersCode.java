import java.util.Random;

public class NumbersCode {
    Player player;

    public NumbersCode(Player player) {
       this.player = player;
    }


    public String generateCode(int length) {
        player.incrementCodesAttempted();
        Random random = new Random();
        String code = "";
        while (code.length() < length) {
            int nextDigit = random.nextInt(10);
            String digitStr = Integer.toString(nextDigit);
            if (!code.contains(digitStr)) {
                code += digitStr;
            }
        }
        return code;
    }
}
