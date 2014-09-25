package mapper;

import mapper.datatransfer.DataTransfer;
import mapper.mapping.ClassMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyMapper implements Mapper {

	private ClassMapper currentProvider;
	private DataTransfer transferProvider;

	final static Logger logger = LoggerFactory.getLogger(MyMapper.class);

	public Object map(Object fromObj, Object targetObject)
			throws MapperException {
		if (currentProvider.getMap().isEmpty()) {
			throw new MapperException("Map of class is missing");
		}
		return transferProvider.map(fromObj, targetObject,
				currentProvider.getMap());
	}

	public void prepareMap(Class<?> fromClass) throws MapperException {
		if (currentProvider == null) {
			throw new MapperException("Provider is null");
		}
		currentProvider.createMap(fromClass);
	}

	public <T extends ClassMapper> void setMapProvider(T provider) {
		currentProvider = provider;
	}

	public ClassMapper getMapProvider() {
		return currentProvider;
	}

	public <T extends DataTransfer> void setTransferProvider(T provider) {
		transferProvider = provider;
	}

	public DataTransfer getTransferProvider() {
		return transferProvider;
	}

}