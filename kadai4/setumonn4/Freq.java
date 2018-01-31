import java.util.*;
import java.io.*;

public class Freq
{
    static Map<String, Integer> map = new TreeMap<String, Integer>();
    /**
    * ファイルを読み込むためのメソッド
    * @param filename 読み込むファイル名
    * @return ファイルのScanner　読み込めなければ処理を終了
    **/
    static Scanner fileScanner(String filename) {
        Scanner value = null;
        try {
            value = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            System.err.println("File:" + filename + " does not exist.");
            System.exit(1);
        }
        return value; // ファイルのScanner
    }
    /**
    * 単語の出現頻度を求めるメソッド
    * @param args[0] ファイル名
    * 
    **/
    public static void main(String args[])
    {
        Scanner input = fileScanner (args [0]);
        String s;
        input.useDelimiter ("[^a-zA-Z]+"); //　デリミターの設定
        while (input.hasNext ()){
            if (map.containsKey (s = ((input.next ()).toLowerCase ()))) { 
                map.put (s,(1 + (map.get (s)))); //　既に登録されているならば、map の value を 1 加算する
            }
            else {
                map.put (s,1);　// ファイル内の文字列をすべて小文字にして保存
            }
        }
        for (String k : map.keySet()){
            System.out.println(map.get (k) + ":" + k);
        }
    }
}
