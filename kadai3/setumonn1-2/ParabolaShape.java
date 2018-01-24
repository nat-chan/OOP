class ParabolaShape extends Shape {
    double a, b, c;  // a * x^2 + b * x + c =  y
    ParabolaShape(double a, double b, double c){
	this.a = a;
	this.b = b;
	this.c = c;
    }
    boolean inside(double x, double y) {
	return a*x*x+b*x+c>=y;
    }
}

// ファイル ParabolaTest.java
class ParabolaTest {
    public static void main(String[] args) {
        ParabolaShape p = new ParabolaShape(-1, 6, -5);
        p.draw(0.0, 6.0, 0.15,
               0.0, 5.0, 0.3);
    }
}
