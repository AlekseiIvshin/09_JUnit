package mapper.datatransfer;

import java.lang.reflect.InvocationTargetException;

import mapper.mapitems.ClassItem;
import mapper.mapitems.FieldItem;
import mapper.mapitems.MapItem;
import mapper.mapitems.RootItem;
import mapper.datatransfer.DataTransferException;

public class DataTransferImpl implements DataTransfer {

	@Override
	public Object map(Object sourceObject, Object targetObject,
			RootItem<MapItem> usingMap) throws DataTransferException {
		if (usingMap == null || usingMap.get() == null) {
			throw new DataTransferException("Map is null");
		}
		MapItem map = usingMap.get();
		if (sourceObject == null) {
			throw new DataTransferException("Source object is null");
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
			throw new DataTransferException("Wrong classes of parameters: "
					+ sourceObject.getClass().getName() + " & "
					+ map.getSourceClass().getName() + " -> "
					+ whatWrong);
		}

		if (targetObject == null) {
			try {
				targetObject = map.getTargetClass().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new DataTransferException(e.getMessage());
			}
		}

		for (MapItem f : map.getClassFields()) {
			targetObject = mapItem(sourceObject, targetObject, f);
		}

		return targetObject;
	}

	public Object mapItem(Object sourceObject, Object targetObject, MapItem map)
			throws DataTransferException {
		if (map.getTargetField() == null || map.getSourceField() == null) {
			throw new DataTransferException("Source or target fields are null");
		}
		Object value = getData(map, sourceObject);
		if (map.isMappedClass()) {
			value = mapValue((ClassItem) map, value);
		} else {
			value = mapValue((FieldItem) map, value);
		}

		return setData(map, targetObject, value);
	}

	public Object setData(MapItem field, Object targetObj, Object value)
			throws DataTransferException {
		if(targetObj == null){
			throw new DataTransferException("Target object is null");
		}
		if(field == null ){
			throw new DataTransferException("Map is null");
		}
		if (field.getTargetField() == null) {
			throw new DataTransferException("Target field is null");
		}
		if (field.getSetter() == null) {
			try {
				field.getTargetField().set(targetObj, value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new DataTransferException(e.getMessage());
			}
		} else {
			try {
				field.getSetter().invoke(targetObj, value);
			} catch (IllegalArgumentException | IllegalAccessException
					| InvocationTargetException e) {
				throw new DataTransferException(e.getMessage());
			}
		}
		return targetObj;
	}

	public Object getData(MapItem field, Object obj) throws DataTransferException {
		if(obj == null){
			throw new DataTransferException("Source object is null");
		}
		if (field == null ) {
			throw new DataTransferException("Map is null is null");
		}
		if (field.getSourceField() == null) {
			throw new DataTransferException("Source field is null");
		}

		if (field.getGetter() == null) {
			try {
				return field.getSourceField().get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new DataTransferException(e.getMessage());
			}
		}
		try {
			return field.getGetter().invoke(obj);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new DataTransferException(e.getMessage());
		}
	}

	private Object mapValue(ClassItem field, Object obj) throws DataTransferException {
		try {
			return map(obj, field.getTargetClass().newInstance(),
					new RootItem<MapItem>(field));
		} catch (InstantiationException | IllegalAccessException e) {
			throw new DataTransferException(e.getMessage());
		}
	}

	private Object mapValue(FieldItem field, Object obj) throws DataTransferException {
		return obj;
	}

}
