package common.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generic DAO implementation.
 * 
 * @author Aleksei_Ivshin
 *
 * @param <Model>
 *            entity type
 * @param <IdType>
 *            entity id
 */
public class GenericDAOImpl<Model, IdType> 
		implements GenericDAO<Model, IdType> {

	/**
	 * Entity manager.
	 */
	protected EntityManager entityManager;

	/**
	 * Type of entity.
	 */
	protected Class<Model> entityType;

	/**
	 * Logger.
	 */
	static final Logger LOG = LoggerFactory.getLogger(GenericDAOImpl.class);

	/**
	 * Add entity manager to DAO class.
	 * 
	 * @param entityManager
	 *            entity manager
	 */
	@SuppressWarnings("unchecked")
	public GenericDAOImpl(final EntityManager entityManager) {
		this.entityType = (Class<Model>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		this.entityManager = entityManager;
	}

	/**
	 * Create new entity in data store.
	 * 
	 * @param entity
	 *            new entity
	 * @return persisted entity
	 */
	public final Model create(final Model entity) {
		entityManager.persist(entity);
		entityManager.flush();
		return entity;
	}

	/**
	 * Update entity in data store.
	 * 
	 * @param entity
	 *            changed entity
	 * @return merged entity
	 */
	public final Model update(final Model entity) {
		return entityManager.merge(entity);
	}

	/**
	 * Find entity in data store by id.
	 * 
	 * @param id
	 *            entity id
	 * @return founded entity
	 */
	public final Model find(final IdType id) {
		return entityManager.find(entityType, id);
	}

	/**
	 * Find all entities (not recommend).
	 * 
	 * @return list of entities
	 */
	@SuppressWarnings("unchecked")
	public final List<Model> findAll() {
		return entityManager.createQuery(
				"SELECT entity FROM " + entityType.getName() + " entity")
				.getResultList();
	}

	/**
	 * Delete entity from data store.
	 * 
	 * @param id
	 *            id of entity
	 */
	public final void deleteById(final IdType id) {
		Model m = find(id);
		entityManager.remove(m);
	}

	/**
	 * Find entities with paging.
	 * 
	 * @param offset
	 *            position, where start get entities
	 * @param limit
	 *            maximum count of entities in result
	 * @return list of entities
	 */
	@SuppressWarnings("unchecked")
	public final List<Model> find(final int offset, final int limit) {
		return entityManager
				.createQuery(
						"SELECT entity FROM " + entityType.getName()
								+ " entity").setFirstResult(offset)
				.setMaxResults(limit).getResultList();
	}

	/**
	 * Delete entity from data store.
	 * 
	 * @param entity
	 *            entity
	 */
	public final void delete(final Model entity) {
		entityManager.remove(entity);
	}

}
