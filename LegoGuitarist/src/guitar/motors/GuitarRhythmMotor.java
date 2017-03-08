package guitar.motors;

import org.apache.log4j.Logger;

import guitar.GuitarBrick;
import guitar.GuitarConstants;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.robotics.RegulatedMotor;

/**
 * Handler for the motor managing switching rhythms.
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class GuitarRhythmMotor implements GuitarStepMotor {
	/** logger for program messages output feed */
	private static final Logger LOGGER = Logger.getLogger(GuitarRhythmMotor.class);
	
	/** how many possible positions this automaton has */
	private static final int NUMBER_OF_POSITIONS = 4;
	
	/** to which port in the brick is this motor connected */
	private final String port;
	/** handler of this motor from native library */
	private final RegulatedMotor motor;
	/** in which position this motor is */
	private int currentPosition;
	
	public GuitarRhythmMotor(String port, GuitarBrick master) {
		LOGGER.debug("Object: GuitarRhythmMotor created.");
		
		if (!port.equalsIgnoreCase("A") && !port.equalsIgnoreCase("B")
				&& !port.equalsIgnoreCase("C") && !port.equalsIgnoreCase("D"))
			throw new IllegalArgumentException("GuitarFretMotor can be initialized only on ports A,B,C,D.");
		
		this.port = port;
		motor = new EV3MediumRegulatedMotor(master.getBrick().getPort(port));
		motor.setAcceleration(GuitarConstants.MOTOR_ACCELERATION);
		motor.setSpeed(GuitarConstants.MOTOR_SPEED);
		currentPosition = 1;
	}

	@Override
	public String getPort() {
		return port;
	}
	
	@Override
	public int getPosition() {
		return currentPosition;
	}

	@Override
	public void makeStepForward() {
		if(currentPosition == NUMBER_OF_POSITIONS) {
			throw new UnsupportedOperationException("Cannot proceed far more. The motor is at its end position.");
		}
		currentPosition++;
		new Thread(new Runnable() {
			public void run() {
				motor.rotate(GuitarConstants.MOTOR_RHYTHM_STEP);
			}
		}).start();
	}

	@Override
	public void makeStepBackward() {
		if(currentPosition == 1) {
			throw new UnsupportedOperationException("Cannot back far more. The motor is at its start position.");
		}
		currentPosition--;
		new Thread(new Runnable() {
			public void run() {
				motor.rotate(-GuitarConstants.MOTOR_RHYTHM_STEP);
			}
		}).start();		
	}
	
}
