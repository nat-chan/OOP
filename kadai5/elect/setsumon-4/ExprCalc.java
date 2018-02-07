public class ExprCalc extends OOGCalc<ExprCalc.Tree> {
  abstract class Tree implements Numeric<Tree> {
    public abstract String toString();

    public Tree add(Tree t) {
      return new Node("+", this, t);
    }

    public Tree subtract(Tree t) {
      return new Node("-", this, t);
    }

    public Tree multiply(Tree t) {
      return new Node("*", this, t);
    }

    public Tree divide(Tree t) {
      return new Node("/", this, t);
    }
  }

  class Leaf extends Tree {
    int value;

    Leaf(int value) {
      this.value = value;
    }

    public String toString() {
      return Integer.toString(value);
    }
  }

  class Node extends Tree {
    Tree right;
    Tree left;
    String operator;

    Node(String operator, Tree left, Tree right) {
      this.operator = operator;
      this.left = left;
      this.right = right;
    }

    public String toString() {
      return ("(" + left.toString() + operator + right.toString() + ")");
    }
  }

  protected Tree fromInt(int v) {
    return new Leaf(v);
  }

  public static void main(String[] args) {
    new ExprCalc().run();
  }
}
