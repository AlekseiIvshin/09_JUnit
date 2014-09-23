package mapper.mapclass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import annotation.ClassTarget;
import annotation.FieldName;
import mapper.DataGetter;
import mapper.MapperException;
import mapper.MyMapper;

public class MapClass implements MapUnit<Class<?>> {
	final static Logger logger = LoggerFactory.getLogger(MyMapper.class);

	Class<?> fromClass;
	Class<?> targetClass;
	Set<MapUnit> classFields;
	
	public MapClass(){
		classFields = new HashSet<MapUnit>();
	}
	
	public void addToMap(MapUnit mapUnit){
		if(!classFields.contains(mapUnit)){
			classFields.add(mapUnit);
		}
	}
	
	public void clear(){
		classFields.clear();
	}


	@Override
	public void setFrom(Class<?> from) {
		this.fromClass = from;
	}

	@Override
	public Class<?> getFrom() {
		return fromClass;
	}

	@Override
	public void setTarget(Class<?> target) {
		this.targetClass = target;
	}

	@Override
	public Class<?> getTarget() {
		return targetClass;
	}
	
	private static String getTargetFieldName(Field fromField) {
		FieldName fieldName = (FieldName) fromField
				.getAnnotation(FieldName.class);
		return fieldName != null ? fieldName.value() : fromField.getName();
	}
	private static boolean checkFieldsHaveThisClass(Class<?> thisClass) {
		Field[] toClassFields = thisClass.getDeclaredFields();
		for (Field tf : toClassFields) {
			if (tf.getType().equals(thisClass)) {
				return true;
			}
		}
		return false;
	}
	
	private static Class<?> getTargetClass(Class<?> fromClass) throws ClassNotFoundException {
		Annotation[] annotations = fromClass.getAnnotations();
		for (Annotation a : annotations) {
			if (a.annotationType().equals(ClassTarget.class)) {
				ClassTarget ct = (ClassTarget) a;
				return Class.forName(ct.value());
			}
		}
		return null;
	}
	
	/**
	 * Check class has annotation for mapping
	 * 
	 * @param Checked
	 *            class
	 * @return Mapping target class
	 */
	private static boolean isMapped(Class<?> obj) {
		Annotation[] annotations = obj.getAnnotations();
		for (Annotation a : annotations) {
			if (a.annotationType().equals(ClassTarget.class)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check field has annotation for mapping
	 * 
	 * @param field
	 * @return
	 */
	private static boolean isMapped(Field field) {
		FieldName fieldName = (FieldName) field.getAnnotation(FieldName.class);
		return fieldName != null;
	}
	

	private static Field getTargetField(Class<?> targetClass,
			String targetFieldName) throws MapperException {
		Field[] toClassFields = targetClass.getDeclaredFields();
		for (Field tf : toClassFields) {
			if (tf.getName().equals(targetFieldName)) {
				return tf;
			}
		}
		throw new MapperException("Field not found: "+targetClass.getName()+"."+targetFieldName);
	}

	@Override
	public void getMap() throws MapperException {
		if(targetClass == null || fromClass == null){
			throw new MapperException("From and target field are null");
		}
		
		if (checkFieldsHaveThisClass(fromClass)) {
			throw new MapperException("Class " + fromClass.getName()
					+ " contains itself in fields");
		}

		Field[] fields = fromClass.getDeclaredFields();

		for (Field fromField : fields) {

			if (!isMapped(fromField)) {
				continue;
			}

			String targetFieldName = getTargetFieldName(fromField);

			Field toField = getTargetField(targetClass, targetFieldName);

			if (toField == null) {
				throw new MapperException("Field not found: " + targetClass.getName() + "."
						+ targetFieldName);
			}

			boolean fieldClassIsMapped = isMapped(fromField.getType());
			
			if (fieldClassIsMapped) {
				logger.info("Field is mapped {}.{} -> {}.{}",
						fromClass.getName(), fromField.getName(),
						targetClass.getName(), toField.getName());
				
				
				MapClass mapCLass = new MapClass();
				mapCLass.setFrom(fromClass);
				mapCLass.setTarget(targetClass);
				mapCLass.getMap();
				addToMap(new MapField());
			} else {
				MapField mapField = new MapField();
				mapField.setFrom(fromField);
				mapField.setTarget(toField);
				mapField.setFromClass(fromClass);
				mapField.setTargetClass(targetClass);
				mapField.getMap();
				addToMap(mapField);
			}
		}

		logger.info("Mapping done: class {}", fromClass.getName());
	}
	
}
