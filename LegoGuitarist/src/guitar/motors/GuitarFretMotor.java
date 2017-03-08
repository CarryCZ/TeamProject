package guitar.motors;

import java.io.IOException;

import org.apache.log4j.Logger;

import guitar.GuitarBrick;
import guitar.GuitarConstants;
import lejos.remote.ev3.RemoteRequestEV3;
import lejos.robotics.RegulatedMotor;

/**
 * Handler for a motor on a fret.
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class GuitarFretMotor implements GuitarStepMotor {
	/** logger for program messages output feed */
	private static final Logger LOGGER = Logger.getLogger(GuitarFretMotor.class);
	
	/** how many possible positions this automaton has */
	private final int numberOfPositions;
	
	/** in which fret is this motor located */
	private final int fret;
	/** to which port in the brick is this motor connected */
	private final String port;
	/** handler of this motor from native library */
	private final RegulatedMotor motor;
	/** in which position this motor is */
	private int currentPosition;
	
	public GuitarFretMotor(int fret, String port, GuitarBrick slave) {
		LOGGER.debug("Object: GuitarFretMotor" + fret + " for fret " + fret + " on port " + port + " created.");
		
		if (fret < 1 || fret > 3)
			throw new IllegalArgumentException("GuitarFretMotor can be initialized only on frets 1,2,3.");
		if (!port.equalsIgnoreCase("A") && !port.equalsIgnoreCase("B")
				&& !port.equalsIgnoreCase("C") && !port.equalsIgnoreCase("D"))
			throw new IllegalArgumentException("GuitarFretMotor can be initialized only on ports A,B,C,D.");
		
		if (fret == 1)
			numberOfPositions = GuitarConstants.MOTOR_FRET1_MAX_STEPS;
		else if (fret == 2)
			numberOfPositions = GuitarConstants.MOTOR_FRET2_MAX_STEPS;
		else
			numberOfPositions = GuitarConstants.MOTOR_FRET3_MAX_STEPS;
		
		RemoteRequestEV3 remote = null;
		try {
			remote = new RemoteRequestEV3(slave.getIpAddress());
		} catch (IOException e) {
			LOGGER.error("GuitarMotorsHandler: Error in initiation of remote access.");
			e.printStackTrace();
			System.exit(1);
		}
		this.fret = fret;
		this.port = port;
		motor = remote.createRegulatedMotor(port, GuitarConstants.MOTOR_ID_LARGE);
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
	
	public int getFret() {
		return fret;
	}

	@Override
	public void makeStepForward() {
		currentPosition++;
		if(currentPosition > numberOfPositions)
			currentPosition = 1;
		new Thread(new Runnable() {
		     public void run() {
		          switch(fret) {
		          case 1:
		        	  motor.rotate(GuitarConstants.MOTOR_FRET1_STEP);
		        	  break;
		          case 2:
		        	  motor.rotate(GuitarConstants.MOTOR_FRET2_STEP);
		        	  break;
		          case 3:
		        	  motor.rotate(GuitarConstants.MOTOR_FRET3_STEP);
		          default:
		        	  return;
		          }
		     }
		}).start();
	}

	@Override
	public void makeStepBackward() {
		throw new UnsupportedOperationException("Fret motors are forbidden to rotate backwards.");
	}
		
}
