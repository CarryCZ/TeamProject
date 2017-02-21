package guitar;

/**
 * Holds constants for the guitar playing robot.
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class GuitarConstants {
	
	// BRICKS NAMES //
	
	/** Holds name of the master brick */
	public static final String MASTER_BRICK_NAME = "Primary";
	/** Holds name of the slave brick */
	public static final String SLAVE_BRICK_NAME = "Secondary";
	
	
	// LED PATTERNS VALUES //
	
	/** value of LED pattern to turn the LED off */
	public static final int LED_OFF = 0;
	
	/** value of LED pattern to lit the LED green */
	public static final int LED_GREEN_STATIC = 1;
	/** value of LED pattern to lit the LED red */
	public static final int LED_RED_STATIC = 2;
	/** value of LED pattern to lit the LED orange */
	public static final int LED_ORANGE_STATIC = 3;
	
	/** value of LED pattern to make the LED blink green slowly */
	public static final int LED_GREEN_SLOW_BLINK = 4;
	/** value of LED pattern to make the LED blink red slowly */
	public static final int LED_RED_SLOW_BLINK = 5;
	/** value of LED pattern to make the LED blink orange slowly */
	public static final int LED_ORANGE_SLOW_BLINK = 6;
	
	/** value of LED pattern to make the LED blink green fast */
	public static final int LED_GREEN_FAST_BLINK = 7;
	/** value of LED pattern to make the LED blink red fast */
	public static final int LED_RED_FAST_BLINK = 8;
	/** value of LED pattern to make the LED blink orange fast */
	public static final int LED_ORANGE_FAST_BLINK = 9;
	
	
	// MOTORS VALUES //
	
	/** all motors acceleration in deg/s^2 */
	public static final int MOTOR_ACCELERATION = 900;
	/** all motors speed in deg/s (should not be larger than voltage times 10) */
	public static final int MOTOR_SPEED = 740;
	/** how many degrees of rotation is one step of the automata */
	public static final int MOTOR_ONE_STEP = 360;
	
	/** identifier of large motors (side axel) */
	public static final char MOTOR_ID_LARGE = 'L';
	/** identifier of medium motors (straight axel) */
	public static final char MOTOR_ID_MEDIUM = 'M';
	
	/** port to which the motor servicing 1st fret is connected */
	public static final String MOTOR_PORT_FRET1 = "A";
	/** port to which the motor servicing 2nd fret is connected */
	public static final String MOTOR_PORT_FRET2 = "B";
	/** port to which the motor servicing 3rd fret is connected */
	public static final String MOTOR_PORT_FRET3 = "C";
	/** port to which the motor servicing strumming movement is connected */
	public static final String MOTOR_PORT_STRUMMING = "A";
	/** port to which the motor servicing rhytm selection is connected */
	public static final String MOTOR_PORT_RHYTM_SELECTOR = "B";
	/** port to which the motor servicing selection of root string is connected */
	public static final String MOTOR_PORT_ROOT_SELECTOR = "C";
}
