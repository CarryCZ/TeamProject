package file;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * Used to run testing programs on file writer/reader
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class FileTester {
	/** logger for program messages output feed */
	private static final Logger LOGGER = Logger.getLogger(FileTester.class);
	
	private static String TEST_FILE_NAME = "test";

	public static void main(String[] args) {
		initializeLogger();
//		testWriter();
		testReader();
	}
	
	private static void addRecords(BlockingQueue<FileRecord> chordBufferQueue, BlockingQueue<FileRecord> rhythmTempoBufferQueue) {
		Chord[] chordArray = {Chord.G, Chord.C, Chord.D};
		
		chordBufferQueue.add(new ChordRecord(0, chordArray[0]));
		chordBufferQueue.add(new ChordRecord(4, chordArray[1]));
		chordBufferQueue.add(new ChordRecord(8, chordArray[0]));
		chordBufferQueue.add(new ChordRecord(10, chordArray[2]));
		chordBufferQueue.add(new ChordRecord(12, chordArray[0]));
		
		rhythmTempoBufferQueue.add(new RhythmTempoRecord(0, FileConstants.RHYTHM_ID_PIONEER, 120));
	}
	
	private static void testWriter() {
		BlockingQueue<FileRecord> chordBufferQueue = new LinkedBlockingQueue<>();
		BlockingQueue<FileRecord> rhythmTempoBufferQueue = new LinkedBlockingQueue<>();
		
		BinaryWriterThread chordWriter = new BinaryWriterThread(chordBufferQueue, ChordRecord.PACKET_SIZE,
				1, TEST_FILE_NAME + FileConstants.CHORDS_FILE_SUFFIX);
		BinaryWriterThread rhythmTempoWriter = new BinaryWriterThread(rhythmTempoBufferQueue, RhythmTempoRecord.PACKET_SIZE,
				1, TEST_FILE_NAME + FileConstants.RHYTHM_TEMPO_FILE_SUFFIX);
		chordWriter.start();
		rhythmTempoWriter.start();
		
		addRecords(chordBufferQueue, rhythmTempoBufferQueue);
		
		chordWriter.interrupt();
		rhythmTempoWriter.interrupt();
		try {
			chordWriter.join();
			rhythmTempoWriter.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static void testReader() {
		BinaryReader chordReader = new BinaryReader(TEST_FILE_NAME + FileConstants.CHORDS_FILE_SUFFIX + ".bin",
				ChordRecord.PACKET_SIZE);
		BinaryReader rhythmTempoReader = new BinaryReader(TEST_FILE_NAME + FileConstants.RHYTHM_TEMPO_FILE_SUFFIX + ".bin",
				RhythmTempoRecord.PACKET_SIZE);
		
		ChordRecord tmp1 = new ChordRecord();
		while(chordReader.hasNext()) {
			tmp1.fillFromBuffer(chordReader.next());
			LOGGER.info(tmp1.toString());
		}
		RhythmTempoRecord tmp2 = new RhythmTempoRecord();
		while(rhythmTempoReader.hasNext()) {
			tmp2.fillFromBuffer(rhythmTempoReader.next());
			LOGGER.info(tmp2.toString());
		}
		
		chordReader.close();
		rhythmTempoReader.close();
	}
	
	public static void initializeLogger(){
    	Properties props = new Properties();
    	try {
			props.load(new FileInputStream("log4j/log4j.properties"));
			PropertyConfigurator.configure(props);
		} catch (IOException e) {
			BasicConfigurator.configure();
			System.out.println("Could not load log4j/log4j.properties file, using default logger instead.");
			e.printStackTrace();
		}
    }

}
