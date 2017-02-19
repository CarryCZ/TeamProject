package file;

/**
 * Holds only time information (microseconds).
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class TimeRecord {
	/** size of this record in bytes when serialized */
	protected static final int PACKET_SIZE = 8;
	
	/** time stamp associated with this record (microseconds) */
	protected long timeStamp;
	
	public TimeRecord() {}
	
	public TimeRecord(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public long getTimeStamp() {
		return timeStamp;
	}
}
