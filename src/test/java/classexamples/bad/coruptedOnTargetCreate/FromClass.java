package classexamples.bad.coruptedOnTargetCreate;

import annotation.ClassTarget;
import annotation.FieldName;

@ClassTarget("classexamples.bad.coruptedOnTargetCreate.ToClass")
public class FromClass {

	@FieldName("userId")
	private String id;

	@FieldName("userName")
	public String name;

	@FieldName("userLastName")
	public String lastName;	
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	@Override
	public String toString() {
		return super.toString()+": ["+id+", "+name+", "+lastName+"]";
	}
}