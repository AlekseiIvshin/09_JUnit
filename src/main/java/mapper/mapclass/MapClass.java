package mapper.mapclass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import annotation.ClassTarget;
import annotation.FieldName;
import mapper.MapperException;
import mapper.MyMapper;

public class MapClass implements MapUnit {
	final static Logger logger = LoggerFactory.getLogger(MyMapper.class);

	Class<?> sourceClass;
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
	public void setSourceClass(Class<?> source) {
		this.sourceClass = source;
	}

	@Override
	public Class<?> getSourceClass() {
		return sourceClass;
	}

	private void setTargetClass(Class<?> target) {
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

	private static Class<?> getTargetClass(Class<?> sourceClass) throws MapperException
			 {
		Annotation[] annotations = sourceClass.getAnnotations();
		for (Annotation a : annotations) {
			if (a.annotationType().equals(ClassTarget.class)) {
				ClassTarget ct = (ClassTarget) a;
				try {
					return Class.forName(ct.value());
				} catch (ClassNotFoundException e) {
					throw new MapperException(e.getMessage());
				}
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
	public void createMap() throws MapperException {
		
		targetClass = getTargetClass(sourceClass);
		if (targetClass == null || sourceClass == null) {
			throw new MapperException("From and target field are null");
		}

		if (checkFieldsHaveThisClass(sourceClass)) {
			throw new MapperException("Class " + sourceClass.getName()
					+ " contains itself in fields");
		}

		Field[] fields = sourceClass.getDeclaredFields();

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

			// boolean fieldClassIsMapped = isMapped(fromField.getType());

			Method getter = getGetterMethod(fromField.getName());
			if (getter == null) {
				if (!Modifier.isPublic(fromField.getModifiers())) {
					throw new MapperException("Field " + sourceClass.getName()
							+ "." + fromField.getName()
							+ " value is not avaible for get");
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
			// if (fieldClassIsMapped) {
			// logger.info("Field is mapped {}.{} -> {}.{}",
			// sourceClass.getName(), fromField.getName(),
			// targetClass.getName(), toField.getName());
			//
			// unit = new MapClass();
			// unit.setSourceClass(fromField.getType());
			// unit.setTargetClass(toField.getType());
			// } else {
			unit = new MapField();
			unit.setSourceClass(sourceClass);
			unit.setTargetClass(targetClass);
			// }
			// unit.setSetter(setter);
			// unit.setGetter(getter);
			unit.setSourceField(fromField);
			unit.setTargetField(toField);
			unit.createMap();
			addToMap(unit);
		}

		logger.info("Mapping done: class {}", sourceClass.getName());
	}

	private Method getGetterMethod(String fieldName) {
		Method[] methods = sourceClass.getDeclaredMethods();
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
	
	public static boolean isEmpty(MapClass map){
		return map == null || map.getSourceClass() == null;
	}

	public Object getNewInstanceOfTarget() throws MapperException{
		try {
			return targetClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new MapperException(e.getMessage());
		}
	}
}
