enum Lr {
	L, R
};

public class BinarySearchTree{
	static Node<Integer> root;         // ２分探索木の根（nullで初期化されている）

	// 木構造の表示：２分木の実装のメソッド displayを再利用
	public static void display (Node<Integer> n){
		BinaryTree.display(n);
	}

	// 最小値の探索
	public static Integer min_bst(Node<Integer> n){
		if(n.left == null) return n.val;
		return min_bst(n.left);
	}

	// 値の探索
	public static boolean search_bst(Node<Integer> n, Integer d){
		if(n == null) return false;
		if(n.val > d) return search_bst(n.left, d);
		if(n.val < d) return search_bst(n.right, d);
		return true;
	}

	// 値の挿入
	public static void insert_bst(Node<Integer> n, Integer d){
		if(n.val > d){
			if(n.left == null){
				n.left = new Node<Integer>(d, null, null);
			}else{
				insert_bst(n.left, d);
			}
		}else if(n.val < d){
			if(n.right == null){
				n.right = new Node<Integer>(d, null, null);
			}else{
				insert_bst(n.right, d);
			}
		}
	}

	// 値の削除
	public static void delete_bst(Node<Integer> n, Integer d){
		if(n.val > d){
			if(n.left == null){
				n.left = new Node<Integer>(d, null, null);
			}else{
				insert_bst(n.left, d);
			}
		}else if(n.val < d){
			if(n.right == null){
				n.right = new Node<Integer>(d, null, null);
			}else{
				insert_bst(n.right, d);
			}
		}
	}
	
	public static void main (String[] args){
		// 課題の要件を満たすテストを行うためには、main関数で行うテストは自分で書く必要があります。
		// 値の挿入
//		   4
//		 2   6
//		1 3 5 7
		Node<Integer> one   = new Node<Integer>(1, null, null);
		Node<Integer> three = new Node<Integer>(3, null, null);
		Node<Integer> two   = new Node<Integer>(2, one, three);
		Node<Integer> five  = new Node<Integer>(5, null, null);
		Node<Integer> seven = new Node<Integer>(7, null, null);
		Node<Integer> six   = new Node<Integer>(6, five, seven);
		Node<Integer> four  = new Node<Integer>(4, two, six);
		insert_bst(four,8);
		insert_bst(four,9);
		Graph.plot(four, "imgcat");

//		System.out.print("行きがけ順   : "); BinaryTree.preorder( four);            System.out.println();
//		System.out.print("帰りがけ順   : "); BinaryTree.postorder(four);            System.out.println();
		System.out.print("通りがけ順   : "); BinaryTree.inorder(  four);            System.out.println();
//		System.out.print("幅優先探索   : "); BinaryTree.breadth_first_search(four); System.out.println();
//		System.out.print("深さ優先探索 : "); BinaryTree.depth_first_search(four);   System.out.println();
//		System.out.print("display      : "); BinaryTree.display(  four);            System.out.println();
		System.out.print("height       : "); System.out.println(BinaryTree.height(four));
//
//		insert_bst(20);
//		insert_bst(10);
//		insert_bst(26);
//		insert_bst(14);
//		insert_bst(13);
//		insert_bst(5);
//		display(root);
//		System.out.println();
//		
//		// その他のメソッドのテスト
//		if (search_bst(14)) 
//		    System.out.println("Found!");
//		else
//		    System.out.println("Not found!");
//		if (search_bst(7)) 
//		    System.out.println("Found!");
//		else
//		    System.out.println("Not found!");
//		
//		System.out.println("最小値："+min_bst());
//		
//		// 発展課題のテスト
//		delete_bst(10);
//		display(root);
//		System.out.println();
	}
}
