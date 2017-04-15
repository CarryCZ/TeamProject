package file;

/**
 * Holds only time information (microseconds).
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class TactRecord {
	/** size of this record in bytes when serialized */
	protected static final int PACKET_SIZE = Integer.BYTES;
	
	/** time stamp associated with this record (microseconds) */
	protected int tact;
	
	public TactRecord() {}
	
	public TactRecord(int tact) {
		this.tact = tact;
	}
	
	public int getTact() {
		return tact;
	}
}
