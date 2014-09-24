package mapper.mapclass;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.sun.org.apache.xpath.internal.operations.Mod;

import annotation.FieldName;
import mapper.DataGetter;
import mapper.MapperException;

public class MapField implements MapUnit<Field> {

	Class<?> fromClass;
	Class<?> targetClass;
	Field fromField;
	Field targetField;
	Method getter;
	Method setter;

	@Override
	public void setFromField(Field from) {
		this.fromField = from;
	}

	@Override
	public Field getFromField() {
		return fromField;
	}

	@Override
	public void setTargetField(Field target) {
		this.targetField = target;
	}

	@Override
	public Field getTargetField() {
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
			if (!Modifier.isPublic(targetField.getModifiers())) {
				throw new MapperException("Field " + targetClass.getName()
						+ "." + targetField.getName()
						+ " value is not avaible for set");
			}
		}

	}

	@Override
	public Object map(Object fromObject, Object targetObject)
			throws MapperException {
		if (targetField == null || fromField == null) {
			throw new MapperException("From and target fields are null");
		}
		Object value = getValue(fromObject);

		return setValue(targetObject, value);
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

	public Object getValue(Object fromObject) throws MapperException {
		if (getter == null) {
			try {
				return fromField.get(fromObject);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new MapperException(e.getMessage());
			}
		}
		try {
			return getter.invoke(fromObject);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new MapperException(e.getCause());
		}
	}

	public Object setValue(Object targetObject, Object value)
			throws MapperException {
		if (setter == null) {
			try {
				targetField.set(targetObject, value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new MapperException(e.getCause());
			}
		} else {
			try {
				setter.invoke(targetObject, value);
			} catch (IllegalArgumentException | IllegalAccessException
					| InvocationTargetException e) {
				throw new MapperException(e.getCause());
			}
		}
		return targetObject;
	}

	@Override
	public Class<?> getFromClass() {
		return fromClass;
	}

	@Override
	public Class<?> getTargetClass() {
		return targetClass;
	}

}
