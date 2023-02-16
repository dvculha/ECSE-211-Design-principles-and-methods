package ca.mcgill.ecse211.lab3;

import static ca.mcgill.ecse211.lab3.Resources.*;

import lejos.hardware.Button;
import lejos.robotics.SampleProvider;

public class Main {

  public static void main(String[] args) {

    // sensor
    SampleProvider usDistance = SENSOR.getMode("Distance"); // get mode
    float[] usData = new float[usDistance.sampleSize()]; // store data from sensor

    int buttonChoice; // record choice made by user

    // initialize display for odometer
    Display display = new Display();

    // initialize navigation and avoidance 
    Navigation nav = new Navigation();
    Avoidance avoidObs = new Avoidance(usDistance, usData);

    // setup display screen
    do {
      LCD.clear();
      LCD.drawString("< Left | Right >", 0, 0);
      LCD.drawString("       |        ", 0, 1);
      LCD.drawString(" Basic | Avoid  ", 0, 2);
      LCD.drawString(" Nav   | Obs    ", 0, 3);

      buttonChoice = Button.waitForAnyPress();
    } while (buttonChoice != Button.ID_LEFT && buttonChoice != Button.ID_RIGHT);

    new Thread(odo).start(); // start odometer
    new Thread(display).start(); // start display

    if (buttonChoice == Button.ID_RIGHT) { // if user chooses right, start avoid obstacle
      avoidObs.start();
    } else { // otherwise start basic navigation
      nav.start();
    }
    while (Button.waitForAnyPress() != Button.ID_ESCAPE)
      ;
    System.exit(0); // exit program
  }
}
