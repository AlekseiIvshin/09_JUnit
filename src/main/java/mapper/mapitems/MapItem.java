package mapper.mapitems;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import mapper.mapclass.MapField;

public abstract class MapItem{

	Class<?> sourceClass;
	Class<?> targetClass;
	Field sourceField;
	Field targetField;
	Method getter;
	Method setter;
	Set<MapItem> classFields;
	
	public Class<?> getSourceClass() {
		return sourceClass;
	}
	public void setSourceClass(Class<?> sourceClass) {
		this.sourceClass = sourceClass;
	}
	public Class<?> getTargetClass() {
		return targetClass;
	}
	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}
	public Field getSourceField() {
		return sourceField;
	}
	public void setSourceField(Field sourceField) {
		this.sourceField = sourceField;
	}
	public Field getTargetField() {
		return targetField;
	}
	public void setTargetField(Field targetField) {
		this.targetField = targetField;
	}
	public Method getGetter() {
		return getter;
	}
	public void setGetter(Method getter) {
		this.getter = getter;
	}
	public Method getSetter() {
		return setter;
	}
	public void setSetter(Method setter) {
		this.setter = setter;
	}
	abstract public Set<MapItem> getClassFields();
	abstract public void setClassFields(Set<MapItem> classFields);
	
	abstract public void addFields(MapItem fields);
	
}
