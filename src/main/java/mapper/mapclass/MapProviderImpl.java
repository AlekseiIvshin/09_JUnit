package mapper.mapclass;

import java.util.Set;

import mapper.MapperException;

public class MapProviderImpl implements MapProvider {

	MapClass classMap;
	
	public MapProviderImpl() {
		classMap = new MapClass();
	}
	
	@Override
	public void createMap(Class<?> sourceObject) throws MapperException {
		classMap.setSourceClass(sourceObject);
		classMap.createMap();
	}

	@Override
	public MapClass getMap() {
		return classMap;
	}

	@Override
	public Set<MapField> getFields() {
		return classMap.getFields();
	}

}
