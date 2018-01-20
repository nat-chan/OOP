// QueueArray.java
public class QueueArray<T> {
	int length, front, rear;
	Object[] queue;

	// 指定された長さの配列を生成するコンストラクタ
	QueueArray(int len) {
		front = 0; rear = 0; length = 0;
		queue = new Object[len];
	}
	
	// データのエンキュー
	void enqueue(T val) {
		if(length == queue.length){
			throw new RuntimeException("The queue is full.");
		}else{
			queue[rear] = val;
			length++;
			rear++; if(rear >= queue.length) rear -= queue.length;
		}
	}
	
	void enqueue(T values[]) {
		if(values.length > queue.length - length){
			throw new RuntimeException("The queue is too short.");
		}else{
			for(int i=0; i<values.length; i++){
				enqueue(values[i]);
			}
		}
	}

	// データのデキュー
	T dequeue() {
		if(length == 0){
			throw new RuntimeException("The queue is empty.");
		}else{
			T data = (T)queue[front];
			length--;
			front++; if(front >= queue.length) front -= queue.length;
			return data;
		}
	}
	
	// キューの要素の表示
	void display() {
		int i=front;
		while(i != rear){
			System.out.print(queue[i]+" ");
			i++; if(i>=queue.length) i-= queue.length;
		}
		System.out.println();
	}
	
	// main メソッド
	public static void main(String[] args) {
		QueueArray queue = new QueueArray<Integer>(10);
		queue.enqueue(new Integer[]{1,2,3,4,5});
		queue.display();
		System.out.println(queue.dequeue());
		queue.display();
		System.out.println(queue.dequeue());
		queue.display();
		System.out.println(queue.dequeue());
		queue.display();
		queue.enqueue(new Integer[]{6,7,8,9,10});
		queue.display();
		System.out.println(queue.dequeue());
		queue.display();
		System.out.println(queue.dequeue());
		queue.display();
		System.out.println(queue.dequeue());
		queue.display();
	}
}
