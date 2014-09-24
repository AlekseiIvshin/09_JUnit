package mapper.mapclass;

import java.util.Set;

import mapper.MapperException;

public interface MapProvider {

	void createMap(Class<?> fromObject) throws MapperException;
	MapClass getMap(); 
	Set<MapField> getFields();
}
