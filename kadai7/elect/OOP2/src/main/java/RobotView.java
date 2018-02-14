interface RobotView {
  public void turn(double oldHeading, double newHeading);
  public void move(double oldX, double oldY,
                   double newX, double newY);
}
