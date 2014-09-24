package mapper.datagetter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import mapper.MapperException;
import mapper.mapclass.MapClass;
import mapper.mapclass.MapField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for hidden process of getting data from field
 * 
 * @author Aleksei_Ivshin
 *
 */
public class DataGetter {

	final static Logger logger = LoggerFactory.getLogger(DataGetter.class);

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
