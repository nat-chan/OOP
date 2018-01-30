//2つの数m, nを入力すると，m/nの値を出力するプログラム(クラス名はFraction)を作りなさい．
//
//m/nが整数になる場合は，単にその整数を出力する．
//m/nが有限桁の小数で表わされる場合には，その小数のすべての桁を正確に出力する．
//m/nが循環小数で表わされる場合には，循環し終える部分までを出力し，次の行で循環する範囲を^で示す．
//たとえ「整数部も含めて循環している」とみなせる場合であっても，循環を示す印は，小数点以下の範囲のみにつけることにする(以下の100/7の例参照)．
//
//Fraction.javaを提出しなさい．
//
//ヒント: 筆算の手順で割り算を進めていき，同じ余りの値がまた出てきたら循環したことがわかる．
//
//実行例:
//(太字が入力) ``
//
//$ java Fraction 100 4
//100/4 = 25
//$ java Fraction 10 4
//10/4 = 2.5
//$ java Fraction 100 3
//100/3 = 33.3
//           ^
//$ java Fraction 100 7
//100/7 = 14.285714
//           ^^^^^^
//$ java Fraction 1 7
//1/7 = 0.142857
//        ^^^^^^
//$ java Fraction 1 170
//1/170 = 0.00588235294117647
//           ^^^^^^^^^^^^^^^^
//$ java Fraction 1 379721
//(略)
//

//import java.Math.*;
//import java.lang.Math;

class Fraction{
	//aとbの最大公約数を返す
	public static int GCD(int a, int b){return b==0 ? a : GCD(b, a%b);}

	//nをdで割り切れなくまで再帰的に割った値を返す
	public static int div_rec(int d, int n){return n%d==0 ? div_rec(d, n/d) : n;}

	public static void main(String[] args){
		int m = Integer.parseInt(args[0]);
		int n = Integer.parseInt(args[1]);
		int gcd = GCD(m, n);
		m = m/gcd; // m/nを既約分数にする
		n = n/gcd;
		if(n == 1){// m/nが整数の時
			System.out.println(m);
		}else if(div_rec(2, div_rec(5, n)) == 1){ // m/nが有限小数の時
			System.out.println((float)m/(float)n);
		}else{
		}
	}
}
