package mapper.datatransfer;

import java.lang.reflect.InvocationTargetException;

import mapper.MapperException;
import mapper.mapitems.ClassItem;
import mapper.mapitems.FieldItem;
import mapper.mapitems.MapItem;
import mapper.mapitems.RootItem;

public class DataTransferImpl implements DataTransfer {

	@Override
	public Object map(Object sourceObject, Object targetObject,
			RootItem<MapItem> usingMap) throws MapperException {
		if (usingMap == null || usingMap.get() == null) {
			throw new MapperException("Map is null");
		}
		MapItem map = usingMap.get();
		if (sourceObject == null) {
			throw new MapperException("Source object is null");
		}

		if (!map.sourceEquals(sourceObject.getClass())
				|| (targetObject != null && !map.targetEquals(targetObject
						.getClass()))) {
			String whatWrong = "";
			if(targetObject == null){
				whatWrong = " null (target object is null)";
			} else {
				whatWrong+=targetObject.getClass().getName() + " & "
						+ map.getTargetClass().getName();
			}
			throw new MapperException("Wrong classes of parameters: "
					+ sourceObject.getClass().getName() + " & "
					+ map.getSourceClass().getName() + " -> "
					+ whatWrong);
		}

		if (targetObject == null) {
			try {
				targetObject = map.getTargetClass().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new MapperException(e.getMessage());
			}
		}

		for (MapItem f : map.getClassFields()) {
			targetObject = map(sourceObject, targetObject, f);
		}

		return targetObject;
	}

	private Object map(Object sourceObject, Object targetObject, MapItem map)
			throws MapperException {
		if (map.getTargetField() == null || map.getSourceField() == null) {
			throw new MapperException("Source or target fields are null");
		}
		Object value = getData(map, sourceObject);
		if (map.isMappedClass()) {
			value = mapValue((ClassItem) map, value);
		} else {
			value = mapValue((FieldItem) map, value);
		}

		return setData(map, targetObject, value);
	}

	private Object setData(MapItem field, Object targetObj, Object value)
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

	private Object getData(MapItem field, Object obj) throws MapperException {
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

	private Object mapValue(ClassItem field, Object obj) throws MapperException {
		try {
			return map(obj, field.getTargetClass().newInstance(),
					new RootItem<MapItem>(field));
		} catch (InstantiationException | IllegalAccessException e) {
			throw new MapperException(e.getMessage());
		}
	}

	private Object mapValue(FieldItem field, Object obj) throws MapperException {
		return obj;
	}

}
