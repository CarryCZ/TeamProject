package guitar;

import org.apache.log4j.Logger;

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;

/**
 * Represents the master EV3 brick in the system on which this application should run.
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class GuitarMasterBrick extends GuitarBrick {
	/** logger for program messages output feed */
	private static final Logger LOGGER = Logger.getLogger(GuitarMasterBrick.class);

	/**
	 * Constructs master brick's dedicated object using {@link GuitarConstants#MASTER_BRICK_NAME}.
	 * 
	 * @throws Exception when there is no IP address available for the given name
	 */
	public GuitarMasterBrick() throws Exception {
		super(GuitarConstants.MASTER_BRICK_NAME);
		LOGGER.debug("Object: GuitarMasterBrick created. Name: " + GuitarConstants.MASTER_BRICK_NAME);
	}

	@Override
	protected Brick findBrick() {
		return BrickFinder.getLocal();
	}
}
