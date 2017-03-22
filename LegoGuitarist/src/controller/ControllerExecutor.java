package controller;

import file.BinaryReader;
import file.ChordRecord;
import file.RhythmTempoRecord;
import guitar.GuitarMotorsHandler;

/**
 * Contains only method {@link #main(String[])} which calls for controller execution.
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class ControllerExecutor {
	
	/**
	 * Should contain only construct for the main controller class and its startup.
	 * 
	 * @param args - input parameters from console execution
	 */
	public static void main(String[] args) {
		
		//Reading ChordRecord binary file
		ChordRecord chordRecordPrevious = null;
		ChordRecord chordRecord = new ChordRecord();
		
		BinaryReader binaryReaderForChordRecord = new BinaryReader("inputBinary", chordRecord.getPacketSize());
		
		while (binaryReaderForChordRecord.hasNext()) {
			
			chordRecordPrevious = chordRecord;
			chordRecord = new ChordRecord();
			chordRecord.fillFromBuffer(binaryReaderForChordRecord.next());
			//System.out.println("Prave jsme precetli akord: " + chordRecord.getChord());
			int [] arraySteps = chordRecordPrevious.getChord().stepsRequiredFortransitionTo(chordRecord.getChord());
			
			GuitarMotorsHandler quitarMotorsHandler = new GuitarMotorsHandler(null, null);
			
			long holdTimeChord = chordRecord.getTimeStamp() - chordRecordPrevious.getTimeStamp();
			
			try {
			    Thread.sleep(holdTimeChord);               
			    } 
			catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
			}
			
			for(int i = 0; i < arraySteps[0]; i++) {
				quitarMotorsHandler.makeStepFret(1);
			}
			
			for(int i = 0; i < arraySteps[1]; i++) {
				quitarMotorsHandler.makeStepFret(2);
			}
			
			for(int i = 0; i < arraySteps[2]; i++){
				quitarMotorsHandler.makeStepFret(3);
			}
			
		}
		binaryReaderForChordRecord.close();


		/*
		//Reading RhythmTempoRecord binary file
		RhythmTempoRecord rhythmTempoRecordEmpty = new RhythmTempoRecord();
		BinaryReader binaryReaderForRhythmTempoRecord = new BinaryReader("inputBinary", rhythmTempoRecordEmpty.getPacketSize());
		while (binaryReaderForRhythmTempoRecord.hasNext()) {
			rhythmTempoRecordEmpty.fillFromBuffer(binaryReaderForRhythmTempoRecord.next());
		}
		binaryReaderForRhythmTempoRecord.close();
		*/

				
	}

}