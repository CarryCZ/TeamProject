package guitar;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import lejos.hardware.Brick;
import lejos.remote.ev3.RemoteEV3;

/**
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class GuitarSlaveBrick extends GuitarBrick {
	/** logger for program messages output feed */
	private static final Logger LOGGER = Logger.getLogger(GuitarSlaveBrick.class);
	
	public GuitarSlaveBrick(String name) throws Exception {
		super(name);
		LOGGER.debug("Object: GuitarSlaveBrick created.");
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
