package mapper.mapclass;

import mapper.MapperException;

public interface MapUnit<T> {

	void setFrom(T from);
	T getFrom();
	void setTarget(T target);
	T getTarget();
	
	
	void getMap() throws MapperException;
	
	Object map(Object fromObject, Object targetObject) throws MapperException;
	public Object getValue(Object fromObject) throws MapperException;
	public Object setValue(Object targetObject, Object value) throws MapperException;
}
