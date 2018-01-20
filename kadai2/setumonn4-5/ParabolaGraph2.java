// ファイルParabolaGraph2.java
class ParabolaGraph2 extends ParabolaGraph {
	void drawLine(int x, int y) {
		System.out.println(x + " " + y);
	}
	public static void main(String[] args) {
		(new ParabolaGraph2()).drawAll();
	}
}
