package store.domain;

import car.domain.CarDomain;

/**
 * Store domain interface.
 * 
 * @author Aleksei_Ivshin
 *
 */
public interface StoreDomain {

	/**
	 * Get id.
	 * 
	 * @return store item id
	 */
	int getId();

	/**
	 * Set id.
	 * 
	 * @param id
	 *            id
	 */
	void setId(int id);

	/**
	 * Get car from store.
	 * 
	 * @return car
	 */
	CarDomain getCar();

	/**
	 * Set car to store.
	 * 
	 * @param car
	 *            car
	 */
	void setCar(CarDomain car);

	/**
	 * Get car quantity in store.
	 * 
	 * @return car quantity
	 */
	int getQuantity();

	/**
	 * Set car quantity to store.
	 * 
	 * @param quantity
	 *            quantity
	 */
	void setQuantity(int quantity);

	/**
	 * Get car price.
	 * 
	 * @return price
	 */
	float getPrice();

	/**
	 * Set car price.
	 * 
	 * @param price
	 *            price
	 */
	void setPrice(float price);

	/**
	 * Get car enable for test drive.
	 * 
	 * @return can test drive car
	 */
	boolean getEnableTestDrive();

	/**
	 * Set car enable for test drive.
	 * 
	 * @param canTestDrive
	 *            can test drive
	 */
	void setEnableTestDrive(boolean canTestDrive);

	/**
	 * Enable for sale.
	 * 
	 * @return store is enable for sale
	 */
	boolean canSale();
}
