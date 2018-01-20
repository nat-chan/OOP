class Fun2 extends Fun {
	int f(int x) {
		return x*x;
	}

	void print() {
		for (int i = 0; i < 5; i++) {
			System.out.println(f(i));
		}
	}
}
