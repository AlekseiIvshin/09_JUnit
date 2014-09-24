package mapper.datagetter;

import mapper.MapperException;
import mapper.mapclass.MapClass;
import mapper.mapclass.MapField;

public interface DoMap {
	public Object map(Object fromObject, Object targetObject, MapClass map) throws MapperException;
	public Object map(Object fromObject, Object targetObject, MapField map) throws MapperException;
}
