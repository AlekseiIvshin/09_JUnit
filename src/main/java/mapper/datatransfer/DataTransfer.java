package mapper.datatransfer;

import mapper.mapitems.MapItem;
import mapper.mapitems.RootItem;
import mapper.mapping.MappingException;

public interface DataTransfer {

	Object map(Object sourceObject, Object targetObject, RootItem<MapItem> usingMap)
			throws DataTransferException;
}
