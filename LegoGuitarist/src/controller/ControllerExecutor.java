package controller;

/**
 * Contains only method {@link #main(String[])} which calls for controller execution.
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class ControllerExecutor {
	
	/**
	 * Should contain only construct for the main controller class and its startup.
	 * 
	 * @param args - input parameters from console execution
	 */
	public static void main(String[] args) {
		
		ControllerMaster controllerMaster = new ControllerMaster();
		controllerMaster.StartUp("chordInputBinary","rhythmTempoInputBinary");			
	}
}