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
	
	public String toString () {
		
		StringBuffer inventoryString = new StringBuffer ();
		
		inventoryString.append("ID: " + id + ", Quantity: " + quantity + ", Weight: " + weight +
							   ", Height: " + height + ", Width: " + width + ", Category: " + category + ", Description: " +
							   description);
		
		return inventoryString.toString();
	}
	
}
