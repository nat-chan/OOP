import java.util.*;

public class TreeScript1 extends TreeScript {
	private NameLeaf left = new NameLeaf("left");
	private NameLeaf right = new NameLeaf("right");
	private Node TRUE  = new Node( new FunOp(), left, new Node(new FunOp(), right, left));
	private Node FALSE = new Node( new FunOp(), left, new Node(new FunOp(), right, right));
	class EqOp extends Op {
		public String opName() { return "=="; }

		public Tree calc(Tree left, Tree right) {
			if( ((IntLeaf) left.eval()).value == ((IntLeaf) right.eval()).value ){
				return TRUE;
			}else{
				return FALSE;
			}
		}
	}


	TreeScript1(){
		Op op = new EqOp();
		ops.put(op.opName(), op);
	}
	public static void main(String args[]) {
		new TreeScript1().run();
	}
}
