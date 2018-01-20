[]()

オブジェクト指向プログラミング実習 個人課題3
============================================

**(注意: 個人課題では，他の人の作った結果を利用しないこと!)**

ツリー指向スクリプト言語
------------------------

以下のプログラムは，[課題５](stack-and-tree.html)のTreeCalc
と少し似ているが，トークン単位で読み込むのではなく，**式全体を読み込んでから
値を計算(eval)する**ところが違っている．入力する式の文法は，以下のようになっている．

-   カッコ( … )に３つの要素をくくって，式のノード(節点)を表す．
    ３つの要素は左から順に，演算子名，左の子，右の子である．
    (すなわち，式の木を**行きがけ順(preorder)**で表している．)
-   カッコを用いない整数や名前は葉を表す．
-   カッコは，空白（スペース，タブ，改行など）で区切る必要がある．

``

``` {.program}
import java.util.*;

public class TreeScript {
    abstract class Tree {
        abstract public Tree eval(); // 木の値を計算する
        abstract public Tree replace(String name, Tree r); // 木の中の名前nameをrに置き換える
    }

    class IntLeaf extends Tree {
        int value;
        IntLeaf(int value) { this.value = value; }
        public Tree eval() { return this; }
        public Tree replace(String name, Tree r) { return this; } // 置き換えは起きない
        public String toString() { return Integer.toString(value); }
    }
    class NameLeaf extends Tree {
        String name;
        NameLeaf(String name) { this.name = name; }
        public Tree eval() { return this; }
        public Tree replace(String name, Tree r) { 
            return name.equals(this.name) ? r : this; // 名前が同じなら置き換える
        }
        public String toString() { return name; }
    }
    class Node extends Tree {
        Op operator;
        Tree left, right;
        Node(Op operator, Tree left, Tree right) {
            this.operator = operator;
            this.left = left; this.right = right;
        }
        public Tree replace(String name, Tree r) {
            return operator.replace(this, left, right, name, r); // Op によって置き換えの仕方を変えることができる
        }
        public Tree eval() {
            return operator.calc(left, right);
        }
        public String toString() {
            return "( " + operator.opName() + " " + left.toString() + " " + right.toString() + " )";
        }
    }
    abstract class Op {
        abstract Tree calc(Tree left, Tree right);
        abstract String opName();
        Tree replace(Node original, Tree left, Tree right, String name, Tree r) {
            return new Node(this, left.replace(name, r), right.replace(name, r)); // Nodeをコピーし，左右の子を再帰的にreplaceする
        }
    }

    abstract class BinOp extends Op{
        // 整数の計算をする２項演算子
        abstract public int op(int rand1, int rand2);
        public Tree calc(Tree left, Tree right) {
            int l = ((IntLeaf)left.eval()).value;
            int r = ((IntLeaf)right.eval()).value;
            return new IntLeaf(op(l, r));
        }
    }
    class AddOp extends BinOp {
        public String opName() { return "+"; }
        public int op(int rand1, int rand2) { return rand1 + rand2; }
    }
    class SubOp extends BinOp {
        public String opName() { return "-"; }
        public int op(int rand1, int rand2) { return rand1 - rand2; }
    }
    class MulOp extends BinOp {
        public String opName() { return "*"; }
        public int op(int rand1, int rand2) { return rand1 * rand2; }
    }
    class DivOp extends BinOp {
        public String opName() { return "/"; }
        public int op(int rand1, int rand2) { return rand1 / rand2; }
    }
    // 特別な演算子
    class FunOp extends Op {
        // ( fun param bodyTree ) という形．左の子paramはNameLeafに限る．
        public String opName() { return "fun"; }
        public Tree calc(Tree left, Tree right) { 
            return new Node(this, left, right); // 自分と同じものを返す
        }
    }
    class CallOp extends Op {
        // ( call Fun Tree ) という形．左の子をevalした値は，FunOpを持つノードに限る．
        public String opName() { return "call"; }
        public Tree calc(Tree left, Tree right) {
            // 左の子をevalした値は，FunOpを持つノードでなければならない．
            Node fun = (Node) left.eval();
            NameLeaf param = (NameLeaf) fun.left;
            Tree body = fun.right;
            // funのbodyTree内のparamを，rightに置き換える
            Tree expression = body.replace(param.name, right);
            System.out.println("置換: " + expression.toString());
            // bodyをevalした値を返す．
            return expression.eval();
        }
    }
    // 命令一覧表
    Op[] optable = {new AddOp(), new SubOp(), new MulOp(), new DivOp(),
                    new FunOp(), new CallOp()};
    Scanner scanner;
    Map<String, Op> ops;
    TreeScript() {
        scanner = new Scanner(System.in);
        ops = new HashMap<String, Op>();
        // 辞書に命令語を登録する．
        for (Op op : optable) {
            ops.put(op.opName(), op);
        }
    }
    
    public Tree parse() {
        if (scanner.hasNextInt()) {
            return new IntLeaf(scanner.nextInt());
        } else if (scanner.hasNext("\\(")) {
            scanner.next();               // "("を読み飛ばす．
            String name = scanner.next(); // オペレータの名前
            Tree left = parse();          // 左の子
            Tree right = parse();         // 右の子
            scanner.next("\\)");          // ")" を読み飛ばす
            return new Node(ops.get(name), left, right);
        } else {
            return new NameLeaf(scanner.next());
        }
    }
    public void run() {
        while (scanner.hasNext()) {
            try {
                Tree t = parse();
                System.out.println("式: " + t.toString());
                System.out.println(t.eval());
            } catch (Exception e) {
                System.out.println("エラー");
                e.printStackTrace(System.out);
            }
        }
    }
    public static void main(String[] args) {
        new TreeScript().run();
    }
}
```

#### 実行例:

(下線が入力, 緑字は注釈．) ``

``` {.interaction}
$ java TreeScript
( + 3 8 )
式: ( + 3 8 )
11

( + 5 ( * 3 7 ) )
式: ( + 5 ( * 3 7 ) )
26

3
式: 3
3             ← 整数を入れると，そのまま整数が値になる

abc
式: abc
abc           ← 名前を入れると，そのまま名前が値になる

^D            ← 行の先頭でControl-Dを入力
$ 
```

また，TreeScript
には，式の中の変数を，別の式で置き換えてから計算する機能がある． 「( fun
変数 E
)」という形の式は，「Eの中で使っている変数を，他の式に置き換える」
ことを期待しており，数学でいう関数のような役割をする．

「( call 関数 引数
)」という形の式は，関数の中の変数を「引数」で置き換える．

#### 実行例:

(下線が入力, 緑字は注釈．) ``

``` {.interaction}
$ java TreeScript
( call ( fun x ( * x x ) ) 3 )
式: ( call ( fun x ( * x x ) ) 3 )
置換: ( * 3 3 )                       ← ( * x x ) の中のxが，3で置き換えられた
9
$ 
```

「( call 関数 引数 )」という形が，式の中に複数出てきた場合は，
一番左側のcallから順に置き換えが起きる．

#### 実行例:

(下線が入力, 緑字は注釈．) ``

``` {.interaction}
$ java TreeScript

( call ( fun square ( call square 3 ) )
       ( fun x ( * x x ) ) )
式: ( call ( fun square ( call square 3 ) ) ( fun x ( * x x ) ) )
置換: ( call ( fun x ( * x x ) ) 3 )   ← squareが( fun x ( * x x ) )に置き換わった
置換: ( * 3 3 )                        ← xが3で置き換えられた
9
$ 
```

２つの引数を受け取る関数のようなものを作りたい場合は，callとfunを２組使って，１つずつ置き換えを行っていく．１引数関数を組み合わせて，多引数関数を模倣するわけである．

#### 実行例:

(下線が入力, 緑字は注釈．) ``

``` {.interaction}
$ java TreeScript
( call
   ( call
      ( fun x ( fun y ( / ( + x y ) 2 ) ) ) ←xとyの平均を計算する式
      5 )
    9 )

式: ( call ( call ( fun x ( fun y ( / ( + x y ) 2 ) ) ) 5 ) 9 )
置換: ( fun y ( / ( + 5 y ) 2 ) )          ←まずxが5に置き換わった．
置換: ( / ( + 5 9 ) 2 )                    ←次にyが7に置き換わった．
7
```

------------------------------------------------------------------------

設問(基本レベル): 条件分岐
--------------------------

2つの整数を受け取り，等しいかどうかを判定する演算子「==」(EqOp)をTreeScript
に加えた TreeScript1を作りなさい．ただし，返す値は次のようにしなさい．

-   等しい場合は，「式 ( fun left ( fun right left ) )
    を入力した時にできるのと同じ形の木」を返す．
-   等しくない場合は，「式 ( fun left ( fun right right ) )
    を入力した時にできるのと同じ形の木」を返す．

上記のような式を入力すると，どういうツリーが作られるかよく考えること．

#### 実行例:

(下線が入力, 緑字は注釈．) ``

``` {.interaction}
$ java TreeScript1
( == ( + 1 1 ) 2 )
式: ( == ( + 1 1 ) 2 )
( fun left ( fun right left ) )   ←結果が真の時の値

( == ( + 1 1 ) 0 )
式: ( == ( + 1 1 ) 0 )
( fun left ( fun right right ) )  ←結果が偽の時の値

( call ( call ( == 1 1 ) ( * 3 3 ) ) ( * 5 5 ) )   ← 条件式に，２つの引数( * 3 3 )と( * 5 5 )を与える
式: ( call ( call ( == 1 1 ) ( * 3 3 ) ) ( * 5 5 ) )
置換: ( fun right ( * 3 3 ) )
置換: ( * 3 3 )                                           ←左の式( * 3 3 )が選ばれた
9

( call ( call ( == 0 1 ) ( * 3 3 ) ) ( * 5 5 ) )
式: ( call ( call ( == 0 1 ) ( * 3 3 ) ) ( * 5 5 ) )
置換: ( fun right right )
置換: ( * 5 5 )                                           ←右の式( * 5 5 )が選ばれた
25
```

つまり，JavaやC言語では「if (条件式) then 文1 else 文２」
と書くところを「( call ( call 条件式 式1 ) 式2 )」と書くのである．
条件式の値として，式１か式２かどちらかを返す関数を返すようにして，
if文のかわりをさせている．

**TreeScript1.javaを提出しなさい．**

すべてのチーム課題に加えて，基本レベルまで解答すれば合格とする．

------------------------------------------------------------------------

設問(発展レベル): グローバルな名前の定義
----------------------------------------

基本レベルに加えて，名前nameと式Eを受け取り，式Eを評価して，それを値とする名前nameを定義する「def」演算子(DefOp)を
TreeScript1に加えたTreeScript2を作りなさい．def
演算子の値は，第1引数の名前を返すようにしなさい．

#### 実行例:

(下線が入力, 緑字は注釈．) ``

``` {.interaction}
$ java TreeScript2
( def three 3 )
式: ( def three 3 )
three  ←変数 three が定義された

three
式: three
3      ←変数 three の値は3

( def square
      ( fun x ( * x x ) ) )
式: ( def square ( fun x ( * x x ) ) )
square  ←変数 square が定義された

( call square three )
式: ( call square three )      ←変数 square の値は2乗する関数
置換: ( * three three )
9


( def fact
      ( fun n
       ( call ( call ( == n 0 ) 1 )
              ( * n ( call fact ( - n 1 ) ) ) ) ) )

式: ( def fact ( fun n ( call ( call ( == n 0 ) 1 ) ( * n ( call fact ( - n 1 ) ) ) ) ) )
fact

( call fact 10 )
式: ( call fact 10 )
置換: ( call ( call ( == 10 0 ) 1 ) ( * 10 ( call fact ( - 10 1 ) ) ) )
置換: ( fun right right )
置換: ( * 10 ( call fact ( - 10 1 ) ) )
置換: ( call ( call ( == ( - 10 1 ) 0 ) 1 ) ( * ( - 10 1 ) ( call fact ( - ( - 10 1 ) 1 ) ) ) )
(略)
置換: ( call ( call ( == ( - ( - ( - ( - ( - ( - ( - ( - ( - ( - 10 1 ) 1 ) 1 ) 1 ) 1 ) 1 ) 1 ) 1 ) 1 ) 1 ) 0 ) 1 ) ( * ( - ( - ( - ( - ( - ( - ( - ( - ( - ( - 10 1 ) 1 ) 1 ) 1 ) 1 ) 1 ) 1 ) 1 ) 1 ) 1 ) ( call fact ( - ( - ( - ( - ( - ( - ( - ( - ( - ( - ( - 10 1 ) 1 ) 1 ) 1 ) 1 ) 1 ) 1 ) 1 ) 1 ) 1 ) 1 ) ) ) )
置換: ( fun right 1 )
置換: 1
3628800
```

**TreeScript2.javaを提出しなさい．**
(TreeScript2.javaを提出する場合，TreeScript1.javaは提出しなくてよい．
部分的に動いている場合は，どこまで動いているか明記すること．)

------------------------------------------------------------------------

設問(高度レベル): 名前の衝突の回避
----------------------------------

TreeScript2では，たまたま同じ名前の変数が２つ出てくると，不都合が生じることがある．

#### 実行例:

(下線が入力, 緑字は注釈．) ``

``` {.interaction}
$ java TreeScript2
問題点1
( call ( fun x ( call ( fun x ( + x x ) )    ← 外側と内側の２つのxが存在する．
                      ( + x 1 ) ) )
       2 )

式: ( call ( fun x ( call ( fun x ( + x x ) ) ( + x 1 ) ) ) 2 )
置換: ( call ( fun 2 ( + 2 2 ) ) ( + 2 1 ) ) ← 外側のxを置き換えるはずが，内側のxまで，すべて2に置き換わってしまった．
エラー                                       ← ( fun 2 ... )という形の式は正しくないのでエラー．
java.lang.ClassCastException: TreeScript2$IntLeaf cannot be cast to TreeScript$NameLeaf
at TreeScript$CallOp.calc(TreeScript.java:89)
at (略)

( * y y )
式: ( * y y )        ← yを評価するとyになるが…
エラー               ← *演算子の引数は整数でなければならない．
java.lang.ClassCastException: TreeScript2$EnvNameLeaf cannot be cast to TreeScript$IntLeaf
at TreeScript$BinOp.calc(TreeScript.java:54)
at (略)

問題点2
( call ( fun x ( call ( fun y x )      ← 外側の(評価するとyになる)yと，内側のyの２つがある
                      2 ) )
       ( * y y ) )                     ← ( * y y )はエラーになるはずである．

式: ( call ( fun x ( call ( fun y x ) 2 ) ) ( * y y ) )
置換: ( call ( fun y ( * y y ) ) 2 )   ← ( * y y )が，内側のyのスコープに入ってしまった．
置換: ( * 2 2 )                        ←外側にあったyが，内側のyと解釈されてしまった．
4
```

問題点1か問題点2か，あるいはその両方を解決したTreeScript3 を作りなさい．

#### 実行例:

(下線が入力, 緑字は注釈．) ``

``` {.interaction}
$ java TreeScript3
問題点1の解決
java TreeScript3
( call ( fun x ( call ( fun x ( + x x ) )    ← 外側と内側の２つのxが存在する．
                      ( + x 1 ) ) )
       2 )
式: ( call ( fun x ( call ( fun x ( + x x ) ) ( + x 1 ) ) ) 2 )
置換: ( call ( fun x ( + x x ) ) ( + 2 1 ) ) ← 外側のxだけが2に置き換わった．
置換: ( + ( + 2 1 ) ( + 2 1 ) )
6

問題点2の解決
( call ( fun x ( call ( fun y x )
                      2 ) )
       ( * y y ) )                                 ← このyには値がない
式: ( call ( fun x ( call ( fun y x ) 2 ) ) ( * y y ) )
置換: ( call ( fun y ( * <error> <error> ) ) 2 )   ← 値がないyを，内側のyのスコープに持ち込もうとした際に<error>というシンボルに置き換えた
置換: ( * <error> <error> )
エラー                                            ← 整数でないのでエラー
java.lang.ClassCastException: TreeScript3$NameLeaf cannot be cast to TreeScript$IntLeaf
at TreeScript$BinOp.calc(TreeScript.java:54)
at (略)

defを使わない再帰的呼び出し
( call
  ( call
    ( fun f ( call ( fun x ( call f ( call x x ) ) )
                   ( fun x ( call f ( call x x ) ) ) ) )
    ( fun fact
      ( fun n
       ( call ( call ( == n 0 ) 1 )
              ( * n ( call fact ( - n 1 ) ) ) ) ) )
   )
5 )
式: ( call ( call ( fun f ( call ( fun x ( call f ( call x x ) ) ) ( fun x ( call f ( call x x ) ) ) ) ) ( fun fact ( fun n ( call ( call ( == n 0 ) 1 ) ( * n ( call fact ( - n 1 ) ) ) ) ) ) ) 5 )
置換: ( call ( fun x ( call ( fun fact ( fun n ( call ( call ( == n 0 ) 1 ) ( * n ( call fact ( - n 1 ) ) ) ) ) ) ( call x x ) ) ) ( fun x ( call ( fun fact ( fun n ( call ( call ( == n 0 ) 1 ) ( * n ( call fact ( - n 1 ) ) ) ) ) ) ( call x x ) ) ) )
置換: ( call ( fun fact ( fun n ( call ( call ( == n 0 ) 1 ) ( * n ( call fact ( - n 1 ) ) ) ) ) ) ( call ( fun x ( call ( fun fact ( fun n ( call ( call ( == n 0 ) 1 ) ( * n ( call fact ( - n 1 ) ) ) ) ) ) ( call x x ) ) ) ( fun x ( call ( fun fact ( fun n ( call ( call ( == n 0 ) 1 ) ( * n ( call fact ( - n 1 ) ) ) ) ) ) ( call x x ) ) ) ) )
(略)
120
```

**TreeScript3.javaを提出しなさい．**
(TreeScript3.javaを提出する場合，TreeScript1.java,
TreeScript2.javaは提出しなくてよい．
部分的に動いている場合は，どこまで動いているか明記すること．)\
\
\
\
\
\

