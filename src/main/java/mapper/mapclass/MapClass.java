package mapper.mapclass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.org.apache.regexp.internal.recompile;

import annotation.ClassTarget;
import annotation.FieldName;
import mapper.DataGetter;
import mapper.MapperException;
import mapper.MyMapper;

public class MapClass implements MapUnit {
	final static Logger logger = LoggerFactory.getLogger(MyMapper.class);

	Class<?> fromClass;
	Class<?> targetClass;
	Set<MapField> classFields;
	Method getter;
	Method setter;

	public MapClass() {
		classFields = new HashSet<MapField>();
	}

	public void addToMap(MapField mapUnit) {
		if (!classFields.contains(mapUnit)) {
			classFields.add(mapUnit);
		}
	}

	public void clear() {
		classFields.clear();
	}

	@Override
	public void setFromClass(Class<?> from) {
		this.fromClass = from;
	}

	@Override
	public Class<?> getFromClass() {
		return fromClass;
	}
	@Override
	public void setTargetClass(Class<?> target) {
		this.targetClass = target;
	}

	@Override
	public Class<?> getTargetClass() {
		return targetClass;
	}

	private static String getTargetFieldName(Field fromField) {
		FieldName fieldName = (FieldName) fromField
				.getAnnotation(FieldName.class);
		return fieldName != null ? fieldName.value() : fromField.getName();
	}

	private static boolean checkFieldsHaveThisClass(Class<?> thisClass) {
		Field[] toClassFields = thisClass.getDeclaredFields();
		for (Field tf : toClassFields) {
			if (tf.getType().equals(thisClass)) {
				return true;
			}
		}
		return false;
	}

	private static Class<?> getTargetClass(Class<?> fromClass)
			throws ClassNotFoundException {
		Annotation[] annotations = fromClass.getAnnotations();
		for (Annotation a : annotations) {
			if (a.annotationType().equals(ClassTarget.class)) {
				ClassTarget ct = (ClassTarget) a;
				return Class.forName(ct.value());
			}
		}
		return null;
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
	public void getMap() throws MapperException {
		if (targetClass == null || fromClass == null) {
			throw new MapperException("From and target field are null");
		}

		if (checkFieldsHaveThisClass(fromClass)) {
			throw new MapperException("Class " + fromClass.getName()
					+ " contains itself in fields");
		}

		Field[] fields = fromClass.getDeclaredFields();

		for (Field fromField : fields) {

			if (!isMapped(fromField)) {
				continue;
			}

			String targetFieldName = getTargetFieldName(fromField);

			Field toField = getTargetField(targetClass, targetFieldName);

			if (toField == null) {
				throw new MapperException("Field not found: "
						+ targetClass.getName() + "." + targetFieldName);
			}

			boolean fieldClassIsMapped = isMapped(fromField.getType());

			Method getter = getGetterMethod(fromField.getName());
			if (getter == null) {
				if (!Modifier.isPublic(fromField.getModifiers())) {
					throw new MapperException("Field " + fromClass.getName() + "."
							+ fromField.getName() + " value is not avaible for get");
				}
			}
			Method setter = getSetterMethod(toField.getName());
			if (setter == null) {
				if (!Modifier.isPublic(toField.getModifiers())) {
					throw new MapperException("Field " + targetClass.getName()
							+ "." + toField.getName()
							+ " value is not avaible for set");
				}
			}
			
			MapField unit = null;
//			if (fieldClassIsMapped) {
//				logger.info("Field is mapped {}.{} -> {}.{}",
//						fromClass.getName(), fromField.getName(),
//						targetClass.getName(), toField.getName());
//
//				unit = new MapClass();
//				unit.setFromClass(fromField.getType());
//				unit.setTargetClass(toField.getType());
//			} else { 
				unit = new MapField();
				unit.setFromClass(fromClass);
				unit.setTargetClass(targetClass);
//			}
//			unit.setSetter(setter);
//			unit.setGetter(getter);
			unit.setFromField(fromField);
			unit.setTargetField(toField);
			unit.getMap();
			addToMap(unit);
		}

		logger.info("Mapping done: class {}", fromClass.getName());
	}



	
	private Method getGetterMethod(String fieldName) {
		Method[] methods = fromClass.getDeclaredMethods();
		String getterName = "get" + fieldName;
		for (Method m : methods) {
			if (m.getName().equalsIgnoreCase(getterName)) {
				return m;
			}
		}
		return null;
	}

	private Method getSetterMethod(String fieldName) {
		Method[] methods = targetClass.getDeclaredMethods();
		String getterName = "set" + fieldName;
		for (Method m : methods) {
			if (m.getName().equalsIgnoreCase(getterName)) {
				return m;
			}
		}
		return null;
	}

	@Override
	public Set<MapField> getFields() {
		return classFields;
	}


}
