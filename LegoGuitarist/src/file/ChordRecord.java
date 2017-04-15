package file;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Holds information about the type of chord along with time stamp.
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class ChordRecord extends TactRecord implements FileRecord {
	/** size of this record in bytes when serialized */
	private static final int PACKET_SIZE = TactRecord.PACKET_SIZE + Byte.BYTES;
	
	/** holds basic chord information */
	private Chord chord;
	
	public ChordRecord() {}
	
	public ChordRecord(int tact, Chord chord) {
		super(tact);
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
		buffer.putInt(tact);
		buffer.put(chord.getIdentifier());
		buffer.rewind();
		return buffer;
	}

	@Override
	public void fillFromBuffer(ByteBuffer buffer) {
		buffer.order(ByteOrder.BIG_ENDIAN);
		tact = buffer.getInt();
		chord = Chord.getChordFromIdentifier(buffer.get());
	}
}
