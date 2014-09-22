package sales.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import store.dao.Store;
import store.dao.StoreDAOImpl;
import common.dao.GenericDAOImpl;
import merchant.dao.Merchant;
import customer.dao.Customer;
import car.dao.modifiacation.Modification;

/**
 * Sales DAO implementation.
 * 
 * @author Aleksei_Ivshin
 *
 */
public class SalesDAOImpl extends GenericDAOImpl<Sales, Integer> implements
		SalesDAO {

	/**
	 * Constructor.
	 * 
	 * @param entityManager
	 */
	public SalesDAOImpl(final EntityManager entityManager) {
		super(entityManager);
	}

	/**
	 * Find sales by modification.
	 * 
	 * @param modification
	 *            modification
	 * @return list of sales
	 */
	@SuppressWarnings("unchecked")
	public final List<Sales> find(final Modification modification) {
		return entityManager
				.createQuery(
						"SELECT s FROM Sales s "
								+ "WHERE s.modification=:modif")
				.setParameter("modif", modification).getResultList();
	}

	/**
	 * Find sales by customer.
	 * 
	 * @param customer
	 *            customer
	 * @return list of sales
	 */
	@SuppressWarnings("unchecked")
	public final List<Sales> find(final Customer customer) {
		return entityManager
				.createQuery(
						"SELECT s FROM Sales s " + "WHERE s.customer=:customer")
				.setParameter("customer", customer).getResultList();
	}

	/**
	 * Find sales by merchant.
	 * 
	 * @param merchant
	 *            merchant
	 * @return list of sales
	 */
	@SuppressWarnings("unchecked")
	public final List<Sales> find(final Merchant merchant) {
		return entityManager
				.createQuery(
						"SELECT s FROM Sales s " + "WHERE s.merchant=:merchant")
				.setParameter("merchant", merchant).getResultList();
	}

	/**
	 * Create new sale and update store: decrement count of cars in store item.
	 * 
	 * @param customer
	 *            customer, who buy car
	 * @param merchant
	 *            merchant, who sale car
	 * @param modification
	 *            car that is sale
	 * @return created sales
	 */
	public final Sales newSaleAndUpdateStore(final Customer customer,
			final Merchant merchant, final Modification modification) {
		Store store = new StoreDAOImpl(entityManager).find(modification);
		if (store == null || store.getCount() == 0) {
			throw new NoResultException("Car " + modification.toString()
					+ " is not exist in store.");
		}
		entityManager.getTransaction().begin();
		try {
			Sales sales = new Sales();
			sales.setPrice(store.getPrice());
			sales.setCustomer(customer);
			sales.setMerchant(merchant);
			sales.setModification(modification);
			entityManager.persist(sales);
			store.setCount(store.getCount() - 1);
			entityManager.merge(store);
			return sales;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new IllegalArgumentException("Store can't update: "
					+ e.getMessage());
		}
	}
}
