public class Star {
	public static void main(String[] args) {
		SimpleRobot robot = new SimpleRobot();
		for (int i = 0; i < 5; i++) {
			robot.moveForward(100);
			robot.turnRight(144);
		}
	}
}
