package classexamples.bad;

public class TA {

	private String id;
	
	public int dNumber;
	
	public void setId(String id){
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "["+id+","+dNumber+"]";
	}
}
