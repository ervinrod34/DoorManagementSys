package inventory;

public class Inventory {

	private int id, quantity;
	private double weight, height, width;
	private String category, description;
	
	public Inventory (int id, int quantity, double weight, double height, double width,
					  String category, String description) {
		this.id = id;
		this.quantity = quantity;
		this.weight = weight;
		this.height = height;
		this.width = width;
		this.category = category;
		this.description = description;
		
		
	}
	
	
	
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getQuantity() {
		return quantity;
	}



	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}



	public double getWeight() {
		return weight;
	}



	public void setWeight(double weight) {
		this.weight = weight;
	}



	public double getHeight() {
		return height;
	}



	public void setHeight(double height) {
		this.height = height;
	}



	public double getWidth() {
		return width;
	}



	public void setWidth(double width) {
		this.width = width;
	}



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String toString () {
		
		StringBuffer inventoryString = new StringBuffer ();
		
		inventoryString.append("ID: " + id + ", Quantity: " + quantity + ", Weight: " + weight +
							   ", Height: " + height + ", Width: " + width + ", Category: " + category + ", Description: " +
							   description);
		
		return inventoryString.toString();
	}
	
}
