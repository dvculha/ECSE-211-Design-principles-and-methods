package ca.mcgill.ecse211.lab4;

import static ca.mcgill.ecse211.lab4.Resources.*;
import lejos.robotics.SampleProvider;

public class UltrasonicLocalizer implements Runnable {

  private SampleProvider usSensor;
  private float[] usData;
  private int distance = 0;
  private int filterControl = 0;
  
  /**
   * UltrasonicLocalizer default constructor.
   */
  public UltrasonicLocalizer() {
    this.usSensor = SENSOR.getMode("Distance");
    this.usData = new float[usSensor.sampleSize()];
  }

  /**
   * Run method which calls the constructor and determines whether to run
   * risingEdge() or fallingEdge().
   */
  public void run() {
    try {
      Thread.sleep(SLEEP);
    } catch (InterruptedException e) {
    }

    if (!Main.wall) {
      risingEdge();
    } else if (Main.wall) {
      fallingEdge();
    }
  }

  /**
   * Retrieves and filters data from US sensor.
   */
  private void filterData() {
    usSensor.fetchSample(usData, 0);
    int dist = (int) Math.abs(usData[0] * 100.0);
    if (dist >= MAX_DISTANCE_FILTER && filterControl < FILTER_OUT) {
      filterControl++;
    } else if (dist >= MAX_DISTANCE_FILTER) {
      this.distance = dist;
    } else {
      filterControl = 0;
      this.distance = dist;
    }
  }

  /**
   * Rising edge method.
   */
  public void risingEdge() {
    double theta;
    filterData();

    if (this.distance > REDISTANCE) { // far from wall

      while (this.distance > REDISTANCE) { // rotate robot CCW until it is close enough
        rotateCCW();
        filterData();
      }
      stop(); // stop motors

      while (this.distance < WALL_DISTANCE) { // rotate CCW until robot is far enough from wall
        rotateCCW();
        filterData();
      }

      stop();
      odo.setTheta(0); // reset angle to 0 and restart loop

      while (this.distance > REDISTANCE) { // rotate CW until robot is closer than rising edge distance
        rotateCW();
        filterData();
      }
      stop();

      while (this.distance < WALL_DISTANCE) { // rotate CW until robot is far enough from wall
        rotateCW();
        filterData();
      }

      stop();
      theta = odo.getXYT()[2]; // record angle

    } else { // close to wall

      while (this.distance < WALL_DISTANCE) { // rotate CCW until robot is far enough from wall
        rotateCCW();
        filterData();
      }

      stop();
      odo.setTheta(0); // reset angle to 0 and restart loop

      while (this.distance > REDISTANCE) { // rotate CW until robot is closer than rising edge distance
        rotateCW();
        filterData();
      }
      stop(); // stop motors when it is

      while (this.distance < WALL_DISTANCE) { // rotate CW until robot is far enough from wall
        rotateCW();
        filterData();
      }

      stop();
      theta = odo.getXYT()[2]; // record theta
    }

    // place robot in 0-degree angle by using rising edge method
    LEFT_MOTOR.rotate(convertAngle(US_WHEEL_RAD, US_TRACK, theta / 2.0 - REOFFSET), true);
    RIGHT_MOTOR.rotate(-convertAngle(US_WHEEL_RAD, US_TRACK, theta / 2.0 - REOFFSET), false);
    odo.setTheta(0);
    stop(); // stop motors
  }

  /**
   * Falling edge method.
   */
  public void fallingEdge() {
    double theta;
    filterData();

    if (this.distance < FEDISTANCE) { // Rotate CCW until far enough from wall

      while (this.distance < FEDISTANCE) {
        rotateCCW();
        filterData();
      }
      stop(); // stop robot when it is further than falling edge distance

      while (this.distance > WALL_DISTANCE) { // rotate CCW until close enough to wall
        rotateCCW();
        filterData();
      }

      stop(); // stop robot when it is closer to wall distance
      odo.setTheta(0); // reset angle to 0

      while (this.distance < FEDISTANCE) { // rotate CW until robot is further than falling edge distance
        rotateCW();
        filterData();
      }
      stop(); // stop robot when it is far enough

      while (this.distance > WALL_DISTANCE) { // rotate CW until robot is close enough to wall
        rotateCW();
        filterData();
      }

      stop();
      theta = odo.getXYT()[2]; // get theta value from odometer
    }

    else {

      while (this.distance > WALL_DISTANCE) { // rotate CCW until robot is close enough
        rotateCCW();
        filterData();
      }

      stop();
      odo.setTheta(0); // reset theta

      while (this.distance < FEDISTANCE) { // rotate CW until robot is further than falling edge distance
        rotateCW();
        filterData();
      }
      stop(); // stop robot when it is far enough

      while (this.distance > WALL_DISTANCE) { // rotate CW until robot is close enough to wall
        rotateCW();
        filterData();
      }

      stop();
      theta = odo.getXYT()[2]; // get theta value from odometer.
    }

    // face the robot in the 0-degree angle by applying the falling edge method
    LEFT_MOTOR.rotate(-convertAngle(US_WHEEL_RAD, US_TRACK, FEOFFSET + theta / 2.0), true);
    RIGHT_MOTOR.rotate(convertAngle(US_WHEEL_RAD, US_TRACK, FEOFFSET + theta / 2.0), false);
    odo.setTheta(0);
    stop();
  }

  /**
   * Rotate robot counter-clockwise (to the left).
   */
  public void rotateCCW() {
    LEFT_MOTOR.setSpeed(ROTATE_SPEED);
    RIGHT_MOTOR.setSpeed(ROTATE_SPEED);
    LEFT_MOTOR.backward();
    RIGHT_MOTOR.forward();
  }

  /**
   * Rotate robot clockwise (to the right).
   */
  public void rotateCW() {
    LEFT_MOTOR.setSpeed(ROTATE_SPEED);
    RIGHT_MOTOR.setSpeed(ROTATE_SPEED);
    LEFT_MOTOR.forward();
    RIGHT_MOTOR.backward();
  }

  /**
   * Stops robot.
   */
  public void stop() {
    LEFT_MOTOR.stop();
    RIGHT_MOTOR.stop();
  }

  /**
   * Convert distance for motors.
   * 
   * @param radius
   * @param distance
   * @return
   */
  public static int convertDistance(double radius, double distance) {
    return (int) ((180.0 * distance) / (PI * radius));
  }

  /**
   * Convert angle for motors.
   * 
   * @param radius
   * @param width
   * @param angle
   * @return
   */
  public static int convertAngle(double radius, double width, double angle) {
    return convertDistance(radius, PI * width * angle / 360.0);
  }
}
