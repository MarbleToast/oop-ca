package beanbags;

public class BeanbagSale {
	private String beanbagId;
	private int saleAmount;
	private int priceInPence;
	
	public BeanbagSale(String id, int amount, int price) {
		this.beanbagId = id;
		this.saleAmount = amount;
		this.priceInPence = price;
	}
	
	public String getBeanbagId() {
		return this.beanbagId;
	}
	public int getSaleAmount() {
		return this.saleAmount;
	}
	public int getPriceInPence() {
		return this.priceInPence;
	}
	
	public void setBeanbagId(String id) {
		this.beanbagId = id;
	}
}
