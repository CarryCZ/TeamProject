package file;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Holds information about chord and its type (dur/moll).
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
	/** true ~ this chord is dur ; false ~ this chord is moll */
	private boolean isDur;
	
	public ChordRecord() {}
	
	public ChordRecord(long timeStamp, Chord chord, boolean isDur) {
		super(timeStamp);
		this.chord = chord;
		this.isDur = isDur;
	}
	
	public Chord getChord() {
		return chord;
	}
	
	/** @return true ~ dur ; false ~ moll */
	public boolean isDur() {
		return isDur;
	}

	@Override
	public int getPacketSize() {
		return PACKET_SIZE;
	}

	@Override
	public void toBuffer(ByteBuffer buffer) {
		buffer.putLong(timeStamp);
		buffer.put(chord.getIdentifier());
		buffer.put((byte) (isDur ? 1 : 0));
	}

	@Override
	public void fillFromBuffer(ByteBuffer buffer) {
		buffer.order(ByteOrder.BIG_ENDIAN);
		timeStamp = buffer.getLong();
		chord = Chord.getChordFromIdentifier(buffer.get());
		isDur = buffer.get() == (byte) 1;
	}
}
