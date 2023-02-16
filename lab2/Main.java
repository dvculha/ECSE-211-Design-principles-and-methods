// Lab2.java
package ca.mcgill.ecse211.lab2;

import lejos.hardware.Button;

// static import to avoid duplicating variables and make the code easier to read
import static ca.mcgill.ecse211.lab2.Resources.*;

/**
 * The main driver class for the odometry lab. Depending on the button pressed,
 * odometer, odometer display and odometer correction threads are started. Robot
 * drives a square path upon starting.
 */
public class Main {

  /**
   * The main entry point.
   * 
   * @param args
   */
  public static void main(String[] args) {

    int buttonChoice; // record button chosen by user

    // Odometer related objects
    OdometryCorrection odoCorrection = new OdometryCorrection();
    Display display = new Display(); // No need to change

    do {
      LCD.clear();

      // ask the user whether the motors should drive in a square or float
      LCD.drawString("< Left | Right >", 0, 0);
      LCD.drawString("       |        ", 0, 1);
      LCD.drawString(" Float | Drive  ", 0, 2);
      LCD.drawString("motors | in a   ", 0, 3);
      LCD.drawString("       | square ", 0, 4);

      buttonChoice = Button.waitForAnyPress(); // left or right press
    } while (buttonChoice != Button.ID_LEFT && buttonChoice != Button.ID_RIGHT); // must be either left or right

    if (buttonChoice == Button.ID_LEFT) {
      // Float the motors (float mode)
      leftMotor.forward();
      leftMotor.flt();
      rightMotor.forward();
      rightMotor.flt();

      // Display changes in position as wheels are (manually) moved
      new Thread(odometer).start();
      new Thread(display).start();

    } else { // other than left
      LCD.clear();

      // ask the user whether odometry correction should be run or not
      LCD.drawString("< Left | Right >", 0, 0);
      LCD.drawString("  No   | with   ", 0, 1);
      LCD.drawString(" corr- | corr-  ", 0, 2);
      LCD.drawString(" ection| ection ", 0, 3);
      LCD.drawString("       |        ", 0, 4);

      buttonChoice = Button.waitForAnyPress(); // record choice

      new Thread(odometer).start(); // start odometer + display threads
      new Thread(display).start();

      if (buttonChoice == Button.ID_RIGHT) { // if right, start correction thread
        new Thread(odoCorrection).start();
      }

      // spawn a new Thread to avoid SquareDriver.drive() from blocking
      (new Thread() {
        public void run() {
          SquareDriver.drive();
        }
      }).start();
    }

    while (Button.waitForAnyPress() != Button.ID_ESCAPE) // prevent program from ending
      ; // do nothing

    System.exit(0); // exit program
  }

  /**
   * Sleeps current thread for the specified duration.
   * 
   * @param duration
   *          sleep duration in milliseconds
   */
  public static void sleepFor(long duration) {
    try {
      Thread.sleep(duration);
    } catch (InterruptedException e) {
      // There is nothing to be done here
    }
  }
}
