public class TreeScript1 extends TreeScript {

  class EqOp extends Op {
    public String opName() {
      return "==";
    }

    public Tree calc(Tree left, Tree right) {
      Node node_;
      NameLeaf nameleaf_left0 = new NameLeaf("left");
      NameLeaf nameleaf_right0 = new NameLeaf("right");
      if ((((IntLeaf) (left.eval())).value) ==
          (((IntLeaf) (right.eval())).value)) {
        node_ =
          new Node(
                   new FunOp(),
                   nameleaf_left0,
                   new Node(new FunOp(),
                            nameleaf_right0,
                            nameleaf_left0));
        return node_;
      } else {
        node_ =
          new Node(
                   new FunOp(),
                   nameleaf_left0,
                   new Node(new FunOp(),
                            nameleaf_right0,
                            nameleaf_right0));
        return node_;
      }
    }
  }

  TreeScript1() {
    ops.put("==", new EqOp());
  }

  public static void main(String args[]) {
    new TreeScript1().run();
  }
}
