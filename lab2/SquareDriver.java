package ca.mcgill.ecse211.lab2;

//static import to avoid duplicating variables and make the code easier to read
import static ca.mcgill.ecse211.lab2.Resources.*;

/**
 * This class is used to drive the robot on the demo floor in a square fashion
 * and controls its behaviour within the square.
 */
public class SquareDriver {

  /**
   * Drives the robot in a square of size 3x3 Tiles. It is to be run in parallel
   * with the odometer and odometer correction classes to allow the testing of
   * their functionality. The method controls the path of the robot by specifying
   * the total degrees the left and right motors need to turn, thus achieving the
   * purpose of going a fixed distance or turning a fixed angle.
   */
  public static void drive() {
    // reset the motors
    leftMotor.stop();
    rightMotor.stop();
    leftMotor.setAcceleration(ACCELERATION);
    rightMotor.setAcceleration(ACCELERATION);

    // Sleep for 3 seconds
    Main.sleepFor(SLEEP_TIME);

    // loops 4 times for 4 sides of the square
    for (int i = 0; i < 4; i++) {
      // drive forward three tiles
      leftMotor.setSpeed(FORWARD_SPEED);
      rightMotor.setSpeed(FORWARD_SPEED);

      // immediately pass control out to next motor to ensure synchronization
      leftMotor.rotate(convertDistance(TILE_RATIO * TILE_SIZE), true); // 2.99 instead of 3 so that robot doesn't fall off tile                                                                 
      rightMotor.rotate(convertDistance(TILE_RATIO * TILE_SIZE), false);

      // turn 90 degrees clockwise
      leftMotor.setSpeed(ROTATE_SPEED);
      rightMotor.setSpeed(ROTATE_SPEED);

      // rotate both motors at the same time 90 deg CW
      leftMotor.rotate(convertAngle(90.0), true);
      rightMotor.rotate(-convertAngle(90.0), false);
    }
  }

  /**
   * Converts input distance to the total rotation of each wheel needed to cover
   * that distance.
   * 
   * @param distance
   * @return the wheel rotations necessary to cover the distance
   */
  public static int convertDistance(double distance) {
    return (int) ((180.0 * distance) / (Math.PI * WHEEL_RAD));
  }

  /**
   * Converts input angle to the total rotation of each wheel needed to rotate the
   * robot by that angle.
   * 
   * @param angle
   * @return the wheel rotations necessary to rotate the robot by the angle
   */
  public static int convertAngle(double angle) {
    return convertDistance(Math.PI * TRACK * angle / 360.0);
  }
}
