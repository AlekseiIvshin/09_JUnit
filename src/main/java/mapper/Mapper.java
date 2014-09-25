package mapper;

import mapper.datatransfer.DataTransfer;
import mapper.datatransfer.DataTransferException;
import mapper.mapping.ClassMapper;
import mapper.mapping.MappingException;

public interface Mapper {

	/**
	 * Map class to other class
	 * @param fromClass 
	 * @return 
	 * @throws MappingException 
	 * @throws DataTransferException 
	 */
	public Object map(Object fromObject, Object targetObject) throws MappingException, DataTransferException;
	
	public void prepareMap(Class<?> fromObject) throws MappingException;
	
	public <T extends ClassMapper> void setMapProvider(T provider);
	public  ClassMapper getMapProvider();
	
	public <T extends DataTransfer> void setTransferProvider(T provider);
	public DataTransfer getTransferProvider();
}
