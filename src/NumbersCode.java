import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NumbersCode {

    public String generateCode(){
        String code;
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0 ; i < 10 ; i++){
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        code = "" + numbers.get(0)+ numbers.get(1)+ numbers.get(2)+ numbers.get(3);
        return code;
    }
}
