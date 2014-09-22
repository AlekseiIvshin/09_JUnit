package sales.service;

import merchant.dao.Merchant;
import merchant.domen.MerchantDomain;

import car.dao.modifiacation.Modification;
import car.domain.CarDomain;
import common.service.DomainServiceImpl;
import customer.dao.Customer;
import customer.domain.CustomerDomain;
import sales.dao.Sales;
import sales.dao.SalesDAOImpl;
import sales.domain.SalesDomain;

/**
 * Sales service implementation.
 * 
 * @author Aleksei_Ivshin
 *
 */
public class SalesServiceImpl extends
		DomainServiceImpl<SalesDomain, Integer, Sales, Integer, SalesDAOImpl>
		implements SalesService {

	/**
	 * Default constructor.
	 */
	public SalesServiceImpl() {
		super();
		dao = new SalesDAOImpl(entityManager);
	}

	/**
	 * Create new sale and update store (decrement car count).
	 * 
	 * @param newSales
	 *            new sale data
	 * @return sale data
	 * @throws Exception
	 *             some error
	 */
	public final SalesDomain newSaleAndUpdateStore(final SalesDomain newSales)
			throws Exception {
		return newSaleAndUpdateStore(newSales.getCustomer(),
				newSales.getMerchant(), newSales.getCar());
	}

	/**
	 * Create new sale and update store (decrement car count).
	 * 
	 * @param customer
	 *            who buy car
	 * @param merchant
	 *            who sale car
	 * @param car
	 *            car
	 * @return sale date
	 * @throws Exception
	 *             some error
	 */
	public final SalesDomain newSaleAndUpdateStore(
			final CustomerDomain customer, final MerchantDomain merchant,
			final CarDomain car) throws Exception {
		Customer cust = mapper.map(customer, Customer.class);
		Merchant merch = mapper.map(merchant, Merchant.class);
		Modification modif = mapper.map(car, Modification.class);

		Sales changedSales = dao.newSaleAndUpdateStore(cust, merch, modif);
		return mapper.map(changedSales, SalesDomain.class);
	}

}
