package car.domain;

/**
 * Car interface.
 * @author AlekseiIvshin
 *
 */
public interface CarDomain {

	
	/**
	 * Get id car.
	 * @return id 
	 */
	long getId();
	
	/** 
	 * Get car mark.
 	 * @return mark
	 */
	String getMark();
	
	/**
	 * Set car mark.
	 * @param mark mark
	 */
	void setMark(String mark);
	
	/**
	 * Get car model.
	 * @return model
	 */
	String getModel();
	
	/**
	 * Set car model.
	 * @param model model
	 */
	void setModel(String model);
	
	/**
	 * Get car modification.
	 * @return modification
	 */
	String getModification();
	
	/**
	 * Set modification.
	 * @param modification modification
	 */
	void setModification(String modification);
	
}
