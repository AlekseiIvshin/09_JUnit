package common.mapper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sales.dao.Sales;
import sales.domain.SalesDomainImpl;
import store.dao.Store;
import store.domain.StoreDomainImpl;
import car.dao.modifiacation.Modification;
import car.domain.CarDomainImpl;
import customer.dao.Customer;
import customer.domain.CustomerDomainImpl;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import merchant.dao.Merchant;
import merchant.domen.MerchantDomainImpl;

/**
 * Main mapper for application.
 * 
 * @author Aleksei_Ivshin
 *
 */
public class MainMapper implements Mapper {

	/**
	 * Logger.
	 */
	static final Logger LOG = LoggerFactory.getLogger(MainMapper.class);

	/**
	 * Mapper factory.
	 */
	private MapperFactory mapperFactory;

	/**
	 * Default constructor.
	 */
	public MainMapper() {
		mapperFactory = new DefaultMapperFactory.Builder().build();
		initMap();
	}

	/**
	 * Initialize mapping.
	 */
	private void initMap() {
		// Customer Domain to Customer Entity
		mapperFactory.classMap(CustomerDomainImpl.class, Customer.class)
				.constructorA("id").field("name", "name")
				.field("surname", "surname").field("patronymic", "patronymic")
				.field("passportSeries", "passportSeries")
				.field("passportNumber", "passportNumber")
				.field("birthDate", "birthDate").field("id", "id").register();

		// Merchant domain to Merchant entity
		mapperFactory.classMap(MerchantDomainImpl.class, Merchant.class)
				.constructorA("id").field("name", "name")
				.field("surname", "surname").field("patronymic", "patronymic")
				.field("id", "id").register();

		// Car domain to Car Modification entity
		mapperFactory.classMap(CarDomainImpl.class, Modification.class)
				.constructorA("id").field("id", "id")
				.field("mark", "model.mark.name").field("model", "model.name")
				.field("modification", "name").register();

		// Store domain to Store entity
		mapperFactory.classMap(StoreDomainImpl.class, Store.class)
				.field("id", "id").field("quantity", "count")
				.field("price", "price")
				.field("enableTestDrive", "testDriveAvaible")
				.field("car", "modification").register();

		// Sales domain to sales entity
		mapperFactory.classMap(SalesDomainImpl.class, Sales.class)
				.field("id", "id").field("customer", "customer")
				.field("merchant", "merchant").field("saleDate", "saleDate")
				.field("price", "price").field("car", "modification")
				.register();

	}

	/**
	 * Map object.
	 * 
	 * @param <FromClass>
	 *            object class
	 * @param <ToClass>
	 *            target class
	 * @param object
	 *            object, that will be mapped
	 * @param toClass
	 *            target class for mapping
	 * @return mapped object
	 */
	public final <FromClass, ToClass> ToClass map(final FromClass object,
			final Class<ToClass> toClass) {
		MapperFacade mapperFacade = mapperFactory.getMapperFacade();
		return mapperFacade.map(object, toClass);
	}

	/**
	 * Map objects list.
	 * 
	 * @param <FromClass>
	 *            object class
	 * @param <ToClass>
	 *            target class
	 * @param object
	 *            object, that will be mapped
	 * @param toClass
	 *            target class for mapping
	 * @return list of mapped objects
	 */
	public final <FromClass, ToClass> List<ToClass> mapAsList(
			final List<FromClass> object,
			final Class<ToClass> toClass) {
		MapperFacade mapperFacade = mapperFactory.getMapperFacade();
		return mapperFacade.mapAsList(object, toClass);
	}
}
