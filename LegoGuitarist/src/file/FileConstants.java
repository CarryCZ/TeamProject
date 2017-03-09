package file;

/**
 * Contains constants associated with exchange files and records.
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class FileConstants {
	
	// RHYTHMS //
	
	/** identifier for pioneer's rhythm */
	public static final byte RHYTHM_ID_PIONEER = (byte) 1;
	
	
	// CHORDS //
	
	/** identifier for chord C */
	public static final byte CHORD_ID_C = (byte) 1;
	/** identifier for chord D */
	public static final byte CHORD_ID_D = (byte) 2;
	/** identifier for chord E */
	public static final byte CHORD_ID_E = (byte) 3;
	/** identifier for chord F */
	public static final byte CHORD_ID_F = (byte) 4;
	/** identifier for chord G */
	public static final byte CHORD_ID_G = (byte) 5;
	/** identifier for chord A */
	public static final byte CHORD_ID_A = (byte) 6;
	/** identifier for chord H */
	public static final byte CHORD_ID_H = (byte) 7;
	/** identifier for chord Dm */
	public static final byte CHORD_ID_Dm = (byte) 8;
	/** identifier for chord Em */
	public static final byte CHORD_ID_Em = (byte) 9;
	/** identifier for chord Am */
	public static final byte CHORD_ID_Am = (byte) 10;
	
	
	// FILES //
	
	/** size of the buffer waiting to be filled before binary writing operation (for {@linkplain ChordRecord}) */
	public static final int BIN_WRITER_CHORD_BUFFER_LENGTH = 10;
	/** size of the buffer waiting to be filled before binary writing operation (for {@linkplain RhythmTempoRecord}) */
	public static final int BIN_WRITER_RT_BUFFER_LENGTH = 10;
}
