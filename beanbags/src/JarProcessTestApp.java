
import beanbags.BeanBagIDNotRecognisedException;
import beanbags.BeanBagMismatchException;
import beanbags.BeanBagNotInStockException;
import beanbags.IllegalIDException;
import beanbags.IllegalNumberOfBeanBagsAddedException;
import beanbags.IllegalNumberOfBeanBagsReservedException;
import beanbags.IllegalNumberOfBeanBagsSoldException;
import beanbags.InsufficientStockException;
import beanbags.InvalidMonthException;
import beanbags.InvalidPriceException;
import beanbags.PriceNotSetException;
import beanbags.Store;
/**
 * Please follow instructions in the ECM1410_CA_jar_walkthrough
 * document in conjunction with this small application to
 * check you have built your jar file correctly
 * <p>
 * You should of course expand this and instantiate a Store
 * instance when checking the performance of the package as
 * your pair develops it
 *
 * @author Jonathan Fieldsend
 * @version 1.0
 */
public class JarProcessTestApp {
	public static void main(String[] args) {
			
		// Create Store instance.
		Store goodStore = new Store();
		System.out.println("Store instance successfully made, with "
                + goodStore.beanBagsInStock()
                + " beanbags in stock.");
		
		/* =================
		 * ADD BEANBAGS TEST
		 * =================
		 *
		 * Test: Add 10 beanbags to stock.
		 * Expected result: Successfully added.
		 * Result: Successfully added.
		 */
		System.out.println("Adding 10 beanbags.");
		try {
			goodStore.addBeanBags(10, "beans", "bug", "12345678",(short) 2020, (byte) 5);
			System.out.println("Successfully added.");
		} catch (IllegalNumberOfBeanBagsAddedException | BeanBagMismatchException | IllegalIDException
				| InvalidMonthException e) {
			e.printStackTrace();
		}
		
		/*
		 * Test: Attempt to add 10 beanbags to stock with the same ID but differing text.
		 * Expected result: BeanbagMismatchException.
		 * Result: 
		   beanbags.BeanBagMismatchException
			   at beanbags.Store.addBeanBags(Store.java:71)
		       at JarProcessTestApp.main(JarProcessTestApp.java:56)
		 */
		System.out.println("Adding 10 beanbags.");
		try {
			goodStore.addBeanBags(10, "bean", "bug", "12345678",(short) 2020, (byte) 5);
			System.out.println("Successfully added.");
		} catch (IllegalNumberOfBeanBagsAddedException | BeanBagMismatchException | IllegalIDException
				| InvalidMonthException e) {
			e.printStackTrace();
		}
		
		/*
		 * Test: Attempt to add 10 beanbags to stock with an invalid month.
		 * Expected result: InvalidMonthException.
		 * Result: 
		   beanbags.InvalidMonthException
				at beanbags.Store.addBeanBags(Store.java:62)
				at JarProcessTestApp.main(JarProcessTestApp.java:77)
		 */
		System.out.println("Adding 10 beanbags.");
		try {
			goodStore.addBeanBags(10, "bags", "bug", "F561AAAA",(short) 2020, (byte) 14);
			System.out.println("Successfully added.");
		} catch (IllegalNumberOfBeanBagsAddedException | BeanBagMismatchException | IllegalIDException
				| InvalidMonthException e) {
			e.printStackTrace();
		}
		
		/*
		 * Test: Attempt to add -1 beanbags.
		 * Expected result: IllegalNumberOfBeanBagsAddedException.
		 * Result: 
		   beanbags.IllegalNumberOfBeanBagsAddedException
				at beanbags.Store.addBeanBags(Store.java:61)
				at JarProcessTestApp.main(JarProcessTestApp.java:94)
		 */
		System.out.println("Adding -1 beanbags.");
		try {
			goodStore.addBeanBags(-1, "bags", "bug", "F561AAAA",(short) 2020, (byte) 14);
			System.out.println("Successfully added.");
		} catch (IllegalNumberOfBeanBagsAddedException | BeanBagMismatchException | IllegalIDException
				| InvalidMonthException e) {
			e.printStackTrace();
		}
		
		/*
		 * Test: Attempt to add 10 beanbags with an invalid ID.
		 * Expected result: IllegalIDException.
		 * Result: 
		   beanbags.IllegalIDException
				at beanbags.Store.addBeanBags(Store.java:63)
				at JarProcessTestApp.main(JarProcessTestApp.java:114)
		 */
		System.out.println("Adding 10 beanbags.");
		try {
			goodStore.addBeanBags(10, "bags", "bug", "F56heloA",(short) 2020, (byte) 12);
			System.out.println("Successfully added.");
		} catch (IllegalNumberOfBeanBagsAddedException | BeanBagMismatchException | IllegalIDException
				| InvalidMonthException e) {
			e.printStackTrace();
		}
		
		/*
		 * Test: Attempt to add 10 beanbags with a negative ID.
		 * Expected result: IllegalIDException.
		 * Result: 
		   beanbags.IllegalIDException
				at beanbags.Store.addBeanBags(Store.java:63)
				at JarProcessTestApp.main(JarProcessTestApp.java:131)
		 */
		System.out.println("Adding 10 beanbags.");
		try {
			goodStore.addBeanBags(10, "bags", "bug", "-ABCD123",(short) 2020, (byte) 12);
			System.out.println("Successfully added.");
		} catch (IllegalNumberOfBeanBagsAddedException | BeanBagMismatchException | IllegalIDException
				| InvalidMonthException e) {
			e.printStackTrace();
		}
		
		System.out.println("Store instance currently holds " + goodStore.beanBagsInStock() + " beanbags in stock.");
		
		
		/* =================
		 * RESERVE BEANBAGS TEST
		 * =================
		 *
		 * Test: Attempt to reserve 10 beanbags before a price is set.
		 * Expected result: PriceNotSetException.
		 * Result: 
		   beanbags.PriceNotSetException
				at beanbags.Store.reserveBeanBags(Store.java:148)
				at JarProcessTestApp.main(JarProcessTestApp.java:113)
		 */
		System.out.println("Adding 10 beanbags.");
		try {
			System.out.print(goodStore.reserveBeanBags(10, "12345678"));
		} catch (BeanBagNotInStockException | InsufficientStockException | IllegalNumberOfBeanBagsReservedException
				| PriceNotSetException | BeanBagIDNotRecognisedException | IllegalIDException e) {
			e.printStackTrace();
		}
		
		try {
			goodStore.setBeanBagPrice("12345678", 100);
		} catch (InvalidPriceException | BeanBagIDNotRecognisedException | IllegalIDException e) {
			e.printStackTrace();
		}
		
		try {
			System.out.print(goodStore.reserveBeanBags(9, "12345678"));
		} catch (BeanBagNotInStockException | InsufficientStockException | IllegalNumberOfBeanBagsReservedException
				| PriceNotSetException | BeanBagIDNotRecognisedException | IllegalIDException e) {
			e.printStackTrace();
		}
		try {
			goodStore.sellBeanBags(1, "12345678");
		} catch (BeanBagNotInStockException | InsufficientStockException | IllegalNumberOfBeanBagsSoldException
				| PriceNotSetException | BeanBagIDNotRecognisedException | IllegalIDException e) {
			e.printStackTrace();
		}
	}
	
}
