package guitar;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

public class GuitarTester {

	public static void main(String[] args) {	
		GuitarBrick masterBrick = null, slaveBrick = null;
		try {
			masterBrick = new GuitarMasterBrick();
			slaveBrick = new GuitarSlaveBrick();
		} catch (Exception e) {
			System.exit(1);
			e.printStackTrace();
		}
		GuitarLedHandler ledHandler = new GuitarLedHandler(masterBrick, slaveBrick);
		GuitarMotorsHandler motorsHandler = new GuitarMotorsHandler(masterBrick, slaveBrick);
		
		ledHandler.setFastBlinkGreenToMaster();
		ledHandler.setFastBlinkGreenToSlave();
		motorsHandler.setSpeedStrumMotor(100);
		motorsHandler.makeStepFret(1);
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		motorsHandler.setSpeedStrumMotor(0);
		ledHandler.setOffToSlave();
	}
	
	public static void initializeLogger(){
    	Properties props = new Properties();
    	try {
			props.load(new FileInputStream("log4j/log4j.properties"));
			PropertyConfigurator.configure(props);
		} catch (IOException e) {
			BasicConfigurator.configure();
			System.out.println("Could not load log4j/log4j.properties file, using default logger instead.");
			e.printStackTrace();
		}
    }
}
