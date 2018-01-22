public class SimpleViewTriangle {
    public static void main(String[] args) {
        RobotModel robot = new RobotModel();
        RobotView view = new SimpleView();
        robot.setView(view);
        for (int i = 0; i < 3; i++) {
            robot.moveForward(100);
            robot.turnLeft(120);
        }
    }
}
