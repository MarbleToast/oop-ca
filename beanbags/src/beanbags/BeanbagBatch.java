package beanbags;

import java.io.Serializable;

/**
 * Beanbag batch class. Stores manufacture date information, pointing
 * to an existing beanbag.
 * @author 690000912
 * @author 690008290
 * @version 1.0
 */
public class BeanbagBatch implements Serializable {
	
	// CLASS ATTRIBUTES
	private String beanbagId; // Beanbag ID
	private short year; // Year of manufacture
	private byte month; // Month of manufacture
	
	// MUTATORS
	/**
	 * Method sets the beanbag ID.
	 * @param id					the new beanbag ID
	 */
	public void setBeanbagId(String id) {
		this.beanbagId = id;
	}
	
	/**
	 * Method sets the year of manufacture
	 * @param year					the new year of manufacture
	 */
	public void setYear(short year) {
		this.year = year;
	}
	
	/**
	 * Method sets the month of manufacture
	 * @param month					the new month of manufacture
	 */
	public void setMonth(byte month) {
		this.month = month;
	}
	
	
	// ACCESSORS
	/**
	 * Method returns the beanbag ID the batch points to
	 * @return						the beanbag ID
	 */
	public String getBeanbagId() {
		return this.beanbagId;
	}
	
	/**
	 * Method returns the year of manufacture
	 * @return					the year of manufacture	
	 */
	public short getYear() {
		return this.year;
	}
	
	/**
	 * Method returns the month of manufacture
	 * @return					the month of manufacture	
	 */
	public byte getMonth() {
		return this.month;
	}
	
	/**
	 * Beanbag batch constructor.
	 * @param id				the beanbag ID
	 * @param year				the year of manufacture	
	 * @param month				the month of manufacture
	 */
	public BeanbagBatch(String id, short year, byte month) {
		this.beanbagId = id;
		this.year = year;
		this.month = month;
	}
}
