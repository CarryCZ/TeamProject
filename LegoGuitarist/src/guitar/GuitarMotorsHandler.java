package guitar;

import org.apache.log4j.Logger;

import guitar.motors.*;

/**
 * Handles communication with all motors within the system.
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class GuitarMotorsHandler {
	/** logger for program messages output feed */
	private static final Logger LOGGER = Logger.getLogger(GuitarMotorsHandler.class);
	/** field of all fret motors used in the setup {fret1, fret2, fret3} */
	private final GuitarFretMotor[] motors = new GuitarFretMotor[3];
	private final GuitarStrumMotor strumMotor;
	private final GuitarRhythmMotor rhythmMotor;
	private final GuitarBassMotor bassMotor;
	
	public GuitarMotorsHandler(GuitarBrick master, GuitarBrick slave) {
		LOGGER.debug("Object: GuitarMotorshandler created.");
		
		motors[0] = new GuitarFretMotor(1, GuitarConstants.MOTOR_PORT_FRET1, slave);
		motors[1] = new GuitarFretMotor(2, GuitarConstants.MOTOR_PORT_FRET2, slave);
		motors[2] = new GuitarFretMotor(3, GuitarConstants.MOTOR_PORT_FRET3, slave);
		strumMotor = new GuitarStrumMotor(GuitarConstants.MOTOR_PORT_STRUMMING, master);
		rhythmMotor = new GuitarRhythmMotor(GuitarConstants.MOTOR_PORT_RHYTHM, master);
		bassMotor = new GuitarBassMotor(GuitarConstants.MOTOR_PORT_BASS, master);
	}
	
	/**
	 * Rotate motor on the given fret to the next position.
	 * 
	 * @param fret - acceptable values are {1,2,3}
	 */
	public void makeStepFret(int fret) {
		motors[fret-1].makeStepForward();
	}
	
	public void makeStepRhythmForward() {
		rhythmMotor.makeStepForward();
	}
	
	public void makeStepRhythmBackward() {
		rhythmMotor.makeStepBackward();
	}
	
	public void makeStepBassForward() {
		bassMotor.makeStepForward();
	}
	
	public void makeStepBassBackward() {
		bassMotor.makeStepBackward();
	}
	
	/**
	 * Makes the motor rotate at given speed.
	 * 
	 * @param speed - in deg/s (for 0 it breaks)
	 */
	public void setSpeedStrumMotor(int speed) {
		if (speed == 0)
			strumMotor.stop();
		else
			strumMotor.rotate(speed);
	}
}
