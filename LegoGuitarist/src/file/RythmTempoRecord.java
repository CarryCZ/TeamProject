package file;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Holds information about rhythm (see {@link FileConstants} for values) and tempo.
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class RythmTempoRecord extends TimeRecord implements FileRecord {
	/** identifier of this record, used for record distinguishing in file */
	public static final byte CHORD_RECORD_ID = (byte) 1;
	/** size of this record in bytes when serialized */
	private static final int PACKET_SIZE = TimeRecord.PACKET_SIZE + 5;
	
	/** hold identifier for rhythm (see {@link FileConstants}) */
	private byte rhythmID;
	/** holds song's tempo in BPM */
	private int tempo;
	
	public RythmTempoRecord() {}
	
	public RythmTempoRecord(long timeStamp, byte rhythmID, int tempo) {
		this.timeStamp = timeStamp;
		this.rhythmID = rhythmID;
		this.tempo = tempo;
	}
	
	public byte getRhythmID() {
		return rhythmID;
	}
	
	public int getTempo() {
		return tempo;
	}

	@Override
	public int getPacketSize() {
		return PACKET_SIZE;
	}

	@Override
	public void toBuffer(ByteBuffer buffer) {
		buffer.putLong(timeStamp);
		buffer.put(rhythmID);
		buffer.putInt(tempo);
	}

	@Override
	public void fillFromBuffer(ByteBuffer buffer) {
		buffer.order(ByteOrder.BIG_ENDIAN);
		timeStamp = buffer.getLong();
		rhythmID = buffer.get();
		tempo = buffer.getInt();
	}
	
}
