package guitar;

import org.apache.log4j.Logger;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.BrickInfo;

/**
 * Holds name, IP address and a reference to a EV3 brick.
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
	
	/**
	 * Save name, search for IP address of a brick with the name and store it, store reference to the brick's object.
	 * 
	 * @param name of the brick in leJOS inside the brick.
	 * @throws Exception when there is no IP address available for the given name
	 */
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
