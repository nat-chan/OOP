import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Dot{
	public static String edges(Node n){
		String res = "";
		if(n.left != null)  res += edges(n.left)  + n.val + "->" + n.left.val  + " ";
		if(n.right != null) res += edges(n.right) + n.val + "->" + n.right.val + " ";
		return res;
	}
	public static void toSixel(Node n){
		try {
			String[] cmd = {"/bin/sh","-c","echo 'digraph{"+edges(n)+"}'|dot -Tpng|img2sixel"};
			Process p = Runtime.getRuntime().exec(cmd);
			InputStream is = p.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			System.out.println(br.readLine());
		} catch (IOException ex) {
			System.out.println("graphvizとlibsixelをインストールしてください");
			System.exit(1);
		}
	}
}
