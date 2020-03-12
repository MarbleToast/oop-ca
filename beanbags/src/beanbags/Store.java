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
 * @author 690008290
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
    private boolean isLegalID(String id) {
    	
    	/*
    	 * If the string's length is not 8, it is not 8-bit, and
    	 * therefore we return false.
    	 * 
		 * If we can parse the string to a negative hex number,
		 * then it isn't positive and so we return false.
		 * 
		 * If we can parse the string to a non-negative hex number,
		 * then we drop out of the try-catch and return true.
		 * 
		 * If the string cannot be parsed to a hex number,
		 * then we return false.
		 */
    	if (id.length() != 8) {
    		return false;
    	}
        try {
            if (Long.parseLong(id, 16) < 0) {
            	return false;
            }
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
            if (resID > maxResID) {
            	maxResID = resID;
            }
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
		 * Handle for precondition exceptions.
		 * 
		 * If the number is less than 1, throw an IllegalNumberOfBeanBagsAddedException.
		 * If the month is not between 1 and 12, throw an InvalidMonthException.
		 * If the ID is illegal, throw an IllegalIDException.
		 */    	
        if (num < 1) {
        	throw new IllegalNumberOfBeanBagsAddedException(num + " is less than 0");
        }
        if (!(month >= 1 && month <= 12)) {
        	throw new InvalidMonthException(month + " is not a valid month (1-12)");
        }
        if (!isLegalID(id)) {
        	throw new IllegalIDException(id + " is not a valid ID");
        }

        /*
         * Attempt to retrieve the beanbag from the stock list with the given
         * ID. If none exists, then we can create a new Beanbag object and add
         * it to the stock list, seeing as we know the information is valid 
         * (we can validate it, not verify it).
         */
        Beanbag bag = checkStockListID(id);

        if (bag != null) {
        	
        	/*
        	 * If the ID does exist, then we need to check the text attributes of
        	 * this new attempted addition are the same. If they are, we can simply
        	 * add to the amount of this existing Beanbag.
        	 * 
        	 * If they aren't, then throw a BeanBagMismatchException.
        	 */
            if (bag.getName() == name 
            		&& bag.getManufacturerName() == manufacturer 
            		&& bag.getDescription() == "") {
                bag.setNumberOf(bag.getNumberOf() + num);
            } 
            else {
            	throw new BeanBagMismatchException(id + "is already in use");
            }
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

    	/*
		 * Handle for precondition exceptions.
		 * 
		 * If the number is less than 1, throw an IllegalNumberOfBeanBagsAddedException.
		 * If the month is not between 1 and 12, throw an InvalidMonthException.
		 * If the ID is illegal, throw an IllegalIDException.
		 */ 
        if (num < 1) {
        	throw new IllegalNumberOfBeanBagsAddedException(num + " is less than 1");
        }
        if (!(month >= 1 && month <= 12)) {
        	throw new InvalidMonthException(month + " is not a valid month (1-12)");
        }
        if (!isLegalID(id)) {
        	throw new IllegalIDException(id + " is not a valid ID");
        }

        /*
         * Attempt to retrieve the beanbag from the stock list with the given
         * ID. If none exists, then we can create a new Beanbag object and add
         * it to the stock list, seeing as we know the information is valid 
         * (we can validate it, not verify it).
         */
        Beanbag bag = checkStockListID(id);

        if (bag != null) {
        	
        	/*
        	 * If the ID does exist, then we need to check the text attributes of
        	 * this new attempted addition are the same. If they are, we can simply
        	 * add to the amount of this existing Beanbag.
        	 * 
        	 * If they aren't, then throw a BeanBagMismatchException.
        	 */
            if (bag.getName() == name 
            		&& bag.getManufacturerName() == manufacturer 
            		&& bag.getDescription() == information) {
                bag.setNumberOf(bag.getNumberOf() + num);
            } 
            else {
            	throw new BeanBagMismatchException(id + "is already in use");
            }
        } else {
            stockList.add(new Beanbag(manufacturer, name, id, information, num));
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

    
    public void setBeanBagPrice(String id, int priceInPence)
    throws InvalidPriceException, BeanBagIDNotRecognisedException, IllegalIDException {
    	
    	/*
		 * Handle for precondition exceptions.
		 * 
		 * If the price is less than 1, throw an InvalidPriceException.
		 * If the ID is illegal, throw an IllegalIDException.
		 */ 
        if (priceInPence < 1) {
        	throw new InvalidPriceException(priceInPence + " is less than 1");
        }
        if (!isLegalID(id)) {
        	throw new IllegalIDException(id + " is not a valid ID");
        }

        
        /*
         * Attempt to retrieve the beanbag from the stock list with the given
         * ID. If none exists, then we throw a BeanBagIDNotRecognisedException.
         */
        Beanbag b = checkStockListID(id);

        if (b == null) {
        	throw new BeanBagIDNotRecognisedException(id + " does not match an existing ID");
        }

        // If we're here then we can set the beanbag's price.
        b.setPriceInPence(priceInPence);
    }

    
    public void sellBeanBags(int num, String id) throws BeanBagNotInStockException,
        InsufficientStockException, IllegalNumberOfBeanBagsSoldException,
        PriceNotSetException, BeanBagIDNotRecognisedException, IllegalIDException {
    	
    	/*
		 * Handle for precondition exceptions.
		 * 
		 * If the ID is illegal, throw an IllegalIDException.
		 * If num is less than 1, throw an IllegalNumberOfBeanBagsSoldException.
		 */ 
        if (!isLegalID(id)) {
        	throw new IllegalIDException(id + " is not a valid ID");
        }
        if (num < 1) {
        	throw new IllegalNumberOfBeanBagsSoldException(num + " is less than 1");
        }

        /*
         * Attempt to retrieve the beanbag from the stock list with the given
         * ID. If none exists, then we throw a BeanBagIDNotRecognisedException.
         * If the beanbag does exist but there is none in stock, we throw a 
         * BeanBagNotInStockException.
         */
        Beanbag bag = checkStockListID(id);

        if (bag == null) {
        	throw new BeanBagIDNotRecognisedException(id + " does not match an existing ID");
        }
        if (bag.getNumberOf() == 0) {
        	throw new BeanBagNotInStockException("Beanbag " + id + " is out of stock");
        }

        /*
         * If there is sufficient, unreserved stock, then we check the beanbag's
         * price. If it is not set, then we throw a PriceNotSetException.
         * Otherwise, we take the number from the beanbag's stock, and make
         * note of a new sale in the sale list.
         * 
         * If there is not sufficient stock, we throw an InsufficientStockException.
         */
        if (bag.getNumberOf() - bag.getReservations() >= num) {
        	if (bag.getPriceInPence() == -1) {
        		throw new PriceNotSetException("Price not set on beanbag " + id);
        	}
        	
            bag.setNumberOf(bag.getNumberOf() - num);
            saleList.add(new BeanbagSale(id, num, bag.getPriceInPence()));
        } 
        else {
            throw new InsufficientStockException("Insufficient stock of beanbag " + id);
        }
    }

    
    public int reserveBeanBags(int num, String id) throws BeanBagNotInStockException,
        InsufficientStockException, IllegalNumberOfBeanBagsReservedException,
        PriceNotSetException, BeanBagIDNotRecognisedException, IllegalIDException {
    	
    	/*
		 * Handle for precondition exceptions.
		 * 
		 * If the ID is illegal, throw an IllegalIDException.
		 * If num is less than 1, throw an IllegalNumberOfBeanBagsReservedException.
		 */ 

        if (num < 1) {
        	throw new IllegalNumberOfBeanBagsReservedException(num + " is less than 1");
        }
        if (!isLegalID(id)) {
        	throw new IllegalIDException(id + " is not a valid ID");
        }

        /*
         * Attempt to retrieve the beanbag from the stock list with the given
         * ID. If none exists, then we throw a BeanBagIDNotRecognisedException.
         * If the beanbag does exist but there is none in stock, we throw a 
         * BeanBagNotInStockException.
         */
        Beanbag bag = checkStockListID(id);

        if (bag == null) {
        	throw new BeanBagIDNotRecognisedException(id + " does not match an existing ID");
        }
        if (bag.getNumberOf() == 0) {
        	throw new BeanBagNotInStockException("Beanbag " + id + " is out of stock");
        }

        /*
         * If there is sufficient, unreserved stock, then we check the beanbag's
         * price. If it is not set, then we throw a PriceNotSetException.
         * Otherwise, we increase the number of reservations in the beanbag listing,
         * find the next reservation number to use, create the reservation in the
         * reservation list, and return the reservation number.
         * 
         * If there is not sufficient stock, we throw an InsufficientStockException.
         */
        if (bag.getNumberOf() - bag.getReservations() >= num) {
        	if (bag.getPriceInPence() == -1) {
        		throw new PriceNotSetException("Price not set on beanbag " + id);
        	}
        	
            bag.setReservations(bag.getReservations() + num);
            int newResID = getNewReservationID();
            reservationList.add(new BeanbagReservation(id, num, newResID, bag.getPriceInPence()));
            return newResID;
        } 
        else {
        	throw new InsufficientStockException("Insufficient stock of beanbag " + id);
        }
    }

    
    public void unreserveBeanBags(int reservationID)
    throws ReservationNumberNotRecognisedException {
    	
    	/*
         * Attempt to retrieve the reservation from the reservation list with
         * the given reservation number. If there isn't one, throw a
         * ReservationNumberNotRecognisedException.
         */
        BeanbagReservation reservation = checkReservationID(reservationID);
        if (reservation == null) {
        	throw new ReservationNumberNotRecognisedException("Reservation number not recognised");
        }

        /*
         * Retrieve the beanbag in the reservation, then reduce its reservations
         * by the amount in the to-be-unreserved reservation. Then, we remove
         * the reservation from the reservation list.
         */
        Beanbag bag = checkStockListID(reservation.getBeanbagId());
        
        /*
         * The ID in the reservation is assumed to be valid, as it would have
         * been valid on the beanbag's creation, and in an ID change, the
         * reservation is similarly updated- ergo, there should be no chance
         * of desync.
         */
        assert bag != null : "A reservation is pointing to nothing";
        bag.setReservations(bag.getReservations() - reservation.getReservationAmount());
        reservationList.remove(reservation);
    }

    
    public void sellBeanBags(int reservationNumber)
    throws ReservationNumberNotRecognisedException {
    	
    	/*
         * Attempt to retrieve the reservation from the reservation list with
         * the given reservation number. If there isn't one, throw a
         * ReservationNumberNotRecognisedException.
         */
        BeanbagReservation reservation = checkReservationID(reservationNumber);
        if (reservation == null) {
        	throw new ReservationNumberNotRecognisedException("Reservation number not recognised");
        }

        /*
         * Retrieve the beanbag in the reservation, then reduce its reservations
         * and unreserved stock by the amount in the to-be-sold reservation.
         * Then, we find which price was cheaper; the at-reserve price or the
         * current price. We then make a note of the sale at the lower price
         * and remove the reservation from the reservation list.
         */
        Beanbag bag = checkStockListID(reservation.getBeanbagId());
        
        /*
         * The ID in the reservation is assumed to be valid, as it would have
         * been valid on the beanbag's creation, and in an ID change, the
         * reservation is similarly updated- ergo, there should be no chance
         * of desync.
         */
        assert bag != null : "A reservation is pointing to nothing";
        bag.setReservations(bag.getReservations() - reservation.getReservationAmount());
        bag.setNumberOf(bag.getNumberOf() - reservation.getReservationAmount());

        int price = bag.getPriceInPence() < reservation.getPriceInPence() ?
        			bag.getPriceInPence() :
        			reservation.getPriceInPence();

        saleList.add(new BeanbagSale(bag.getManufacturerId(), reservation.getReservationAmount(), price));
        reservationList.remove(reservation);
    }

    
    public int beanBagsInStock() {
    	// A simple iterative counter, adding together the stock of all beanbags.
        int beanbagsInStock = 0;
        for (int index = 0; index < stockList.size(); index++) {
            beanbagsInStock += ((Beanbag) stockList.get(index)).getNumberOf();
        }
        return beanbagsInStock;
    }

    
    public int reservedBeanBagsInStock() {
    	// A simple iterative counter, adding together the reserves of all beanbags.
        int beanbagsReserved = 0;
        for (int index = 0; index < stockList.size(); index++) {
            beanbagsReserved += ((Beanbag) stockList.get(index)).getReservations();
        }
        return beanbagsReserved;
    }

    
    public int beanBagsInStock(String id) throws BeanBagIDNotRecognisedException,
        IllegalIDException {
    	
    	/*
		 * Handle for precondition exceptions.
		 * 
		 * If the ID is illegal, throw an IllegalIDException.
		 */
        if (!isLegalID(id)) {
        	throw new IllegalIDException(id + " is not a valid ID");
        }

        /*
         * Attempt to retrieve the beanbag from the stock list with the given
         * ID. If none exists, then we throw a BeanBagIDNotRecognisedException.
         * If it does, we return the number of it in stock (reserved and 
         * unreserved).
         */
        Beanbag bag = checkStockListID(id);
        if (bag == null) {
        	throw new BeanBagIDNotRecognisedException(id + " does not match an existing ID");
        }

        return bag.getNumberOf();
    }

    
    public void saveStoreContents(String filename) throws IOException {
    	
    	/*
    	 * Create an array of the contents of the store's contents. Output it
    	 * serialised to a file at the given directory, then close the stream.
    	 * 
    	 * The try-catch block handles for IOExceptions thrown by the streams.
    	 */
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
        } 
        catch (IOException ex) {
            throw new IOException();
        }
    }

    
    public void loadStoreContents(String filename) throws IOException,
        ClassNotFoundException {
    	
    	/*
    	 * Create a input stream and read the contents of the file at the
    	 * given directory. Assign the store contents to the elements
    	 * of the array read from the file, then close the stream.
    	 * 
    	 * The try-catch block handles for IOExceptions and
    	 * ClassNotFoundExceptions thrown by the streams.
    	 */	
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            ObjectArrayList[] storeContents = (ObjectArrayList[]) objectIn.readObject();
            stockList = storeContents[0];
            batchList = storeContents[1];
            reservationList = storeContents[2];
            saleList = storeContents[3];
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
    	
    	/*
    	 * Iterate through the stock list. If the beanbag has something in stock,
    	 * increment beanbagsStocked. After iterating, return beanbagsStocked.
    	 */
    	int beanbagsStocked = 0;
        for (int index = 0; index < stockList.size(); index++) {
        	if (((Beanbag) stockList.get(index)).getNumberOf() > 0) {
        		beanbagsStocked++;
        	}
        }
        return beanbagsStocked;
    }

    
    public int getNumberOfSoldBeanBags() {

    	// A simple iterative counter, adding together the total stock sold.
        int beanbagsSales = 0;
        for (int index = 0; index < saleList.size(); index++) {
            beanbagsSales += ((BeanbagSale) saleList.get(index)).getSaleAmount();
        }
        return beanbagsSales;
    }

    
    public int getNumberOfSoldBeanBags(String id) throws
    BeanBagIDNotRecognisedException, IllegalIDException {
    	
    	/*
		 * Handle for precondition exceptions.
		 * 
		 * If the ID is illegal, throw an IllegalIDException.
		 */ 
    	if (!isLegalID(id)) {
    		throw new IllegalIDException(id + " is not a valid ID");
    	}

        /*
         * Attempt to retrieve the beanbag from the stock list with the given
         * ID. If none exists, then we throw a BeanBagIDNotRecognisedException.
         */
        Beanbag b = checkStockListID(id);
        if (b == null) {
        	throw new BeanBagIDNotRecognisedException(id + " does not match an existing ID");
        }

        /*
         * If the beanbag does exist, iterate through the sale list and sum
         * together the number sold featuring it.
         */
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
    	
    	// A simple iterative counter, adding together the sales of stock sold.
        int beanbagsSales = 0;
        for (int i = 0; i < saleList.size(); i++) {
            beanbagsSales += ((BeanbagSale) saleList.get(i)).getPriceInPence();
        }
        return beanbagsSales;
    }

    
    public int getTotalPriceOfSoldBeanBags(String id) throws
    BeanBagIDNotRecognisedException, IllegalIDException {
    	
    	/*
		 * Handle for precondition exceptions.
		 * 
		 * If the ID is illegal, throw an IllegalIDException.
		 */ 
        if (!isLegalID(id)) {
        	throw new IllegalIDException(id + " is not a valid ID");
        }

        /*
         * Attempt to retrieve the beanbag from the stock list with the given
         * ID. If none exists, then we throw a BeanBagIDNotRecognisedException.
         */
        Beanbag b = checkStockListID(id);
        if (b == null) {
        	throw new BeanBagIDNotRecognisedException(id + " does not match an existing ID");
        }

        /*
         * If the beanbag does exist, iterate through the sale list and sum
         * together the sales featuring it.
         */
        int beanbagsSales = 0;
        for (int i = 0; i < saleList.size(); i++) {
            BeanbagSale s = (BeanbagSale) saleList.get(i);
            if (s.getBeanbagId() == id) {
                beanbagsSales += s.getPriceInPence() * s.getSaleAmount();
            }
        }
        return beanbagsSales;
    }

    
    public int getTotalPriceOfReservedBeanBags() {

    	/*
    	 * Iterate through the reservation list. For each reservation, get
    	 * the beanbag it points to, and find the lower price; the at-reserve
    	 * price or the current price. Add that lower price to a counter,
    	 * and after iterating, return the counter.
    	 */
        int beanbagsReservedPrice = 0;
        for (int i = 0; i < reservationList.size(); i++) {
        	BeanbagReservation reservation = (BeanbagReservation) reservationList.get(i);
        	Beanbag bag = checkStockListID(reservation.getBeanbagId());
        	
        	/*
             * The ID in the reservation is assumed to be valid, as it would have
             * been valid on the beanbag's creation, and in an ID change, the
             * reservation is similarly updated- ergo, there should be no chance
             * of desync.
             */
        	assert bag != null: "A reservation is pointing to nothing";
        	int price = bag.getPriceInPence() < reservation.getPriceInPence() ?
        				bag.getPriceInPence() :
        				reservation.getPriceInPence();
        				
            beanbagsReservedPrice += price;
        }
        return beanbagsReservedPrice;
    }

    
    public String getBeanBagDetails(String id) throws
    BeanBagIDNotRecognisedException, IllegalIDException {
    	
    	/*
		 * Handle for precondition exceptions.
		 * 
		 * If the ID is illegal, throw an IllegalIDException.
		 */ 
        if (!isLegalID(id)) {
        	throw new IllegalIDException(id + " is not a valid ID");
        }

        /*
         * Attempt to retrieve the beanbag from the stock list with the given
         * ID. If none exists, then we throw a BeanBagIDNotRecognisedException.
         */
        Beanbag b = checkStockListID(id);
        if (b == null) {
        	throw new BeanBagIDNotRecognisedException(id + " does not match an existing ID");
        }

        // Return the beanbag's description.
        return b.getDescription();
    }

    
    public void empty() {
    	// Clear each store list by assigning it an empty list. 
        stockList = new ObjectArrayList();
        batchList = new ObjectArrayList();
        reservationList = new ObjectArrayList();
        saleList = new ObjectArrayList();
    }

    
    public void resetSaleAndCostTracking() {
    	// Clear just the sale list (as both sale and cost are tracked by it)
        saleList = new ObjectArrayList();
    }

    
    public void replace(String oldId, String replacementId)
    throws BeanBagIDNotRecognisedException, IllegalIDException {
    	
    	/*
		 * Handle for precondition exceptions.
		 * 
		 * If the ID is illegal, throw an IllegalIDException.
		 */ 
        if (!isLegalID(oldId)) {
        	throw new IllegalIDException(oldId + " is not a valid ID");
        }

        /*
         * Attempt to retrieve the beanbag from the stock list with the given
         * ID. If none exists, then we throw a BeanBagIDNotRecognisedException.
         */
        Beanbag b = checkStockListID(oldId);
        if (b == null) {
        	throw new BeanBagIDNotRecognisedException(oldId + " does not match an existing ID");
        }

        /*
         * Set the beanbag's ID to the replacement ID. Then, iterate through
         * each store list and swap out any old IDs for the new ID.
         */
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