package file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

import org.apache.log4j.Logger;

public class BinaryReader {
	/** logger for error output feed */
	private final Logger LOGGER = Logger.getLogger(BinaryReader.class);

	/** buffer used for binary read */
	ByteBuffer byteBuffer;
	/** stores access to the opened file */
	private RandomAccessFile aFile = null;
	/** channel used for input byte stream */
	private FileChannel inChannel = null;
	/** number of bytes that is needed for object representation */
	private int packetSize;
	
	/**
	 * Opens the file and make it ready for reading.
	 * 
	 * @param inputBinary - whole file path with file name and suffix
	 * @param packetSize - number of bytes of one record
	 */
	public BinaryReader(String inputBinary, int packetSize) {
		LOGGER.debug("Object: BinaryReader created.");
		this.packetSize = packetSize;
		byteBuffer = ByteBuffer.allocate(packetSize);
		byteBuffer.order(ByteOrder.BIG_ENDIAN);
		try {
			aFile = new RandomAccessFile(inputBinary, "r");
			inChannel = aFile.getChannel();
		} catch (FileNotFoundException e) {
			LOGGER.error("BinaryReader: File " + inputBinary + " not found.");
			System.exit(0);
		}
		LOGGER.debug("BinaryReader: input file opened.");
	}
	
	/**
	 * Closes inputs: stream and file.
	 */
	public void close() {
		try {
			if (inChannel != null)
				inChannel.close();
			if (aFile != null)
				aFile.close();
		} catch (IOException e) {
			LOGGER.error("BinaryReader: Error closing channel/file.");
			e.printStackTrace();
		}
		LOGGER.debug("BinaryReader: input file closed.");
	}
	
	public boolean hasNext() {
		try {
			long channelPosition = inChannel.position();
			if(inChannel.read(byteBuffer) >= packetSize) {
				inChannel.position(channelPosition);
				byteBuffer.clear();
				return true;
			}
		} catch (IOException e) {
			LOGGER.error("BinaryReader: I/O error has occured.");
			e.printStackTrace();
		}
		return false;
	}

	public ByteBuffer next() {
		try {
			inChannel.read(byteBuffer);
		} catch (IOException e) {
			LOGGER.error("BinaryReader: I/O error has occured.");
			e.printStackTrace();
		}
		byteBuffer.flip();
		byte[] tmp = byteBuffer.array().clone();
		byteBuffer.clear();
		return ByteBuffer.wrap(tmp);
	}
}
