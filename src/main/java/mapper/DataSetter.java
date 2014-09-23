package mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for hidden process of setting data to field
 * @author Aleksei_Ivshin
 *
 */
public class DataSetter {

	final static Logger logger = LoggerFactory.getLogger(DataGetter.class);
		
	/**
	 * Set data to field
	 * @param field Target field
	 * @param obj object where set data
	 * @param value value of data
	 * @return 
	 * @throws MapperException 
	 */
	public static boolean setData(Field field, Object targetObj,Object value) throws MapperException{
		logger.info("Set data to {}.{}",targetObj.getClass(),field.getName());
		boolean result = false;
		if(Modifier.isFinal(field.getModifiers())){
			logger.error("Field {}.{} is final",targetObj.getClass(),field.getName());
			return false;
		}
		try {
			field.set(targetObj, value);
			result = true;
		} catch (IllegalAccessException | IllegalArgumentException e) {
			logger.info("Field {}.{} is inaccessible for 'set' operations",targetObj.getClass(),field.getName());
			// if can't set to field, use setter method
			result = setDataToSetter(field,targetObj,value);
			if(!result){
				logger.error(e.getMessage(), e);
				throw new MapperException("Can't set value to field "+field.getName());
			}
		}
		logger.info("Set result {}", result? "SUCCESS": "FAIL");
		return result;
	}
	
	/**
	 * Set data to field using setter
	 * @param field
	 * @param obj
	 * @param value
	 * @return
	 */
	private static boolean setDataToSetter(Field field, Object obj,Object value) {
		logger.info("Set data (use setter) to {}.{} with value = {}",obj.getClass(),field.getName(),value);
		Class<?> c = obj.getClass();
		String setterName = "set"+field.getName();
		boolean result = false;
		Method[] methods = c.getDeclaredMethods();
		for(Method m: methods){
			if(m.getName().equalsIgnoreCase(setterName)){
				try {
					m.invoke(obj, value);
					result = true;
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					logger.error(e.getMessage(), e);
				}
				break;
			}
		}
		
		return result;
	}
}
