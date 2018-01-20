// Node.java
// ２分木のノードを実現するクラス
public class Node<T> {
	T val;
	Node<T> left, right;  
	
	public Node (T val, Node<T> left,  Node<T> right) {
		this.val = val;
		this.left = left;
		this.right = right;
	}
}
