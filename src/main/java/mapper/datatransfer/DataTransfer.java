package mapper.datatransfer;

import mapper.MapperException;
import mapper.mapitems.MapItem;

public interface DataTransfer {

	Object map(Object sourceObject, Object targetObject, MapItem usingMap) throws MapperException;
}
