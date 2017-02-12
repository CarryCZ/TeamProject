package guitar;

import org.apache.log4j.Logger;

import lejos.hardware.BrickFinder;

/**
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class GuitarBrick {
	/** logger for program messages output feed */
	private static final Logger LOGGER = Logger.getLogger(GuitarBrick.class);
	
	/** holds name of this brick */
	private final String name;
	/** holds IP address of this brick */
	private final String ipAddress;
	
	public GuitarBrick(String name) {
		LOGGER.debug("Object: GuitarBrick created.");
		this.name = name;
		ipAddress = BrickFinder.find(name)[0].getIPAddress();
	}
	
	public String getName() {
		return name;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
}
