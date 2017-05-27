package controller;

import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import file.BinaryReader;
import file.ChordRecord;
import file.FileConstants;
import file.RhythmTempoRecord;
import guitar.GuitarBrick;
import guitar.GuitarMasterBrick;
import guitar.GuitarMotorsHandler;
import guitar.GuitarSlaveBrick;

public class ControllerMaster {

	/**
	 * Startovni metoda, ktere davame na vstup bin soubory s informacemi o
	 * aktualnich dobach a akordy a opet o dobe a rytmu a tempu. Vytvarime zde
	 * dve fronty (chords a rhytmTempo), ktere vkladame jako parametry do metod
	 * pro naplneni techto front zaznamem z bin souboru. Naplnene fronty davame
	 * jako parametry do metody pro prehrani zaznamu.
	 * 
	 * @author Vaclav Novoty - vaclav.novotny93@gmail.com
	 *
	 */
	public void StartUp(String chordInputBinary, String rhythmTempoInputBinary) {
		Queue<ChordRecord> chords = new LinkedList<ChordRecord>();
		ReadingChordRecordBinFile(chords, chordInputBinary);

		Queue<RhythmTempoRecord> rhythmTempo = new LinkedList<RhythmTempoRecord>();
		ReadingRhythmTemptoRecordBinFile(rhythmTempo, rhythmTempoInputBinary);

		PlayMusic(chords, rhythmTempo);
	}

	/**
	 * Metoda pro naplneni prazdne fronty nactenym bin souborem, ktery obsahuje
	 * informace o aktualnich dobach a k nim prirazenych akordech.
	 * 
	 * @author Vaclav Novoty - vaclav.novotny93@gmail.com
	 *
	 */
	private void ReadingChordRecordBinFile(Queue<ChordRecord> chords, String chordInputBinary) {
		ChordRecord chordRecord = new ChordRecord(); //objekt, ktery je shopen si zapamatovat info o akordu a dobe, ale zatim je prazdny
		BinaryReader binaryReaderForChordRecord = new BinaryReader(chordInputBinary, chordRecord.getPacketSize()); 

		while (binaryReaderForChordRecord.hasNext()) {
			chordRecord = new ChordRecord(); // hraju vic, nez jeden akord
			chordRecord.fillFromBuffer(binaryReaderForChordRecord.next());
			chords.add(chordRecord);
			System.out.println("Doba: " + chordRecord.getTact() + "Akord: " + chordRecord.getChord());
		}
		binaryReaderForChordRecord.close();
	}

	/**
	 * Metoda pro naplneni prazdne fronty nactenym bin souborem, ktery obsahuje
	 * informace o aktualnich dobach a k nim prirazene rytmy a tempa
	 * 
	 * @author Vaclav Novoty - vaclav.novotny93@gmail.com
	 *
	 */
	private void ReadingRhythmTemptoRecordBinFile(Queue<RhythmTempoRecord> rhythmTempo, String rhythmTempoInputBinary) {
		RhythmTempoRecord rhythmTemtpoRecord = new RhythmTempoRecord(); //objekt, ktery je shopen si zapamatovat info o rytmu a tempu a dobe, ale zatim je prazdny
		BinaryReader binaryReaderForRhythmTempoRecord = new BinaryReader(rhythmTempoInputBinary,
				rhythmTemtpoRecord.getPacketSize());

		while (binaryReaderForRhythmTempoRecord.hasNext()) {
			rhythmTemtpoRecord = new RhythmTempoRecord();
			rhythmTemtpoRecord.fillFromBuffer(binaryReaderForRhythmTempoRecord.next());
			rhythmTempo.add(rhythmTemtpoRecord);
			System.out.println("Doba: " + rhythmTemtpoRecord.getTact() + "Rytmus: " + rhythmTemtpoRecord.getRhythmID() + "Tempo: " + rhythmTemtpoRecord.getTempo());
		}
		binaryReaderForRhythmTempoRecord.close();
	}

	private int GetBeatsCountInBar(RhythmTempoRecord rythm) {
		if (rythm.getRhythmID() == (byte) 1) {
			return 4;
		} else if (rythm.getRhythmID() == (byte) 2) {
			return 3;
		} else
			return 0;
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

		int tempo = 0;
		int globalBeatCount = 1;
		boolean endBarLine = false;
		LeftHandMove lastLeftMove = null;
		RightHandMove lastRightDownMove = null;
		RightHandMove lastRightUpMove = null;
		
		
		while (!endBarLine) { //end song
			
			ArrayList<ChordRhythmTempoRecord> sequenceActionForLeftRightHand = new ArrayList<ChordRhythmTempoRecord>();
			int leftTimeStamp = 0;
			boolean endBar = false; 
			int iterator = 0;
			RhythmTempoRecord rhythmTempoRecord = null; 
			ChordRecord chordRecordPrevious = new ChordRecord(0, null);											
			ChordRecord chordRecordActual = new ChordRecord(0, null);
			//zde je nutne nastavit nejaky pocatecni akord 
																		
			while (!endBar) { //end bar
				
				rhythmTempoRecord = rhythmTempo.peek();
				tempo = rhythmTempoRecord.getTempo();

				if (chordRecordActual != null && (lastLeftMove == null || globalBeatCount == chords.peek().getTact())) {
					lastLeftMove = new LeftHandMove();
					chordRecordPrevious = chordRecordActual;
					chordRecordActual = chords.poll();
					if(chordRecordActual == null) {
						endBar = true;
						endBarLine = true;
					}
					lastLeftMove.stepsArray = chordRecordPrevious.getChord().stepsRequiredFortransitionTo(chordRecordActual.getChord());
				}
				
				sequenceActionForLeftRightHand.add(new ChordRhythmTempoRecord(leftTimeStamp, lastLeftMove, null)); 
				leftTimeStamp = leftTimeStamp + (60 / tempo * 1000);
				int beatsCount = GetBeatsCountInBar(rhythmTempoRecord);
				if (iterator == beatsCount) {
					endBar = true;
				}
				iterator++;
				globalBeatCount++;
			}
			
			int numberOfMovesRightHand = 0;
			int rightTimeStamp = 0;

			if (rhythmTempoRecord.getRhythmID() == (byte) 1) {
				numberOfMovesRightHand = 4;
			} else if (rhythmTempoRecord.getRhythmID() == (byte) 2) {
				numberOfMovesRightHand = 3;
			}
			int beatsCount = GetBeatsCountInBar(rhythmTempoRecord);

			if (rhythmTempoRecord != null && (lastRightDownMove == null || lastRightUpMove == null || globalBeatCount - beatsCount == rhythmTempoRecord.getTact())) {
				lastRightDownMove = new RightHandMove();
				lastRightDownMove.moveDown = true;
				lastRightDownMove.deflection = false;

				lastRightUpMove = new RightHandMove();
				lastRightUpMove.moveDown = false;
				lastRightUpMove.deflection = true;
				
				rhythmTempo.poll();
			}

			for (int i = 0; i < numberOfMovesRightHand; i++) {
				int moveDelta = beatsCount * ((60 / tempo) * 1000) / (numberOfMovesRightHand * 2);
				sequenceActionForLeftRightHand.add(new ChordRhythmTempoRecord(rightTimeStamp, null, lastRightDownMove));
				rightTimeStamp = rightTimeStamp + moveDelta;
				sequenceActionForLeftRightHand.add(new ChordRhythmTempoRecord(rightTimeStamp, null, lastRightUpMove));
				rightTimeStamp = rightTimeStamp + moveDelta;
			}

			Collections.sort(sequenceActionForLeftRightHand);

			int timeStampPrevious = 0;
			int timeWait = 0;
			
			for (ChordRhythmTempoRecord chordRhythmTempoRecord : sequenceActionForLeftRightHand) {
				timeWait = chordRhythmTempoRecord.timeStamp - timeStampPrevious;
				timeStampPrevious = chordRhythmTempoRecord.timeStamp;
				
				if(timeWait > 0) {
					try {
						wait(timeWait);
					} catch (Exception e) {
						//TO DO
					}
				}

				if (chordRhythmTempoRecord.leftHandMove != null) {

					int[] stepsArray = chordRhythmTempoRecord.leftHandMove.stepsArray;

					for (int i = 0; i < stepsArray[0]; i++) {
						guitarMotorsHandler.makeStepFret(1);
						System.out.println("ahoj");
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
							guitarMotorsHandler.makeStepRhythmForward();
						}
					} else if (chordRhythmTempoRecord.rightHandMove.moveDown == false) {
						if (chordRhythmTempoRecord.rightHandMove.deflection == false) {
							guitarMotorsHandler.makeStepRhythmBackward();
						} else {
							guitarMotorsHandler.makeStepBassBackward();
							guitarMotorsHandler.makeStepRhythmBackward();
						}
					}
				}
			}
		}
	}
}
