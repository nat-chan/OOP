public class SuperStacola extends Stacola {

  class ModOp extends BinOp {
    public String opName() {
      return "余り";
    }

    public double op(double rand1, double rand2) {
      return ((long) rand1) % ((long) rand2);
    }
  }

  class IfOp extends Op {
    public String opName() {
      return "選ぶ";
    }

    public void exec() {
      Program B = state.pstack.pop();
      Program A = state.pstack.pop();
      double n = state.stack.pop();
      if (n != 0.0) {
        A.run();
      } else {
        B.run();
      }
    }
  }

  class GtOp extends BinOp {
    public String opName() {
      return "より大きい";
    }

    public double op(double rand1, double rand2) {
      if (rand1 > rand2) {
        return 1.0;
      } else {
        return 0.0;
      }
    }
  }

  class LtOp extends BinOp {
    public String opName() {
      return "より小さい";
    }

    public double op(double rand1, double rand2) {
      if (rand1 < rand2) {
        return 1.0;
      } else {
        return 0.0;
      }
    }
  }

  class EqOp extends BinOp {
    public String opName() {
      return "等しい";
    }

    public double op(double rand1, double rand2) {
      if (rand1 == rand2) {
        return 1.0;
      } else {
        return 0.0;
      }
    }
  }

  class DupOp extends Op {
    public String opName() {
      return "コピー";
    }

    public void exec() {
      double d = state.stack.pop();
      state.stack.push(d);
      state.stack.push(d);
    }
  }

  class PopOp extends Op {
    public String opName() {
      return "捨てる";
    }

    public void exec() {
      state.stack.pop();
    }
  }

  Op[] a = {new ModOp(), new IfOp(), new GtOp(), new LtOp(), new EqOp(), new DupOp(), new PopOp()};

  SuperStacola() {
    for (Op e : a) {
      state.ops.put(e.opName(), e);
    }
  }

  public static void main(String args[]) {
    new SuperStacola().run();
  }
}
