package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

/**
 * 
 * @author dafneculha
 * P- type implementation of the controller
 */

public class PController extends UltrasonicController {

  public PController() {

    LEFT_MOTOR.setSpeed(MOTOR_SPEED); // Initialize motor 
    RIGHT_MOTOR.setSpeed(MOTOR_SPEED);
    LEFT_MOTOR.forward();
    RIGHT_MOTOR.forward();
  }

  @Override
  /**
   * A method to perform actions based on US data to implement p-type control
   * @param distance
   * @return    
   */

  public void processUSData(int distance) {


    filter(distance);


    // A variable to determine the position of robot with respect to the wall 
    //(ie. too far, too close and extremely close)
    // If robot is further from the wall than it should be, bandcenter - distance gives a negative value
    // If robot is closer the wall than it should be, bandcenter - distance gives a positive value
    int distError = P_BAND_CENTER - distance;


    // Variable for speed correction;
    int pDelta;

    // If the distance between the robot and the wall is within accepted limits, 
    //keep going forward by turning both motors at our default speed.

    if (Math.abs(distError) <= BAND_WIDTH) {
      LEFT_MOTOR.setSpeed(MOTOR_SPEED); 
      RIGHT_MOTOR.setSpeed(MOTOR_SPEED);
      LEFT_MOTOR.forward();
      RIGHT_MOTOR.forward(); 
    }

    // If the robot is closer to the wall than it should be,
    // Evaluate the closeness of the robot
    // If it's extremely close to the wall, 
    // take a very sharp turn away from the wall

    // If it's too close but not extremely close, 
    // turn a bit towards right 

    else if (distError > 0) {


      // If it's extremely close to the wall, 
      // take a very sharp turn away from the wall
      // by setting the outer wheel (right) turn backwards and inner wheel (right) turn forwards at low speeds.
      // While calculating speed adjustment,
      // to avoid speed becoming too low, set pDelta as Minimum correction.
      // If it's closer than it should be but not extremely close, 
      // then set the speed of inner wheel to high and speed of outer wheel to low so it goes a bit further away from the wall.


      if (distError <  P_VERY_CLOSE)  {


        pDelta = calculateCorrection(distError);

        // to avoid speed becoming too low, set pDelta as a minimum value.


        LEFT_MOTOR.setSpeed((int)(MOTOR_SPEED) - pDelta); // both at low speed
        RIGHT_MOTOR.setSpeed((int)(MOTOR_SPEED) - pDelta);
        RIGHT_MOTOR.backward();
        LEFT_MOTOR.forward(); 

      }

      // If it's too close but not extremely close, 
      // calculate speed adjustment 
      // and turn right 
      // by setting left motor to low speed forward and right motor to high speed backwards. 


      else {

        pDelta = calculateCorrection(distError);
        RIGHT_MOTOR.setSpeed((int)(MOTOR_SPEED - pDelta));
        LEFT_MOTOR.setSpeed((int)(MOTOR_SPEED) + pDelta);
        LEFT_MOTOR.forward();
        RIGHT_MOTOR.forward(); 
      }

    } 


    // If robot is further from the wall than it should be,
    // Calculate speed correction
    // increase the speed of outer wheel (right) and decrease the speed of lower wheel

    else if (distError < 0) {
      pDelta = calculateCorrection(distError);


      // To avoid very sharp turns or spinning when the robot is too far from the wall and distError is too large
      if (pDelta > MAX_CORRECTION) {
        pDelta = MAX_CORRECTION;

        LEFT_MOTOR.setSpeed((int)(MOTOR_SPEED - pDelta));
        RIGHT_MOTOR.setSpeed((int)(MOTOR_SPEED) + pDelta/2);
        LEFT_MOTOR.forward();
        RIGHT_MOTOR.forward(); 

      }


      else {

        LEFT_MOTOR.setSpeed((int)(MOTOR_SPEED) - pDelta);
        RIGHT_MOTOR.setSpeed((int)(MOTOR_SPEED) + pDelta);
        LEFT_MOTOR.forward();
        RIGHT_MOTOR.forward();

      }
    }

  } 


  /**
   *  A method to calculate the speed correction the wheels need in order for robot to follow the wall
   * @param distError
   * @return
   */
  public int calculateCorrection (int distError) {

    int correction = (int) (Math.abs(distError) * PCONST);

    return correction;
  } 

  @Override
  public int readUSDistance() {
    return this.distance;
  }

}
