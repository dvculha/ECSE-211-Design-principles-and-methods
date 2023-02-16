package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

/**
 * @author dafneculha
 * Bang Bang implementation of the controller
 */
public class BangBangController extends UltrasonicController {

  public BangBangController() {
    LEFT_MOTOR.setSpeed(0); // Start robot
    RIGHT_MOTOR.setSpeed(0);
    LEFT_MOTOR.forward();
    RIGHT_MOTOR.forward();
  }

  @Override

  /**
   * A method to perform actions based on US data
   * @param distance 
   * @return
   */
  public void processUSData(int distance) {

    filter(distance);


    // A variable to determine the position of robot with respect to the wall 
    //(ie. too far, too close and extremely close)
    // If robot is further from the wall than it should be, distError gives a negative value
    // If robot is closer the wall than it should be, distError gives a positive value
    int distError = (int) (BB_BAND_CENTER - distance);


    // If the error of distance between the robot and the wall is within accepted limits, continue
    if (Math.abs(distError) <= BAND_WIDTH) {

      LEFT_MOTOR.setSpeed(MOTOR_SPEED); 
      RIGHT_MOTOR.setSpeed(MOTOR_SPEED);
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.forward();

    } 


    // Else if, the robot is further from the wall than it should be,
    // increase the speed of outer wheel (right) and decrease the speed of lower wheel

    else if (distError < 0){

      LEFT_MOTOR.setSpeed(MOTOR_LOW);  //inner wheel slower
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH); //outer wheel faster
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.forward();

    }



    // Else if, the robot is closer to the wall than it should be,
    // Evaluate the closeness of the robot
    // If it's extremely close to the wall, take a very sharp turn by making the outer wheel turn backwards and inner wheel turn forwards.
    // If it's closer than it should be but not extremely close, then set the speed of inner wheel to high and speed of outer wheel to low so it goes a bit further from the wall.

    else if (distError > 0) {


      // extremely close

      if (distError <  BB_VERY_CLOSE) {

        RIGHT_MOTOR.setSpeed(MOTOR_LOW); // slow speed
        LEFT_MOTOR.setSpeed(MOTOR_LOW);  // slow speed
        LEFT_MOTOR.forward();
        RIGHT_MOTOR.backward();
      }

      // normal close

      else {

        RIGHT_MOTOR.setSpeed((int) (MOTOR_LOW)); //outer wheel slower
        LEFT_MOTOR.setSpeed((int) (MOTOR_HIGH));  //inner wheel faster
        LEFT_MOTOR.forward();
        RIGHT_MOTOR.forward();

      }
    }
  }

  @Override
  public int readUSDistance() {
    return this.distance;
  }

}
