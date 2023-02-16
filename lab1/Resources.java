package ca.mcgill.ecse211.lab1;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

/**
 * Class for static resources (things that stay the same throughout the entire program execution),
 * like constants and hardware.
 * 
 * Use these resources in other files
 */

public class Resources {

  //Parameters: adjust these for desired performance

  /**
   * Offset from the wall (cm).
   * distance from wall
   */
  public static final int BB_BAND_CENTER = 28;

  /**
   * Offset from the wall (cm).
   * distance from wall
   */
  public static final int P_BAND_CENTER = 27;

  /**
   * Width of dead band (cm).
   */
  public static final int BAND_WIDTH = 3;

  /**
   * Distance that is considered extremely close to the wall in p-type
   */
  public static final int P_VERY_CLOSE = 20;
  
  /**
   * Distance that is considered extremely close to the wall in bang- bangtype
   */
  public static final int BB_VERY_CLOSE = 21;

  /**
   * Default speed for our robot.
   */
  public static final int MOTOR_SPEED = 200;

  /**
   * Speed of the faster rotating wheel (deg/sec).
   */
  public static final int MOTOR_HIGH = 240;

  /**
   * Speed of the slower rotating wheel (deg/sec).
   */
  public static final int MOTOR_LOW = 140;
  
  /**
   * Constant multiplied with distance which is used to correct the speed in p-type implementation.
   */
  public static final int PCONST = 5;

  /**
   * Maximum correction speed correction for p-type implementation when the robot is too far (distError too large)
   */
  public static final int MAX_CORRECTION = 80;

  /**
   * To ignore the gaps and tell the difference between gaps and real turns.
   * Sometimes it will get bad readings or gaps, this variable is to ignore the bad readings
   * If you see more than FILTER_OUT of bad readings, that should be a real turn
   */
  public static final int FILTER_OUT = 20;

  /**
   * The LCD screen used for displaying text.
   */ 
  public static final TextLCD TEXT_LCD = LocalEV3.get().getTextLCD();

  /**
   * The ultrasonic sensor.
   */
  public static final EV3UltrasonicSensor US_SENSOR = 
      new EV3UltrasonicSensor(LocalEV3.get().getPort("S1"));

  /**
   * The left motor.
   */
  public static final EV3LargeRegulatedMotor LEFT_MOTOR =
      new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));

  /**
   * The right motor.
   */
  public static final EV3LargeRegulatedMotor RIGHT_MOTOR =
      new EV3LargeRegulatedMotor(LocalEV3.get().getPort("D"));
}
