package guitar.motors;

public interface GuitarStepMotor extends GuitarMotor {
	
	/**
	 * Returns current position of this automata.
	 */
	public int getPosition();
	
	/**
	 * Makes the motor rotate forwards about degrees value corresponding to one step of this motor.
	 */
	public void makeStepForward();
	
	/**
	 * Makes the motor rotate backwards about degrees value corresponding to one step of this motor.
	 */
	public void makeStepBackward();
}
