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
	
}
