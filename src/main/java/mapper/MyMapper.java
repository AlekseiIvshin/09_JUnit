package mapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import mapper.datagetter.DoMapClass;
import mapper.mapclass.MapClass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import annotation.ClassTarget;
import annotation.FieldName;

public class MyMapper implements Mapper {

	private MapClass classMap;

	final static Logger logger = LoggerFactory.getLogger(MyMapper.class);

	public Object map(Object fromObj, Object targetObject) throws MapperException {
		if(!fromObj.getClass().equals(classMap.getFromClass())){
			throw new MapperException("Classes are not equals: " +fromObj.getClass()+" != "+classMap.getFromClass());
		}
		DoMapClass doMapClass = new DoMapClass();
		return doMapClass.map(fromObj, targetObject, classMap);
	}

	@Override
	public void prepareMap(Class<?> fromClass) throws MapperException {
		classMap = new MapClass();

		Class<?> toClass = getTargetClass(fromClass);
		if (toClass == null) {
			throw new MapperException(
					"Can't find target 'ClassTarget' annotation");
		}
		classMap.setFromClass(fromClass);
		classMap.setTargetClass(toClass);
		classMap.getMap();
	}

	/**
	 * Get target filed name from annotation
	 * 
	 * @param fromField
	 *            field that have annotation FieldName
	 * @return target field name
	 */
	private static String getTargetFieldName(Field fromField) {
		FieldName fieldName = (FieldName) fromField
				.getAnnotation(FieldName.class);
		return fieldName != null ? fieldName.value() : fromField.getName();
	}

	/**
	 * Check class has annotation for mapping
	 * 
	 * @param Checked
	 *            class
	 * @return Mapping target class
	 */
	private static boolean isMapped(Class<?> obj) {
		Annotation[] annotations = obj.getAnnotations();
		for (Annotation a : annotations) {
			if (a.annotationType().equals(ClassTarget.class)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check field has annotation for mapping
	 * 
	 * @param field
	 * @return
	 */
	private static boolean isMapped(Field field) {
		FieldName fieldName = (FieldName) field.getAnnotation(FieldName.class);
		return fieldName != null;
	}

	/**
	 * Get target class from annotation
	 * 
	 * @param fromClass
	 * @return target class
	 */
	private static Class<?> getTargetClass(Class<?> fromClass) {
		Annotation[] annotations = fromClass.getAnnotations();
		for (Annotation a : annotations) {
			if (a.annotationType().equals(ClassTarget.class)) {
				ClassTarget ct = (ClassTarget) a;
				try {
					return Class.forName(ct.value());
				} catch (ClassNotFoundException e) {
					logger.error("Target class {} for {} not founded",
							ct.value(), fromClass);
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * Check on class has itself in the field (anti recursive)
	 * 
	 * @param thisClass
	 * @return
	 */
	private static boolean checkFieldsHaveThisClass(Class<?> thisClass) {
		Field[] toClassFields = thisClass.getDeclaredFields();
		for (Field tf : toClassFields) {
			if (tf.getType().equals(thisClass)) {
				return true;
			}
		}
		return false;
	}

//	private void setData(Field toField, Object targetObject, Object value,
//			boolean fromFieldClassIsMapped) throws MapperException {
//
//		if (fromFieldClassIsMapped) {
//			Object resultValue = map(value);
//
//			if (resultValue != null) {
//				DataSetter.setData(toField, targetObject, resultValue);
//			}
//		} else {
//			DataSetter.setData(toField, targetObject, value);
//		}
//	}

	private static Field getTargetField(Class<?> targetClass,
			String targetFieldName) throws MapperException {
		Field[] toClassFields = targetClass.getDeclaredFields();
		for (Field tf : toClassFields) {
			if (tf.getName().equals(targetFieldName)) {
				return tf;
			}
		}
		throw new MapperException("Field not found: " + targetClass.getName()
				+ "." + targetFieldName);
	}

}