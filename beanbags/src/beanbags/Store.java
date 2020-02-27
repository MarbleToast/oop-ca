package beanbags;

import java.io.IOException;

public class Store {
	//TODO: Implement each function. 
	//TODO: Need structures to handle current stock, reserved stock, and sold stock
	
	public ObjectArrayList stockList = new ObjectArrayList();
	
	private boolean isLegalID(String id) {
		try {
    		Long.parseLong(id, 16);
    	}
        catch (NumberFormatException e) {
        	return false;
        }
		return true;
	}
	
    public void addBeanBags(int num, String manufacturer, String name, 
    String id, short year, byte month)
    throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException,
    IllegalIDException, InvalidMonthException {
    	
    	if (num <= 0) throw new IllegalNumberOfBeanBagsAddedException();
    	if (!(month >= 1 && month <= 12)) throw new InvalidMonthException();
    	
    	if (!isLegalID(id)) throw new IllegalIDException();
    	
    	for (int i = 0; i<stockList.size(); i++) {
    		Beanbag b = (Beanbag) stockList.get(i);
    		if (b.getManufacturerId()==id) {
    			
    		}
    	}
    	
    	for (int i = 0; i < num; i++) {
    		stockList.add(new Beanbag(manufacturer, name, id, year, month));
    	}
    }

    public void addBeanBags(int num, String manufacturer, String name, 
    String id, short year, byte month, String information)
    throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException,
    IllegalIDException, InvalidMonthException {

    	if (num <= 0) throw new IllegalNumberOfBeanBagsAddedException();
    	if (!(month >= 1 && month <= 12)) throw new InvalidMonthException();
    	
    	if (!isLegalID(id)) throw new IllegalIDException();
    	
    	for (int i = 0; i<stockList.size(); i++) {
    		Beanbag b = (Beanbag) stockList.get(i);
    		if (b.getManufacturerId()==id) {
    			if (b.getName()==name) {
    				if (b.getManufacturerName()==manufacturer) {
    					if (b.getDescription()==information) {
    						throw new BeanBagMismatchException();
    					}
    				}
    			}
    		}
    	}
    	
    	for (int i = 0; i < num; i++) {
    		stockList.add(new Beanbag(manufacturer, name, id, year, month, information));
    	}
    }

    public void setBeanBagPrice(String id, int priceInPence) 
    throws InvalidPriceException, BeanBagIDNotRecognisedException, IllegalIDException { }

    public void sellBeanBags(int num, String id) throws BeanBagNotInStockException,
    InsufficientStockException, IllegalNumberOfBeanBagsSoldException,
    PriceNotSetException, BeanBagIDNotRecognisedException, IllegalIDException { }

    public int reserveBeanBags(int num, String id) throws BeanBagNotInStockException,
    InsufficientStockException, IllegalNumberOfBeanBagsReservedException,
    PriceNotSetException, BeanBagIDNotRecognisedException, IllegalIDException { return 0; }

    public void unreserveBeanBags(int reservationNumber)
    throws ReservationNumberNotRecognisedException { }

    public void sellBeanBags(int reservationNumber)
    throws ReservationNumberNotRecognisedException { }

    public int beanBagsInStock() { return stockList.size(); }

    public int reservedBeanBagsInStock() { return 0; }

    public int beanBagsInStock(String id) throws BeanBagIDNotRecognisedException,
    IllegalIDException { 
    	if (!isLegalID(id)) throw new IllegalIDException();
    	int count = 0;
    	for (int i = 0; i < stockList.size(); i++) {
    		if (((Beanbag)stockList.get(i)).getManufacturerId() == id) count++;
    	}
    	if (count == 0) throw new BeanBagIDNotRecognisedException();
    	
    	return count;
    }

    public void saveStoreContents(String filename) throws IOException { }

    public void loadStoreContents(String filename) throws IOException,
    ClassNotFoundException { }

    public int getNumberOfDifferentBeanBagsInStock() { 
    	ObjectArrayList previous = new ObjectArrayList();
    	for (int i = 0; i < stockList.size(); i++) {
    		
    		boolean isInList = false;
    		Beanbag b = (Beanbag) stockList.get(i);
    		
    		for (int n = 0; n < previous.size(); n++) {
	    		if (b.getManufacturerId()==((Beanbag)previous.get(n)).getManufacturerId()) {
	    			isInList = true;
	    			break;
	    		}
	    	}
    		if (!isInList) previous.add(b);
    	}
    	return previous.size();
    }

    public int getNumberOfSoldBeanBags() { return 0; }

    public int getNumberOfSoldBeanBags(String id) throws
    BeanBagIDNotRecognisedException, IllegalIDException { return 0; }

    public int getTotalPriceOfSoldBeanBags() { return 0; }

    public int getTotalPriceOfSoldBeanBags(String id) throws
    BeanBagIDNotRecognisedException, IllegalIDException { return 0; }

    public int getTotalPriceOfReservedBeanBags() { return 0; }

    public String getBeanBagDetails(String id) throws
    BeanBagIDNotRecognisedException, IllegalIDException { return ""; }

    public void empty() {
    	stockList = new ObjectArrayList();
    }
     
    public void resetSaleAndCostTracking() { }
     
    public void replace(String oldId, String replacementId) 
    throws BeanBagIDNotRecognisedException, IllegalIDException { }
}
