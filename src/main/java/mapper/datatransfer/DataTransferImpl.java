package mapper.datatransfer;

import mapper.MapperException;
import mapper.mapclass.MapClass;
import mapper.mapclass.MapField;
import mapper.mapitems.ClassItem;
import mapper.mapitems.FieldItem;
import mapper.mapitems.MapItem;

public class DataTransferImpl<Source, Target> implements DataTransfer {

	@Override
	public Object map(Object sourceObject, Object targetObject, MapItem usingMap)
			throws MapperException {
		if (sourceObject == null) {
			throw new MapperException("Source object is null");
		}
		if (targetObject == null) {
			try {
				targetObject = usingMap.getTargetClass().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new MapperException(e.getMessage());
			}
		}

		if (!targetObject.getClass().equals(usingMap.getTargetClass())
				|| !sourceObject.getClass().equals(usingMap.getSourceClass())) {
			throw new MapperException("Wrong classes of parameters");
		}

		for (MapItem f : usingMap.getClassFields()) {
			targetObject = map(sourceObject, targetObject, f);
		}

		return targetObject;
	}
	
	
	public Object map(Object sourceObject, Object targetObject, ClassItem map)
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
	
	public Object map(Object sourceObject, Object targetObject, FieldItem map)
			throws MapperException {
		if (map.getTargetField() == null || map.getSourceField() == null) {
			throw new MapperException("Source and target fields are null");
		}
		Object value = getData(map, sourceObject);

		return setData(map, targetObject, value);
	}

}
