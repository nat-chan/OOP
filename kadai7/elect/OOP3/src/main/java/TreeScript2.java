import java.util.Map;
import java.util.HashMap;

public class TreeScript2 extends TreeScript1 {

  class NameLeaf2 extends NameLeaf {
    NameLeaf2(String name) { super(name); }
    @Override
    public Tree eval() {
      if (funcs.containsKey (name)) {
        return funcs.get (name);
      }
      return this;
    }
  }

  Map<String, Tree> funcs;
  class DefOp extends Op {
    public String opName () { return "def"; }
    public Tree calc (Tree name, Tree E) {
      funcs.put (((NameLeaf2) name).name, E);
      return name;
    }
  }

  class CallOp2 extends Op {
    public String opName() { return "call"; }
    public Tree calc(Tree left, Tree right) {
      Node fun = (Node) left.eval();
      NameLeaf2 param;
      try {
        param = (NameLeaf2)fun.left ;
      }
      catch (ClassCastException e) {
        param = new NameLeaf2(((NameLeaf)fun.left).name) ;
      }
      Tree body = fun.right;
      Tree expression = body.replace(param.name, right);
      System.out.println("置換: " + expression.toString());
      return expression.eval();
    }
  }

  @Override
  public Tree parse() {
    if (scanner.hasNextInt()) {
      return new IntLeaf(scanner.nextInt());
    } else if (scanner.hasNext("\\(")) {
      scanner.next();
      String name = scanner.next();
      Tree left = parse();
      Tree right = parse();
      scanner.next("\\)");
      return new Node(ops.get(name), left, right);
    } else {
      return new NameLeaf2(scanner.next());
    }
  }

  TreeScript2() {
    funcs = new HashMap<String, Tree>();
    ops.put ("def", new DefOp ());
    ops.remove("call");
    ops.put("call", new CallOp2());
  }

  public static void main(String args[]) {
    new TreeScript2().run();
  }
}

