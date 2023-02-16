package ca.mcgill.ecse211.lab2;

import static ca.mcgill.ecse211.lab2.Resources.*;
import lejos.hardware.Sound;

/**
 * This class specifies the algorithm for correction the odometry mechanism on
 * the EV3 robot.
 */
public class OdometryCorrection implements Runnable {

  /**
   * The (x, y, theta) position as an array.
   */
  private double[] position;

  /*
   * Here is where the odometer correction code should be run.
   */
  public void run() {
    long correctionStart, correctionEnd; // record start time, end time

    int xCount = -1, yCount = -1; // count for the number of lines traversed
    double newX, newY; // new position values for x and y

    while (true) {
      correctionStart = System.currentTimeMillis(); // record start time

      myColorSample.fetchSample(sampleColor, 0); // fetch data from sensor

      // current position
      position = odometer.getXYT();
      double theta = position[2] % 360;

      // when the sensor detects a black line (i.e. difference in color ratio)
      if ((sampleColor[0] * FACTOR) < MAX_RATIO && (sampleColor[0] * FACTOR) > MIN_RATIO) {
        Sound.beep();

        if (theta > (0 - THRESHOLD) * TO_RAD && theta < (0 + THRESHOLD) * TO_RAD) // angle is 0: up; increase in y
        {
          yCount++;
          newY = yCount * TILE_SIZE;
          odometer.setY(newY); // update y
          odometer.setTheta(0);
        } else if (theta > (90 - THRESHOLD) * TO_RAD && theta < (90 + THRESHOLD) * TO_RAD) // angle is 90: right;
                                                                                           // increase in x
        {
          xCount++;
          newX = xCount * TILE_SIZE;
          odometer.setX(newX); // update x
          odometer.setTheta((90) * TO_RAD);
        } else if (theta > (180 - THRESHOLD) * TO_RAD && theta < (180 + THRESHOLD) * TO_RAD) // angle is 180: down;
                                                                                             // decrease in y
        {
          newY = yCount * TILE_SIZE;
          odometer.setY(newY); // update y
          odometer.setTheta((180) * TO_RAD);
          yCount--;
        } else if (theta > (270 - THRESHOLD) * TO_RAD && theta < (270 + THRESHOLD) * TO_RAD) // angle is 270: left;
                                                                                             // decrease in x
        {
          newX = xCount * TILE_SIZE;
          odometer.setX(newX); // update x
          odometer.setTheta((270) * TO_RAD);
          xCount--;
        }
      }

      // this ensures the odometry correction occurs only once every period
      correctionEnd = System.currentTimeMillis();
      if (correctionEnd - correctionStart < CORRECTION_PERIOD) {
        Main.sleepFor(CORRECTION_PERIOD - (correctionEnd - correctionStart));
      }
    }
  }
}
