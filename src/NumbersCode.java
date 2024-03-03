import java.util.Random;

public class NumbersCode extends SecretCode {

    public NumbersCode(Player player) {
        super(player);
    }

    @Override
    public String generateCode() {
        player.incrementCodesAttempted();
        Random random = new Random();
        String code = "";
        while (code.length() < 4) {
            int nextDigit = random.nextInt(10);
            String digitStr = Integer.toString(nextDigit);
            if (!code.contains(digitStr)) {
                code += digitStr;
            }
        }
        return code;
    }
}
