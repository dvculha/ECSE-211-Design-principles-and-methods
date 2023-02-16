package ca.mcgill.ecse211.lab3;

import static ca.mcgill.ecse211.lab3.Resources.*;
import lejos.robotics.SampleProvider;

public class Avoidance extends Thread {

  // initialize to collect data and store it
  private SampleProvider us;
  private float[] usData;
  int distance;

  /**
   * Constuctor for Avoidance.
   * 
   * @param us
   * @param usData
   */
  public Avoidance(SampleProvider us, float[] usData) {
    this.us = us;
    this.usData = usData;
  }

  /**
   * Flag for if robot is navigating.
   */
  private static boolean navigating = true; // set to true

  @Override
  public void run() {
    for (int i = 0; i < 5; i++)
      travelTo(MAPS[MAP - 1][i][0] * TILE_SIZE, MAPS[MAP - 1][i][1] * TILE_SIZE); // travel to specified waypoints
  }

  /**
   * Returns true if the robot is navigating.
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

    // calculate waypoint path and angle
    double pathX = x - odo.getX();
    double pathY = y - odo.getY();
    double pathAngle = Math.atan2(pathX, pathY);

    // rotate to waypoint
    LEFT_MOTOR.setSpeed(ROTATE_SPEED);
    RIGHT_MOTOR.setSpeed(ROTATE_SPEED);
    turnTo(pathAngle); // get heading

    // get distance
    double path = Math.sqrt(Math.pow(pathX, 2) + Math.pow(pathY, 2));

    // adjust motors
    LEFT_MOTOR.setSpeed(FORWARD_SPEED);
    RIGHT_MOTOR.setSpeed(FORWARD_SPEED);
    LEFT_MOTOR.rotate(convertDistance(path), true);
    LEFT_MOTOR.rotate(convertDistance(path), true);

    int distance;

    // while robot is moving, collect data from sensor
    while (LEFT_MOTOR.isMoving() || RIGHT_MOTOR.isMoving()) {
      us.fetchSample(usData, 0);
      distance = (int) (usData[0] * 100.0);

      // filter data
      filter(distance);

      // if too close to obstacle, stop the robot!
      if (distance <= BAND_CENTER) {
        LEFT_MOTOR.stop(true);
        RIGHT_MOTOR.stop(false);
        navigating = false;
      }
      try {
        Thread.sleep(50);
      } catch (Exception e) {
      }
    }

    // avoidance mode
    if (!this.isNavigating()) {
      // obstacle detected so use bang bang controller to avoid it
      avoidObstacle();
      navigating = true;
      travelTo(x, y); // resume navigation
      return;
    }
  }

  /**
   * Method that allows the robot to avoid obstacles using bang bang approach from
   * lab 1.
   */
  public void avoidObstacle() {

    // rotate robot and calculate angle for robot to exit obstacle
    turnTo(odo.getTheta() - PI / 2);
    double angleF = odo.getTheta() + PI * END_BUFFER;

    // bang bang logic
    while (odo.getTheta() < angleF) {

      // get data
      us.fetchSample(usData, 0);
      distance = (int) (usData[0] * 100.0);

      // error in distance of robot w.r.t. wall
      int errorDist = BAND_CENTER - distance;

      // continue straight
      if (Math.abs(errorDist) <= BAND_WIDTH) {
        LEFT_MOTOR.setSpeed(AVOIDANCE_SPEED);
        RIGHT_MOTOR.setSpeed(AVOIDANCE_SPEED);
        LEFT_MOTOR.forward();
        RIGHT_MOTOR.forward();
      }

      // if error in distance is positive, then robot is too close to wall
      else if (errorDist > 0) {
        LEFT_MOTOR.setSpeed(CLOSE_SPEED);
        RIGHT_MOTOR.setSpeed(AVOIDANCE_SPEED);
        LEFT_MOTOR.backward();
        RIGHT_MOTOR.forward();
      }

      // otherwise it is too far from wall
      else if (errorDist < 0) {
        RIGHT_MOTOR.setSpeed(AVOIDANCE_SPEED);
        LEFT_MOTOR.setSpeed(FAR_SPEED);// Setting the outer wheel to move faster
        RIGHT_MOTOR.forward();
        LEFT_MOTOR.forward();
      }
    }

    // stop motors
    LEFT_MOTOR.stop();
    RIGHT_MOTOR.stop();

  }

  /**
   * Sends robot to specified waypoints. 
   * 
   * @param x
   * @param y
   */
  public void turnTo(double theta) {

    double angle = getMinAngle(theta - odo.getTheta()); // get minimum angle for turning
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
      angle += 2 * Math.PI;
    angle %= 2 * Math.PI;
    if (angle > Math.PI)
      angle -= 2 * Math.PI;
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

  /**
   * Read data from sensor.
   * 
   * @return
   */
  public int readUSDistance() {
    return this.distance;
  }

  /**
   * Filter data from US sensor.
   * 
   * @param distance
   */
  public void filter(int distance) {
    int filterControl = 0;

    // rudimentary filter
    if (distance >= MAX_DISTANCE_FILTER && filterControl < FILTER_OUT) {
      filterControl++;
    } else if (distance >= MAX_DISTANCE_FILTER) {
      this.distance = distance;
    } else {
      filterControl = 0;
      this.distance = distance;
    }
  }

}
