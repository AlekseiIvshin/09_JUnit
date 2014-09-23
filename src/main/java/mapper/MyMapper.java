package mapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import mapper.mapclass.MapClass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import annotation.ClassTarget;
import annotation.FieldName;

public class MyMapper implements Mapper {

	private MapClass classMap;

	final static Logger logger = LoggerFactory.getLogger(MyMapper.class);

	public Object map(Object fromObj) throws MapperException {
		Class<?> fromClass = fromObj.getClass();

		logger.info("Starting mapping: class {}", fromClass);

		if (checkFieldsHaveThisClass(fromClass)) {
			throw new MapperException("Class " + fromClass.getName()
					+ " contains itself in fields");
		}

		Class<?> toClass = getTargetClass(fromClass);
		if (toClass == null) {
			throw new MapperException(
					"Can't find target 'ClassTarget' annotation");
		}
		// Get result class instance
		Object result;
		try {
			result = toClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			// logger.error("Error on create new instance of {}: {}",
			// toClass.getName(), e1.toString());
			throw new MapperException(e1.getCause());
		}

		Field[] fields = fromClass.getDeclaredFields();

		for (Field fromField : fields) {

			if (!isMapped(fromField)) {
				continue;
			}

			String targetFieldName = getTargetFieldName(fromField);

			Field toField = getTargetField(toClass, targetFieldName);

			if (toField == null) {
				throw new MapperException("Field not found: " + toClass + "."
						+ targetFieldName);
			}

			boolean fieldClassIsMapped = isMapped(fromField.getType());

			if (fieldClassIsMapped) {
				logger.info("Field is mapped {}.{} -> {}.{}",
						fromClass.getName(), fromField.getName(),
						toClass.getName(), toField.getName());
			} else {
				if (!toField.getType().equals(fromField.getType())) {
					throw new MapperException("Types of fields not equals:"
							+ fromField.getName() + "[" + fromField.getType()
							+ "] " + "and " + toField.getName() + "["
							+ toField.getType() + "]");
				}
			}

			Object valueForTargetField = DataGetter.getData(fromField, fromObj);

			if (valueForTargetField == null) {
				logger.warn("Value of {} equals NULL", fromField.getName());
				continue;
			}

			setData(toField, result, valueForTargetField, fieldClassIsMapped);

		}

		logger.info("Mapping done: class {}", fromClass.getName());
		return result;
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

	private void setData(Field toField, Object targetObject, Object value,
			boolean fromFieldClassIsMapped) throws MapperException {

		if (fromFieldClassIsMapped) {
			Object resultValue = map(value);

			if (resultValue != null) {
				DataSetter.setData(toField, targetObject, resultValue);
			}
		} else {
			DataSetter.setData(toField, targetObject, value);
		}
	}

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

	@Override
	public void prepareMap(Class<?> fromClass) throws MapperException {
		classMap = new MapClass();

		Class<?> toClass = getTargetClass(fromClass);
		if (toClass == null) {
			throw new MapperException(
					"Can't find target 'ClassTarget' annotation");
		}
		classMap.setFrom(fromClass);
		classMap.setTarget(toClass);
		classMap.getMap();
	}

}