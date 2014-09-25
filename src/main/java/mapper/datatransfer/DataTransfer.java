package mapper.datatransfer;

import mapper.MapperException;
import mapper.mapitems.MapItem;
import mapper.mapitems.RootItem;

public interface DataTransfer {

	Object map(Object sourceObject, Object targetObject, RootItem<MapItem> usingMap)
			throws MapperException;
}
