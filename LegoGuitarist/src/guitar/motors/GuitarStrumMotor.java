package guitar.motors;

import org.apache.log4j.Logger;

import guitar.GuitarBrick;
import guitar.GuitarConstants;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.RegulatedMotor;

/**
 * handler for the motor which manages strumming movement.
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class GuitarStrumMotor implements GuitarMotor {
	/** logger for program messages output feed */
	private static final Logger LOGGER = Logger.getLogger(GuitarStrumMotor.class);
	
	/** to which port in the brick is this motor connected */
	private final String port;
	/** handler of this motor from native library */
	private final RegulatedMotor motor;
	
	public GuitarStrumMotor(String port, GuitarBrick master) {
		LOGGER.debug("Object: GuitarStrumMotor created.");
		
		if (!port.equalsIgnoreCase("A") && !port.equalsIgnoreCase("B")
				&& !port.equalsIgnoreCase("C") && !port.equalsIgnoreCase("D"))
			throw new IllegalArgumentException("GuitarFretMotor can be initialized only on ports A,B,C,D.");
		
		this.port = port;
		motor = new EV3LargeRegulatedMotor(master.getBrick().getPort(port));
		motor.setAcceleration(GuitarConstants.MOTOR_ACCELERATION);
	}
	
	/**
	 * Makes the motor rotate by given speed in deg/s forward.
	 * 
	 * @param speed - max 100 * voltage
	 */
	public void rotate(int speed) {
		motor.setSpeed(speed);
		motor.forward();
	}
	
	/**
	 * Stops the motor immediately. Does not block the thread.
	 */
	public void stop() {
		motor.stop(true);
	}

	@Override
	public String getPort() {
		return port;
	}

}
