package mapper.mapclass;

import java.util.Set;

import mapper.MapperException;

public interface MapUnit {

	void setSourceClass(Class<?> from);
	Class<?> getSourceClass();
	//void setTargetClass(Class<?> target);
	Class<?> getTargetClass();
	
	
	void createMap() throws MapperException;
	
	
	Set<MapField> getFields();
}
