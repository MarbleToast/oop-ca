package beanbags;

import java.io.Serializable;

/**
 * Beanbag class. Stores information on a particular beanbag, and contains
 * methods for manipulating said information.
 * @author 690000912
 * @author 690008290
 * @version 1.1
 */
public class Beanbag implements Serializable {
	
	//CLASS ATTRIBUTES
	private String name; // Beanbag name
	private String manufacturerName; // Manufacturer name
	private String manufacturerId; // Unique 8-bit hex number ID
	private String desc; // Free text component
	private int numberOf; // Total reserved and unreserved stock
	private int priceInPence; // Current price in pence
	private int reservations; // Number of reserved stock
	
	// MUTATORS
	/**
	 * Method sets the number of the beanbag in stock (reserved and unreserved)
	 * @param numberOf				the number to set the stock to
	 */
	public void setNumberOf(int numberOf) {
		this.numberOf = numberOf;
	}
	
	/**
	 * Method sets the manufacturer ID
	 * @param id					the new ID
	 */
	public void setManufacturerId(String id) {
		this.manufacturerId = id;
	}
	
	/**
	 * Method sets the beanbag name
	 * @param name					the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Method sets the manufacturer's name
	 * @param manufacturerName		the new manufacturer's name
	 */
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
	
	/**
	 * Method sets the beanbag's price
	 * @param priceInPence			the new price
	 */
	public void setPriceInPence(int priceInPence) {
		this.priceInPence = priceInPence;
	}
	
	/**
	 * Method sets the beanbag's description/free text attribute
	 * @param desc					the new description
	 */
	public void setDescription(String desc) {
		this.desc = desc;
	}
	
	/**
	 * Method sets the beanbag's reserved stock number
	 * @param reserved				the new reserved stock
	 */
	public void setReservations(int reserved) {
		this.reservations = reserved;
	}
	
	
	// ACCESSORS
	/**
	 * Return the current reserved and unreserved stock
	 * @return 						the current reserved and unreserved 
	 * 								stock
	 */
	public int getNumberOf() {
		return this.numberOf;
	}
	
	/**
	 * Return the manufacturer ID
	 * @return 						the manufacturer ID
	 */
	public String getManufacturerId() {
		return this.manufacturerId;
	}
	
	/**
	 * Return the beanbag name
	 * @return 						the beanbag name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Return the manufacturer name
	 * @return 						the manufacturer name
	 */
	public String getManufacturerName() {
		return this.manufacturerName;
	}
	
	/**
	 * Return the current price in pence
	 * @return 						the price in pence
	 */
	public int getPriceInPence() {
		return this.priceInPence;
	}
	
	/**
	 * Return the beanbag's description
	 * @return 						the description
	 */
	public String getDescription() {
		return this.desc;
	}
	
	/**
	 * Return the number of reservations for the beanbag
	 * @return 						the number of reservations
	 */
	public int getReservations() {
		return this.reservations;
	}
	
	/**
	 * The Beanbag constructor, without free text component.
	 * @param manufacturer			the manufacturer's name
	 * @param name					the beanbag's name
	 * @param id					the unique 8-bit positive hexadecimal number
	 * @param numberOf				the initial number in stock
	 */
	public Beanbag(String manufacturer, String name, String id, int numberOf) {
		this.name = name;
		this.manufacturerName = manufacturer;
		this.manufacturerId = id;
		
		this.priceInPence = -1;
		this.desc = "";
		
		this.reservations = 0;
		this.numberOf = numberOf;
	}
	
	/**
	 * The Beanbag constructor, with free text component.
	 * @param manufacturer			the manufacturer's name
	 * @param name					the beanbag's name
	 * @param id					the unique 8-bit positive hexadecimal number
	 * @param description			the free text component
	 * @param numberOf				the initial number in stock
	 */
	public Beanbag(String manufacturer, String name, 
		    String id, String description, int numberOf) {
		this.name = name;
		this.manufacturerName = manufacturer;
		this.manufacturerId = id;
		
		this.priceInPence = 0;
		this.desc = description;
		
		this.reservations = 0;
		this.numberOf = numberOf;
	}

}

