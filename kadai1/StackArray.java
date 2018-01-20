// StackArray.java
public class StackArray<T>{
	int rear;
	Object[] stack;

	// 指定された長さの配列を生成するコンストラクタ
	StackArray(int len){
		rear = 0;
		stack = new Object[len];
	}

	// データのpush
	void push(T val){
		if(rear == stack.length) throw new RuntimeException("Stack over flow.");
		stack[rear] = val;
		rear++;
	}

	// データのpop
	T pop(){
		if(rear == 0) throw new RuntimeException("Stack under flow.");
		rear--;
		return (T)stack[rear];
	}
	
	// キューの要素の表示
	void display(){
		for(int i=0; i<rear; i++) System.out.print(stack[i]+" ");
		System.out.println();
	}
	
	// main メソッド
	public static void main(String[] args){
		StackArray stack = new StackArray<Integer>(10);
		stack.push(1);
		stack.display();
		stack.push(2);
		stack.display();
		stack.push(3);
		stack.display();
		System.out.println(stack.pop());
		stack.display();
		stack.push(4);
		stack.display();
	}
}
