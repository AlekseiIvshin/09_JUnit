package store.service;

import car.dao.modifiacation.Modification;
import car.domain.CarDomain;
import common.mapper.MainMapper;
import common.mapper.Mapper;
import common.service.DomainServiceImpl;
import store.dao.Store;
import store.dao.StoreDAOImpl;
import store.domain.StoreDomain;

/**
 * Store service implementation.
 * 
 * @author Aleksei_Ivshin
 *
 */
public class StoreServiceImpl extends
		DomainServiceImpl<StoreDomain, Integer, Store, Integer, StoreDAOImpl>
		implements StoreService {

	/**
	 * Constructor.
	 */
	public StoreServiceImpl() {
		super();
		dao = new StoreDAOImpl(entityManager);
	}

	/**
	 * Get store by car.
	 * 
	 * @param car
	 *            car
	 * @return founded store
	 */
	public final StoreDomain get(final CarDomain car) {
		Mapper mapper = new MainMapper();
		Modification modif = mapper.map(car, Modification.class);
		Store store = dao.find(modif);
		return mapper.map(store, StoreDomain.class);
	}

}
