package ca.mcgill.ecse211.lab4;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;

/**
 * Class for static resources (things that stay the same throughout the entire
 * program execution), like constants and hardware.
 * 
 * Use these resources in other files by adding this line at the top (see
 * examples):<br>
 * 
 * {@code import static ca.mcgill.ecse211.lab4.Resources.*;}
 */

public class Resources {

  /**
   * The radius of the wheel.
   */
  public static final double WHEEL_RAD = 2.19;

  /**
   * The width of the robot (distance between wheels).
   */
  public static final double TRACK = 11.6;
  
  /**
   * Wheel radius constant used for Ultrasonic Localizer.
   */
  public static final double US_WHEEL_RAD = 2.2;

  /**
   * Track constant used for Ultrasonic Localizer
   */
  public static final double US_TRACK = 10.95;

  /**
   * Robot distance from wall.
   */
  public static final int WALL_DISTANCE = 40;

  /**
   * Wall distance for rising edge.
   */
  public static final int REDISTANCE = 30;

  /**
   * Wall distance for falling edge.
   */
  public static final int FEDISTANCE = 100;

  /**
   * Offset value for rising edge.
   */
  public static final double REOFFSET = 49;

  /**
   * Offset value for falling edge.
   */
  public static final double FEOFFSET = 46;

  /**
   * The size of one tile.
   */
  public static final double TILE_SIZE = 30.48;

  /**
   * Distance the robot backs up to align itself on the Y-axis.
   */
  public static final int BACKUP_Y = 13;

  /**
   * Distance the robot backs up to align itself on the X-axis.
   */
  public static final int BACKUP_X = 9;

  /**
   * Light threshold constant used by color sensor to detect a black line.
   */
  public static final double INTENSITY_THRESHOLD = 0.2;

  /**
   * Sleep time for thread.
   */
  public static final int SLEEP = 2000;

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
  public static final int ROTATE_SPEED = 100;

  /**
   * Filter out value for ultrasonic sensor filtering.
   */
  public static final int FILTER_OUT = 30;

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
   * The color sensor.
   */
  public static final EV3ColorSensor COLOR = new EV3ColorSensor(SensorPort.S3);
  public static SampleProvider myColorSample = COLOR.getMode("Red"); // set mode of color sensor

  /**
   * The LCD.
   */
  public static final TextLCD LCD = LocalEV3.get().getTextLCD();

  /**
   * The odometer.
   */
  public static Odometer odo = Odometer.getOdometer();
}
