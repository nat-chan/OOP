interface I {}
abstract class A {}
class B extends A {}
class C implements I {}

class test
{
    public static void main(String args[])
    {
        A a = new A();
        B b = new B();
        C c = new C();
        I i = new I();
        A b = new B();
        B a_ = new A();
        I b_ = new B();
        I c_ = new C();
    }
}

// λ javac test.java
//     test.java:10: エラー: Aはabstractです。インスタンスを生成することはできません
//         A a = new A();
// ^
//      test.java:13: エラー: Iはabstractです。インスタンスを生成することはできません
//          I i = new I();
// ^
//      test.java:14: エラー: 変数 bはすでにメソッド main(String[])で定義されています
//           A b = new B();
// ^
//       test.java:15: エラー: Aはabstractです。インスタンスを生成することはできません
//           B a_ = new A();
// ^
//       test.java:16: エラー: 不適合な型: BをIに変換できません:
//           I b_ = new B();
// ^
//     エラー5個
