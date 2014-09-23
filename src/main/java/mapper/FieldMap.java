package mapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public interface FieldMap {

	void setFromField(Field field);
	void setTargetField(Field field);
	Field getFromField();
	Field getTargetField();
	Method getSetter();
	Method getGetter();
	void setSetter(Method setter);
	void setGetter(Method getter);
}
