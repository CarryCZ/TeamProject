package guitar;

import org.apache.log4j.Logger;

import lejos.hardware.Brick;
import lejos.hardware.LED;

/**
 * Handles communication with all LEDs within the system.
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class GuitarLedHandler {
	/** logger for program messages output feed */
	private static final Logger LOGGER = Logger.getLogger(GuitarLedHandler.class);
	
	/** holds reference to the master's LED */
	private final LED masterLED;
	/** holds reference to the slave's LED */
	private final LED slaveLED;
	
	public GuitarLedHandler(Brick masterBrick, Brick slaveBrick) {
		LOGGER.debug("Object: GuitarLedHandler created.");
		masterLED = masterBrick.getLED();
		slaveLED = slaveBrick.getLED();
	}
	
	// MASTER'S LED HANDLING //
	
	/**
	 * Sets the given pattern to the master's LED.
	 * 
	 * @param pattern - see {@link GuitarConstants} for possible patterns
	 */
	public void setPatternToMaster(int pattern) {
		LOGGER.trace("GuitarLedHandler: Master's LED pattern set to: " + pattern + ".");
		masterLED.setPattern(pattern);
	}
	
	/** Lit the master's LED green. */
	public void setStaticGreenToMaster() {
		LOGGER.trace("GuitarLedHandler: Master's LED pattern set to: Static Green.");
		masterLED.setPattern(GuitarConstants.LED_GREEN_STATIC);
	}
	
	/** Lit the master's LED red. */
	public void setStaticRedToMaster() {
		LOGGER.trace("GuitarLedHandler: Master's LED pattern set to: Static Red.");
		masterLED.setPattern(GuitarConstants.LED_RED_STATIC);
	}
	
	/** Lit the master's LED orange. */
	public void setStaticOrangeToMaster() {
		LOGGER.trace("GuitarLedHandler: Master's LED pattern set to: Static Orange.");
		masterLED.setPattern(GuitarConstants.LED_ORANGE_STATIC);
	}
	
	/** Make the master's LED blink green slowly. */
	public void setSlowBlinkGreenToMaster() {
		LOGGER.trace("GuitarLedHandler: Master's LED pattern set to: Slow Blink Green.");
		masterLED.setPattern(GuitarConstants.LED_GREEN_SLOW_BLINK);
	}
	
	/** Make the master's LED blink red slowly. */
	public void setSlowBlinkRedToMaster() {
		LOGGER.trace("GuitarLedHandler: Master's LED pattern set to: Slow Blink Red.");
		masterLED.setPattern(GuitarConstants.LED_RED_SLOW_BLINK);
	}
	
	/** Make the master's LED blink orange slowly. */
	public void setSlowBlinkOrangeToMaster() {
		LOGGER.trace("GuitarLedHandler: Master's LED pattern set to: Slow Blink Orange.");
		masterLED.setPattern(GuitarConstants.LED_ORANGE_SLOW_BLINK);
	}
	
	/** Make the master's LED blink green fast. */
	public void setFastBlinkGreenToMaster() {
		LOGGER.trace("GuitarLedHandler: Master's LED pattern set to: Fast Blink Green.");
		masterLED.setPattern(GuitarConstants.LED_GREEN_FAST_BLINK);
	}
	
	/** Make the master's LED blink red fast. */
	public void setFastBlinkRedToMaster() {
		LOGGER.trace("GuitarLedHandler: Master's LED pattern set to: Fast Blink Red.");
		masterLED.setPattern(GuitarConstants.LED_RED_FAST_BLINK);
	}
	
	/** Make the master's LED blink orange fast. */
	public void setFastBlinkOrangeToMaster() {
		LOGGER.trace("GuitarLedHandler: Master's LED pattern set to: Fast Blink Orange.");
		masterLED.setPattern(GuitarConstants.LED_ORANGE_FAST_BLINK);
	}
	
	// SLAVE'S LED HANDLING //
	
	/**
	 * Sets the given pattern to the slave's LED.
	 * 
	 * @param pattern - see {@link GuitarConstants} for possible patterns
	 */
	public void setPatternToSlave(int pattern) {
		LOGGER.trace("GuitarLedHandler: Slave's LED pattern set to: " + pattern + ".");
		slaveLED.setPattern(pattern);
	}
	
	/** Lit the slave's LED green. */
	public void setStaticGreenToSlave() {
		LOGGER.trace("GuitarLedHandler: Slave's LED pattern set to: Static Green.");
		slaveLED.setPattern(GuitarConstants.LED_GREEN_STATIC);
	}
	
	/** Lit the slave's LED red. */
	public void setStaticRedToSlave() {
		LOGGER.trace("GuitarLedHandler: Slave's LED pattern set to: Static Red.");
		slaveLED.setPattern(GuitarConstants.LED_RED_STATIC);
	}
	
	/** Lit the slave's LED orange. */
	public void setStaticOrangeToSlave() {
		LOGGER.trace("GuitarLedHandler: Slave's LED pattern set to: Static Orange.");
		slaveLED.setPattern(GuitarConstants.LED_ORANGE_STATIC);
	}
	
	/** Make the slave's LED blink green slowly. */
	public void setSlowBlinkGreenToSlave() {
		LOGGER.trace("GuitarLedHandler: Slave's LED pattern set to: Slow Blink Green.");
		slaveLED.setPattern(GuitarConstants.LED_GREEN_SLOW_BLINK);
	}
	
	/** Make the slave's LED blink red slowly. */
	public void setSlowBlinkRedToSlave() {
		LOGGER.trace("GuitarLedHandler: Slave's LED pattern set to: Slow Blink Red.");
		slaveLED.setPattern(GuitarConstants.LED_RED_SLOW_BLINK);
	}
	
	/** Make the slave's LED blink orange slowly. */
	public void setSlowBlinkOrangeToSlave() {
		LOGGER.trace("GuitarLedHandler: Slave's LED pattern set to: Slow Blink Orange.");
		slaveLED.setPattern(GuitarConstants.LED_ORANGE_SLOW_BLINK);
	}
	
	/** Make the slave's LED blink green fast. */
	public void setFastBlinkGreenToSlave() {
		LOGGER.trace("GuitarLedHandler: Slave's LED pattern set to: Fast Blink Green.");
		slaveLED.setPattern(GuitarConstants.LED_GREEN_FAST_BLINK);
	}
	
	/** Make the slave's LED blink red fast. */
	public void setFastBlinkRedToSlave() {
		LOGGER.trace("GuitarLedHandler: Slave's LED pattern set to: Fast Blink Red.");
		slaveLED.setPattern(GuitarConstants.LED_RED_FAST_BLINK);
	}
	
	/** Make the slave's LED blink orange fast. */
	public void setFastBlinkOrangeToSlave() {
		LOGGER.trace("GuitarLedHandler: Slave's LED pattern set to: Fast Blink Orange.");
		slaveLED.setPattern(GuitarConstants.LED_ORANGE_FAST_BLINK);
	}
	
}
