package store.dao;

import common.dao.GenericDAO;

import car.dao.modifiacation.Modification;

/**
 * Store DAO interface.
 * 
 * @author Aleksei_Ivshin
 *
 */
public interface StoreDAO extends GenericDAO<Store, Integer> {

	/**
	 * Find store by car modification.
	 * 
	 * @param modification
	 *            car modification
	 * @return founded store item
	 */
	Store find(Modification modification);
}
