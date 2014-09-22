package store.service;

import store.domain.StoreDomain;
import car.domain.CarDomain;

import common.service.DomainService;

/**
 * Store service interface.
 * 
 * @author Aleksei_Ivshin
 *
 */
public interface StoreService extends DomainService<StoreDomain, Integer> {

	/**
	 * Get store by car.
	 * 
	 * @param car
	 *            car
	 * @return founded store
	 */
	StoreDomain get(CarDomain car);
}
