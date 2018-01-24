class CircleShape extends Shape {
    double x0, y0, r;  // 中心座標(x0, y0), 半径r
    CircleShape(double x0, double y0, double r) {
	this.x0 = x0;
	this.y0 = y0;
	this.r = r;
    }
    boolean inside(double x, double y) {
	return  (x-x0)*(x-x0)+(y-y0)*(y-y0)<=r*r;
    }
}

