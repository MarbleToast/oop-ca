package beanbags;

public class BeanbagBatch {
	private String beanbagId;
	private short year;
	private byte month;
	
	public BeanbagBatch(String id, short year, byte month) {
		this.beanbagId = id;
		this.year = year;
		this.month = month;
	}
	
	public void setBeanbagId(String id) {
		this.beanbagId = id;
	}
	public void setYear(short year) {
		this.year = year;
	}
	public void setMonth(byte month) {
		this.month = month;
	}
	
	public String getBeanbagId() {
		return this.beanbagId;
	}
	public short getYear() {
		return this.year;
	}
	public byte getMonth() {
		return this.month;
	}
}
