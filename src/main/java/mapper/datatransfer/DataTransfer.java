package mapper.datatransfer;

import mapper.MapperException;
import mapper.mapitems.ClassItem;

public interface DataTransfer {

	Object map(Object sourceObject, Object targetObject, ClassItem usingMap)
			throws MapperException;
}
