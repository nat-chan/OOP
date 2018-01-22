public class SimpleView implements RobotView
{
    public void turn(double oldHeading, double newHeading){}
    @Override
    public void move(double oldX, double oldY,
                     double newX, double newY) {
        System.out.println(oldX + "  " + oldY);
        System.out.println(newX + "  " + newY);
    }
}
