package guitar;

import java.io.IOException;

import org.apache.log4j.Logger;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
import lejos.remote.ev3.RemoteRequestEV3;
import lejos.robotics.RegulatedMotor;

/**
 * Handles communication with all motors within the system.
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class GuitarMotorsHandler {
	/** logger for program messages output feed */
	private static final Logger LOGGER = Logger.getLogger(GuitarMotorsHandler.class);
	/** field of all six motors used in the setup {fret1, fret2, fret3, strumming, rhythm, root} */
	private final RegulatedMotor[] motors = new RegulatedMotor[6];
	
	public GuitarMotorsHandler(GuitarBrick master, GuitarBrick slave) {
		LOGGER.debug("Object: GuitarMotorshandler created.");
		RemoteRequestEV3 remote = null;
		try {
			remote = new RemoteRequestEV3(slave.getIpAddress());
		} catch (IOException e) {
			LOGGER.error("GuitarMotorsHandler: Error in initiation of remote access.");
			e.printStackTrace();
			System.exit(1);
		}
		motors[0] = remote.createRegulatedMotor(GuitarConstants.MOTOR_PORT_FRET1, GuitarConstants.MOTOR_ID_LARGE);
		motors[1] = remote.createRegulatedMotor(GuitarConstants.MOTOR_PORT_FRET2, GuitarConstants.MOTOR_ID_LARGE);
		motors[2] = remote.createRegulatedMotor(GuitarConstants.MOTOR_PORT_FRET3, GuitarConstants.MOTOR_ID_LARGE);
		motors[3] = new EV3LargeRegulatedMotor(master.getBrick().getPort(GuitarConstants.MOTOR_PORT_STRUMMING));
		motors[4] = new EV3MediumRegulatedMotor(master.getBrick().getPort(GuitarConstants.MOTOR_PORT_RHYTM_SELECTOR));
		motors[5] = new EV3LargeRegulatedMotor(master.getBrick().getPort(GuitarConstants.MOTOR_PORT_ROOT_SELECTOR));
		
		//TODO: Some motors should be reversed for theirs correct operation.
		for(int index=0; index<motors.length; index++) {
			motors[index].setAcceleration(GuitarConstants.MOTOR_ACCELERATION);
			motors[index].setSpeed(GuitarConstants.MOTOR_SPEED);
		}
	}
	
	/**
	 * Rotate the motor of the master brick plugged in the given port about 60 degrees.
	 * 
	 * @param port - Possible strings are {"A","B","C"}
	 */
	public void makeStepMaster(final String port) {
		new Thread(new Runnable() {
		     public void run() {
		          switch(port) {
		          case "A":
		        	  motors[3].rotate(GuitarConstants.MOTOR_ONE_STEP);
		        	  break;
		          case "B":
		        	  motors[4].rotate(GuitarConstants.MOTOR_ONE_STEP);
		        	  break;
		          case "C":
		        	  motors[5].rotate(GuitarConstants.MOTOR_ONE_STEP);
		          default:
		        	  throw new IllegalArgumentException("This method accepts only {\"A\",\"B\" or \"C\"}.");
		          }
		     }
		}).start();
	}
	
	/**
	 * Rotate the motor of the slave brick plugged in the given port about 60 degrees.
	 * 
	 * @param port - Possible strings are {"A","B","C"}
	 */
	public void makeStepSlave(final String port) {
		new Thread(new Runnable() {
		     public void run() {
		          switch(port) {
		          case "A":
		        	  motors[0].rotate(GuitarConstants.MOTOR_ONE_STEP);
		        	  break;
		          case "B":
		        	  motors[1].rotate(GuitarConstants.MOTOR_ONE_STEP);
		        	  break;
		          case "C":
		        	  motors[2].rotate(GuitarConstants.MOTOR_ONE_STEP);
		          default:
		        	  throw new IllegalArgumentException("This method accepts only {\"A\",\"B\" or \"C\"}.");
		          }
		     }
		}).start();
	}
}
