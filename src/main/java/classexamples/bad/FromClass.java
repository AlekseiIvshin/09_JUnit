package classexamples.bad;

import annotation.ClassTarget;
import annotation.FieldName;

public class FromClass {

	@FieldName("userId")
	private String id;

	@FieldName("userName")
	public String name;

	@FieldName("userLastName")
	public String lastName;
	
	@FieldName("ta")
	public FA fa;
	
	
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	@Override
	public String toString() {
		return super.toString()+": ["+id+", "+name+", "+lastName+","+(fa ==null? "null":fa.toString())+"]";
	}
}