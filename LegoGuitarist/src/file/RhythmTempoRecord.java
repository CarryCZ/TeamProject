package file;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Holds information about rhythm (see {@link FileConstants} for values) and tempo.
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class RhythmTempoRecord extends TactRecord implements FileRecord {
	/** size of this record in bytes when serialized */
	private static final int PACKET_SIZE = TactRecord.PACKET_SIZE + Integer.BYTES + Byte.BYTES;
	
	/** hold identifier for rhythm (see {@link FileConstants}) */
	private byte rhythmID;
	/** holds song's tempo in BPM */
	private int tempo;
	
	public RhythmTempoRecord() {}
	
	public RhythmTempoRecord(int tact, byte rhythmID, int tempo) {
		this.tact = tact;
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
	public ByteBuffer toBuffer() {
		ByteBuffer buffer = ByteBuffer.allocate(PACKET_SIZE);
		buffer.putInt(tact);
		buffer.put(rhythmID);
		buffer.putInt(tempo);
		buffer.rewind();
		return buffer;
	}

	@Override
	public void fillFromBuffer(ByteBuffer buffer) {
		buffer.order(ByteOrder.BIG_ENDIAN);
		tact = buffer.getInt();
		rhythmID = buffer.get();
		tempo = buffer.getInt();
	}
	
}
