public class SecretCode {

    String code;
    public SecretCode(String type){
        if (type.equals("nc")){
            NumbersCode c = new NumbersCode();
            this.code = c.generateCode();
        } else{
            LettersCode c = new LettersCode();
            this.code = c.generateCode();
        }
    }
}
