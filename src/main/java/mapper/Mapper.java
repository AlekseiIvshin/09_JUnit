package mapper;

import mapper.datagetter.DoMap;
import mapper.mapclass.MapProvider;

public interface Mapper {

	/**
	 * Map class to other class
	 * @param fromClass 
	 * @return 
	 * @throws MapperException 
	 */
	public Object map(Object fromObject, Object targetObject) throws MapperException;
	
	public void prepareMap(Class<?> fromObject) throws MapperException;
	
	public <T extends MapProvider> void setMapProvider(T provider);
	public  MapProvider getMapProvider();
	
	public <T extends DoMap> void setTransferProvider(T provider);
	public DoMap getTransferProvider();
}
