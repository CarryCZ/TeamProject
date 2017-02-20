package file;

/**
 * This enum consists of basic chords, excluding sharps, flats, dur, moll or other modificators.
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public enum Chord {	
	C (FileConstants.CHORD_ID_C),
	D (FileConstants.CHORD_ID_D),
	E (FileConstants.CHORD_ID_E),
	F (FileConstants.CHORD_ID_F),
	G (FileConstants.CHORD_ID_G),
	A (FileConstants.CHORD_ID_A),
	H (FileConstants.CHORD_ID_H);
	
	/** byte identifier for the chord */
	private byte identifier;
	
	private Chord(byte identifier) {
		this.identifier = identifier;
	}
	
	public byte getIdentifier() {
		return identifier;
	}
	
	/**
	 * Construct chord corresponding to the given identifier.
	 * 
	 * @param identifier - see {@link FileConstants} for possible values
	 */
	public static Chord getChordFromIdentifier(byte identifier) {
		switch (identifier) {
        case FileConstants.CHORD_ID_C:
            return Chord.C;
        case FileConstants.CHORD_ID_D:
            return Chord.D;
        case FileConstants.CHORD_ID_E:
            return Chord.E;
        case FileConstants.CHORD_ID_F:
            return Chord.F;
        case FileConstants.CHORD_ID_G:
            return Chord.G;
        case FileConstants.CHORD_ID_A:
            return Chord.A;
        case FileConstants.CHORD_ID_H:
            return Chord.H;
        default:
            throw new IllegalArgumentException("Chord: No Chord value exists for this identifier: " + identifier);
		}
	}
}
