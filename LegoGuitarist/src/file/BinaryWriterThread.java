package file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

/**
 * Manages writing of file records to a binary file.
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public class BinaryWriterThread extends Thread {
	/** logger for program messages output feed */
	private static final Logger LOGGER = Logger.getLogger(BinaryWriterThread.class);
	
	/** used to buffer records before writing them into output file */
	private ByteBuffer buffer;
	/** how many records were added to buffer after last file write */
	private int counterIndex = 0;
	/** used for binary output */
	private OutputStream outBin;
	/** temporary array used to bridge the record buffer and the output stream */
	private byte[] outArray;
	/** after exceeding this number of records in the buffer, file write is initiated */
	private int bufferLength;

	/** input buffer containing records to be written to a file */
	private BlockingQueue<FileRecord> recordBufferQueue;
	
	/**
	 * Creates binary file and fills it with records from the given buffer.
	 * 
	 * @param bq - buffer queue holding any implementation of {@link FileRecord} that are about to be written to file
	 * @param packetSize - size of the {@link FileRecord} in bytes. If the size is not always the same,
	 * 						 then pass the maximum size the record can have
	 * @param bufferLength - how many records to put into buffer before passing the buffer to actual file write
	 * @param outpuFilePath - path of the output file including its name (without type suffix)
	 */
	public BinaryWriterThread(BlockingQueue<FileRecord> bq, int packetSize, int bufferLength, String outputFilePath) {
		LOGGER.debug("Thread: BinaryWriterThread created.");
		this.setName("BinaryWriterThread");

		this.bufferLength = bufferLength;
		this.recordBufferQueue = bq;
		outArray = new byte[packetSize * bufferLength];
		buffer = ByteBuffer.allocate(packetSize * bufferLength);
		buffer.order(ByteOrder.BIG_ENDIAN);

		try {
			outBin = new FileOutputStream(outputFilePath + ".bin", false);
		} catch (FileNotFoundException e) {
			LOGGER.error("BinaryWriterThread: Error in creating binary file.");
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		LOGGER.debug("Thread: BinaryWriterThread started.");

		FileRecord record = null;
		try {
			while (!Thread.interrupted()) {
				record = recordBufferQueue.take();
				LOGGER.trace(record.toString());
				writeBinary(record);
			}
		} catch (Exception e) {
			LOGGER.warn("Thread: BinaryWriterThread interrupted. STOPPING");
		}
	}
	
	/**
	 * Buffers the incoming data. Once the buffer's limit is reached, the data from it is written to the output file.
	 * 
	 * @param record
	 * @throws Exception
	 */
	private void writeBinary(FileRecord record) throws Exception {		
		if (counterIndex < bufferLength) {
			buffer.put(record.toBuffer());
			counterIndex++;
		} else {
			buffer.flip();
			LOGGER.trace("ByteBuffer length: " + buffer.remaining());
			int remainingBytes = buffer.remaining(); // see how many bytes were written
			buffer.get(outArray, 0, remainingBytes); // copy them to outArray
			outBin.write(outArray, 0, remainingBytes); // write them to disk
			outBin.flush(); // just make sure all byte are passed to OS
			buffer.clear(); // position to zero, limit to capacity, ready for writing

			buffer.put(record.toBuffer());
			counterIndex = 1;
		}
	}
}
