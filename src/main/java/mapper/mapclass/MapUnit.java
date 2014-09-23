package mapper.mapclass;

import mapper.MapperException;

public interface MapUnit<T> {

	void setFrom(T from);
	T getFrom();
	void setTarget(T target);
	T getTarget();
	
	
	void getMap() throws MapperException;
}
