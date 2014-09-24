package mapper.datagetter;

import java.lang.reflect.InvocationTargetException;

import mapper.MapperException;
import mapper.mapclass.MapClass;
import mapper.mapclass.MapField;

public class DoMapClass implements DoMap{

	public Object map(Object sourceObject, Object targetObject, MapClass map)
			throws MapperException {
		if (sourceObject == null){
			throw new MapperException("Source object is null");
		}
		if (targetObject == null) {
			targetObject = map.getNewInstanceOfTarget();
		}

		if (!targetObject.getClass().equals(map.getTargetClass())
				|| !sourceObject.getClass().equals(map.getSourceClass())) {
			throw new MapperException("Wrong classes of parameters");
		}

		for (MapField f : map.getFields()) {
			targetObject = map(sourceObject, targetObject,f);
		}

		return targetObject;
	}
	
	public Object map(Object sourceObject, Object targetObject, MapField map)
			throws MapperException {
		if (map.getTargetField() == null || map.getSourceField() == null) {
			throw new MapperException("Source and target fields are null");
		}
		Object value = getData(map, sourceObject);

		return setData(map, targetObject, value);
	}
	
	public static Object setData(MapField field, Object targetObj, Object value)
			throws MapperException {
		if (field.getSetter() == null) {
			try {
				field.getTargetField().set(targetObj, value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new MapperException(e.getCause());
			}
		} else {
			try {
				field.getSetter().invoke(targetObj, value);
			} catch (IllegalArgumentException | IllegalAccessException
					| InvocationTargetException e) {
				throw new MapperException(e.getCause());
			}
		}
		return targetObj;
	}
	
	public static Object getData(MapField field, Object obj)
			throws MapperException {
		if (field == null) {
			throw new MapperException("Field is null");
		}
		
		if (field.getGetter() == null) {
			try {
				return field.getSourceField().get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new MapperException(e.getMessage());
			}
		}
		try {
			return field.getGetter().invoke(obj);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new MapperException(e.getCause());
		}
	}
}
