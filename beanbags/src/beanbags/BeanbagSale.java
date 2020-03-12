package beanbags;

/**
 * Beanbag sale class. Holds information on a sale of a particular
 * beanbag.
 * @author 690000912
 * @author can't remember the other ID
 * @version 1.0
 */
public class BeanbagSale {
	
	// CLASS ATTRIBUTES
	private String beanbagId; // The beanbag ID
	private int saleAmount; // The amount sold
	private int priceInPence; // The price sold at
	
	// MUTATORS
	/**
	 * Method sets the beanbag ID
	 * @param id					the new beanbag ID
	 */
	public void setBeanbagId(String id) {
		this.beanbagId = id;
	}
	
	
	// ACCESSORS
	/**
	 * Method returns the current beanbag ID
	 * @return						the current beanbag ID
	 */
	public String getBeanbagId() {
		return this.beanbagId;
	}
	
	/**
	 * Method returns the number sold
	 * @return						the number sold
	 */
	public int getSaleAmount() {
		return this.saleAmount;
	}
	
	/**
	 * Method returns the price sold at
	 * @return						the number sold
	 */
	public int getPriceInPence() {
		return this.priceInPence;
	}
	
	/**
	 * Beanbag sale constructor.
	 * @param id					the beanbag ID
	 * @param amount				the amount sold
	 * @param price					the price sold at
	 */
	public BeanbagSale(String id, int amount, int price) {
		this.beanbagId = id;
		this.saleAmount = amount;
		this.priceInPence = price;
	}
}
