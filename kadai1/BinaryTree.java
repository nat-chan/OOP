// BinaryTree.java
public class BinaryTree{
	// 行きがけ順のなぞり
	public static void preorder (Node<?> n){
		if(n!=null){
			System.out.print(n.val+" ");
			preorder(n.left);
			preorder(n.right);
		}
	}

	// 通りがけ順のなぞり
	public static void inorder (Node<?> n){
		if(n!=null){
			inorder(n.left);
			System.out.print(n.val+" ");
			inorder(n.right);
		}
	}

	// 帰りがけ順のなぞり
	public static void postorder (Node<?> n){
		if(n!=null){
			postorder(n.left);
			postorder(n.right);
			System.out.print(n.val+" ");
		}
	}

	// 木構造の表示
	public static void display (Node<?> n){
		if(n!=null){
			System.out.print(n.val+"(");
			display(n.left);
			System.out.print(",");
			display(n.right);
			System.out.print(")");
		}else{
			System.out.print("null");
		}
	}

	/* 幅優先探索
	 * 1. 根ノードを空のキューに加える。
	 * 2. ノードをキューの先頭から取り出し、以下の処理を行う。
	 *      ノードが探索対象であれば、探索をやめ結果を返す。
	 *      そうでない場合、ノードの子で未探索のものを全てキューに追加する。
	 * 3. もしキューが空ならば、グラフ内の全てのノードに対して処理が行われた。
	 * 4. 2に戻る。
	 */
	public static void breadth_first_search (Node<?> n){
		QueueArray<Node<?>> queue = new QueueArray<Node<?>>(100); // 課題２で実装した待ち行列
		queue.enqueue(n);
		while(queue.length != 0){
			Node<?> elem = queue.dequeue();
			System.out.print(elem.val+" ");
			if(elem.left  != null)queue.enqueue(elem.left);
			if(elem.right != null)queue.enqueue(elem.right);
		}
	}

	// 深さ優先探索
	//1. 根ノードを空のスタックに加える。
	//2. ノードをキューの先頭から取り出し、以下の処理を行う。
	//     ノードが探索対象であれば、探索をやめ結果を返す。
	//     そうでない場合、ノードの子で未探索のものを全てキューに追加する。
	//3. もしキューが空ならば、グラフ内の全てのノードに対して処理が行われた。
	//4. 2に戻る。
	public static void depth_first_search (Node<?> n){
		StackArray<Node<?>> stack = new StackArray<Node<?>>(100); // Stack
		stack.push(n);
		while(stack.rear != 0){
			Node<?> elem = stack.pop();
			System.out.print(elem.val+" ");
			if(elem.right != null)stack.push(elem.right);
			if(elem.left  != null)stack.push(elem.left);
		}
	}

	// 木の高さ：ただし、教科書の定義の高さ + 1とする。nullの高さが0, 根のみの木が高さ１
	public static int height (Node<?> n){
		if(n == null) return 0;
		return Math.max(height(n.left), height(n.right)) + 1;
	}

	public static void main (String[] args){
		// 課題の要件を満たすテストを行うためには、main関数で行うテストは自分で書く必要があります。
		// 木構造の構築
		Node<String> f = new Node<String>("F", null, null);
		Node<String> i = new Node<String>("I", null, null);
		Node<String> d = new Node<String>("D", f, null);
		Node<String> g = new Node<String>("G", null, null);
		Node<String> a = new Node<String>("A", i, d);
		Node<String> l = new Node<String>("L", null, g);
		Node<String> c = new Node<String>("C", a, l);
		
		// 各メソッドのテスト
		Graph.plot(c, "img2sixel");
		System.out.print("行きがけ順   : "); preorder(c);              System.out.println();
		System.out.print("帰りがけ順   : "); postorder(c);             System.out.println();
		System.out.print("通りがけ順   : "); inorder(c);               System.out.println();
		System.out.print("幅優先探索   : "); breadth_first_search (c); System.out.println();
		System.out.print("深さ優先探索 : "); depth_first_search (c);   System.out.println();
		System.out.print("display      : "); display(c);               System.out.println();
		System.out.print("height       : "); System.out.println(height(c));
		System.out.
	}
}
