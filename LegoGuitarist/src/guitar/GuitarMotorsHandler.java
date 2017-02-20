package guitar;

import org.apache.log4j.Logger;

import lejos.hardware.Brick;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
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
	
	private final RegulatedMotor[] motors = new RegulatedMotor[6];
	
	public GuitarMotorsHandler(Brick master, Brick slave) {
		LOGGER.debug("Object: GuitarMotorshandler created.");
		motors[0] = new EV3LargeRegulatedMotor(slave.getPort("A"));
		motors[1] = new EV3LargeRegulatedMotor(slave.getPort("B"));
		motors[2] = new EV3LargeRegulatedMotor(slave.getPort("C"));
		motors[3] = new EV3LargeRegulatedMotor(master.getPort("A"));
		motors[4] = new EV3LargeRegulatedMotor(master.getPort("B"));
		motors[5] = new EV3LargeRegulatedMotor(master.getPort("C"));
		
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
		        	  motors[3].rotate(60);
		        	  break;
		          case "B":
		        	  motors[4].rotate(60);
		        	  break;
		          case "C":
		        	  motors[5].rotate(60);
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
		        	  motors[0].rotate(60);
		        	  break;
		          case "B":
		        	  motors[1].rotate(60);
		        	  break;
		          case "C":
		        	  motors[2].rotate(60);
		          default:
		        	  throw new IllegalArgumentException("This method accepts only {\"A\",\"B\" or \"C\"}.");
		          }
		     }
		}).start();
	}
}
