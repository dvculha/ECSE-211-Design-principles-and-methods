package ca.mcgill.ecse211.lab2;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.*;
import lejos.robotics.SampleProvider;

/**
 * This class is used to define static resources in one place for easy access
 * and to avoid cluttering the rest of the codebase. All resources can be
 * imported at once like this:
 * 
 * <p>
 * {@code import static ca.mcgill.ecse211.lab3.Resources.*;}
 */
public class Resources {

  /**
   * The wheel radius in centimeters.
   */
  public static final double WHEEL_RAD = 2.18; 

  /**
   * The robot width in centimeters in terms of center of its left and right
   * wheels.
   */
  public static final double TRACK = 9.56;

  /**
   * The speed at which the robot moves forward in degrees per second.
   */
  public static final int FORWARD_SPEED = 250;

  /**
   * The speed at which the robot rotates in degrees per second.
   */
  public static final int ROTATE_SPEED = 150;

  /**
   * The tile size in centimeters (of one tile).
   */
  public static final double TILE_SIZE = 30.48;

  /**
   * The offset for the sensor.
   */
  public static final double SENSOR_OFFSET = 2.5;

  /**
   * LCD refresh frequency
   */
  public static final long DISPLAY_PERIOD = 25;

  /**
   * The odometer update period in ms.
   */
  public static final long ODOMETER_PERIOD = 25;

  /**
   * Sets the correction frequency, controlling each correction loop at specified
   * period.
   */
  public static final long CORRECTION_PERIOD = 10;

  /**
   * Acceptable delta difference in theta angle.
   */
  public static final double THRESHOLD = 1.0;

  /**
   * Multiplication factor for data received from sensor.
   */
  public static final int FACTOR = 1000;

  /**
   * Minimum color ratio value from sensor.
   */
  public static final int MIN_RATIO = 100;

  /**
   * Maximum color ratio value from sensor.
   */
  public static final int MAX_RATIO = 175;

  /**
   * Motor acceleration value.
   */
  public static final int ACCELERATION = 3000;

  /**
   * Main thread sleep time.
   */
  public static final int SLEEP_TIME = 3000;

  /**
   * Degrees to rad conversion factor.
   */
  public static final double TO_RAD = Math.PI / 180.0;

  /**
   * Rad to degrees conversion factor.
   */
  public static final double TO_DEG = 180.0 / Math.PI;

  /**
   * The value for the tile ratio covered by the robot so that it doesn't fall off
   * the demo floor.
   */
  public static final double TILE_RATIO = 2.98;

  /**
   * The left motor.
   */
  public static final EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));

  /**
   * The right motor.
   */
  public static final EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("D"));

  /**
   * The color sensor.
   */
  public static final EV3ColorSensor color = new EV3ColorSensor(SensorPort.S1);
  public static SensorModes myColor = color;
  public static SampleProvider myColorSample = myColor.getMode("Red"); // set mode of color sensor
  public static float[] sampleColor = new float[myColor.sampleSize()]; // array to store sample data

  /**
   * The LCD.
   */
  public static final TextLCD LCD = LocalEV3.get().getTextLCD();

  /**
   * The odometer.
   */
  public static Odometer odometer = Odometer.getOdometer();

}
