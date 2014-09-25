package mapper.mapping;

import mapper.mapitems.ClassItem;

public interface ClassMapper {

	void createMap(Class<?> sourceClass) throws MappingException;
	ClassItem getMap();
}
