package file;

import guitar.GuitarConstants;

/**
 * This enum consists of basic chords, excluding sharps, flats, dur, moll or other modificators.
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public enum Chord {	
	//TODO: fill in correct automaton positions for each chord
	C (FileConstants.CHORD_ID_C, 0, 0, 0),
	D (FileConstants.CHORD_ID_D, 0, 0, 0),
	E (FileConstants.CHORD_ID_E, 0, 0, 0),
	F (FileConstants.CHORD_ID_F, 0, 0, 0),
	G (FileConstants.CHORD_ID_G, 0, 0, 0),
	A (FileConstants.CHORD_ID_A, 0, 0, 0),
	H (FileConstants.CHORD_ID_H, 0, 0, 0),
	Dm (FileConstants.CHORD_ID_Dm, 0, 0, 0),
	Am (FileConstants.CHORD_ID_Am, 0, 0, 0);
	
	/** byte identifier for the chord */
	private byte identifier;
	/** what positions on the particular fret corresponds to this chord */
	private int fret1, fret2, fret3;
	
	private Chord(byte identifier, int fret1, int fret2, int fret3) {
		this.identifier = identifier;
		this.fret1 = fret1;
		this.fret2 = fret2;
		this.fret3 = fret3;
	}
	
	public byte getIdentifier() {
		return identifier;
	}
	
	/**
	 * Says how many steps on each fret must be done to make transition from this chord
	 * to the given one.
	 * 
	 * @param chord - following chord
	 * @return needed number of steps for each fret {fret1, fret2, fret3}
	 */
	public int[] stepsRequiredFortransitionTo(Chord chord) {
		int[] tmp = new int[3];
		tmp[0] = ((chord.fret1 + GuitarConstants.MOTOR_FRET1_MAX_STEPS) - fret1) % GuitarConstants.MOTOR_FRET1_MAX_STEPS;
		tmp[1] = ((chord.fret2 + GuitarConstants.MOTOR_FRET2_MAX_STEPS) - fret2) % GuitarConstants.MOTOR_FRET2_MAX_STEPS;
		tmp[2] = ((chord.fret3 + GuitarConstants.MOTOR_FRET3_MAX_STEPS) - fret3) % GuitarConstants.MOTOR_FRET3_MAX_STEPS;
		return tmp;
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
            //TODO: fill in moll chords
        default:
            throw new IllegalArgumentException("Chord: No Chord value exists for this identifier: " + identifier);
		}
	}
	
	public int getFret1() {
		return fret1;
	}
	
	public int getFret2() {
		return fret2;
	}
	
	public int getFret3() {
		return fret3;
	}
}
