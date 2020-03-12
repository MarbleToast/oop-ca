package beanbags;

/**
 * Beanbag reservation class. Holds information on reservations.
 * @author 690000912
 * @author can't remember the other ID
 * @version 1.2.1
 */
public class BeanbagReservation {
	
	// CLASS ATTRIBUTES
	private String beanbagId; // The beanbag ID
	private int reservationAmount; // The number reserved
	private int reservationId; // The reservation number
	private int priceInPence; // The price reserved at
	
	// MUTATORS
	/**
	 * Method sets the beanbag ID.
	 * @param id					the new beanbag ID
	 */
	public void setBeanbagId(String id) {
		this.beanbagId = id;
	}
	
	/**
	 * Method sets the number reserved
	 * @param reservationAmount		the new number reserved
	 */
	public void setReservationAmount(int reservationAmount) {
		this.reservationAmount = reservationAmount;
	}
	
	/**
	 * Method sets the reservation number
	 * @param reservationId			the new reservation number
	 */
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	
	/**
	 * Method sets the price reserved at
	 * @param priceInPence			the new price reserved at
	 */
	public void setPriceInPence(int priceInPence) {
		this.priceInPence = priceInPence;
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
	 * Method returns the current amount reserved
	 * @return						the amount reserved
	 */
	public int getReservationAmount() {
		return this.reservationAmount;
	}
	
	/**
	 * Method returns the current reservation number
	 * @return						the current reservation number
	 */
	public int getReservationId() {
		return this.reservationId;
	}
	
	/**
	 * Method returns the current price
	 * @return						the current price
	 */
	public int getPriceInPence() {
		return this.priceInPence;
	}
	
	/**
	 * Beanbag reservation constructor.
	 * @param id					the beanbag ID
	 * @param amount				the amount reserved
	 * @param reservationId			the reservation number
	 * @param price					the price reserved at
	 */
	public BeanbagReservation(String id, int amount, int reservationId, int price) {
		this.beanbagId = id;
		this.reservationAmount = amount;
		this.reservationId = reservationId;
		this.priceInPence = price;
	}
}
