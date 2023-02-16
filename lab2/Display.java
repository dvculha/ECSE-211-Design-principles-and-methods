package ca.mcgill.ecse211.lab2;

import java.text.DecimalFormat; // for display

//static import to avoid duplicating variables and make the code easier to read
import static ca.mcgill.ecse211.lab2.Resources.*;

/**
 * This class is used to display the content of the odometer variables (x, y,
 * Theta)
 */
public class Display implements Runnable {

  /**
   * The (x, y, theta) position as an array.
   */
  private double[] position;

  /**
   * The time limit to stop the display program.
   */
  private long timeout = Long.MAX_VALUE;

  public void run() {

    LCD.clear(); // clear ev3 display

    long updateStart, updateEnd; // stores method start time, finish time

    long tStart = System.currentTimeMillis();

    do {
      updateStart = System.currentTimeMillis(); // record current time

      // Retrieve x, y and Theta information
      position = odometer.getXYT();

      // Print x,y, and theta information
      DecimalFormat numberFormat = new DecimalFormat("######0.00");
      LCD.drawString("X: " + numberFormat.format(position[0]), 0, 0);
      LCD.drawString("Y: " + numberFormat.format(position[1]), 0, 1);
      LCD.drawString("T: " + numberFormat.format(position[2]), 0, 2);

      // this ensures that the data is updated only once every period
      updateEnd = System.currentTimeMillis(); // record current time

      // check if time elapsed is shorter than required
      if (updateEnd - updateStart < DISPLAY_PERIOD) {
        try {
          // suspend thread until time period is satisfied
          Thread.sleep(DISPLAY_PERIOD - (updateEnd - updateStart));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      // if loop takes too long, stop program
    } while ((updateEnd - tStart) <= timeout);

  }

  /**
   * Sets the timeout in ms.
   * 
   * @param timeout
   */
  public void setTimeout(long timeout) {
    this.timeout = timeout;
  }

  /**
   * Shows the text on the LCD, line by line.
   * 
   * @param strings
   *          comma-separated list of strings, one per line
   */
  // public static void showText(String... strings) {
  // LCD.clear();
  // for (int i = 0; i < strings.length; i++) {
  // LCD.drawString(strings[i], 0, i);
  // }
  // }
}
