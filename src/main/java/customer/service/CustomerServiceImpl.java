package customer.service;

import common.service.DomainServiceImpl;
import customer.dao.Customer;
import customer.dao.CustomerDAOImpl;
import customer.domain.CustomerDomain;

/**
 * Customer service implementation.
 * 
 * @author Aleksei_Ivshin
 *
 */
public class CustomerServiceImpl
		extends
		DomainServiceImpl
		<CustomerDomain, Integer, Customer, Integer, CustomerDAOImpl>
		implements CustomerService {

	/**
	 * Default constructor.
	 */
	public CustomerServiceImpl() {
		super();
		dao = new CustomerDAOImpl(entityManager);
	}

	/**
	 * Find customer by passport.
	 * 
	 * @param customer
	 *            some customer data
	 * @return founded customer
	 */
	public final CustomerDomain findByPassport(final CustomerDomain customer) {
		Customer customerDao = mapper.map(customer, Customer.class);
		return mapper
				.map(dao.findByPassport(customerDao), CustomerDomain.class);
	}

}
