package mapper.mapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import mapper.MapperException;
import mapper.mapitems.ClassItem;
import mapper.mapitems.FieldItem;
import mapper.mapitems.MapItem;
import annotation.ClassTarget;
import annotation.FieldName;

public class ClassMapperImpl implements ClassMapper {
	MapItem map;

	ClassMapperImpl() {
		map = new ClassItem();
	}

	@Override
	public MapItem getMap() {
		return map;
	};

	@Override
	public void createMap(Class<?> sourceClass) throws MapperException {
		Class<?> targetClass = getTargetClass(sourceClass);
		if (targetClass == null || sourceClass == null) {
			throw new MapperException("From and target field are null");
		}

		if (checkFieldsHaveThisClass(sourceClass)) {
			throw new MapperException("Class " + sourceClass.getName()
					+ " contains itself in fields");
		}

		map.setSourceClass(sourceClass);
		map.setTargetClass(targetClass);

		Field[] fields = sourceClass.getDeclaredFields();

		for (Field sourceField : fields) {

			if (!isMapped(sourceField)) {
				continue;
			}

			Field targetField = getTargetField(targetClass, sourceField);

			if (targetField == null) {
				throw new MapperException("Field was in annotation for "
						+ sourceClass.getName() + "." + sourceField.getName()
						+ " but not found in " + targetClass.getName());
			}

			boolean fieldClassIsMapped = isMapped(sourceField.getType());

			Method getter = getGetterMethod(sourceField.getName());
			if (getter == null) {
				if (!Modifier.isPublic(sourceField.getModifiers())) {
					throw new MapperException("Field " + sourceClass.getName()
							+ "." + sourceField.getName()
							+ " value is not avaible for get");
				}
			}

			Method setter = getSetterMethod(targetField.getName());
			if (setter == null) {
				if (!Modifier.isPublic(targetField.getModifiers())) {
					throw new MapperException("Field " + targetClass.getName()
							+ "." + targetField.getName()
							+ " value is not avaible for set");
				}
			}

			MapItem item = null;

			if (!fieldClassIsMapped) {
				if (!sourceField.getType().equals(targetField.getType())) {
					throw new MapperException(
							"Types of fields mapped not equals: "
									+ sourceClass.getName() + "."
									+ sourceField.getName() + " -> "
									+ targetClass.getName() + "."
									+ targetField.getName());
				}
				item = new FieldItem();
				item.setSourceClass(sourceField.getType());
				item.setTargetClass(sourceField.getType());

			} else {
				ClassMapperImpl tmpMapper = new ClassMapperImpl();
				tmpMapper.createMap(sourceField.getType());
				item = tmpMapper.getMap();
			}

			item.setSourceField(sourceField);
			item.setTargetField(targetField);
			item.setGetter(getter);
			item.setSetter(setter);
			map.addFields(item);
		}

	}

	private static Class<?> getTargetClass(Class<?> sourceClass)
			throws MapperException {
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

	private static boolean checkFieldsHaveThisClass(Class<?> thisClass) {
		Field[] toClassFields = thisClass.getDeclaredFields();
		for (Field tf : toClassFields) {
			if (tf.getType().equals(thisClass)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isMapped(Field field) {
		FieldName fieldName = (FieldName) field.getAnnotation(FieldName.class);
		return fieldName != null;
	}

	private static Field getTargetField(Class<?> targetClass, Field sourceField)
			throws MapperException {

		FieldName fieldName = (FieldName) sourceField
				.getAnnotation(FieldName.class);
		String targetFieldName = fieldName != null ? fieldName.value()
				: sourceField.getName();

		Field[] toClassFields = targetClass.getDeclaredFields();
		for (Field tf : toClassFields) {
			if (tf.getName().equals(targetFieldName)) {
				return tf;
			}
		}
		throw new MapperException("Field not found: " + targetClass.getName()
				+ "." + targetFieldName);
	}

	private Method getGetterMethod(String fieldName) {
		Method[] methods = map.getSourceClass().getDeclaredMethods();
		String getterName = "get" + fieldName;
		for (Method m : methods) {
			if (m.getName().equalsIgnoreCase(getterName)) {
				return m;
			}
		}
		return null;
	}

	private Method getSetterMethod(String fieldName) {
		Method[] methods = map.getTargetClass().getDeclaredMethods();
		String getterName = "set" + fieldName;
		for (Method m : methods) {
			if (m.getName().equalsIgnoreCase(getterName)) {
				return m;
			}
		}
		return null;
	}

	private static boolean isMapped(Class<?> obj) {
		Annotation[] annotations = obj.getAnnotations();
		for (Annotation a : annotations) {
			if (a.annotationType().equals(ClassTarget.class)) {
				return true;
			}
		}
		return false;
	}

}
