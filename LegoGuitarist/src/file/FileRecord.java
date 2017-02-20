package file;

import java.nio.ByteBuffer;

/**
 * Prescribes necessary methods for various file records.
 * 
 * @author Tomas Trafina - ttrafina.at.gmail.com
 *
 */
public interface FileRecord {
	
	/**
	 * @return number of bytes needed for storage of this object when serialized
	 */
	public int getPacketSize();
	
	/**
	 * Serializes the structure of this object and puts the parameters into the buffer.
	 */
	public void toBuffer(ByteBuffer buffer);
	
	/**
	 * Gets parameters from the buffer, storing them into the current object.
	 */
	public void fillFromBuffer(ByteBuffer buffer);
}
