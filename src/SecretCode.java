public class SecretCode {
    protected Player player;
    String code;

    public SecretCode(Player player, String choice, int length) {
        this.player = player;

        if (choice.equals("nc")) {
            NumbersCode n = new NumbersCode(player);
            this.code = n.generateCode(length);
        } else {
            LettersCode l = new LettersCode(player);
            this.code = l.generateCode(length);
        }
    }
}

