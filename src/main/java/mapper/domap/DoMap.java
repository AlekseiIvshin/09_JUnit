package mapper.domap;

import mapper.MapperException;
import mapper.mapclass.MapClass;
import mapper.mapclass.MapUnit;

public interface DoMap {

	void setClassMap(MapUnit map);
	Object map(Object fromObject, Object targetObject) throws MapperException;
}
