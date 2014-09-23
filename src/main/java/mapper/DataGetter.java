package mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for hidden process of getting data from field
 * @author Aleksei_Ivshin
 *
 */
public class DataGetter   {

	final static Logger logger = LoggerFactory.getLogger(DataGetter.class);
	
	/**
	 * Get data from field
	 * @param field target field 
	 * @param obj object that contain field
	 * @return data
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws MapperException 
	 */
	public static Object getData(Field field,Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, MapperException{
		logger.info("Get data {}.{}",obj.getClass(),field.getName());
		try {
			return getDataFromField(field, obj);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			logger.info("Field {}.{} is inaccessible for 'get' operations",obj.getClass(),field.getName());
		}
		
		return getDataUseGetter(field, obj);
	}
	
	private static Object getDataFromField(Field field,Object obj) throws IllegalArgumentException, IllegalAccessException{
		logger.info("Get from field",obj.getClass(),field.getName());
		return  field.get(obj);
	}
	
	private static Object getDataUseGetter(Field field,Object obj) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, MapperException{
		logger.info("Get data (use getter) from field {}",field.getName());
		Class<?> c = obj.getClass();
		String getterName = "get"+field.getName();
		Method[] methods = c.getDeclaredMethods();
				
		for(Method m: methods){
			if(m.getName().equalsIgnoreCase(getterName)){
				return m.invoke(obj);
			}
		}
		
		throw new MapperException("Not found getter method for field "+field.getName());
	}

}
