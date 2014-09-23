package mapper;

public interface Mapper {

	/**
	 * Map class to other class
	 * @param fromClass 
	 * @return 
	 * @throws MapperException 
	 */
	public Object map(Object fromObject, Object targetObject) throws MapperException;
	
	public void prepareMap(Class<?> fromObject) throws MapperException;
}
