package mapper;

public interface Mapper {

	/**
	 * Map class to other class
	 * @param fromClass 
	 * @return 
	 * @throws MapperException 
	 */
	public Object map(Object fromClass) throws MapperException;
}
