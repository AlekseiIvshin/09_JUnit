package mapper.mapclass;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.sun.org.apache.xpath.internal.operations.Mod;

import annotation.FieldName;
import mapper.MapperException;

public class MapField implements MapUnit<Field> {

	Class<?> fromClass;
	Class<?> targetClass;
	Field fromField;
	Field targetField;
	Method getter;
	Method setter;
	boolean useGetter;
	boolean useSetter;

	@Override
	public void setFrom(Field from) {
		this.fromField = from;
	}

	@Override
	public Field getFrom() {
		return fromField;
	}

	@Override
	public void setTarget(Field target) {
		this.targetField = target;
	}

	@Override
	public Field getTarget() {
		return targetField;
	}

	@Override
	public void getMap() throws MapperException {
		if (targetField == null || fromField == null) {
			throw new MapperException("From and target field are null");
		}
		if (!targetField.getType().equals(fromField.getType())) {
			throw new MapperException("Types of fields not equals:"
					+ fromField.getName() + "[" + fromField.getType() + "] "
					+ "and " + targetField.getName() + "["
					+ targetField.getType() + "]");
		}

		if (Modifier.isFinal(targetField.getModifiers())) {
			throw new MapperException("Field " + fromClass.getName() + "."
					+ fromField.getName() + " is final");
		}

		getter = getGetterMethod(fromField.getName());
		if (getter == null) {
			if (!Modifier.isPublic(fromField.getModifiers())) {
				throw new MapperException("Field " + fromClass.getName() + "."
						+ fromField.getName() + " value is not avaible for get");
			}
		}
		setter = getSetterMethod(targetField.getName());
		if (setter == null) {
			if (!Modifier.isPublic(fromField.getModifiers())) {
				throw new MapperException("Field " + fromClass.getName() + "."
						+ fromField.getName() + " value is not avaible for set");
			}
		}

	}

	public void setFromClass(Class<?> fromClass) {
		this.fromClass = fromClass;
	}

	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
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

}
