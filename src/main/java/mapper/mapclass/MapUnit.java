package mapper.mapclass;

import java.util.Set;

import mapper.MapperException;

public interface MapUnit {

	void setFromClass(Class<?> from);
	Class<?> getFromClass();
	void setTargetClass(Class<?> target);
	Class<?> getTargetClass();
	
	
	void getMap() throws MapperException;
	
	
	Set<MapField> getFields();
}
