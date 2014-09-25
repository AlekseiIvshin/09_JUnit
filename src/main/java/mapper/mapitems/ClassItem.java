package mapper.mapitems;

import java.util.HashSet;
import java.util.Set;

public class ClassItem extends MapItem{

	public ClassItem(){
		classFields = new HashSet<MapItem>();
	}
	
	@Override
	public Set<MapItem> getClassFields() {
		return this.classFields;
	}

	@Override
	public void setClassFields(Set<MapItem> classFields) {
		this.classFields = classFields;
	}

	@Override
	public void addFields(MapItem fields) {
		if(!classFields.contains(fields)){
			classFields.add(fields);
		}
	}

	@Override
	public boolean isMappedClass() {
		return true;
	}


}
