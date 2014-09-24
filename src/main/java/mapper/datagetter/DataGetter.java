package mapper.datagetter;

import java.lang.reflect.InvocationTargetException;
import mapper.MapperException;
import mapper.mapclass.MapField;

/**
 * Class for hidden process of getting data from field
 * 
 * @author Aleksei_Ivshin
 *
 */
public class DataGetter {

	/**
	 * Get data from field
	 * 
	 * @param field
	 *            target field
	 * @param obj
	 *            object that contain field
	 * @return data
	 * @throws MapperException
	 */
	public static Object getData(MapField field, Object obj)
			throws MapperException {
		if (field == null) {
			throw new MapperException("Field is null");
		}
		
		if (field.getGetter() == null) {
			try {
				return field.getFromField().get(obj);
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
