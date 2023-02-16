package ca.mcgill.ecse211.lab4;

import static ca.mcgill.ecse211.lab4.Resources.*;
import lejos.hardware.Button;

public class Main {

  /**
   * Specifies whether user chose falling edge or rising edge.
   */
  public static boolean wall;

  public static void main(String[] args) {

    int buttonChoice; // record choice made by user

    UltrasonicLocalizer us = new UltrasonicLocalizer();
    LightLocalizer light = new LightLocalizer();
    Display display = new Display(); // initialize display for odometer

    do {

      LCD.clear(); // clear display

      LCD.drawString("< Left  | Right > ", 0, 0);
      LCD.drawString("        |         ", 0, 1);
      LCD.drawString(" Rising | Falling ", 0, 2);
      LCD.drawString(" edge   | edge    ", 0, 3);

      buttonChoice = Button.waitForAnyPress();

      if (buttonChoice == Button.ID_ESCAPE) { // allows exiting to menu and not run program
        System.exit(0);
      }

    } while (buttonChoice != Button.ID_LEFT && buttonChoice != Button.ID_RIGHT);

    if (buttonChoice == Button.ID_LEFT) { // rising edge
      wall = false;
      new Thread(odo).start(); // start odometer
      new Thread(display).start(); // start display
      us.run(); // run ultrasonic localizer
      buttonChoice = Button.waitForAnyPress();
      if (buttonChoice == Button.ID_ESCAPE) { // exit to menu
        System.exit(0);
      } else if (buttonChoice == Button.ID_ENTER) { // run light localizer
        light.run();
      }

    } else if (buttonChoice == Button.ID_RIGHT) { // falling edge
      wall = true;
      new Thread(odo).start(); // start odometer
      new Thread(display).start(); // start display
      us.run(); // run ultrasonic localizer
      buttonChoice = Button.waitForAnyPress();
      if (buttonChoice == Button.ID_ESCAPE) { // exit to menu
        System.exit(0);
      } else if (buttonChoice == Button.ID_ENTER) { // run light localizer
        light.run();
      }
    }

    while (Button.waitForAnyPress() != Button.ID_ESCAPE)
      ;
    System.exit(0);
  }
}