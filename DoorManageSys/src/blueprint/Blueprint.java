package blueprint;

public class Blueprint {
	private int id;
	
	public Blueprint(int id){
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public String toString() {
		return Integer.toString(this.id);
	}
}
