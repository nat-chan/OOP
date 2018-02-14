public class TreeScript3 extends TreeScript2 {
  class CallOp3 extends CallOp2 {
    Tree rep (Tree t) {
      if (t instanceof Node) {
        if (((Node) t).operator instanceof FunOp) {
          return t;
        }
        return new Node (((Node) t).operator,
                         (rep (((Node) t).left)),
                         (rep (((Node) t).right)));
      } else if (t instanceof NameLeaf2) {
        if (funcs.containsKey (((NameLeaf2) t).name)) {
          return t;
        } else {
          return new NameLeaf2 ("<error>");
        }
      }
      else if (t instanceof NameLeaf) {
        return new NameLeaf2 ("<error>");
      }
      else {
        return t;
      }
    }

    @Override
    public Tree calc (Tree left, Tree right) {
      Node fun = (Node) left.eval ();
      NameLeaf2 param;
      try {
        param = (NameLeaf2) fun.left ;
      } catch (ClassCastException e) {
        param = new NameLeaf2 (((NameLeaf)fun.left).name);
      }
      Tree body = fun.right;
      Tree expression;
      expression = body.replace(param.name, (rep(right)));
      if (body instanceof Node) {
        if ((((Node) body).operator) instanceof CallOp3) {
          if ((((Node) (((Node) body).left)).left instanceof NameLeaf2)) {
            if (((NameLeaf2) ((Node) ((Node) body).left).left).name.equals (param.name)) {

              expression = new Node ((((Node) body).operator),
                                     (((Node) body).left),
                                     (((Node) body).right.replace (param.name, (rep (right)))));
            }
          }
        }
      }

      System.out.println("置換: " + expression.toString());
      return expression.eval();
    }

  }

  TreeScript3 () {
    ops.remove ("call");
    ops.put ("call", new CallOp3 ());
  }

  public static void main(String[] args) {
    new TreeScript3 ().run ();
  }
}
