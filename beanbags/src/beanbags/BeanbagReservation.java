package beanbags;

public class BeanbagReservation {
	private String beanbagId;
	private int reservationAmount;
	private int reservationId;
	private int priceInPence;
	
	public BeanbagReservation(String id, int amount, int reservationId, int price) {
		this.beanbagId = id;
		this.reservationAmount = amount;
		this.reservationId = reservationId;
		this.priceInPence = price;
	}
	
	public void setBeanbagId(String id) {
		this.beanbagId = id;
	}
	public void setReservationAmount(int reservationAmount) {
		this.reservationAmount = reservationAmount;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	public void setPriceInPence(int priceInPence) {
		this.priceInPence = priceInPence;
	}
	
	public String getBeanbagId() {
		return this.beanbagId;
	}
	public int getReservationAmount() {
		return this.reservationAmount;
	}
	public int getReservationId() {
		return this.reservationId;
	}
	public int getPriceInPence() {
		return this.priceInPence;
	}
}
