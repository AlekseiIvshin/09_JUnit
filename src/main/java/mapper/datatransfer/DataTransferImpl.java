package mapper.datatransfer;

import java.lang.reflect.InvocationTargetException;

import mapper.MapperException;
import mapper.mapitems.ClassItem;
import mapper.mapitems.FieldItem;
import mapper.mapitems.MapItem;

public class DataTransferImpl implements DataTransfer {

	@Override
	public Object map(Object sourceObject, Object targetObject,
			ClassItem usingMap) throws MapperException {
		if (sourceObject == null) {
			throw new MapperException("Source object is null");
		}
		if(usingMap == null){
			throw new MapperException("Map is null");
		}

		if (!usingMap.sourceEquals(sourceObject.getClass())
				|| (targetObject != null 
				&& !usingMap.targetEquals(targetObject.getClass()))) {
			throw new MapperException("Wrong classes of parameters: "
					+ sourceObject.getClass().getName() + " & "
					+ usingMap.getSourceClass().getName() + " -> "
					+ targetObject.getClass().getName() + " & "
					+ usingMap.getTargetClass().getName());
		}

		if (targetObject == null) {
			try {
				targetObject = usingMap.getTargetClass().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new MapperException(e.getMessage());
			}
		}

		for (MapItem f : usingMap.getClassFields()) {
			if (f.isMappedClass()) {
				targetObject = map(sourceObject, targetObject, (ClassItem) f);
			} else {
				targetObject = mapField(sourceObject, targetObject,
						(FieldItem) f);
			}
		}

		return targetObject;
	}

	private Object mapField(Object sourceObject, Object targetObject,
			FieldItem map) throws MapperException {
		if (map.getTargetField() == null || map.getSourceField() == null) {
			throw new MapperException("Source or target fields are null");
		}
		Object value = getData(map, sourceObject);

		return setData(map, targetObject, value);
	}

	private Object setData(FieldItem field, Object targetObj, Object value)
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

	private Object getData(FieldItem field, Object obj) throws MapperException {
		if (field == null) {
			throw new MapperException("Field is null");
		}
		if (field.getSourceField() == null) {
			throw new MapperException("Source field is null");
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
