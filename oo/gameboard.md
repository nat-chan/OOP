[]()

オブジェクト指向プログラミング実習 個人課題1
============================================

**(注意: 個人課題では，他の人の作った結果を利用しないこと!)**

思考ゲーム記述用フレームワーク
------------------------------

ある範囲の対戦型の思考ゲームを簡便に作れる仕組みを用意しよう．

### 三目並べ

最初のゲームの例としては，3x3のマスの中で，２人のプレイヤーが交互にひとつずつ○と☓を書いていき，３つ並べば勝ちというゲーム(三目並べ;
Tic Tac Toe)を考える．
(３つ並ばないまま，9個のマスが全部埋まったら引き分けである．）

#### 実行例:

(**太字**が入力) ``

``` {.interaction}
$ java TicTacToe
(0)|(1)|(2)
---+---+---
(3)|(4)|(5)
---+---+---
(6)|(7)|(8)
プレイヤー1の手番
選択してください: 1

プレイヤー1は1を選びました
(0)| O |(2)
---+---+---
(3)|(4)|(5)
---+---+---
(6)|(7)|(8)
プレイヤー2の手番

プレイヤー2は4を選びました
(0)| O |(2)
---+---+---
(3)| X |(5)
---+---+---
(6)|(7)|(8)
プレイヤー1の手番
選択してください: 2

プレイヤー1は2を選びました
(0)| O | O 
---+---+---
(3)| X |(5)
---+---+---
(6)|(7)|(8)
プレイヤー2の手番

プレイヤー2は0を選びました
 X | O | O 
---+---+---
(3)| X |(5)
---+---+---
(6)|(7)|(8)
プレイヤー1の手番
選択してください: 8

プレイヤー1は8を選びました
 X | O | O 
---+---+---
(3)| X |(5)
---+---+---
(6)|(7)| O 
プレイヤー2の手番

プレイヤー2は5を選びました
 X | O | O 
---+---+---
(3)| X | X 
---+---+---
(6)|(7)| O 
プレイヤー1の手番
選択してください: 3

プレイヤー1は3を選びました
 X | O | O 
---+---+---
 O | X | X 
---+---+---
(6)|(7)| O 
プレイヤー2の手番

プレイヤー2は6を選びました
 X | O | O 
---+---+---
 O | X | X 
---+---+---
 X |(7)| O 
プレイヤー1の手番
選択してください: 7

プレイヤー1は7を選びました
 X | O | O 
---+---+---
 O | X | X 
---+---+---
 X | O | O 
ゲーム終了
引き分け
$ 
```

実際に「`java -cp ~maeda/oo TicTacToe`」とすると，試してみることができる．

コマンドライン引数として「`--p1 プレイヤーの種類`」または
「`--p2 プレイヤーの種類`」を与えることができる．
プレイヤーの種類としては「simple」(人間)または「general」(コンピュータ)
を指定できる．

### PlayerインターフェースとBoardインターフェース

このゲームだけでなく，いろいろなゲームが記述できるように，まずは
一般的な「ゲームプレイヤー」と「ゲーム盤」を表すインターフェースを
以下のように定義する． ``

``` {.program}
interface Player {
    enum ID {                   // プレイヤーを区別するための定数を定義する
        NONE,                   // 「どちらのプレイヤーでもない」ことを表す
        P1,                     // プレイヤー1を表す
        P2                      // プレイヤー2を表す
    }
    int move(Board board);      // Boardの可能な手からひとつ選ぶ
    String getName();           // プレイヤー名を返す
}
```

``

``` {.program}
import java.util.List;
import java.io.PrintStream;

// ゲーム盤を表すインターフェース
interface Board {
    Player.ID nextTurn();       // 次の手番のプレイヤー
    boolean isEndOfGame();      // ゲームが終了したならtrue
    Player.ID winner();         // 勝者のプレイヤーID．引き分けならNONE．
                                //  (ゲーム終了していなければ未定義)．
    List<Integer> legalMoves(); // 打てる手のリスト
    Object boardState();        // 盤の状態を返す(内容はゲームに依存する)
    void put(int m);            // 手を打つ
    void unput();               // putの効果を取り消す．何段階でも取り消せる．
    void print(PrintStream out); // 盤の状態，可能な手をプリントする．
}
```

Playerの中で使われているenumについては，プログラミング入門Aの教科書
の続編「Java言語プログラミングレッスン下」のp.363に解説がある．ここでは，
以下の点さえ知っていれば十分である．

-   Player.ID型の値として３つの定数 NONE, P1, P2 が定義されている
-   Player.ID型の変数には，この値のいずれかが入る
-   Player.ID型の変数の値と，これらの定数を==演算子で比較したり，
    switch文の分岐に用いたりできる．

### Playerの実装例

上記のインターフェースを用いた，
単純なプレイヤーの実装が以下のSimplePlayerである．
これは単に，どの手にするか人間に尋ねるようになっている．
(先ほどの実行例では，プレイヤー1がこのSimplePlayerのインスタンスであった．)
``

``` {.program}
import java.util.*;

public class SimplePlayer implements Player {
    String playerName;
    Scanner scanner;
    SimplePlayer(String name) {
        playerName = name;
        scanner = new Scanner(System.in);
    }
    public String getName() { return playerName; }
    public int move(Board board) {
        List<Integer> moves = board.legalMoves();
        int selection;
        while (true) {
            System.out.print("選択してください: ");
            String str = scanner.next();
            try {
                selection = Integer.parseInt(str);
                if (moves.contains(selection)) {
                    break;
                }
            } catch (NumberFormatException e) {
            }
            System.out.println(str + "は選べません．");
        }
        return selection;
    }
}
```

Boardが返す，「いま可能な手(合法手)のリスト」にない手や，整数でない値を入力したら，もう一度入力を促すようになっている．

また，以下はBoardインターフェースを持つゲームについて，ゲーム特有の知識を使わず，
ゲーム終了までのランダムシミュレーションを繰り返し，(勝利確率−敗北確率)の高い手を
選ぶプレイヤーである．
(先ほどの実行例では，プレイヤー2がこのGeneralPlayerのインスタンスであった．)
``

``` {.program}
import java.util.*;

// モンテカルロ法を用い，Boardインターフェースを守っているゲーム盤なら何でも，ある程度
// 「よい」手を選ぶ，何でも屋ゲームプレイヤー
public class GeneralPlayer implements Player {
    String name;
    Random random;
    public GeneralPlayer(String name) {
        this.name = name;
        random = new Random();
    }
    public String getName() { return name; }
    public int move(Board board) {
        List<Integer> moves = board.legalMoves();
        int n = moves.size();
        Player.ID myID = board.nextTurn();
        long[] win = new long[n];
        long[] lost = new long[n];
        long start = System.nanoTime();
        while (System.nanoTime() - start < 1000000000L) { // 1秒間シミュレーションを繰り返す
            int i = 0;
            for (int m : moves) { // すべての可能な手について1回ずつシミュレーションする
                board.put(m);
                Player.ID winner = tryOne(board); // ゲーム終了までランダムな手を選ぶ
                if (winner == myID) {             // 自分の勝ちだった
                    win[i]++;
                } else if (winner != Player.ID.NONE) {
                    lost[i]++;  // 勝ちでも引き分けでもなければ負け
                }
                board.unput();
                i++;
            }
        }
        // (勝利数 - 敗北数) がもっとも大きい手を選ぶ
        int max = 0;
        for (int i = 0; i < n; i++) {
            if (win[max] - lost[max] < win[i] - lost[i]) {
                max = i;
            }
        }
        return moves.get(max);
    }
    private Player.ID tryOne(Board board) {
        if (board.isEndOfGame()) {
            return board.winner(); // 最終的な勝者を返す
        }
        List<Integer> m = board.legalMoves();
        board.put(m.get(random.nextInt(m.size()))); // ランダムな手を選ぶ
        Player.ID value = tryOne(board);            // 再帰的に次の局面へ
        board.unput();                              // 元の状態を復元
        return value;                               // このシミュレーションの結果の勝者
    }
}
```

### Boardの実装例

次に，ゲーム盤の側のプログラムについて説明する．
以下の2つが，三目並べのルールを表すゲーム盤の実装である．
(他のゲームでも使えそうな一部の機能は，AbstractBoardとして切り出してある．)
``

``` {.program}
import java.util.*;

// Boardに必要な機能のうち，よく使うものをまとめたクラス
public abstract class AbstractBoard implements Board {
    Player.ID nextTurn;
    Deque<Integer> history;     // 打った手をプッシュして記録する
    public AbstractBoard() {
        history = new LinkedList<Integer>();
        nextTurn = Player.ID.P1;
    }
    void flipTurn() { nextTurn = opposite(); } // 手番を入れ替える
    Player.ID opposite() {                     // 「今の手番のプレイヤー」の相手のプレイヤー
        return nextTurn == Player.ID.P1 ? Player.ID.P2 : Player.ID.P1;
    }
    public Player.ID nextTurn() { return nextTurn; } // 今の手番のプレイヤー
}
```

``

``` {.program}
import java.util.*;
import java.io.PrintStream;

public class TicTacToeBoard extends AbstractBoard {
    Player.ID[] board; // 3x3 の２次元配列でなく，(x,y)座標をx+3yに配置した１次元配列を使う
    TicTacToeBoard() {
        board = new Player.ID[3 * 3];
        for (int i = 0; i < 3 * 3; i++) {
            board[i] = Player.ID.NONE;
        }
    }
    // posから，vector方向にプレイヤーpのコマが３つ並んでいればtrue
    boolean three(int pos, int vector, Player.ID p) {
        for (int i = 0; i < 3; i++) {
            if (board[pos] != p) {
                return false;
            }
            pos += vector;
        }
        return true;
    }
    boolean testWin(Player.ID p) {
        return 
            // 縦 … +3すると，y座標を1増やすことになる
            three(0, 3, p) || three(1, 3, p) || three(2, 3, p) ||
            // 横 … +1すると，x座標を1増やすことになる
            three(0, 1, p) || three(3, 1, p) || three(6, 1, p) ||
            // 斜め … +3 + +1 と， -3 + +1
            three(0, 3 + 1, p) || three(6, -2, p);
    }
    boolean didP1Win() { return testWin(Player.ID.P1); } // P1の勝ちか？
    boolean didP2Win() { return testWin(Player.ID.P2); } // P2の勝ちか？
    public boolean isEndOfGame() { 
        return didP1Win() || didP2Win() || history.size() == 9;
    }
    public Player.ID winner() {
        if (didP1Win()) {
            return Player.ID.P1;
        } else if (didP2Win()) {
            return Player.ID.P2;
        } else {
            return Player.ID.NONE;
        }
    }
    public List<Integer> legalMoves() { // 空いている盤の位置のリストを返す
        List<Integer> result = new LinkedList<Integer>();
        for (int i = 0; i < 3 * 3; i++) {
            if (board[i] == Player.ID.NONE) {
                result.add(i);
            }
        }
        return result;
    }
    public Object boardState() { return board; }
    public void put(int m) {
        history.push(m);
        board[m] = nextTurn;
        flipTurn();             // 手番を反転する
    }
    public void unput() {
        int m = history.pop();
        board[m] = Player.ID.NONE;
        flipTurn();             // 手番を反転する
    }
    public void print(PrintStream out) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                int num = x + y * 3;
                switch (board[num]) {
                case P1: out.print(" O "); break;
                case P2: out.print(" X "); break;
                case NONE: out.print("(" + num + ")"); break;
                }
                if (x < 2) { 
                    out.print("|");
                }
            }
            out.println();
            if (y < 2) {
                out.println("---+---+---");
            }
        }
    }
}
```

### メインプログラム

最後に，これらを組み合わせて実行させるメインプログラムが以下の2つである．
``

``` {.program}
import java.util.*;

public class SimpleGame {
    // typeに応じてプレイヤーを作って返す．オーバーライドすればプレイヤーの種類を増やせる
    Player makePlayer(String type, String name) {
        if (type.equals("general")) {
            return new GeneralPlayer(name);
        } else {
            return new SimplePlayer(name);
        }
    }
    Map<String,String> map;
    public SimpleGame() {
        map = new HashMap<String,String>();
        map.put("--p1", "simple");
        map.put("--p2", "general");
    }
    void parseArgs(String[] args) {
        for (int i = 0; i < args.length; i += 2) {
            map.put(args[i], args[i + 1]);
        }
    }
    public SimpleGame(String[] args) {
        this();                 // 引数なしのコンストラクタを呼びだす
        parseArgs(args);
    }
    // Boardと2つのプレイヤーを使う，ゲームのメインプログラム
    void play(Board board) {
        Player p1 = makePlayer(map.get("--p1"), "プレイヤー1");
        Player p2 = makePlayer(map.get("--p2"), "プレイヤー2");
        while (! board.isEndOfGame()) {
            board.print(System.out);
            Player.ID turn = board.nextTurn();
            Player nextPlayer = (turn == Player.ID.P1 ? p1 : p2);
            System.out.println(nextPlayer.getName() + "の手番");
            int m = nextPlayer.move(board);
            System.out.println();
            System.out.println(nextPlayer.getName() + "は" + m + "を選びました");
            board.put(m);
        }
        board.print(System.out);
        System.out.println("ゲーム終了");
        Player.ID winner = board.winner();
        if (winner != Player.ID.NONE) {
            System.out.println((winner == Player.ID.P1 ? p1 : p2).getName() + "の勝ち．");
        } else {
            System.out.println("引き分け");
        }
    }
}
```

``

``` {.program}
public class TicTacToe {
    public static void main(String[] args) {
        (new SimpleGame(args)).play(new TicTacToeBoard());
    }
}
```

------------------------------------------------------------------------

設問(基本レベル): 石取りゲーム(ニム)
------------------------------------

ここで考える石取りゲーム(ニム)のルールは，以下の通りである．

-   最初，盤上にはn個の石がある．
-   2人のプレイヤーが交互に石を取り合う．
    自分の手番では1個〜3個の石を取ることができる．
-   最後の石を**取ったもの**を勝者とする．

#### 実行例:

(**太字**が入力) ``

``` {.interaction}
$ java Nim --p2 simple
30個残っています．
1〜3個取れます．
プレイヤー1の手番
選択してください: 3

プレイヤー1は3を選びました
27個残っています．
1〜3個取れます．
プレイヤー2の手番

プレイヤー2は3を選びました
24個残っています．
1〜3個取れます．
プレイヤー1の手番
選択してください: 2

(略)

プレイヤー1は3を選びました
4個残っています．
1〜3個取れます．
プレイヤー2の手番

プレイヤー2は2を選びました
2個残っています．
1〜2個取れます．
プレイヤー1の手番
選択してください: 2

プレイヤー1は2を選びました
0個残っています．
1〜0個取れます．
ゲーム終了
プレイヤー1の勝ち．
$ 
```

(実際に「java -cp \~maeda/oo Nim」とすると，試してみることができる．)

今まで説明したクラスやインターフェースを利用し，
この石取りゲームのゲーム盤を実現するクラスNimBoardを作りなさい．
また，石取りゲームにおいて，(勝てる可能性があるときは)絶対に勝つプレイヤーNimPlayerを作りなさい．
(勝てる可能性は，石の数と先手後手で決まる．石の数n=30なら先手必勝である．)
NimPlayerは、Boardインターフェースに含まれないNimBoard特有の
情報を使う必要が有ることに注意。move()では、受け取ったboardをNimBoardにキャストする
必要がある。

NimBoardを用いるメインプログラムとしては，以下を用いなさい． ``

``` {.program}
public class Nim extends SimpleGame {
    Nim(String[] args) {
        super();
        map.put("--n", "30");   // 石の数のデフォルト値
        map.put("--p1", "nim"); // プレイヤー１はデフォルトでNimPlayer
        parseArgs(args);
    }
    Player makePlayer(String type, String name) {
        if (type.equals("nim")) {
            return new NimPlayer(name);
        } else {
            return super.makePlayer(type, name);
        }
    }
    void play() {
        play(new NimBoard(Integer.parseInt(map.get("--n"))));
    }
    public static void main(String[] args) {
        (new Nim(args)).play();
    }
}
```

**NimBoard.javaとNimPlayer.javaを提出しなさい．**

-   それ以外のクラス(Player, Board, SimplePlayer, GeneralPlayer,
    SimpleGame, Nim) を変更してはいけない．
-   実行の途中でエラーになったり，先手(あるいは後手)必勝のときに先手(後手)で
    負けるようでは解けたとみなさない．

すべてのチーム課題に加えて，基本レベルまで解答すれば合格とする．

------------------------------------------------------------------------

設問(発展レベル・高度レベル)
----------------------------

以下のいずれかのプログラムを作りなさい．

1.  (発展レベル)
    三目並べ(TicTacToe)において，絶対に負けないプレイヤーTTTMaster．
    やはり，エラーになったり負けたりするようでは正解といえない．
2.  (発展レベル)
    SimplePlayerやGeneralPlayerがきちんと動く，8x8の盤を用いた 五目並べのゲームを表すBoardであるGomokuBoard．(正式な連珠のルールを実現しなくてもよい．先手後手の区別なく，５つ並べば勝ち．)
    三目並べのプログラムを変更して利用するとよい．
3.  (高度レベル) その他，SimplePlayerやGeneralPlayerがきちんと動く，
    ある程度複雑なゲームのBoard．

1については，Nimクラスを参考に，「simple」「general」に加えて自分の作ったプレイヤーを
指定できるようにしたSimpleGameのサブクラスを作って試しなさい． (ヒント:
ゲーム固有の情報boardState()を利用する．)

3については，ゲームの難しさによって評価が異なる．例えば，以下のHamlet，オセロ，はさみ将棋の程度のゲームを実装すれば高く評価する．

いずれも，動作の正しさ(エラーにならない・ルール通り動いている)に加えて，
ゲーム盤の見やすさやプレイヤーの強さも考慮する(GeneralPlayerの強さはBoardの各メソッドのスピードに依存する)．

**NimBoard.java,
NimPlayer.javaに加えて，作成したソースファイルを提出しなさい．**

------------------------------------------------------------------------

### (参考)Hamletゲーム

Hamletは，垂直に立った盤を使い，下から順に石を積み上げていき，四目以上並べたプレイヤーが勝者となるゲームである．

#### 実行例:

(**太字**が入力) ``

``` {.interaction}
$ java Hamlet
|   |   |   |   |   |   |
+---+---+---+---+---+---+
|   |   |   |   |   |   |
+---+---+---+---+---+---+
|   |   |   |   |   |   |
+---+---+---+---+---+---+
|   |   |   |   |   |   |
+---+---+---+---+---+---+
|   |   |   |   |   |   |
+---+---+---+---+---+---+
|(0)|(1)|(2)|(3)|(4)|(5)|
+---+---+---+---+---+---+
プレイヤー1の手番
選択してください: 2

プレイヤー1は2を選びました
|   |   |   |   |   |   |
+---+---+---+---+---+---+
|   |   |   |   |   |   |
+---+---+---+---+---+---+
|   |   |   |   |   |   |
+---+---+---+---+---+---+
|   |   |   |   |   |   |
+---+---+---+---+---+---+
|   |   |(2)|   |   |   |
+---+---+---+---+---+---+
|(0)|(1)| O |(3)|(4)|(5)|
+---+---+---+---+---+---+
プレイヤー2の手番

プレイヤー2は3を選びました
|   |   |   |   |   |   |
+---+---+---+---+---+---+
|   |   |   |   |   |   |
+---+---+---+---+---+---+
|   |   |   |   |   |   |
+---+---+---+---+---+---+
|   |   |   |   |   |   |
+---+---+---+---+---+---+
|   |   |(2)|(3)|   |   |
+---+---+---+---+---+---+
|(0)|(1)| O | X |(4)|(5)|
+---+---+---+---+---+---+
プレイヤー1の手番
選択してください: 2

プレイヤー1は2を選びました
|   |   |   |   |   |   |
+---+---+---+---+---+---+
|   |   |   |   |   |   |
+---+---+---+---+---+---+
|   |   |   |   |   |   |
+---+---+---+---+---+---+
|   |   |(2)|   |   |   |
+---+---+---+---+---+---+
|   |   | O |(3)|   |   |
+---+---+---+---+---+---+
|(0)|(1)| O | X |(4)|(5)|
+---+---+---+---+---+---+
プレイヤー2の手番

(略)

プレイヤー2は5を選びました
|   |   |(2)|   |   |   |
+---+---+---+---+---+---+
|   |   | O |(3)|   |   |
+---+---+---+---+---+---+
|   |   | X | O |   |(5)|
+---+---+---+---+---+---+
|   |   | X | X |   | X |
+---+---+---+---+---+---+
|   |   | O | O |   | O |
+---+---+---+---+---+---+
|(0)|(1)| O | X |(4)| X |
+---+---+---+---+---+---+
プレイヤー1の手番
選択してください: 4

プレイヤー1は4を選びました
|   |   |(2)|   |   |   |
+---+---+---+---+---+---+
|   |   | O |(3)|   |   |
+---+---+---+---+---+---+
|   |   | X | O |   |(5)|
+---+---+---+---+---+---+
|   |   | X | X |   | X |
+---+---+---+---+---+---+
|   |   | O | O |(4)| O |
+---+---+---+---+---+---+
|(0)|(1)| O | X | O | X |
+---+---+---+---+---+---+
プレイヤー2の手番

プレイヤー2は4を選びました
|   |   |(2)|   |   |   |
+---+---+---+---+---+---+
|   |   | O |(3)|   |   |
+---+---+---+---+---+---+
|   |   | X | O |   |(5)|
+---+---+---+---+---+---+
|   |   | X | X |(4)| X |
+---+---+---+---+---+---+
|   |   | O | O | X | O |
+---+---+---+---+---+---+
|(0)|(1)| O | X | O | X |
+---+---+---+---+---+---+
ゲーム終了
プレイヤー2の勝ち．
$
```

(実際に「java -cp \~maeda/oo Hamlet」とすると，試してみることができる．)

このゲームは，手の選択肢がたかだか6つしかなく，
コンピュータに扱わせるのが比較的簡単である．

このプログラムのメインプログラムは以下のようになっている． ``

``` {.program}
public class Hamlet {
    public static void main(String[] args) {
        (new SimpleGame(args)).play(new HamletBoard());
    }
}
```

\
\
\
\
\
\
\
\
\
\

