package ca.mcgill.ecse211.lab3;

import static ca.mcgill.ecse211.lab3.Resources.*;

public class Navigation extends Thread {

  private static boolean navigating = false; // by default false

  @Override
  public void run() {
    for (int i = 0; i < 5; i++)
      travelTo(MAPS[MAP - 1][i][0] * TILE_SIZE, MAPS[MAP - 1][i][1] * TILE_SIZE); // travel to all the specified
                                                                                  // waypoints (5)
  }
  
  /**
   * Returns whether the robot is navigating.
   * 
   * @return
   */
  public boolean isNavigating() {
    return navigating;
  }

  /**
   * Sends robot to specified waypoints. *
   * 
   * @param x
   * @param y
   */
  public void travelTo(double x, double y) {

    // reset motors
    LEFT_MOTOR.stop();
    LEFT_MOTOR.setAcceleration(ACCELERATION);
    RIGHT_MOTOR.stop();
    RIGHT_MOTOR.setAcceleration(ACCELERATION);

    navigating = true; // set to true now that it is traveling

    // calculate waypoint path and angle
    double pathX = x - odo.getX();
    double pathY = y - odo.getY();
    double pathAngle = Math.atan2(pathX, pathY);

    // turn to proper angle
    LEFT_MOTOR.setSpeed(ROTATE_SPEED);
    RIGHT_MOTOR.setSpeed(ROTATE_SPEED);
    turnTo(pathAngle);

    // gets path to take to get to waypoint
    double path = Math.hypot(pathX, pathY);

    // move forward for required distance
    LEFT_MOTOR.setSpeed(FORWARD_SPEED);
    RIGHT_MOTOR.setSpeed(FORWARD_SPEED);
    LEFT_MOTOR.rotate(convertDistance(path), true);
    RIGHT_MOTOR.rotate(convertDistance(path), false);
  }

  /**
   * Rotates robot to proper heading.
   * 
   * @param theta
   */
  public void turnTo(double theta) {

    double angle = getMinAngle(theta - odo.getTheta()); // calculate minimum angle
    LEFT_MOTOR.setSpeed(ROTATE_SPEED);
    RIGHT_MOTOR.setSpeed(ROTATE_SPEED);
    LEFT_MOTOR.rotate(convertAngle(angle), true);
    RIGHT_MOTOR.rotate(-convertAngle(angle), false);
  }

  /**
   * Returns minimum angle for robot to make turn.
   * 
   * @param angle
   * @return minAngle
   */
  public double getMinAngle(double angle) {
    while (angle < 0)
      angle += 2 * PI;
    angle %= 2 * PI;
    if (angle > PI)
      angle -= 2 * PI;
    return angle;
  }  

  /**
   * Returns the change in heading the robot needs to do.
   * 
   * @param angle
   * @return the change in heading
   */
  private int convertAngle(double angle) {
    return convertDistance(TRACK * angle / 2);
  }
  
  /**
   * Returns the distance in degrees motors must travel.
   * 
   * @param distance
   * @return the degrees the wheels need to rotate to travel this distance
   */
  private int convertDistance(double distance) {
    return (int) (360 * distance / (2 * PI * WHEEL_RAD));
  }
}
