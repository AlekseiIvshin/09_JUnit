package mapper.mapclass;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import mapper.MapperException;

public interface MapUnit {

	void setFromClass(Class<?> from);
	Class<?> getFromClass();
	void setTargetClass(Class<?> target);
	Class<?> getTargetClass();
	
	void setFromField(Field from);
	Field getFromField();
	void setTargetField(Field target);
	Field getTargetField();
	
	
	void getMap() throws MapperException;
	
	Object map(Object fromObject, Object targetObject) throws MapperException;
	public Object getValue(Object fromObject) throws MapperException;
	public Object setValue(Object targetObject, Object value) throws MapperException;
}
