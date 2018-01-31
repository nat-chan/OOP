import java.util.*;

public class Fib2
{
    static int i;
    // フィボナッチの計結果を保存していくためのマップ
    // <n, fib(n)>
    static Map<Integer, Integer> map = new HashMap<Integer,Integer>(); 
    
    /**
    * フィボナッチを計算するメソッド
    * @param n
    * @return fib(n)
    **/    
    static int fib(int n) {
        if (n < 2) {
            return 1; // fib(1), fib(0) ならば 1 を返す
        } else if (map.containsKey(n)){
            return map.get(n); // fib(n) が既に計算済みならばそれを返す
        }
        else {
            i = (fib(n-1) + fib(n - 2)) % 100000; // 下5桁のみを取り出す
            map.put(n, i); // 取り出した計算結果を保存する
            return i; // 計算結果を返す
        }
    }
    /**
    * フィボナッチの計算時間を計測するためのメソッド
    * @param args[0] nの数
    **/
    public static void main(String args[])
    {
        long start = System.nanoTime(); // 現在時間を保存
        int n = Integer.parseInt(args[0]);
        System.out.println("fib(" + n + ") = " + fib(n)); // fib(n) を計算
        System.out.println((System.nanoTime() - start) / 1000000 + "ms"); // 計算終了時の時間と現在時間を減算する
    }
}
