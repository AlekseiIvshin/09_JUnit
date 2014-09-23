package mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

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
	public static Object getData(Field field, Object obj)
			throws MapperException {
		logger.info("Get data {}.{}", obj.getClass(), field.getName());
		if (Modifier.isPublic(field.getModifiers())) {
			return getDataFromField(field, obj);
		} else {
			logger.info("Field {}.{} is inaccessible for 'get' operations",
					obj.getClass(), field.getName());
			return getDataUseGetter(field, obj);
		}
	}

	private static Object getDataFromField(Field field, Object obj)
			throws MapperException {
		logger.info("Get from field", obj.getClass(), field.getName());
		try {
			return field.get(obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new MapperException(e.getCause());
		}
	}

	private static Object getDataUseGetter(Field field, Object obj)
			throws MapperException {
		logger.info("Get data (use getter) from field {}", field.getName());
		Class<?> c = obj.getClass();
		String getterName = "get" + field.getName();
		Method[] methods = c.getDeclaredMethods();

		for (Method m : methods) {
			if (m.getName().equalsIgnoreCase(getterName)) {
				try {
					return m.invoke(obj);
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					throw new MapperException(e.getCause());
				}
			}
		}

		throw new MapperException("Not found getter method for field "
				+ field.getName());
	}

}
