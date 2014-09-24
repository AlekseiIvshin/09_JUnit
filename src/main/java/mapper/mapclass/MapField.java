package mapper.mapclass;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

import mapper.MapperException;

public class MapField implements MapUnit {

	Class<?> sourceClass;
	Class<?> targetClass;
	Field sourceField;
	Field targetField;
	Method getter;
	Method setter;

	public void setSourceField(Field source) {
		this.sourceField = source;
	}

	public Field getSourceField() {
		return sourceField;
	}

	public void setTargetField(Field target) {
		this.targetField = target;
	}

	public Field getTargetField() {
		return targetField;
	}

	public void createMap() throws MapperException {
		if (targetField == null || sourceField == null) {
			throw new MapperException("Source and target field are null");
		}
		if (!targetField.getType().equals(sourceField.getType())) {
			throw new MapperException("Types of fields not equals:"
					+ sourceField.getName() + "[" + sourceField.getType() + "] "
					+ "and " + targetField.getName() + "["
					+ targetField.getType() + "]");
		}

		if (Modifier.isFinal(targetField.getModifiers())) {
			throw new MapperException("Field " + sourceClass.getName() + "."
					+ sourceField.getName() + " is final");
		}

		getter = getGetterMethod(sourceField.getName());
		if (getter == null) {
			if (!Modifier.isPublic(sourceField.getModifiers())) {
				throw new MapperException("Field " + sourceClass.getName() + "."
						+ sourceField.getName() + " value is not avaible for get");
			}
		}
		setter = getSetterMethod(targetField.getName());
		if (setter == null) {
			if (!Modifier.isPublic(targetField.getModifiers())) {
				throw new MapperException("Field " + targetClass.getName()
						+ "." + targetField.getName()
						+ " value is not avaible for set");
			}
		}

	}

	public void setSourceClass(Class<?> sourceClass) {
		this.sourceClass = sourceClass;
	}

	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
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

	public Class<?> getSourceClass() {
		return sourceClass;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public void setSetter(Method setter) {
		this.setter = setter;
	}

	public Method getSetter() {
		return setter;
	}

	public void setGetter(Method getter) {
		this.getter = getter;
	}

	public Method getGetter() {
		return getter;
	}

	@Override
	public Set<MapField> getFields() {
		return null;
	}

}
