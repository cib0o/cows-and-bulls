public abstract class SecretCode {
    protected Player player;

    public SecretCode(Player player) {
        this.player = player;
    }

    public abstract String generateCode();
}
