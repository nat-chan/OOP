public abstract class Shape {
	// 抽象メソッド inside(x, y)
	// (x, y)が図形の内側ならtrueを，そうでなければfalseを返すものとする．
	// ちょうど境界線上は内側とみなす約束とする．
	abstract boolean inside(double x, double y);

	// draw(x1, x2, xstep,  y1, y2, ystep)
	// x座標: x1からx2までxstep刻み
	// y座標: y1からy2までystep刻み
	// (x, y)が図形の内側なら*を，そうでなければ空白を書く．
	void draw(double x1, double x2, double xstep,
	          double y1, double y2, double ystep) {
	for (double y = y2; y >= y1; y -= ystep) {
		for (double x = x1; x <= x2; x += xstep) {
			if (inside(x, y)) {
				System.out.print("*");
			} else {
				System.out.print(" ");
			}
		}
		System.out.println();
		}
	}
}
