package mapper.datagetter;

import java.lang.reflect.InvocationTargetException;
import mapper.MapperException;
import mapper.mapclass.MapField;

/**
 * Class for hidden process of setting data to field
 * 
 * @author Aleksei_Ivshin
 *
 */
public class DataSetter {

	/**
	 * Set data to field
	 * 
	 * @param field
	 *            Target field
	 * @param obj
	 *            object where set data
	 * @param value
	 *            value of data
	 * @throws MapperException
	 */
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

}
