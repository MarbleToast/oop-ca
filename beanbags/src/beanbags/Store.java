package beanbags;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Store {
	
	public ObjectArrayList batchList = new ObjectArrayList();
	public ObjectArrayList stockList = new ObjectArrayList();
	public ObjectArrayList reservationList = new ObjectArrayList();
	public ObjectArrayList saleList = new ObjectArrayList();
	int reservationId = 0;

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
	
	private BeanbagReservation checkReservationNumber(int resNumber) {
		for (int i = 0; i < reservationList.size(); i++) {
			BeanbagReservation b = (BeanbagReservation) reservationList.get(i);
			if (b.getReservationId()==resNumber) {
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
    PriceNotSetException, BeanBagIDNotRecognisedException, IllegalIDException {
    	if (!isLegalID(id)) throw new IllegalIDException();
    	if (num <= 0) throw new IllegalNumberOfBeanBagsSoldException();
    	
    	Beanbag b = checkStockListID(id);
    	
    	if (b == null) throw new BeanBagIDNotRecognisedException();
    	if (b.getPriceInPence() == -1) throw new PriceNotSetException();
    	
    	if (b.getNumberOf() == 0) throw new BeanBagNotInStockException();
    	
    	if (b.getNumberOf()-b.getReservations() >= num) { 
    		b.setNumberOf(b.getNumberOf() - num);
    		saleList.add(new BeanbagSale(id, num, b.getPriceInPence()));
    	}
    	else {
    		throw new InsufficientStockException();
    	}
    }

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
    throws ReservationNumberNotRecognisedException {
    	BeanbagReservation r = checkReservationNumber(reservationNumber);
    	if (r == null) throw new ReservationNumberNotRecognisedException();
    	
    	Beanbag b = checkStockListID(r.getBeanbagId());
    	b.setReservations(b.getReservations() - r.getReservationAmount());
    	reservationList.remove(r);
    }

    public void sellBeanBags(int reservationNumber)
    throws ReservationNumberNotRecognisedException { 
    	BeanbagReservation r = checkReservationNumber(reservationNumber);
    	if (r == null) throw new ReservationNumberNotRecognisedException();
    	
    	Beanbag b = checkStockListID(r.getBeanbagId());
    	b.setReservations(b.getReservations() - r.getReservationAmount());
    	b.setNumberOf(b.getNumberOf() - r.getReservationAmount());
    	
    	int price = b.getPriceInPence() < r.getPriceInPence() ? b.getPriceInPence() : r.getPriceInPence();
    	
    	saleList.add(new BeanbagSale(b.getManufacturerId(), r.getReservationAmount(), price));
    	reservationList.remove(r);
    }

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

    public void saveStoreContents(String filename) throws IOException {
    	try {	 
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(stockList);
            objectOut.close();
        } 
    	catch (IOException ex) {
            throw new IOException();
        }
    }

    public void loadStoreContents(String filename) throws IOException,
    ClassNotFoundException {
    	 try {
             FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn);
             stockList = (ObjectArrayList) objectIn.readObject();
             objectIn.close();
         } 
    	 catch (IOException exIO) {
             throw new IOException();
         }
    	 catch (ClassNotFoundException exNoClass) {
    		 throw new ClassNotFoundException();
    	 }
    }

    public int getNumberOfDifferentBeanBagsInStock() { 
    	return stockList.size();
    }

    public int getNumberOfSoldBeanBags() { 
    	int beanbagsSales = 0;
		for (int i = 0; i< saleList.size(); i++) {
			beanbagsSales += ((BeanbagSale)saleList.get(i)).getSaleAmount();
		}
		return beanbagsSales; 
    }

    public int getNumberOfSoldBeanBags(String id) throws
    BeanBagIDNotRecognisedException, IllegalIDException { 
    	if (!isLegalID(id)) throw new IllegalIDException();
    	
    	Beanbag b = checkStockListID(id);
    	if (b == null) throw new BeanBagIDNotRecognisedException();
    	
    	int beanbagsSales = 0;
		for (int i = 0; i< saleList.size(); i++) {
			BeanbagSale s = (BeanbagSale) saleList.get(i);
			if (s.getBeanbagId() == id) {
				beanbagsSales += s.getSaleAmount();
			}
		}
		return beanbagsSales; 
    }

    public int getTotalPriceOfSoldBeanBags() { 
    	int beanbagsSales = 0;
		for (int i = 0; i< saleList.size(); i++) {
			beanbagsSales += ((BeanbagSale)saleList.get(i)).getPriceInPence();
		}
		return beanbagsSales; 
    }

    public int getTotalPriceOfSoldBeanBags(String id) throws
    BeanBagIDNotRecognisedException, IllegalIDException { 
    	if (!isLegalID(id)) throw new IllegalIDException();
    	
    	Beanbag b = checkStockListID(id);
    	if (b == null) throw new BeanBagIDNotRecognisedException();
    	
    	int beanbagsSales = 0;
		for (int i = 0; i < saleList.size(); i++) {
			BeanbagSale s = (BeanbagSale) saleList.get(i);
			if (s.getBeanbagId() == id) {
				beanbagsSales += s.getPriceInPence();
			}
		}
		return beanbagsSales; 
    }

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
     
    public void resetSaleAndCostTracking() { 
    	saleList = new ObjectArrayList();
    }
     
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
    	for (int i = 0; i < saleList.size(); i++) {
    		BeanbagSale s = (BeanbagSale) saleList.get(i);
    		if (s.getBeanbagId() == oldId) {
    			s.setBeanbagId(replacementId);
    		}
    	} 
    }
}
