package guitar;

import org.apache.log4j.Logger;

/**
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class GuitarMasterBrick extends GuitarBrick {
	/** logger for program messages output feed */
	private static final Logger LOGGER = Logger.getLogger(GuitarMasterBrick.class);

	public GuitarMasterBrick(String name) {
		super(name);
		LOGGER.debug("Object: GuitarMasterBrick created.");
	}
}
