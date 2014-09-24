package mapper.mapclass;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import com.sun.org.apache.xpath.internal.operations.Mod;

import annotation.FieldName;
import mapper.DataGetter;
import mapper.MapperException;

public class MapField implements MapUnit {

	Class<?> fromClass;
	Class<?> targetClass;
	Field fromField;
	Field targetField;
	Method getter;
	Method setter;

	
	public void setFromField(Field from) {
		this.fromField = from;
	}

	
	public Field getFromField() {
		return fromField; 
	}

	
	public void setTargetField(Field target) {
		this.targetField = target;
	}

	
	public Field getTargetField() {
		return targetField;
	}

	
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


	public Class<?> getFromClass() {
		return fromClass;
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
