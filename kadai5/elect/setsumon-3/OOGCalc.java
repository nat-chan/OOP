// ファイル OOGCalc.java

import java.util.*;

public abstract class OOGCalc<E extends Numeric<E>> {
    // OOGCalcのインナークラス
    abstract class Op {
        abstract public String opName();
        abstract public void exec(Deque<E> stack);
    }
    class PrintOp extends Op {
        public String opName() { return "="; }
        public void exec(Deque<E> stack) {
            for (E p : stack) {
                System.out.println(p);
            }
        }
    }
    abstract class BinOp extends Op {
        abstract public E op(E rand1, E rand2);
        public void exec(Deque<E> stack) {
            E v2 = stack.pop();
            E v1 = stack.pop();
            stack.push(op(v1, v2));
        }
    }
    class AddOp extends BinOp {
        public String opName() { return "+"; }
        public E op(E rand1, E rand2) { return rand1.add(rand2); }
    }
    final AddOp ADD_OP = new AddOp();
    class SubOp extends BinOp {
        public String opName() { return "-"; }
        public E op(E rand1, E rand2) { return rand1.subtract(rand2); }
    }
    final SubOp SUB_OP = new SubOp();
    class MulOp extends BinOp {
        public String opName() { return "*"; }
        public E op(E rand1, E rand2) { return rand1.multiply(rand2); }
    }
    final MulOp MUL_OP = new MulOp();
    class DivOp extends BinOp {
        public String opName() { return "/"; }
        public E op(E rand1, E rand2) { return rand1.divide(rand2); }
    }
    final DivOp DIV_OP = new DivOp();
    // 命令一覧表
    Deque<E> stack;
    Scanner scanner;
    Map<String, Op> ops;  // 単語辞書
    OOGCalc() {
        stack = new LinkedList<E>();
        scanner = new Scanner(System.in);
        ops = new HashMap<String, Op>();
        // 辞書に命令語を登録する．
        ops.put("+", ADD_OP);
        ops.put("-", SUB_OP);
        ops.put("*", MUL_OP);
        ops.put("/", DIV_OP);
        ops.put("=", new PrintOp());
    }
    protected abstract E fromInt(int v);
    public void run() {
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                stack.push(fromInt(scanner.nextInt()));
            } else {
                String token = scanner.next();
                if (ops.containsKey(token)) {
                    try {
                        ops.get(token).exec(stack); // 辞書を引いて命令を解釈する．
                    } catch (Exception e) {
                        System.out.println("エラー: " + e);
                    }
                } else {
                    System.out.println("エラー: 誤ったトークン: " + token);
                }
            }
        }
    }
}

/////////////////////////////////////////////////////////////////////////////////////////
// import java.util.*;                                                                 //
//                                                                                     //
// public abstract class OOGCalc<E extends Numeric<E>> {                               //
//     // OOGCalcのインナークラス                                                      //
//     abstract class Op {                                                             //
//         abstract public String opName();                                            //
//         abstract public void exec(Deque<E> stack);                                  //
//     }                                                                               //
//     class PrintOp extends Op {                                                      //
//         public String opName() { return "="; }                                      //
//         public void exec(Deque<E> stack) {                                          //
//             for (E p : stack) {                                                     //
//                 System.out.println(p);                                              //
//             }                                                                       //
//         }                                                                           //
//     }                                                                               //
//     abstract class BinOp extends Op {                                               //
//         abstract public E op(E rand1, E rand2);                                     //
//         public void exec(Deque<E> stack) {                                          //
//             E v2 = stack.pop();                                                     //
//             E v1 = stack.pop();                                                     //
//             stack.push(op(v1, v2));                                                 //
//         }                                                                           //
//     }                                                                               //
//     class AddOp extends BinOp {                                                     //
//         public String opName() { return "+"; }                                      //
//         public E op(E rand1, E rand2) { return rand1.add(rand2); }                  //
//     }                                                                               //
//     final AddOp ADD_OP = new AddOp();                                               //
//     class SubOp extends BinOp {                                                     //
//         public String opName() { return "-"; }                                      //
//         public E op(E rand1, E rand2) { return rand1.subtract(rand2); }             //
//     }                                                                               //
//     final SubOp SUB_OP = new SubOp();                                               //
//     class MulOp extends BinOp {                                                     //
//         public String opName() { return "*"; }                                      //
//         public E op(E rand1, E rand2) { return rand1.multiply(rand2); }             //
//     }                                                                               //
//     final MulOp MUL_OP = new MulOp();                                               //
//     class DivOp extends BinOp {                                                     //
//         public String opName() { return "/"; }                                      //
//         public E op(E rand1, E rand2) { return rand1.divide(rand2); }               //
//     }                                                                               //
//     final DivOp DIV_OP = new DivOp();                                               //
//     // 命令一覧表                                                                   //
//     Deque<E> stack;                                                                 //
//     Scanner scanner;                                                                //
//     Map<String, Op> ops;  // 単語辞書                                               //
//     OOGCalc() {                                                                     //
//         stack = new LinkedList<E>();                                                //
//         scanner = new Scanner(System.in);                                           //
//         ops = new HashMap<String, Op>();                                            //
//         // 辞書に命令語を登録する．                                                 //
//         ops.put("+", ADD_OP);                                                       //
//         ops.put("-", SUB_OP);                                                       //
//         ops.put("*", MUL_OP);                                                       //
//         ops.put("/", DIV_OP);                                                       //
//         ops.put("=", new PrintOp());                                                //
//     }                                                                               //
//     protected abstract E fromInt(int v);                                            //
//     // protected abstract E fromRatio(long num, long denom);                        //
//     public void run() {                                                             //
//         while (scanner.hasNext()) {                                                 //
//             if (scanner.hasNextInt()) {                                             //
//                 System.out.println("IN");                                           //
//                 stack.push(fromRatio(scanner.nextInt(), 1));                        //
//             } else {                                                                //
//                 String token = scanner.next();                                      //
//                 if (ops.containsKey(token)) {                                       //
//                     try {                                                           //
//                         ops.get(token).exec(stack); // 辞書を引いて命令を解釈する． //
//                     } catch (Exception e) {                                         //
//                         System.out.println("エラー: " + e);                         //
//                     }                                                               //
//                 } else {                                                            //
//                     System.out.println("エラー: 誤ったトークン: " + token);         //
//                 }                                                                   //
//             }                                                                       //
//         }                                                                           //
//     }                                                                               //
// }                                                                                   //
/////////////////////////////////////////////////////////////////////////////////////////
