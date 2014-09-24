package mapper;

import mapper.datagetter.DoMap;
import mapper.mapclass.MapClass;
import mapper.mapclass.MapProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyMapper implements Mapper {

	private MapProvider currentProvider;
	private DoMap transferProvider;

	final static Logger logger = LoggerFactory.getLogger(MyMapper.class);

	public Object map(Object fromObj, Object targetObject)
			throws MapperException {
		if (MapClass.isEmpty(currentProvider.getMap())) {
			throw new MapperException("Map of class is missing");
		}
		return transferProvider.map(fromObj, targetObject,
				currentProvider.getMap());
	}

	@Override
	public void prepareMap(Class<?> fromClass) throws MapperException {
		if (currentProvider == null) {
			throw new MapperException("Provider is null");
		}
		currentProvider.createMap(fromClass);
	}

	@Override
	public <T extends MapProvider> void setMapProvider(T provider) {
		currentProvider = provider;
	}

	@Override
	public MapProvider getMapProvider() {
		return currentProvider;
	}

	@Override
	public <T extends DoMap> void setTransferProvider(T provider) {
		transferProvider = provider;
	}

	@Override
	public DoMap getTransferProvider() {
		return transferProvider;
	}

}