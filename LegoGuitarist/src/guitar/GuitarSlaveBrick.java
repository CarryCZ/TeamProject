package guitar;

import org.apache.log4j.Logger;

/**
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class GuitarSlaveBrick extends GuitarBrick {
	/** logger for program messages output feed */
	private static final Logger LOGGER = Logger.getLogger(GuitarSlaveBrick.class);
	
	public GuitarSlaveBrick(String name) {
		super(name);
		LOGGER.debug("Object: GuitarSlaveBrick created.");
	}
}
