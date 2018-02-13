public class Stacola extends LCalc {

  RobotModel robot;
  ScreenView view;
  
  class ForwardOp extends Op {
    public String opName () {
      return "進む";
    }
    public void exec () {
      robot.moveForward (state.stack.pop ());
    }
  }
  class RightOp extends Op {
    public String opName () {
      return "右へ回る";
    }
    public void exec () {
      robot.turnRight (state.stack.pop ());
    }
  }

  class LeftOp extends Op {
    public String opName () {
      return "左へ回る";
    }
    public void exec () {
      robot.turnLeft (state.stack.pop ());
    }
  }

  class RepeatOp extends Op {
    public String opName () {
      return "繰り返す";
    }
    public void exec () {
      Program p = state.pstack.pop ();
      double j = state.stack.pop ();
      for (int i = 0; i < (j) ; i++) {
        p.run ();
      }
    }
  }

  Op [] a = {new ForwardOp (), new RightOp (), new LeftOp (), new RepeatOp ()};

  Stacola () {
    robot = new RobotModel ();
    view = new ScreenView ();
    robot.setView (view);
    for (Op e : a) {
      state.ops.put (e.opName (), e);
    }
  }
  public static void main(String args[]) {
    new Stacola ().run ();
  }
}
