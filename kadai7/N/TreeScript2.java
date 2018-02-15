import java.util.*;

import javax.xml.bind.annotation.XmlElementDecl.GLOBAL;

public class TreeScript2 extends TreeScript {
	Map<String, Tree> GLOBAL = new HashMap<String, Tree>();

	TreeScript2(){
		Op[] optable = {new DefOp(), new CallOp()};
		for (Op op : optable) {
			ops.remove(op.opName());
			ops.put(op.opName(), op);
		}
	}

	class DefOp extends Op {
		public String opName() { return "def"; }
		public Tree calc(Tree left, Tree right) {
			String name = ((NameLeaf) left.eval()).name;
			GLOBAL.remove(name);     //グローバル変数の更新処理
			GLOBAL.put(name, right); //
			return left;
		}
	}

	class CallOp extends Op {
		public String opName() { return "call"; }
		
		public Tree calc(Tree left, Tree right) {
			Node fun = (Node) left.eval();
			NameLeaf param;
			try {
			  param = (NameLeaf) fun.left;
			} catch (ClassCastException e) {
			  param = new NameLeaf(((NameLeaf) fun.left).name);
			}
			Tree body = fun.right;
			Tree expression = body.replace(param.name, right);
			System.out.println("置換: " + expression.toString());
			return expression.eval();
		}
	}

	class NameLeaf extends Tree {
		String name;
		NameLeaf(String name) { this.name = name; }
		public Tree eval() {
			return GLOBAL.containsKey(this.name) ? GLOBAL.get(this.name) : this;
		}
		public Tree replace(String name, Tree r) { 
			return name.equals(this.name) ? r : this; // 名前が同じなら置き換える
		}
		public String toString() { return name; }
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
			return new NameLeaf(scanner.next());  //ここでTreeScript2.NameLeafが参照される
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

	public static void main(String args[]) {
		new TreeScript2().run();
	}
}
