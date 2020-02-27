package beanbags;

public class Beanbag {
	private String name;
	private String manufacturerName;
	private String manufacturerId;
	private short manufactureYear;
	private byte manufactureMonth;
	private int numberOf;
	
	int priceInPence;
	
	
	String desc;
	
	int reservations;
	
	public void setNumberOf(int numberOf) {
		this.numberOf = numberOf;
	}
	
	public void setManufacturerId(String id) {
		this.manufacturerId = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
	public void setManufacturerYear(short manufactureYear) {
		this.manufactureYear = manufactureYear;
	}
	public void setManufacturerMonth(byte manufactureMonth) {
		this.manufactureMonth = manufactureMonth;
	}
	public void setPriceInPence(int priceInPence) {
		this.priceInPence = priceInPence;
	}
	public void setDescription(String desc) {
		this.desc = desc;
	}
	public void setReservations(int reserved) {
		this.reservations = reserved;
	}
	

	public int getNumberOf() {
		return this.numberOf;
	}
	public String getManufacturerId() {
		return this.manufacturerId;
	}
	public String getName() {
		return this.name;
	}
	public String getManufacturerName() {
		return this.manufacturerName;
	}
	public short getManufacturerYear() {
		return this.manufactureYear;
	}
	public byte getManufacturerMonth() {
		return this.manufactureMonth;
	}
	public int getPriceInPence() {
		return this.priceInPence;
	}
	public String getDescription() {
		return this.desc;
	}
	public int getReservations() {
		return this.reservations;
	}
	
	public Beanbag(String manufacturer, String name, 
		    String id, short year, byte month, int numberOf) {
		this.name = name;
		this.manufacturerName = manufacturer;
		this.manufacturerId = id;
		this.manufactureYear = year;
		this.manufactureMonth = month;
		
		this.priceInPence = 0;
		this.desc = "";
		
		this.reservations = 0;
		this.numberOf = numberOf;
	}
	
	public Beanbag(String manufacturer, String name, 
		    String id, short year, byte month, String description, int numberOf) {
		this.name = name;
		this.manufacturerName = manufacturer;
		this.manufacturerId = id;
		this.manufactureYear = year;
		this.manufactureMonth = month;
		this.priceInPence = 0;
		this.desc = description;
		this.reservations = 0;
		this.numberOf = numberOf;
	}

}
