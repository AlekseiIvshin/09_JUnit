package mapper;

import mapper.datatransfer.DataTransfer;
import mapper.datatransfer.DataTransferException;
import mapper.mapitems.MapItem;
import mapper.mapitems.RootItem;
import mapper.mapping.ClassMapper;
import mapper.mapping.MappingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyMapper implements Mapper {

	private ClassMapper currentProvider;
	private DataTransfer transferProvider;

	final static Logger logger = LoggerFactory.getLogger(MyMapper.class);

	public Object map(Object fromObj, Object targetObject)
			throws MappingException, DataTransferException {
		if (currentProvider.getMap().isEmpty()) {
			throw new MappingException("Map of class is missing");
		}
		return transferProvider.map(fromObj, targetObject, new RootItem<MapItem>(currentProvider.getMap()));
	}

	public void prepareMap(Class<?> fromClass) throws MappingException {
		if (currentProvider == null) {
			throw new MappingException("Provider is null");
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