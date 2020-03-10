package beanbags;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Store class. Implements the BeanBagStore interface, and provides
 * functionality for the back-end stock system.
 * @author 690000912
 * @author can't remember the other ID
 * @version 1.2
 */
public class Store implements BeanBagStore {
	
	/*
	 * Declare our store lists, instantiate the empty ObjectArrayLists, and assign
	 * the ObjectArrayLists to the store lists.
	 */
    public ObjectArrayList batchList = new ObjectArrayList();
    public ObjectArrayList stockList = new ObjectArrayList();
    public ObjectArrayList reservationList = new ObjectArrayList();
    public ObjectArrayList saleList = new ObjectArrayList();

    
    /** 
     * Method checks a given ID to see if it is a positive hexadecimal number.
     * @param id  				the ID to be checked
     * @return  				<code>true</code>, if the ID is a positive hexadecimal 
     * 							number, and therefore a legal ID, or <code>false</code>,
     *							if the ID is not a positive hexadecimal number, and 
     *							therefore not a legal ID
     */
    private boolean isLegalID(String id) { //TODO: 8 bit check?
    	
    	/*
		 * If we can parse the string to a negative hex number,
		 * then it isn't positive and so we return false.
		 * 
		 * If we can parse the string to a non-negative hex number,
		 * then we drop out of the try-catch and return true.
		 * 
		 * If the string cannot be parsed to a hex number,
		 * then we return false.
		 */
        try {
            if (Long.parseLong(id, 16) < 0) return false;
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    /**
     * Method finds the next reservation ID to be used by iterating through the
     * reservation list and finding the maximal ID, then returning that plus one.
     * @return  				the next reservation ID to be used
     */
    private int getNewReservationID() {
    	// Start at a maximum reservation ID of 0.
        int maxResID = 0;
        
		/*
		 * Iterate through the reservation list, comparing each object's reservation
		 * ID to the current max. If its larger than the max, we set the current max
		 * equal to it, and continue.
		 */        
        for (int resIndex = 0; resIndex < reservationList.size(); resIndex++) {
            int resID = ((BeanbagReservation) reservationList.get(resIndex)).getReservationId();
            if (resID > maxResID) maxResID = resID;
        }
        
        // Add one to the maximum to make sure its the new max.
        return maxResID + 1;
    }

    /**
     * Method returns a Beanbag from stock with a given ID.
     * @param id  				the ID to search for in the stock list
     * @return  				the Beanbag with the given ID, or <code>null</code>
     * 							if there is no Beanbag with the given ID
     */
    private Beanbag checkStockListID(String id) {
    	
    	// Assume the precondition of a legal ID is met
    	assert isLegalID(id) : "Not a legal ID.";
    	
    	/*
    	 * Iterate through the stock list. If we find an object with the given ID,
    	 * return it.
    	 */
        for (int bagIndex = 0; bagIndex < stockList.size(); bagIndex++) {
            Beanbag bag = (Beanbag) stockList.get(bagIndex);
            if (bag.getManufacturerId() == id) {
                return bag;
            }
        }
        
        /*
         * If there was no object in the list with the given ID, return null.
         */
        return null;
    }

    /**
     * Method to check the reservation list for a given reservation ID.
     * @param reservationID  			the ID to look for in the reservation list
     * @return  						the reservation with the given reservation 
     * 									ID, or <code>null</code> if no reservation
     * 									has the given ID
     */
    private BeanbagReservation checkReservationID(int reservationID) {
    	
    	// Assume precondition of a positive reservation ID is met
    	assert reservationID > 0 : "The reservation number was less than 1.";
    	
    	/*
    	 * Iterate through the reservation list, and compare the reservation's ID
    	 * to the given ID. If they match, return the reservation.
    	 */
        for (int resIndex = 0; resIndex < reservationList.size(); resIndex++) {
            BeanbagReservation reservation = (BeanbagReservation) reservationList.get(resIndex);
            if (reservation.getReservationId() == reservationID) {
                return reservation;
            }
        }
        
        /*
         * If there was no object in the list with the given ID, return null.
         */
        return null;
    }

    public void addBeanBags(int num, String manufacturer, String name,
        String id, short year, byte month)
    throws IllegalNumberOfBeanBagsAddedException, BeanBagMismatchException,
    IllegalIDException, InvalidMonthException {

		/*
		 * Handle for pre-condition exceptions.
		 * 
		 * If the number is less than 1, throw an IllegalNumberOfBeanBagsAddedException.
		 * If the month is not between 1 and 12, throw an InvalidMonthException.
		 * If the ID is illegal, throw an IllegalIDException.
		 */    	
        if (num < 1) throw new IllegalNumberOfBeanBagsAddedException();
        if (!(month >= 1 && month <= 12)) throw new InvalidMonthException();
        if (!isLegalID(id)) throw new IllegalIDException();

        
        /*
         * Attempt to retrieve the beanbag from the stock list with the given
         * ID. If none exists, then we can create a new Beanbag object and add
         * it to the stock list, seeing as we know the information is valid 
         * (we can validate it, not verify it).
         */
        Beanbag b = checkStockListID(id);

        if (b != null) {
        	
        	/*
        	 * If the ID does exist, then we need to check the text attributes of
        	 * this new attempted addition are the same. If they are, we can simply
        	 * add to the amount of this existing Beanbag.
        	 * 
        	 * If they aren't, then throw a BeanBagMismatchException.
        	 */
            if (b.getName() == name 
            		&& b.getManufacturerName() == manufacturer 
            		&& b.getDescription() == "") {
                b.setNumberOf(b.getNumberOf() + num);
            } else throw new BeanBagMismatchException();
        } else {
            stockList.add(new Beanbag(manufacturer, name, id, num));
        }

        /*
         * In order to store the year and month of a given batch of beanbags
         * somewhere in the system, we add a batch to the batch list, with the
         * ID of the type of beanbag we are referring to, and the date.
         * 
         * If we have reached this point, the beanbags have been added to the
         * stock one way or the other, so we always add the new batch.
         */
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

        if (b != null) {
            if (b.getName() == name 
            		&& b.getManufacturerName() == manufacturer 
            		&& b.getDescription() == information) {
                b.setNumberOf(b.getNumberOf() + num);
            } else throw new BeanBagMismatchException();
        } else {
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
            if (b.getPriceInPence() == -1) throw new PriceNotSetException(); // TODO: move this to after sufficient stock check

            if (b.getNumberOf() == 0) throw new BeanBagNotInStockException();

            if (b.getNumberOf() - b.getReservations() >= num) {
                b.setNumberOf(b.getNumberOf() - num);
                saleList.add(new BeanbagSale(id, num, b.getPriceInPence()));
            } else {
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
            if (b.getPriceInPence() == -1) throw new PriceNotSetException(); // TODO: move this to after sufficient stock check

            if (b.getNumberOf() == 0) throw new BeanBagNotInStockException();

            if (b.getNumberOf() - b.getReservations() >= num) {
                b.setReservations(b.getReservations() + num);
                int newResID = getNewReservationID();
                reservationList.add(new BeanbagReservation(id, num, newResID, b.getPriceInPence()));
                return newResID;
            } else {
                throw new InsufficientStockException();
            }
        }

    public void unreserveBeanBags(int reservationNumber)
    throws ReservationNumberNotRecognisedException {
        BeanbagReservation r = checkReservationID(reservationNumber);
        if (r == null) throw new ReservationNumberNotRecognisedException();

        Beanbag b = checkStockListID(r.getBeanbagId());
        b.setReservations(b.getReservations() - r.getReservationAmount());
        reservationList.remove(r);
    }

    public void sellBeanBags(int reservationNumber)
    throws ReservationNumberNotRecognisedException {
        BeanbagReservation r = checkReservationID(reservationNumber);
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
        for (int i = 0; i < stockList.size(); i++) {
            beanbagsInStock += ((Beanbag) stockList.get(i)).getNumberOf();
        }
        return beanbagsInStock;
    }

    public int reservedBeanBagsInStock() {
        int beanbagsReserved = 0;
        for (int i = 0; i < stockList.size(); i++) {
            beanbagsReserved += ((Beanbag) stockList.get(i)).getReservations();
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
            ObjectArrayList[] storeContents = new ObjectArrayList[] {
                stockList,
                batchList,
                reservationList,
                saleList
            };
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(storeContents);
            objectOut.close();
        } catch (IOException ex) {
            throw new IOException();
        }
    }

    public void loadStoreContents(String filename) throws IOException,
        ClassNotFoundException {
            try {
                FileInputStream fileIn = new FileInputStream(filename);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                ObjectArrayList[] storeContents = (ObjectArrayList[]) objectIn.readObject();
                stockList = storeContents[0];
                batchList = storeContents[1];
                reservationList = storeContents[2];
                saleList = storeContents[3];
                objectIn.close();
            } catch (IOException exIO) {
                throw new IOException();
            } catch (ClassNotFoundException exNoClass) {
                throw new ClassNotFoundException();
            }
        }

    public int getNumberOfDifferentBeanBagsInStock() {
        return stockList.size();
    }

    public int getNumberOfSoldBeanBags() {
        int beanbagsSales = 0;
        for (int i = 0; i < saleList.size(); i++) {
            beanbagsSales += ((BeanbagSale) saleList.get(i)).getSaleAmount();
        }
        return beanbagsSales;
    }

    public int getNumberOfSoldBeanBags(String id) throws
    BeanBagIDNotRecognisedException, IllegalIDException {
        if (!isLegalID(id)) throw new IllegalIDException();

        Beanbag b = checkStockListID(id);
        if (b == null) throw new BeanBagIDNotRecognisedException();

        int beanbagsSales = 0;
        for (int i = 0; i < saleList.size(); i++) {
            BeanbagSale s = (BeanbagSale) saleList.get(i);
            if (s.getBeanbagId() == id) {
                beanbagsSales += s.getSaleAmount();
            }
        }
        return beanbagsSales;
    }

    public int getTotalPriceOfSoldBeanBags() {
        int beanbagsSales = 0;
        for (int i = 0; i < saleList.size(); i++) {
            beanbagsSales += ((BeanbagSale) saleList.get(i)).getPriceInPence();
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
        for (int i = 0; i < reservationList.size(); i++) {
            beanbagsReservedPrice += ((BeanbagReservation) reservationList.get(i)).getPriceInPence();
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
        batchList = new ObjectArrayList();
        reservationList = new ObjectArrayList();
        saleList = new ObjectArrayList();
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