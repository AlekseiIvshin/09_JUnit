package store.dao;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import common.dao.GenericDAOImpl;
import car.dao.modifiacation.Modification;

/**
 * Store DAO implementation.
 * 
 * @author Aleksei_Ivshin
 *
 */
public class StoreDAOImpl extends GenericDAOImpl<Store, Integer> implements
		StoreDAO {

	/**
	 * Constructor.
	 * 
	 * @param entityManager entity manager
	 */
	public StoreDAOImpl(final EntityManager entityManager) {
		super(entityManager);
	}

	/**
	 * Find store by car modification.
	 * 
	 * @param modification
	 *            car modification
	 * @return founded store item
	 */
	public final Store find(final Modification modification) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Store> query = builder.createQuery(Store.class);
		Root<Store> rootStore = query.from(Store.class);
		query.where(
				builder.equal(rootStore.get(Store_.modification), modification))
				.select(rootStore);
		return entityManager.createQuery(query).getSingleResult();
	}
}
