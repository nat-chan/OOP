public class RobotModel  {
    // ロボットの内部状態を表すデータ
    double x, y;    // 現在の座標．
    double heading; // 角度(0〜360)．0は東(右)，90は北(上)を表す．
    RobotView view;

    public RobotModel() {
        x = y = 0.0;
        heading = 0.0;
    }
    public void setView(RobotView view) {
        this.view = view;
    }
    public void setHeading(double heading) {
        view.turn(this.heading, heading);
        this.heading = heading;
    }
    public void turnLeft(double degree) {
        setHeading(heading + degree);
    }
    public void turnRight(double degree) {
        turnLeft(-degree);
    }
    public void moveTo(double x, double y) {
        view.move(this.x, this.y, x, y);
        this.x = x;
        this.y = y;
    }
    public void moveForward(double step) {
        double radian = heading / 180.0 * Math.PI;
        moveTo(this.x + Math.cos(radian) * step,
               this.y + Math.sin(radian) * step);
    }
}

