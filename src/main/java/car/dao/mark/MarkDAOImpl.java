package car.dao.mark;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.dao.GenericDAOImpl;

/**
 * Mark DAO implementation.
 * 
 * @author Aleksei_Ivshin
 *
 */
public class MarkDAOImpl extends GenericDAOImpl<Mark, Integer> implements
		MarkDAO {

	/**
	 * Logger.
	 */
	static final Logger LOG = LoggerFactory.getLogger(MarkDAOImpl.class);

	/**
	 * Constructor.
	 * 
	 * @param entityManager
	 *            entity manager
	 */
	public MarkDAOImpl(final EntityManager entityManager) {
		super(entityManager);
	}

	/**
	 * Find marks with name like parameter.
	 * 
	 * @param name
	 *            part or full car mark name
	 * @return founded marks
	 */
	public final List<Mark> findAny(final String name) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Mark> query = builder.createQuery(Mark.class);
		Root<Mark> root = query.from(Mark.class);
		query.where(builder.like(root.get(Mark_.name), name)).select(root);
		TypedQuery<Mark> ctq = entityManager.createQuery(query);
		return ctq.getResultList();
	}

	/**
	 * Find marks with name equal parameter.
	 * 
	 * @param name
	 *            full mark name
	 * @return founded mark or null (if not found)
	 */
	public final Mark findOne(final String name) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Mark> query = builder.createQuery(Mark.class);
		Root<Mark> root = query.from(Mark.class);
		query.where(builder.equal(root.get(Mark_.name), name));
		TypedQuery<Mark> ctq = entityManager.createQuery(query);
		try {
			return ctq.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Find car mark. If not found create new mark with this name
	 * 
	 * @param name
	 *            mark name
	 * @return founded or created mark
	 */
	public final Mark findOrCreate(final String name) {
		Mark markData = findOne(name);
		if (markData == null) {
			markData = new Mark(name);
			markData = create(markData);
		}
		return markData;
	}

}
