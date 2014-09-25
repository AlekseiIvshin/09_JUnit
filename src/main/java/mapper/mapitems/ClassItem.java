package mapper.mapitems;

import java.util.HashSet;
import java.util.Set;

import mapper.mapping.MappingException;

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
	public void addFields(MapItem fields) throws MappingException {
		if(classFields.contains(fields)){
			throw new MappingException("Field already mapped");
		}
		classFields.add(fields);
	}

	@Override
	public boolean isMappedClass() {
		return true;
	}

}
