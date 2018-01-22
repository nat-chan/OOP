public class DotView implements RobotView
{
    @Override
    public void turn(double oldHeading, double newHeading){}
    public void move(double oldX, double oldY,
                     double newX, double newY){
        double xseq  = (newX - oldX) / 10;
        double yseq  = (newY - oldY) / 10;

        for (int i = 0; i < 11; i++) {
            System.out.println((oldX + (xseq * i)) +  "  " + (oldY + (yseq * i)));
        }
    }
}
