package mapper.mapitems;

import java.util.Set;

public class FieldItem extends MapItem{

	
	@Override
	public Set<MapItem> getClassFields() {
		return null;
	}

	@Override
	public void setClassFields(Set<MapItem> classFields) {	}

	@Override
	public void addFields(MapItem fields) {	}

	@Override
	public boolean isMappedClass() {
		return false;
	}
}
