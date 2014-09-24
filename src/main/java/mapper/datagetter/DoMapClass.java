package mapper.datagetter;

import mapper.MapperException;
import mapper.mapclass.MapClass;
import mapper.mapclass.MapField;

public class DoMapClass {

	public Object map(Object fromObject, Object targetObject, MapClass map)
			throws MapperException {
		if (targetObject == null) {
			try {
				targetObject = map.getTargetClass().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new MapperException(e.getCause());
			}
		}

		if (!targetObject.getClass().equals(map.getTargetClass())
				|| !fromObject.getClass().equals(map.getFromClass())) {
			throw new MapperException("Wrong parameter classes: FROM ["
					+ map.getFromClass().getName() + " & "
					+ fromObject.getClass().getName() + "]" + " TARGET ["
					+ map.getTargetClass().getName() + " & "
					+ targetObject.getClass().getName() + "]");
		}

		for (MapField f : map.getFields()) {
			targetObject = map(fromObject, targetObject,f);
		}

		return targetObject;
	}
	
	public Object map(Object fromObject, Object targetObject, MapField map)
			throws MapperException {
		if (map.getTargetField() == null || map.getFromField() == null) {
			throw new MapperException("From and target fields are null");
		}
		Object value = DataGetter.getData(map, fromObject);

		return DataSetter.setData(map, targetObject, value);
	}
}
