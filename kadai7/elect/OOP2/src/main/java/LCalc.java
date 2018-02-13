import java.util.*;

public class LCalc {
    abstract class Op {
        abstract public String opName();
        abstract public void exec();
    }
    class PrintOp extends Op {
        public String opName() { return "="; }
        public void exec() {
            for (double p : state.stack) {
                System.out.println(p);
            }
        }
    }
    abstract class BinOp extends Op {
        abstract public double op(double rand1, double rand2);
        public void exec() {
            double v2 = state.stack.pop();
            double v1 = state.stack.pop();
            state.stack.push(op(v1, v2));
        }
    }
    class AddOp extends BinOp {
        public String opName() { return "足す"; }
        public double op(double rand1, double rand2) { return rand1 + rand2; }
    }
    class SubOp extends BinOp {
        public String opName() { return "引く"; }
        public double op(double rand1, double rand2) { return rand1 - rand2; }
    }
    class MulOp extends BinOp {
        public String opName() { return "かける"; }
        public double op(double rand1, double rand2) { return rand1 * rand2; }
    }
    class DivOp extends BinOp {
        public String opName() { return "割る"; }
        public double op(double rand1, double rand2) { return rand1 / rand2; }
    }
    class NoOp extends Op {
        public String opName() { return "noop"; }
        public void exec() {
            // 何もしない
        }
    }
    class RunOp extends Op {
        public String opName() { return "実行する"; }
        public void exec() {
            state.pstack.pop().run();
        }
    }

    interface Token {
        abstract void interpret(); // トークンを解釈し，実行する
    }
    class Number implements Token {
        private double num;
        public Number(double num) { this.num = num; }
        public void interpret() {
            state.stack.push(num); // 数値を解釈すると，値スタックに積む
        }
        public String toString() { return Double.toString(num); }
    }
    class Name implements Token {
        private String name;
        public Name(String name) { this.name = name; }
        public void interpret() {
            if (state.ops.containsKey(name)) {
                state.ops.get(name).exec(); // 名前を解釈すると，命令辞書を引いて命令を実行する
            } else {
                System.out.println("エラー: 誤ったトークン: " + name);
            }
        }
        public String toString() { return name; }
    }
    class Program implements Token {
        private List<Token> prog;
        public Program(List<Token> prog) { this.prog = prog; }
        public void interpret() {
            state.pstack.push(this); // プログラムを解釈すると，自分をプログラムスタックに積む
        }
        List<Token> inspect() { return prog; }
        void run() {
            for (Token t : prog) {
                t.interpret();  // トークン列をすべて解釈する
            }
        }
        public String toString() {
            String result = "「 ";
            for (Token t : prog) { result += t.toString() + " "; }
            result += "」";
            return result;
        }
    }

    // 命令一覧表
    Op[] optable = {new AddOp(), new SubOp(), new MulOp(), new DivOp(), new PrintOp(), new RunOp()};
    // 無視するトークン
    String[] noopTable = {"と", "を", "から", "で", "の", "回", "歩", "度"};

    // マシンの状態
    class State {
        Deque<Double> stack;    // 値スタック
        Deque<Program> pstack;  // プログラムスタック
        Map<String, Op> ops;    // 単語辞書
        State() {
            stack = new LinkedList<Double>();
            pstack = new LinkedList<Program>();
            ops = new HashMap<String, Op>();
        }
    }
    State state;
    Scanner scanner;
    LCalc() {
        state = new State();
        scanner = new Scanner(System.in);
        // 辞書に命令語を登録する．
        for (Op op : optable) {
            state.ops.put(op.opName(), op);
        }
        Op noop = new NoOp();
        for (String n : noopTable) {
            state.ops.put(n, noop);
        }
    }
    Program parse(String endToken) {
        List<Token> list = new LinkedList<Token>();
        while (! scanner.hasNext(endToken)) {
            if (scanner.hasNext("「")) { // 次のトークンが"「"ならば
                scanner.next();     // "「"を読み飛ばす．
                Program subList = parse("」"); // "」"まで列にする
                list.add(subList);
            } else if (scanner.hasNextDouble()) {
                list.add(new Number(scanner.nextDouble()));
            } else {
                list.add(new Name(scanner.next()));
            }
        }
        scanner.next();     // endTokenを読み飛ばす．
        return new Program(list);
    }
    public void run() {
        while (scanner.hasNext()) {
            try {
                Program p = parse("。"); // "。"まで列にする
                System.out.println("入力: " + p.toString());
                p.run();   // 列を解釈する
            } catch (Exception e) {
                System.out.println("エラー");
                e.printStackTrace(System.out);
            }
        }
        System.out.println("終了。");
        System.exit(0);
    }
    public static void main(String[] args) {
        new LCalc().run();
    }
}
