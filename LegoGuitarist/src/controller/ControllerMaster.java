package controller;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import file.BinaryReader;
import file.ChordRecord;
import file.RhythmTempoRecord;
import guitar.GuitarBrick;
import guitar.GuitarMasterBrick;
import guitar.GuitarMotorsHandler;
import guitar.GuitarSlaveBrick;

public class ControllerMaster {

	public void StartUp(String chordInputBinary, String rhythmTempoInputBinary) {
		Queue<ChordRecord> chords = new LinkedList<ChordRecord>();
		ReadingChordRecordBinFile(chords, chordInputBinary);

		Queue<RhythmTempoRecord> rhythmTempo = new LinkedList<RhythmTempoRecord>();
		ReadingRhythmTemptoRecordBinFile(rhythmTempo, rhythmTempoInputBinary);

		PlayMusic(chords, rhythmTempo);
	}

	private void ReadingChordRecordBinFile(Queue<ChordRecord> chords, String chordInputBinary) {
		ChordRecord chordRecord = new ChordRecord();
		BinaryReader binaryReaderForChordRecord = new BinaryReader(chordInputBinary, chordRecord.getPacketSize());

		while (binaryReaderForChordRecord.hasNext()) {
			chordRecord = new ChordRecord();
			chordRecord.fillFromBuffer(binaryReaderForChordRecord.next());
			chords.add(chordRecord);
		}
		binaryReaderForChordRecord.close();
	}

	private void ReadingRhythmTemptoRecordBinFile(Queue<RhythmTempoRecord> rhythmTempo, String rhythmTempoInputBinary) {
		RhythmTempoRecord rhythmTemtpoRecord = new RhythmTempoRecord();
		BinaryReader binaryReaderForRhythmTempoRecord = new BinaryReader(rhythmTempoInputBinary,
				rhythmTemtpoRecord.getPacketSize());

		while (binaryReaderForRhythmTempoRecord.hasNext()) {
			rhythmTemtpoRecord = new RhythmTempoRecord();
			rhythmTemtpoRecord.fillFromBuffer(binaryReaderForRhythmTempoRecord.next());
			rhythmTempo.add(rhythmTemtpoRecord);
		}
		binaryReaderForRhythmTempoRecord.close();
	}

	private void PlayMusic(Queue<ChordRecord> chords, Queue<RhythmTempoRecord> rhythmTempo) {

		GuitarBrick masterBrick = null, slaveBrick = null;
		GuitarMotorsHandler guitarMotorsHandler = null;
		try {
			masterBrick = new GuitarMasterBrick();
			slaveBrick = new GuitarSlaveBrick();
			guitarMotorsHandler = new GuitarMotorsHandler(masterBrick, slaveBrick);
		} catch (Exception e) {
			System.exit(1);
			e.printStackTrace();
		}

		// cteni vsech taktu
		boolean endBarLine = false;
		int tempo = 0;
		while (!endBarLine) {
			ArrayList<ChordRhythmTempoRecord> bar = new ArrayList<ChordRhythmTempoRecord>();

			int LeftTimeStamp = 0;
			boolean endBar = false;
			int iterator = 0;
			RhythmTempoRecord rhythmTempoRecord = null;
			ChordRecord chordRecordPrevious = new ChordRecord(0, null);
			ChordRecord chordRecordActual = new ChordRecord(0, null);
			while (!endBar) {
				rhythmTempoRecord = rhythmTempo.poll();
				tempo = rhythmTempoRecord.getTempo();
				// rhythmTempo = rhythmTempoRecord.getRhythmID();

				LeftTimeStamp = (LeftTimeStamp + 60 / tempo) * 1000; // prevod
																		// na ms

				LeftHandMove leftHandMove = new LeftHandMove();

				chordRecordPrevious = chordRecordActual;
				chordRecordActual = chords.poll();
				leftHandMove.stepsArray = chordRecordPrevious.getChord()
						.stepsRequiredFortransitionTo(chordRecordActual.getChord());

				bar.add(new ChordRhythmTempoRecord(LeftTimeStamp, leftHandMove, null));

				if (rhythmTempoRecord.getRhythmID() == (byte) 1) {
					if (iterator == 4) {
						endBar = true;
					}
				} else if (rhythmTempoRecord.getRhythmID() == (byte) 2) {
					if (iterator == 3) {
						endBar = true;
					}
				}
				iterator++;

			}
			int numberBeats = 0;
			int RightTimeStamp = 0;

			if (rhythmTempoRecord.getRhythmID() == (byte) 1) {
				numberBeats = 4;
			} else if (rhythmTempoRecord.getRhythmID() == (byte) 2) {
				numberBeats = 3;
			}

			RightHandMove rightHandMoveDown = new RightHandMove();
			rightHandMoveDown.moveDown = true;
			rightHandMoveDown.deflection = false;

			RightHandMove rightHandMoveUp = new RightHandMove();
			rightHandMoveDown.moveDown = false;
			rightHandMoveDown.deflection = true;
			for (int i = 0; i < numberBeats; i++) {
				bar.add(new ChordRhythmTempoRecord(RightTimeStamp, null, rightHandMoveDown));
				RightTimeStamp = (RightTimeStamp + (60 / tempo) / 2) * 1000;
				bar.add(new ChordRhythmTempoRecord(RightTimeStamp, null, rightHandMoveUp));
				RightTimeStamp = (RightTimeStamp + (60 / tempo) / 2) * 1000;
			}

			Collections.sort(bar);

			int timeStampPrevious = 0;
			int timeStampActual = 0;
			int timeWait = 0;
			for (ChordRhythmTempoRecord chordRhythmTempoRecord : bar) {

				timeWait = chordRhythmTempoRecord.timeStamp - timeStampPrevious;
				timeStampPrevious = chordRhythmTempoRecord.timeStamp;

				try {
					wait(timeWait);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				if (chordRhythmTempoRecord.leftHandMove != null) {

					int[] stepsArray = chordRhythmTempoRecord.leftHandMove.stepsArray;

					for (int i = 0; i < stepsArray[0]; i++) {
						guitarMotorsHandler.makeStepFret(1);
					}

					for (int i = 0; i < stepsArray[1]; i++) {
						guitarMotorsHandler.makeStepFret(2);
					}

					for (int i = 0; i < stepsArray[2]; i++) {
						guitarMotorsHandler.makeStepFret(3);
					}
					chordRecordPrevious = chordRecordActual;


				} else if (chordRhythmTempoRecord.rightHandMove != null) {

					if (chordRhythmTempoRecord.rightHandMove.moveDown == true) {
						if (chordRhythmTempoRecord.rightHandMove.deflection == false) {
							guitarMotorsHandler.makeStepRhythmForward();
						} else {
							guitarMotorsHandler.makeStepBassForward();
						}
					} else if (chordRhythmTempoRecord.rightHandMove.moveDown == false) {
						if (chordRhythmTempoRecord.rightHandMove.deflection == false) {
							guitarMotorsHandler.makeStepRhythmForward();
						} else {
							guitarMotorsHandler.makeStepBassForward();
						}
					}

				}

			}
		}

	}
}
