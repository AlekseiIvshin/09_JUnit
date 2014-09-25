package mapper;

import mapper.datatransfer.DataTransfer;
import mapper.mapping.ClassMapper;

public interface Mapper {

	/**
	 * Map class to other class
	 * @param fromClass 
	 * @return 
	 * @throws MapperException 
	 */
	public Object map(Object fromObject, Object targetObject) throws MapperException;
	
	public void prepareMap(Class<?> fromObject) throws MapperException;
	
	public <T extends ClassMapper> void setMapProvider(T provider);
	public  ClassMapper getMapProvider();
	
	public <T extends DataTransfer> void setTransferProvider(T provider);
	public DataTransfer getTransferProvider();
}
