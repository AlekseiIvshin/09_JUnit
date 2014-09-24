package mapper;

import java.lang.annotation.Annotation;
import mapper.datagetter.DoMapClass;
import mapper.mapclass.MapClass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import annotation.ClassTarget;

public class MyMapper implements Mapper {

	private MapClass classMap;

	final static Logger logger = LoggerFactory.getLogger(MyMapper.class);

	public Object map(Object fromObj, Object targetObject)
			throws MapperException {
		if (classMap == null) {
			throw new MapperException("Map of class is null");
		}
		if (!fromObj.getClass().equals(classMap.getFromClass())) {
			throw new MapperException("Classes are not equals: "
					+ fromObj.getClass() + " != " + classMap.getFromClass());
		}
		DoMapClass doMapClass = new DoMapClass();
		return doMapClass.map(fromObj, targetObject, classMap);
	}

	@Override
	public void prepareMap(Class<?> fromClass) throws MapperException {
		classMap = new MapClass();

		Class<?> toClass = getTargetClass(fromClass);
		if (toClass == null) {
			throw new MapperException(
					"Can't find target 'ClassTarget' annotation");
		}
		classMap.setFromClass(fromClass);
		classMap.setTargetClass(toClass);
		classMap.getMap();
	}

	/**
	 * Get target class from annotation
	 * 
	 * @param fromClass
	 * @return target class
	 */
	private static Class<?> getTargetClass(Class<?> fromClass) {
		Annotation[] annotations = fromClass.getAnnotations();
		for (Annotation a : annotations) {
			if (a.annotationType().equals(ClassTarget.class)) {
				ClassTarget ct = (ClassTarget) a;
				try {
					return Class.forName(ct.value());
				} catch (ClassNotFoundException e) {
					logger.error("Target class {} for {} not founded",
							ct.value(), fromClass);
					return null;
				}
			}
		}
		return null;
	}

}