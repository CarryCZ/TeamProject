package controller;

import file.ChordRecord;

public class ChordRhythmTempoRecord implements Comparable {
	
	protected int timeStamp;
	
	public ChordRecord chordRecord;
	public LeftHandMove leftHandMove;
	public RightHandMove rightHandMove;
	
	public ChordRhythmTempoRecord(int timeStamp, LeftHandMove leftHandMove, RightHandMove rightHandMove) {
		this.timeStamp = timeStamp;
		this.leftHandMove = leftHandMove;
		this.rightHandMove = rightHandMove;
	}

	@Override
	public int compareTo(Object arg0) {
		int timeStamp = ((ChordRhythmTempoRecord)arg0).timeStamp;
		return this.timeStamp - timeStamp;
	}
}
