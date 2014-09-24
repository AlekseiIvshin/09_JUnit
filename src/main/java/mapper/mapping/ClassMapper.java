package mapper.mapping;

import mapper.MapperException;
import mapper.mapitems.MapItem;

public interface ClassMapper {

	void createMap(Class<?> sourceClass) throws MapperException;
	MapItem getMap();
}
