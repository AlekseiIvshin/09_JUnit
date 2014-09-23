package mapper;

import java.lang.annotation.Retention;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FieldMapImpl implements FieldMap {

	private Field fromField;
	private Field targetField;
	private Method setter;
	private Method getter;
	
	@Override
	public void setFromField(Field field) {
		fromField = field;
	}

	@Override
	public void setTargetField(Field field) {
		targetField = field;
	}

	@Override
	public Field getFromField() {
		return fromField;
	}

	@Override
	public Field getTargetField() {
		return targetField; 
	}

	@Override
	public Method getSetter() {
		return setter;
	}

	@Override
	public Method getGetter() {
		return getter;
	}

	@Override
	public void setSetter(Method setter) {
		this.setter = setter;
	}

	@Override
	public void setGetter(Method getter) {
		this.getter = getter;
	}

}
