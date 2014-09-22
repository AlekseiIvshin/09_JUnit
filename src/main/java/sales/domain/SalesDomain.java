package sales.domain;

import java.util.Date;

import customer.domain.CustomerDomain;
import merchant.domen.MerchantDomain;
import car.domain.CarDomain;

/**
 * Sales domain interface.
 * 
 * @author Aleksei_Ivshin
 *
 */
public interface SalesDomain {

	/**
	 * Get sales id.
	 * 
	 * @return sales id
	 */
	int getId();

	/**
	 * Set sales id.
	 * 
	 * @param id
	 *            id
	 */
	void setId(int id);

	/**
	 * Get sales car.
	 * 
	 * @return car
	 */
	CarDomain getCar();

	/**
	 * Set sales car.
	 * 
	 * @param car
	 *            car
	 */
	void setCar(CarDomain car);

	/**
	 * Get sales merchant.
	 * 
	 * @return merchant
	 */
	MerchantDomain getMerchant();

	/**
	 * Set sales merchant.
	 * 
	 * @param merchant
	 *            merchant
	 */
	void setMerchant(MerchantDomain merchant);

	/**
	 * Get sales customer.
	 * 
	 * @return customer
	 */
	CustomerDomain getCustomer();

	/**
	 * Set sale customer.
	 * 
	 * @param customer
	 *            customer
	 */
	void setCustomer(CustomerDomain customer);

	/**
	 * Get sale price.
	 * 
	 * @return price
	 */
	float getPrice();

	/**
	 * Set sale price.
	 * 
	 * @param price
	 *            price
	 */
	void setPrice(float price);

	/**
	 * Get sale date.
	 * 
	 * @return sale date
	 */
	Date getSaleDate();

	/**
	 * Set sale date.
	 * 
	 * @param saleDate
	 *            sale date
	 */
	void setSaleDate(Date saleDate);
}
