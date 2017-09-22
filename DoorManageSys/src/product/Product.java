package product;

public class Product {

	private int id, quantity;
	private double weight, height, width, price;
	private String items, description, category;
	
	public Product (int id, int quantity, double weight, double height, double width, double price,
					String items, String description, String category) {
		
		this.id = id;
		this.quantity = quantity;
		this.weight = weight;
		this.height = height;
		this.width = width;
		this.price = price;
		this.items = items;
		this.description = description;
		this.category = category;
		
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



	public double getPrice() {
		return price;
	}



	public void setPrice(double price) {
		this.price = price;
	}



	public String getItems() {
		return items;
	}



	public void setItems(String items) {
		this.items = items;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public String toString () {
		
		StringBuffer productString = new StringBuffer ();
		
		productString.append("ID: " + id + ", Quantity: " + quantity + ", Weight: " + weight + ", Height: " + height +
							 ", Width: " + width + ", Price: " + price + ", Items: " + items + ", Description: " +
							  description + ", Category: " + category);
		
		return productString.toString();
		
	}
	
}
