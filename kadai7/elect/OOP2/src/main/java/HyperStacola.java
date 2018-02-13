public class HyperStacola extends SuperStacola {
  class TempOp extends Op {
    Program p;
    String name;

    TempOp(String str, Program p) {
      this.name = str;
      this.p = p;
    }

    public String opName() {
      return this.name;
    }

    public void exec() {
      p.run();
    }
  }

  class DefOp extends Op {
    public String opName() {
      return "定義する";
    }

    public void exec() {
      Program body = state.pstack.pop();
      String name = (((Name) (((state.pstack.pop()).inspect()).get(0))).toString());
      state.ops.put(name, new TempOp(name, body));
    }
  }

  HyperStacola() {
    state.ops.put("定義する", new DefOp());
  }

  public static void main(String args[]) {
    new HyperStacola ().run ();
  }
}
