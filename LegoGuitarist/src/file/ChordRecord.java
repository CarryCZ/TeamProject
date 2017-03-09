package file;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Holds information about the type of chord along with time stamp.
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class ChordRecord extends TimeRecord implements FileRecord {
	/** identifier of this record, used for record distinguishing in file */
	public static final byte CHORD_RECORD_ID = (byte) 2;
	/** size of this record in bytes when serialized */
	private static final int PACKET_SIZE = TimeRecord.PACKET_SIZE + 2;
	
	/** holds basic chord information */
	private Chord chord;
	
	public ChordRecord() {}
	
	public ChordRecord(long timeStamp, Chord chord) {
		super(timeStamp);
		this.chord = chord;
	}
	
	public Chord getChord() {
		return chord;
	}

	@Override
	public int getPacketSize() {
		return PACKET_SIZE;
	}

	@Override
	public ByteBuffer toBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(PACKET_SIZE);
		buffer.putLong(timeStamp);
		buffer.put(chord.getIdentifier());
		buffer.rewind();
		return buffer;
	}

	@Override
	public void fillFromBuffer(ByteBuffer buffer) {
		buffer.order(ByteOrder.BIG_ENDIAN);
		timeStamp = buffer.getLong();
		chord = Chord.getChordFromIdentifier(buffer.get());
	}
}
