import java.util.Deque;
public class RatioCalc extends OOGCalc<RatioCalc.Rat>
{
    static class Rat implements Numeric<Rat> {
        Ratio ratio;
        Rat (int i) {
            ratio = new Ratio(i, 1);
        }
        Rat (Ratio r) {
            ratio = r;
        }
        public Rat add(Rat r) {
            return new Rat (ratio.add (r.ratio));
        }
        public Rat subtract (Rat r) {
            return new Rat (ratio.subtract (r.ratio));
        }
        public Rat multiply (Rat r) {
            return new Rat (ratio.multiply (r.ratio));
        }
        public Rat divide (Rat r) {
            return new Rat (ratio.divide (r.ratio));
        }

        public String toString () {
            return ratio.toString ();
        }
    }

    protected Rat fromInt(int v) {
        return new Rat (v);
    }
    class NoOp extends Op {
        public String opName () {
            return "noop";
        }
        public void exec (Deque<Rat> stack) {
        }
    }
    String [] [] alias = {{"足す", "+"}, {"引く", "-"}, {"かける", "*"}, {"割る", "/"},
                          {"から", "noop"}, {"と", "noop"}, {"を", "noop"}, {"で", "noop"}};
    RatioCalc() {
        ops.put ("noop", new NoOp ());
        for (String [] pair : alias) {
            ops.put (pair [0], ops.get (pair [1]));
        }
    }
    public static void main(String args[]) {
        new RatioCalc ().run ();
    }
}
