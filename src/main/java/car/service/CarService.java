package car.service;

import java.sql.SQLException;

import common.service.DomainService;
import car.domain.CarDomain;

/**
 * Car service interface.
 * 
 * @author AlekseiIvshin
 *
 */
public interface CarService extends DomainService<CarDomain, Long> {

	/**
	 * Add new car. Create mark and model if required.
	 * @param mark mark name
	 * @param model model name
	 * @param modification car modification
	 * @return created car
	 * @throws SQLException 
	 */
	CarDomain addCar(String mark, String model, String modification) throws SQLException;

	/**
	 * Remove car.
	 * @param mark mark name
	 * @param model model name
	 * @param modification car modification
	 */
	void removeCar(String mark, String model, String modification);

	/**
	 * Find car by name.
	 * @param mark mark name
	 * @param model model name
	 * @param modification modification
	 * @return founded car
	 */
	CarDomain findOne(String mark, String model, String modification);

}
