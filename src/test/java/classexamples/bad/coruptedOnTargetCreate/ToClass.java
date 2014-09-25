package classexamples.bad.coruptedOnTargetCreate;

public abstract class ToClass {

	public String userId;
	
	public String userName;
	public String userLastName;
	
	public String getUserName(){
		return userName;
	}
	
	
	public void setUserName(String userName){
		this.userName = userName;
	}
		
	@Override
	public String toString() {
		return super.toString()+": ["+userId+", "+userName+", "+userLastName+"]";
	}
}
