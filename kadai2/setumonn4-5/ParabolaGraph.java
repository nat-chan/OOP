// ファイルParabolaGraph.java
class ParabolaGraph extends GraphTemplate {
	public static void main(String[] args) {
		(new ParabolaGraph()).drawAll();
	}
	int fn(int x) {
		return x*x;
	}
	int start() {
		return -7;
	}
	int end() {
		return 7;
	}
}
