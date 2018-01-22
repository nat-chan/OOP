public class RatioTest2 {
    static Ratio zero = new Ratio(0, 1);
    static Ratio one = new Ratio(1, 1);
    static Ratio two = new Ratio(2, 1);
    static Ratio fact(int n) {
        // n!をRatio型で返す
        Ratio r = one;
        for (int i = n; i > 0; i--) {
            r = r.multiply(new Ratio(i, 1));
        }
        return r;
    }
    static void test1() {
        Ratio e = zero;
        for (int i = 0; i < 14; i++) {
            // e = e + ???
            e = e.add(one.divide(fact(i)));
        }
        System.out.println(e.doubleValue());
    }
    static void test2() {
        Ratio a = two;
        Ratio d = one;
        for (int n = 1; n < 14; n++) {
            // d = d * n / (2*n + 1)
            d = (d.multiply(n)).divide(2 * n + 1);
            // a = a + 2*d
            a = a.add(d.multiply(2)) ;
        }
        System.out.println(a.doubleValue());
    }
    static void test3() {
        Ratio x = two;
        for (int i = 0; i < 22; i++) {
            // x = 2 + ???
            x = two.add(one.divide(x)) ;
        }
        System.out.println(x.subtract(one).doubleValue());
    }
    public static void main(String[] args) {
        test1();
        System.out.println("-------------------------");
        test2();
        System.out.println("-------------------------");
        test3();
    }
}
