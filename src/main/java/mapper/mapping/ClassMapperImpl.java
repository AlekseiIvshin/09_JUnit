package mapper.mapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import mapper.mapitems.ClassItem;
import mapper.mapitems.FieldItem;
import mapper.mapitems.MapItem;
import annotation.ClassTarget;
import annotation.FieldName;

public class ClassMapperImpl implements ClassMapper {
	ClassItem map;

	public ClassMapperImpl() {
		map = new ClassItem();
	}

	@Override
	public ClassItem getMap() {
		return map;
	};

	@Override
	public void createMap(Class<?> sourceClass) throws MappingException {
		if(sourceClass == null){
			throw new MappingException("Source class are null");
		}
		Class<?> targetClass = getTargetClass(sourceClass);

		if (classContainItself(sourceClass)) {
			throw new MappingException("Class " + sourceClass.getName()
					+ " contains itself in fields");
		}

		map.setSourceClass(sourceClass);
		map.setTargetClass(targetClass);
		map.setClassFields(getFieldsItems(sourceClass.getDeclaredFields(), targetClass));
	}
	
	public boolean classContainItself(Class<?> anyClass){
		Field[] toClassFields = anyClass.getDeclaredFields();
		for (Field tf : toClassFields) {
			if (tf.getType().equals(anyClass)) {
				return true;
			}
		}
		return false;
	}
	
	private Set<MapItem> getFieldsItems(Field[] fields, Class<?> targetClass) throws MappingException{
		Set<MapItem> fieldItems = new HashSet<MapItem>();
		for (Field sourceField : fields) {

			if (!isMapped(sourceField)) {
				continue;
			}

			Field targetField = getTargetField(targetClass, sourceField);

			boolean fieldClassIsMapped = isMapped(sourceField.getType());

			Method getter = getGetterMethod(sourceField.getName());
			if (getter == null) {
				if (!Modifier.isPublic(sourceField.getModifiers())) {
					throw new MappingException("Field SOURCE_CLASS." + sourceField.getName()
							+ " value is not avaible for get");
				}
			}

			Method setter = getSetterMethod(targetField.getName());
			if (setter == null) {
				if (!Modifier.isPublic(targetField.getModifiers())) {
					throw new MappingException("Field TARGET_CLASS." + targetField.getName()
							+ " value is not avaible for set");
				}
			}

			MapItem item = null;

			if (!fieldClassIsMapped) {
				if (!sourceField.getType().equals(targetField.getType())) {
					throw new MappingException(
							"Types of fields mapped not equals: SOURCE_CLASS."
									+ sourceField.getName() + " -> TARGET_CLASS."
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
			if(!fieldItems.contains(item)){
				fieldItems.add(item);
			}
		}
		return fieldItems;
	}

	
	
	public static Class<?> getTargetClass(Class<?> sourceClass)
			throws MappingException {
		Annotation[] annotations = sourceClass.getAnnotations();
		for (Annotation a : annotations) {
			if (a.annotationType().equals(ClassTarget.class)) {
				ClassTarget ct = (ClassTarget) a;
				try {
					return Class.forName(ct.value());
				} catch (ClassNotFoundException e) {
					throw new MappingException(e.getMessage());
				}
			}
		}
		throw new MappingException("Source class not contain annotation @ClassTarget");
	}

	private static boolean isMapped(Field field) {
		FieldName fieldName = (FieldName) field.getAnnotation(FieldName.class);
		return fieldName != null;
	}

	public Field getTargetField(Class<?> targetClass, Field sourceField)
			throws MappingException {

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
		throw new MappingException("Field was in annotation for SOURCE_CLASS." + sourceField.getName()
				+ " but not found in target class");
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
