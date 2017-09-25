package inventory;

public class Inventory {

	private int id, quantity;
	private double price, totalPrice, weight;
	private String category, description, itemsList;
	
	public Inventory (int id, int quantity, double price, double weight, String category, String description,
					  double totalPrice, String itemsList) {
		
		this.id = id;
		this.quantity = quantity;
		this.price = price;
		this.weight = weight;
		this.category = category;
		this.description = description;
		this.totalPrice = totalPrice;
		this.itemsList = itemsList;
		
	}
	
	public Inventory() {
		
	}

	public String toString () {
		
		StringBuffer inventoryString = new StringBuffer ();
		
		inventoryString.append("ID: " + id + ", Quantity: " + quantity + ", Price: " + price +
							   ", Weight: " + weight + ", Category: " + category + ", Description: " +
							   description + ", totalPrice: " + totalPrice + ", ItemsList: " + itemsList + "\n");
		
		return inventoryString.toString();
	}
	
}
