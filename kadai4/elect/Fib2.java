import java.util.*;

public class Fib2
{
    static int i;
    static Map<Integer, Integer> map = new HashMap<Integer,Integer>();
    static int fib(int n) {
        if (n < 2) {
            return 1;
        } else if (map.containsKey(n)){
            return map.get(n);
        }
        else {
            i = (fib(n-1) + fib(n - 2)) % 100000;
            map.put(n, i);
            return i % 100000;
        }
    }

    public static void main(String args[])
    {
        long start = System.nanoTime();
        int n = Integer.parseInt(args[0]);
        System.out.println("fib(" + n + ") = " + fib(n));
        System.out.println((System.nanoTime() - start) / 1000000 + "ms");
    }
}
