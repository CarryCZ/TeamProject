package guitar;

import org.apache.log4j.Logger;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.BrickInfo;

/**
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
abstract public class GuitarBrick {
	/** logger for program messages output feed */
	private static final Logger LOGGER = Logger.getLogger(GuitarBrick.class);
	
	/** holds name of this brick */
	private final String name;
	/** holds IP address of this brick */
	private final String ipAddress;
	/** holds leJOS object representing this brick */
	private final Brick brick;
	
	public GuitarBrick(String name) throws Exception {
		LOGGER.debug("Object: GuitarBrick created.");
		this.name = name;
		BrickInfo[] brickInfo = BrickFinder.find(name);
		if(brickInfo.length == 0)
			throw new Exception("GuitarBrick: Constructor did not find any brick with the given name.");
		ipAddress = BrickFinder.find(name)[0].getIPAddress();
		brick = findBrick();
	}
	
	/**
	 * Retrieves leJOS {@link Brick} object that represents this brick.
	 */
	abstract protected Brick findBrick();
	
	// GETTERS //
	
	public String getName() {
		return name;
	}
	
	public String getIpAddress() {
		return ipAddress;
	}
	
	public Brick getBrick() {
		return brick;
	}
}
