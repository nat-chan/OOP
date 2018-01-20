import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/* require:
 *     unix (というより/bin/sh)
 *
 *     dotコマンド (計算機室には元々入っている)
 *         brew install graphviz
 *         apt install graphviz
 *         etc...
 *
 *     iTerm2 (計算機室のデフォのターミナル)
 *
 *     ssh越しにはsixel対応ターミナル
 *         Rlogin, mlterm, xterm
 *
 * @author https://github.com/nat-chan
 * @see    http://qiita.com/arakiken/items/3e4bc9a6e43af0198e46
 */
public class Graph{

	// 計算機室のMacでiTerm2に表示させるときはこれを使う
	public static void display_iTerm2(Node n){
		plot(n, "/home/ugrad/16/s1611353/bin/imgcat");
	}

	// Rlogin,mlterm,xtermのいずれかでssh越しに表示するときはこれを使う
	public static void display_sixel(Node n){
		plot(n, "/home/ugrad/16/s1611353/bin/img2sixel");
	}

	// Node<String> nを表現するDOT言語のbodyを返す
	public static String edges(Node n){
		String res = "";
		if(n.left != null)  res += edges(n.left)  + n.val + "->" + n.left.val  + " ";
		if(n.right != null) res += edges(n.right) + n.val + "->" + n.right.val + " ";
		return res;
	}

	/* パイプを解釈するために/bin/shに引数でコマンドを渡している。よってunixでしか動かない。
	 * graphvizをインストールすればdotコマンドは入る
	 * path : pngバイナリをパイプで受け取り処理できるコマンド実体のあるパス
	 */
	public static void plot(Node n, String path){
		try {
			String[] cmd = {"/bin/sh","-c","echo 'digraph{"+edges(n)+"}'|dot -Tpng|"+path};
			Process p = Runtime.getRuntime().exec(cmd);
			InputStream is = p.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			System.out.println(br.readLine());
		} catch (IOException ex) {
			System.out.println("requireを読んでください?");
			System.exit(1);
		}
	}
}
