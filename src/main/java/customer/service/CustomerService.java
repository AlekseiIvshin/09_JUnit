package customer.service;

import common.service.DomainService;
import customer.domain.CustomerDomain;

/**
 * Customer service interface.
 * 
 * @author Aleksei_Ivshin
 *
 */
public interface CustomerService 
		extends DomainService<CustomerDomain, Integer> {

	/**
	 * Find customer by passport.
	 * 
	 * @param customer
	 *            some customer data
	 * @return founded customer
	 */
	CustomerDomain findByPassport(CustomerDomain customer);
}
