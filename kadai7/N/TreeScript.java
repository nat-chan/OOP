import java.util.*;

public class TreeScript {
	abstract class Tree {
		abstract public Tree eval(); // 木の値を計算する
		abstract public Tree replace(String name, Tree r); // 木の中の名前nameをrに置き換える
	}

	class IntLeaf extends Tree {
		int value;
		IntLeaf(int value) { this.value = value; }
		public Tree eval() { return this; }
		public Tree replace(String name, Tree r) { return this; } // 置き換えは起きない
		public String toString() { return Integer.toString(value); }
	}

	class NameLeaf extends Tree {
		String name;
		NameLeaf(String name) { this.name = name; }
		public Tree eval() { return this; }
		public Tree replace(String name, Tree r) { 
			return name.equals(this.name) ? r : this; // 名前が同じなら置き換える
		}
		public String toString() { return name; }
	}

	class Node extends Tree {
		Op operator;
		Tree left, right;
		Node(Op operator, Tree left, Tree right) {
			this.operator = operator;
			this.left = left; this.right = right;
		}
		public Tree replace(String name, Tree r) {
			return operator.replace(this, left, right, name, r); // Op によって置き換えの仕方を変えることができる
		}
		public Tree eval() {
			return operator.calc(left, right);
		}
		public String toString() {
			return "( " + operator.opName() + " " + left.toString() + " " + right.toString() + " )";
		}
	}

	abstract class Op {
		abstract Tree calc(Tree left, Tree right);
		abstract String opName();
		Tree replace(Node original, Tree left, Tree right, String name, Tree r) {
			return new Node(this, left.replace(name, r), right.replace(name, r)); // Nodeをコピーし，左右の子を再帰的にreplaceする
		}
	}

	abstract class BinOp extends Op{
		// 整数の計算をする２項演算子
		abstract public int op(int rand1, int rand2);
		public Tree calc(Tree left, Tree right) {
			int l = ((IntLeaf)left.eval()).value;
			int r = ((IntLeaf)right.eval()).value;
			return new IntLeaf(op(l, r));
		}
	}
	class AddOp extends BinOp {
		public String opName() { return "+"; }
		public int op(int rand1, int rand2) { return rand1 + rand2; }
	}
	class SubOp extends BinOp {
		public String opName() { return "-"; }
		public int op(int rand1, int rand2) { return rand1 - rand2; }
	}
	class MulOp extends BinOp {
		public String opName() { return "*"; }
		public int op(int rand1, int rand2) { return rand1 * rand2; }
	}
	class DivOp extends BinOp {
		public String opName() { return "/"; }
		public int op(int rand1, int rand2) { return rand1 / rand2; }
	}
	// 特別な演算子
	class FunOp extends Op {
		// ( fun param bodyTree ) という形．左の子paramはNameLeafに限る．
		public String opName() { return "fun"; }
		public Tree calc(Tree left, Tree right) { 
			return new Node(this, left, right); // 自分と同じものを返す
		}
	}
	class CallOp extends Op {
		// ( call Fun Tree ) という形．左の子をevalした値は，FunOpを持つノードに限る．
		public String opName() { return "call"; }
		public Tree calc(Tree left, Tree right) {
			// 左の子をevalした値は，FunOpを持つノードでなければならない．
			Node fun = (Node) left.eval();
			NameLeaf param = (NameLeaf) fun.left;
			Tree body = fun.right;
			// funのbodyTree内のparamを，rightに置き換える
			Tree expression = body.replace(param.name, right);
			System.out.println("置換: " + expression.toString());
			// bodyをevalした値を返す．
			return expression.eval();
		}
	}
	// 命令一覧表
	Op[] optable = {new AddOp(), new SubOp(), new MulOp(), new DivOp(),
	                new FunOp(), new CallOp()};
	Scanner scanner;
	Map<String, Op> ops;
	TreeScript() {
		scanner = new Scanner(System.in);
		ops = new HashMap<String, Op>();
		// 辞書に命令語を登録する．
		for (Op op : optable) {
			ops.put(op.opName(), op);
		}
	}

	public Tree parse() {
		if (scanner.hasNextInt()) {
			return new IntLeaf(scanner.nextInt());
		} else if (scanner.hasNext("\\(")) {
			scanner.next();               // "("を読み飛ばす．
			String name = scanner.next(); // オペレータの名前
			Tree left = parse();          // 左の子
			Tree right = parse();         // 右の子
			scanner.next("\\)");          // ")" を読み飛ばす
			return new Node(ops.get(name), left, right);
		} else {
			return new NameLeaf(scanner.next());
		}
	}

	public void run() {
		while (scanner.hasNext()) {
			try {
				Tree t = parse();
				System.out.println("式: " + t.toString());
				System.out.println(t.eval());
			} catch (Exception e) {
				System.out.println("エラー");
				e.printStackTrace(System.out);
			}
		}
	}

	public static void main(String[] args) {
		new TreeScript().run();
	}
}
