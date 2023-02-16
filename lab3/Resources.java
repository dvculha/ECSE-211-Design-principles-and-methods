package ca.mcgill.ecse211.lab3;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorModes;

/**
 * Class for static resources (things that stay the same throughout the entire
 * program execution), like constants and hardware.
 * 
 * Use these resources in other files by adding this line at the top (see
 * examples):<br>
 * 
 * {@code import static ca.mcgill.ecse211.lab3.Resources.*;}
 */

public class Resources {

  /**
   * The radius of the wheel.
   */
  public static final double WHEEL_RAD = 2.18;

  /**
   * The width of the robot (distance between wheels).
   */
  public static final double TRACK = 9.35;

  /**
   * The size of one tile.
   */
  public static final double TILE_SIZE = 30.48;

  /**
   * Offset from the wall (cm) for bang-bang approach.
   */
  public static final int BAND_CENTER = 9;

  /**
   * Width of dead band (cm) for bang-bang approach.
   */
  public static final int BAND_WIDTH = 3;

  /**
   * Acceleration of motors.
   */
  public static final int ACCELERATION = 3000;

  /**
   * Forward speed of motors.
   */
  public static final int FORWARD_SPEED = 222;

  /**
   * Rotate speed for motors for turns.
   */
  public static final int ROTATE_SPEED = 150;

  /**
   * Forward speed of motors for avoidance.
   */
  public static final int AVOIDANCE_SPEED = 150;

  /**
   * Speed around obstacle for avoidance if robot is too far.
   */
  public static final int FAR_SPEED = 215;

  /**
   * Speed to exit away from obstacle for avoidance.
   */
  public static final int CLOSE_SPEED = 100;

  /**
   * Buffer to fix final theta after avoidance.
   */
  public static final double END_BUFFER = 0.85;

  /**
   * Filter out value for ultrasonic sensor filtering.
   */
  public static final int FILTER_OUT = 25;

  /**
   * Maximum distance reading for filtering data from US sensor.
   */
  public static final int MAX_DISTANCE_FILTER = 255;

  /**
   * Odometer update period.
   */
  public static final long ODOMETER_PERIOD = 25;

  /**
   * Screen display period.
   */
  public static final long DISPLAY_PERIOD = 250;

  /**
   * PI value.
   */
  public static final double PI = Math.PI;

  /**
   * Degrees to rad conversion factor.
   */
  public static final double TO_RAD = Math.PI / 180.0;

  /**
   * Left motor.
   */
  public static final EV3LargeRegulatedMotor LEFT_MOTOR = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));

  /**
   * Right motor.
   */
  public static final EV3LargeRegulatedMotor RIGHT_MOTOR = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("D"));

  /**
   * Ultrasonic sensor.
   */
  public static final Port USPORT = LocalEV3.get().getPort("S2");
  public static final SensorModes SENSOR = new EV3UltrasonicSensor(USPORT); // initialize sensor

  /**
   * The LCD.
   */
  public static final TextLCD LCD = LocalEV3.get().getTextLCD();

  /**
   * The odometer.
   */
  public static Odometer odo = Odometer.getOdometer();

  /**
   * Map choice.
   */
  public static final int MAP = 1;

  /**
   * Maps arrays.
   */
  public static final int[][][] MAPS = { { { 1, 3 }, { 2, 2 }, { 3, 3 }, { 3, 2 }, { 2, 1 } },
      { { 2, 2 }, { 1, 3 }, { 3, 3 }, { 3, 2 }, { 2, 1 } } };
}
