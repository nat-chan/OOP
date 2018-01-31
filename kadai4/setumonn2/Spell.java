import java.util.*;
import java.io.*;

public class Spell{

    /**
     * ファイルの読み込みを行うメソッド
     * @param filename ファイル名
     * @return ファイルを開いた先のScannerクラスのインスタンス エラーならば処理を終了する
    **/
    static Scanner fileScanner(String filename) { // ファイルの読み込みを行うメソッド
        Scanner value = null; // 初期化
        try {
            value = new Scanner(new File(filename)); // ファイルの見込み
        } catch (FileNotFoundException e) {
            System.err.println("File:" + filename + " does not exist.");
            System.exit(1);
        }
        return value;
    }
    
    /**
    * HashSet に辞書を登録して、それを元にしてスキャニングを行っている
    * @param args[0] チェックを行うファイル名 
    **/
    public static void main(String args[]) {
        Scanner input = fileScanner("./dict"); // 辞書の読み込み
        Set<String> dict = new HashSet<String>();
        String s;
        while (input.hasNext()) {
            dict.add (s = ((input.next()).toLowerCase())); // 辞書内の文字をすべて小文字に変換して登録する
        }

        input = fileScanner(args [0]); 
        input.useDelimiter ("[^a-zA-Z]+"); // デリミターの設定

        while (input.hasNext ()) {
            s = (input.next ()).toLowerCase (); // 読み込んだ文字列をすべて小文字に変換する
            if (!(dict.contains(s))) { // 辞書に存在していなければ
                dict.add (s); // 辞書に追加する(同じものを二度出力しないようにするため)
                System.out.println(s); // 辞書になかったものを表示
            }
        }
    }
}
