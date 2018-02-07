import java.util.Deque;   // ←忘れがちなので注意．
import java.util.LinkedList;

public class TreeCalc extends OOGCalc<TreeCalc.Tree> {
    RatioCalc ratcalc = new RatioCalc();
    abstract class Tree implements Numeric<Tree> {
        public Tree add(Tree t) { return new Node(ratcalc.ADD_OP, this, t); }
        public Tree subtract(Tree t) {
            return new Node(ratcalc.SUB_OP, this, t);
        }

        public Tree multiply(Tree t) {
            return new Node(ratcalc.MUL_OP, this, t);
        }

        public Tree divide(Tree t) {
            return new Node(ratcalc.DIV_OP, this, t);
        }
        abstract RatioCalc.Rat eval ();
    }
    class Leaf extends Tree {
        int value;
        Leaf(int value) { this.value = value; }
        public String toString() { return Integer.toString(value); }
        public RatioCalc.Rat eval() { return new RatioCalc.Rat(value); }
    }
    class Node extends Tree {
        RatioCalc.BinOp op;
        Tree left, right;
        Node(RatioCalc.BinOp op, Tree left, Tree right) {
            this.op = op;
            this.left = left;
            this.right = right;
        }
        public RatioCalc.Rat eval() {
            RatioCalc.Rat l = left.eval();
            RatioCalc.Rat r = right.eval();
            return op.op(l, r);
        }
        public String toString() {
            return ("(" + left.toString() + op.opName() + right.toString() + ")");
        }
    }
    protected Tree fromInt(int v) {
        return new Leaf (v);
    }
    class ShowOp extends Op {
        public String opName() { return "?"; }
        public void exec (Deque<TreeCalc.Tree> stack) {
            for (Tree p : stack) {
                System.out.println(p.eval());
            }
        }
    }
    TreeCalc() {
        ops.put("?", new ShowOp ());
    }
    public static void main(String[] args) {
        new TreeCalc().run();
    }
}
