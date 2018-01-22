import java.util.*;

public class JCalc extends OOCalc {
    String[][] alias = {{"足す", "+"}, {"引く", "-"}, {"かける", "*"}, {"割る", "/"},
                        {"から", "noop"}, {"と", "noop"}, {"を", "noop"}, {"で", "noop"}};
    JCalc() {
        for (String[] pair : alias) {
            ops.put(pair [0] , ops.get (pair [1]));
        }
    }
    public static void main(String[] args) {
        new JCalc().run();
    }
}
