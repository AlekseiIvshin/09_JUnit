package customer.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.dao.GenericDAOImpl;

/**
 * Customer DAO implementation.
 * 
 * @author Aleksei_Ivshin
 *
 */
public class CustomerDAOImpl extends GenericDAOImpl<Customer, Integer>
		implements CustomerDAO {

	/**
	 * Logger.
	 */
	static final Logger LOG = LoggerFactory.getLogger(CustomerDAOImpl.class);

	/**
	 * Constructor.
	 * 
	 * @param entityManager
	 */
	public CustomerDAOImpl(final EntityManager entityManager) {
		super(entityManager);
	}

	/**
	 * Find customer by passport data.
	 * 
	 * @param customer
	 *            some customer data
	 * @return founded customer
	 */
	public final Customer findByPassport(final Customer customer) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
		Root<Customer> resultCustomer = query.from(Customer.class);
		query.where(
				builder.equal(resultCustomer.get(Customer_.passportSeries),
						customer.getPassportSeries()))
				.where(builder.equal(
						resultCustomer.get(Customer_.passportNumber),
						customer.getPassportNumber())).select(resultCustomer);
		TypedQuery<Customer> ctq = entityManager.createQuery(query);
		return ctq.getSingleResult();
	}

}
