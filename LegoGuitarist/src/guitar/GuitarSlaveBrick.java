package guitar;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import lejos.hardware.Brick;
import lejos.remote.ev3.RemoteEV3;

/**
 * Represents the slave EV3 brick in the system which should be connected to master via PAN and listen to its commands.
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class GuitarSlaveBrick extends GuitarBrick {
	/** logger for program messages output feed */
	private static final Logger LOGGER = Logger.getLogger(GuitarSlaveBrick.class);
	
	/**
	 * Constructs slave brick's dedicated object using {@link GuitarConstants#SLAVE_BRICK_NAME}.
	 * 
	 * @throws Exception when there is no IP address available for the given name
	 */
	public GuitarSlaveBrick() throws Exception {
		super(GuitarConstants.SLAVE_BRICK_NAME);
		LOGGER.debug("Object: GuitarSlaveBrick created. Name: " + GuitarConstants.SLAVE_BRICK_NAME);
	}

	@Override
	protected Brick findBrick() {
		Brick tmp = null;
		try {
			tmp = new RemoteEV3(getIpAddress());
		} catch (RemoteException | MalformedURLException | NotBoundException e) {
			LOGGER.error("GuitarSlaveBrick: Cannot reach the remote brick. TERMINATING");
			e.printStackTrace();
			System.exit(1);
		}
		return tmp;
	}	
}
