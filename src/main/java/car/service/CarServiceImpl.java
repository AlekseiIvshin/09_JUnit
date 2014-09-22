package car.service;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.mapper.MainMapper;
import common.mapper.Mapper;
import common.service.DomainServiceImpl;
import car.dao.mark.Mark;
import car.dao.mark.MarkDAO;
import car.dao.mark.MarkDAOImpl;
import car.dao.model.Model;
import car.dao.model.ModelDAO;
import car.dao.model.ModelDAOImpl;
import car.dao.modifiacation.Modification;
import car.dao.modifiacation.ModificationDAO;
import car.dao.modifiacation.ModificationDAOImpl;
import car.domain.CarDomain;

/**
 * Car service implementation.
 * 
 * @author Aleksei_Ivshin
 *
 */
public class CarServiceImpl
		extends
		DomainServiceImpl<CarDomain, Long, Modification, Long, ModificationDAOImpl>
		implements CarService {

	/**
	 * Logger.
	 */
	static final Logger LOG = LoggerFactory.getLogger(CarServiceImpl.class);

	/**
	 * Entity manager.
	 */
	private EntityManager entityManager;

	/**
	 * Default constructor.
	 */
	public CarServiceImpl() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("07_JPA");
		entityManager = emf.createEntityManager();
	}

	/**
	 * Add new car. Create mark and model if required.
	 * 
	 * @param mark
	 *            mark name
	 * @param model
	 *            model name
	 * @param modification
	 *            car modification
	 * @return created car
	 * @throws SQLException some exception
	 */
	public final CarDomain addCar(final String mark, final String model,
			final String modification) throws SQLException {
		MarkDAO markDAO = new MarkDAOImpl(entityManager);
		ModelDAO modelDAO = new ModelDAOImpl(entityManager);
		ModificationDAO modifDAO = new ModificationDAOImpl(entityManager);
		entityManager.getTransaction().begin();
		// find or create mark
		Mark markData = markDAO.findOrCreate(mark);
		Model modelData = modelDAO.findOrCreate(markData, model);
		Modification modif = modifDAO.findOne(modelData, modification);
		if (modif != null) {
			entityManager.getTransaction().rollback();
			throw new SQLException("This car (" + mark + " " + model + " "
					+ modification + ") is exist!");
		}

		modif = modifDAO.create(modif);

		entityManager.getTransaction().commit();
		return mapper.map(modif, CarDomain.class);
	}

	/**
	 * Remove car.
	 * 
	 * @param mark
	 *            mark name
	 * @param model
	 *            model name
	 * @param modification
	 *            car modification
	 */
	public final void removeCar(final String mark, final String model,
			final String modification) {
		// search mark by name
		MarkDAO markDAO = new MarkDAOImpl(entityManager);
		Mark markEntity = markDAO.findOne(mark);
		if (markEntity == null) {
			throw new NoResultException("No found mark with name " + mark);
		}

		ModelDAO modelDAO = new ModelDAOImpl(entityManager);
		Model modelEntity = modelDAO.findOne(markEntity, model);
		if (modelEntity == null) {
			throw new NoResultException("No found mark with name " + mark
					+ " and model name " + model);
		}

		ModificationDAO modificationDAO = new ModificationDAOImpl(entityManager);
		Modification modifEntity = modificationDAO.findOne(modelEntity,
				modification);

		if (modifEntity == null) {
			throw new NoResultException("No found mark with name " + mark
					+ ", model name " + model + " and modification "
					+ modification);
		}

		modificationDAO.deleteById(modifEntity.getId());
	}

	/**
	 * Find car by name.
	 * 
	 * @param mark
	 *            mark name
	 * @param model
	 *            model name
	 * @param modification
	 *            modification
	 * @return founded car
	 */
	public final CarDomain findOne(final String mark, final String model,
			final String modification) {
		MarkDAO markDAO = new MarkDAOImpl(entityManager);
		Mark markEntity = markDAO.findOne(mark);
		if (markEntity == null) {
			throw new NoResultException("Mark with name " + mark
					+ " not founded");
		}

		ModelDAO modelDAO = new ModelDAOImpl(entityManager);
		Model modelEntity = modelDAO.findOne(markEntity, model);
		if (modelEntity == null) {
			throw new NoResultException("Model with name " + model
					+ " and mark " + mark + " not founded");
		}

		ModificationDAO modificationDAO = new ModificationDAOImpl(entityManager);
		Modification modifEntity = modificationDAO.findOne(modelEntity,
				modification);

		if (modifEntity == null) {
			throw new NoResultException("Modification " + modification
					+ " for " + mark + " " + model + " not founded");
		}

		Mapper mapper = new MainMapper();
		return mapper.map(modifEntity, CarDomain.class);
	}

}
