package beanbags;

import java.io.IOException;

public class Store {
	//TODO: Implement each function. 
	//TODO: Need structures to handle current stock, reserved stock, and sold stock
	
	public ObjectArrayList batchList = new ObjectArrayList();
	public ObjectArrayList stockList = new ObjectArrayList();
	public ObjectArrayList reservationList = new ObjectArrayList();
	int reservationId=0;

	private boolean isLegalID(String id) {
		try {
    		Long.parseLong(id, 16);
    	}
        catch (NumberFormatException e) {
        	return false;
        }
		return true;
	}
	
	private Beanbag checkStockListID(String id) {
		for (int i = 0; i < stockList.size(); i++) {
			Beanbag b = (Beanbag) stockList.get(i);
			if (b.getManufacturerId()==id) {
				return b;
			}
		}
		return null;
	}
	
    public void addBeanBags(int num, String manufacturer, String name, 
    String id, short year, byte month)
    throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException,
    IllegalIDException, InvalidMonthException {
    	
    	if (num <= 0) throw new IllegalNumberOfBeanBagsAddedException();
    	if (!(month >= 1 && month <= 12)) throw new InvalidMonthException();
    	if (!isLegalID(id)) throw new IllegalIDException();
    	
    	Beanbag b = checkStockListID(id);
    	
    	if(b != null) {
			if(b.getName() == name && b.getManufacturerName() == manufacturer && b.getDescription() == "") {
				b.setNumberOf(b.getNumberOf()+num);
			}
			else throw new BeanBagMismatchException();
    	} 
    	else {
    		stockList.add(new Beanbag(manufacturer, name, id, num));
    	}
    	
    	batchList.add(new BeanbagBatch(id, year, month));
    }

    public void addBeanBags(int num, String manufacturer, String name, 
    String id, short year, byte month, String information)
    throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException,
    IllegalIDException, InvalidMonthException {
    	
    	if (num <= 0) throw new IllegalNumberOfBeanBagsAddedException();
    	if (!(month >= 1 && month <= 12)) throw new InvalidMonthException();
    	if (!isLegalID(id)) throw new IllegalIDException();
    	
    	Beanbag b = checkStockListID(id);
    	
    	if(b != null) {
			if(b.getName() == name && b.getManufacturerName() == manufacturer && b.getDescription() == information) {
				b.setNumberOf(b.getNumberOf()+num);
			}
			else throw new BeanBagMismatchException();
    	} 
    	else {
    		stockList.add(new Beanbag(manufacturer, name, id, information, num));
    	}
    	
    	batchList.add(new BeanbagBatch(id, year, month));
    }

    public void setBeanBagPrice(String id, int priceInPence) 
    throws InvalidPriceException, BeanBagIDNotRecognisedException, IllegalIDException {
    	if (priceInPence < 0) throw new InvalidPriceException();
    	if (!isLegalID(id)) throw new IllegalIDException();
    	
    	Beanbag b = checkStockListID(id);
    	
    	if (b == null) throw new BeanBagIDNotRecognisedException();
    	
    	b.setPriceInPence(priceInPence);
    }

    public void sellBeanBags(int num, String id) throws BeanBagNotInStockException,
    InsufficientStockException, IllegalNumberOfBeanBagsSoldException,
    PriceNotSetException, BeanBagIDNotRecognisedException, IllegalIDException { }

    public int reserveBeanBags(int num, String id) throws BeanBagNotInStockException,
    InsufficientStockException, IllegalNumberOfBeanBagsReservedException,
    PriceNotSetException, BeanBagIDNotRecognisedException, IllegalIDException { 
    	
    	if (num <= 0) throw new IllegalNumberOfBeanBagsReservedException();
    	if (!isLegalID(id)) throw new IllegalIDException();
    	
    	Beanbag b = checkStockListID(id);
    	
    	if (b == null) throw new BeanBagIDNotRecognisedException();
    	if (b.getPriceInPence() == -1) throw new PriceNotSetException();
    	
    	if (b.getNumberOf() == 0) throw new BeanBagNotInStockException();
    	
    	if (b.getNumberOf()-b.getReservations() >= num) { 
    		b.setReservations(b.getReservations()+num);
	    	reservationList.add(new BeanbagReservation(id, num, ++reservationId, b.getPriceInPence()));
	    	return reservationId; 
    	} 
    	else {
    		throw new InsufficientStockException();
    	}
    }

    public void unreserveBeanBags(int reservationNumber)
    throws ReservationNumberNotRecognisedException { }

    public void sellBeanBags(int reservationNumber)
    throws ReservationNumberNotRecognisedException { }

    public int beanBagsInStock() { 
    	int beanbagsInStock = 0;
    	for (int i = 0; i<  stockList.size(); i++) {
    		beanbagsInStock += ((Beanbag)stockList.get(i)).getNumberOf();
    	}
    	return beanbagsInStock;
    }

    public int reservedBeanBagsInStock() { 
    	int beanbagsReserved = 0;
		for (int i = 0; i<  stockList.size(); i++) {
			beanbagsReserved += ((Beanbag)stockList.get(i)).getReservations();
		}
		return beanbagsReserved; 
	}

    public int beanBagsInStock(String id) throws BeanBagIDNotRecognisedException,
    IllegalIDException { 
    	if (!isLegalID(id)) throw new IllegalIDException();
    	
    	Beanbag b = checkStockListID(id);
    	if (b == null) throw new BeanBagIDNotRecognisedException();
    	
    	return b.getNumberOf();
    }

    public void saveStoreContents(String filename) throws IOException { }

    public void loadStoreContents(String filename) throws IOException,
    ClassNotFoundException { }

    public int getNumberOfDifferentBeanBagsInStock() { 
    	return stockList.size();
    }

    public int getNumberOfSoldBeanBags() { return 0; }

    public int getNumberOfSoldBeanBags(String id) throws
    BeanBagIDNotRecognisedException, IllegalIDException { return 0; }

    public int getTotalPriceOfSoldBeanBags() { return 0; }

    public int getTotalPriceOfSoldBeanBags(String id) throws
    BeanBagIDNotRecognisedException, IllegalIDException { return 0; }

    public int getTotalPriceOfReservedBeanBags() { 
    	int beanbagsReservedPrice = 0;
		for (int i = 0; i<  reservationList.size(); i++) {
			beanbagsReservedPrice += ((BeanbagReservation)reservationList.get(i)).getPriceInPence();
		}
		return beanbagsReservedPrice; 
    }

    public String getBeanBagDetails(String id) throws
    BeanBagIDNotRecognisedException, IllegalIDException { 
    	if (!isLegalID(id)) throw new IllegalIDException();
    	
    	Beanbag b = checkStockListID(id);
    	if (b == null) throw new BeanBagIDNotRecognisedException();
    	
    	return b.getDescription();
    }

    public void empty() {
    	stockList = new ObjectArrayList();
    }
     
    public void resetSaleAndCostTracking() { }
     
    public void replace(String oldId, String replacementId) 
    throws BeanBagIDNotRecognisedException, IllegalIDException {
    	if (!isLegalID(oldId)) throw new IllegalIDException();
    	
    	Beanbag b = checkStockListID(oldId);
    	if (b == null) throw new BeanBagIDNotRecognisedException();
    	
    	b.setManufacturerId(replacementId);
    	
    	for (int i = 0; i < batchList.size(); i++) {
    		BeanbagBatch batch = (BeanbagBatch) batchList.get(i);
    		if (batch.getBeanbagId() == oldId) {
    			batch.setBeanbagId(replacementId);
    		}
    	}
    	for (int i = 0; i < reservationList.size(); i++) {
    		BeanbagReservation res = (BeanbagReservation) reservationList.get(i);
    		if (res.getBeanbagId() == oldId) {
    			res.setBeanbagId(replacementId);
    		}
    	} 
    }
}
