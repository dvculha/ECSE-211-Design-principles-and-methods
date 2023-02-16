package ca.mcgill.ecse211.lab4;

//static import to avoid duplicating variables and make the code easier to read
import static ca.mcgill.ecse211.lab4.Resources.*;
import lejos.hardware.Sound;

public class LightLocalizer implements Runnable {

  // initialize variables for color sensor
  private float[] colorData;
  private float colorIntensity;

  /**
   * LightLocalizer default constructor.
   */
  public LightLocalizer() {
    colorData = new float[COLOR.sampleSize()];
  }

  /**
   * Retrieves the light reflection intensity from the color sensor.
   * 
   * @return void
   */
  public void getColorIntensity() {
    myColorSample.fetchSample(colorData, 0);
    this.colorIntensity = colorData[0];
  }

  /**
   * Calls the constructor of LightLocalizer.
   */
  public void run() {
    try {
      Thread.sleep(SLEEP);
    } catch (InterruptedException e) {
    }
    localization();
  }

  /**
   * The process in which the robot advances to 1,1 and aligns itself properly on
   * the x and y-axis using the BACKUP_X and BACKUP_Y distances respectively.
   */
  public void localization() {

    getColorIntensity(); // get data from color sensor

    while (colorIntensity > INTENSITY_THRESHOLD) { // keep moving forward until black line detected
      moveForward();
      getColorIntensity();
    }

    Sound.beep(); // beep when black line is detected
    stop(); // stop robot
    LEFT_MOTOR.rotate(convertAngle(WHEEL_RAD, TRACK, 90), true); // rotate wheels 90 degrees CW
    RIGHT_MOTOR.rotate(-convertAngle(WHEEL_RAD, TRACK, 90), false);
    getColorIntensity();

    while (colorIntensity > INTENSITY_THRESHOLD) { // keep moving forward again until black line detected
      moveForward();
      getColorIntensity();
    }

    Sound.beep(); // beep since black line detected
    stop(); // stop robot again
    LEFT_MOTOR.rotate(convertDistance(WHEEL_RAD, -BACKUP_Y), true); // align robot on y-axis
    RIGHT_MOTOR.rotate(convertDistance(WHEEL_RAD, -BACKUP_Y), false); // by backing up appropriate amount
    stop();

    LEFT_MOTOR.rotate(-convertAngle(WHEEL_RAD, TRACK, 90), true); // rotate 90 degrees to face 0-degree angle
    RIGHT_MOTOR.rotate(convertAngle(WHEEL_RAD, TRACK, 90), false);
    stop();

    LEFT_MOTOR.rotate(convertDistance(WHEEL_RAD, -BACKUP_X), true); // align robot on x-axis
    RIGHT_MOTOR.rotate(convertDistance(WHEEL_RAD, -BACKUP_X), false); // by backing up appropriate amount
    stop();
  }

  /**
   * Moves robot forward.
   */
  public void moveForward() {
    LEFT_MOTOR.forward();
    RIGHT_MOTOR.forward();
  }

  /**
   * Stops robot.
   */
  public void stop() {
    LEFT_MOTOR.stop(true);
    RIGHT_MOTOR.stop(false);
  }

  /**
   * Convert distance for robot motors.
   * 
   * @param radius
   * @param distance
   * @return
   */
  public static int convertDistance(double radius, double distance) {
    return (int) ((180.0 * distance) / (PI * radius));
  }

  /**
   * Convert angle for robot motors.
   * 
   * @param radius
   * @param width
   * @param angle
   * @return
   */
  public static int convertAngle(double radius, double width, double angle) {
    return convertDistance(radius, PI * width * angle / 360.0);
  }
}
