// ファイルGraphTemplate.java
class GraphTemplate {
	void drawLine(int x, int y) {
		System.out.print(x + ":");
		for (int i = 0; i < y; i++) {
			System.out.print("*");
		}
		System.out.println();
	}
	void drawAll() {
		for (int x = start(); x <= end(); x++) {
			drawLine(x, fn(x));
		}
	}
	public static void main(String[] args) {
		(new GraphTemplate()).drawAll();
	}
	int fn(int x) {
		return 10;
	}
	int start() {
		return 1;
	}
	int end() {
		return 5;
	}
}
