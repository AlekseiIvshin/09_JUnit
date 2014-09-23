package mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for hidden process of setting data to field
 * 
 * @author Aleksei_Ivshin
 *
 */
public class DataSetter {

	final static Logger logger = LoggerFactory.getLogger(DataGetter.class);

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
	public static void setData(Field field, Object targetObj, Object value)
			throws MapperException {
		logger.info("Set data to {}.{}", targetObj.getClass(), field.getName());
		if (Modifier.isFinal(field.getModifiers())) {
			logger.error("Field {}.{} is final", targetObj.getClass(),
					field.getName());
			throw new MapperException("Field " + field.getName() + " is final");
		}
		try {
			field.set(targetObj, value);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			logger.info("Field {}.{} is inaccessible for 'set' operations",
					targetObj.getClass(), field.getName());
			setDataToSetter(field, targetObj, value);
		}
		logger.info("Set result: SUCCESS");
	}

	/**
	 * Set data to field using setter
	 * 
	 * @param field
	 * @param obj
	 * @param value
	 * @return
	 * @throws MapperException
	 */
	private static void setDataToSetter(Field field, Object obj, Object value)
			throws MapperException {
		logger.info("Set data (use setter) to {}.{} with value = {}",
				obj.getClass(), field.getName(), value);
		Class<?> c = obj.getClass();
		String setterName = "set" + field.getName();
		Method[] methods = c.getDeclaredMethods();
		for (Method m : methods) {
			if (m.getName().equalsIgnoreCase(setterName)) {
				try {
					m.invoke(obj, value);
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					throw new MapperException(e.getCause());
				}
				return;
			}
		}
		throw new MapperException("Not found setter method for " + c.getName()
				+ "." + field.getName());
	}
}
