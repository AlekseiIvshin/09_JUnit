package common;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import javax.persistence.NoResultException;

import merchant.domen.MerchantDomain;
import merchant.service.MerchantService;
import merchant.service.MerchantServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sales.domain.SalesDomain;
import sales.service.SalesService;
import sales.service.SalesServiceImpl;
import car.domain.CarDomain;
import car.service.CarService;
import car.service.CarServiceImpl;
import customer.domain.CustomerDomain;
import customer.domain.CustomerDomainImpl;
import customer.service.CustomerService;
import customer.service.CustomerServiceImpl;

/**
 * Main.
 * 
 * @author AlekseiIvshin
 *
 */
public class App {

	/**
	 * logger.
	 */
	static final Logger LOG = LoggerFactory.getLogger(App.class);

	/**
	 * Car service.
	 */
	private static final CarService CAR_SERVICE = new CarServiceImpl();
	/**
	 * Customer service.
	 */
	private static final CustomerService CUSTOMER_SERVICE = new CustomerServiceImpl();
	/**
	 * Merchant service.
	 */
	private static final MerchantService MERCHANT_SERVICE = new MerchantServiceImpl();
	/**
	 * Sales service.
	 */
	private static final SalesService SALES_SERVICE = new SalesServiceImpl();

	/**
	 * Main.
	 * 
	 * @param args
	 *            in arguments
	 */
	public static void main(final String[] args) {
		// createAnySale();
		try {
			CarDomain car = createNewCar("ZZaazzz", "ZaaiZ6", "620 lss");
			LOG.info("Created: {}", car.toString());
		} catch (SQLException e) {
			LOG.error("Error", e);
		}
	}

	/**
	 * Get any car.
	 * 
	 * @return car
	 */
	private static CarDomain getAnyCar() {
		List<CarDomain> cars = CAR_SERVICE.get(0, 100);
		if (cars.isEmpty()) {
			throw new NoResultException("Cars is not exist");
		}
		Random rnd = new Random();
		return cars.get(rnd.nextInt(cars.size()));
	}

	/**
	 * Get any customer.
	 * 
	 * @return customer
	 */
	private static CustomerDomain getAnyCustomer() {
		List<CustomerDomain> customers = CUSTOMER_SERVICE.get(0, 100);
		if (customers.isEmpty()) {
			throw new NoResultException("Customers is not exist");
		}
		Random rnd = new Random();
		return customers.get(rnd.nextInt(customers.size()));
	}

	/**
	 * Get any merchant.
	 * 
	 * @return merchant
	 */
	private static MerchantDomain getAnyMerchant() {
		List<MerchantDomain> merchants = MERCHANT_SERVICE.get(0, 100);
		if (merchants.isEmpty()) {
			throw new NoResultException("Merchants is not exist");
		}
		Random rnd = new Random();
		return merchants.get(rnd.nextInt(merchants.size()));
	}

	/**
	 * Sale any car.
	 * 
	 * @return new sale data
	 * @throws Exception
	 *             error in process of creation data
	 */
	private static SalesDomain createAnySale() throws Exception {
		CustomerDomain customer = null;
		try {
			customer = getAnyCustomer();
		} catch (NoResultException e) {
			LOG.error(e.toString());
			customer = new CustomerDomainImpl();
			customer.setName("Name");
			customer.setSurname("SurName");
			customer.setPatronymic("Patronymic");
			customer.setPassportNumber("000000");
			customer.setPassportSeries("0000");
			LOG.info("Create new customer in data base: " + customer.toString());
			customer = CUSTOMER_SERVICE.create(customer);
		}
		LOG.info("Get customer: {}", customer.toString());
		MerchantDomain merchant = getAnyMerchant();
		LOG.info("Get merchant: {}", merchant.toString());
		CarDomain car = getAnyCar();
		LOG.info("Get car: {}", car.toString());
		SalesDomain sale = null;
		try {
			sale = SALES_SERVICE.newSaleAndUpdateStore(customer, merchant, car);
		} catch (Exception e) {
			LOG.error("New sale error", e);
		}
		LOG.info(sale.toString());

		return sale;
	}

	/**
	 * Create new car.
	 * 
	 * @param newMark
	 *            mark name
	 * @param newModel
	 *            model name
	 * @param newModification
	 *            modification
	 * @return created car
	 * @throws SQLException
	 *             some exception
	 */
	private static CarDomain createNewCar(final String newMark,
			final String newModel, final String newModification)
			throws SQLException {
		return CAR_SERVICE.addCar(newMark, newModel, newModification);
	}
}
