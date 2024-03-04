public class SecretCode {
    protected Player player;
    String code;

    public SecretCode(Player player, String choice) {
        this.player = player;

        if (choice.equals("nc")) {
            NumbersCode n = new NumbersCode(player);
            this.code = n.generateCode();
        } else {
            LettersCode l = new LettersCode(player);
            this.code = l.generateCode();
        }
    }
}

