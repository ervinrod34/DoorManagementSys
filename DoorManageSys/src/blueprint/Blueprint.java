package blueprint;

public class Blueprint {
	
	private int id;
	private int productID;
	
	private String dimension;
	private String strikeHeight;
	private String frame;
	private String hingeSpaces;
	
	private String notes;
	private String inventoryNotes;
	
	public Blueprint() {
		
	}
	
	public Blueprint(int id){
		this.id = id;
	}
	
	public Blueprint(int id, int productID, String dimension, 
			String strikeHeight, String frame, String hingeSpaces,
			String notes, String inventoryNotes) {
		this.id = id;
		this.productID = productID;
		this.dimension = dimension;
		this.strikeHeight = strikeHeight;
		this.frame = frame;
		this.hingeSpaces = hingeSpaces;
		this.notes = notes;
		this.inventoryNotes = inventoryNotes;
	}

	public int getId() {
		return id;
	}
	
	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getStrikeHeight() {
		return strikeHeight;
	}

	public void setStrikeHeight(String strikeHeight) {
		this.strikeHeight = strikeHeight;
	}

	public String getFrame() {
		return frame;
	}

	public void setFrame(String frame) {
		this.frame = frame;
	}

	public String getHingeSpaces() {
		return hingeSpaces;
	}

	public void setHingeSpaces(String hingeSpaces) {
		this.hingeSpaces = hingeSpaces;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getInventoryNotes() {
		return inventoryNotes;
	}

	public void setInventoryNotes(String inventoryNotes) {
		this.inventoryNotes = inventoryNotes;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String toString() {
		return Integer.toString(this.id);
	}
}
