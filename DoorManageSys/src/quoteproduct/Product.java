package quoteproduct;

import application.*;

import java.util.List;

public class Product {

	private int id;

	private String inventoryIDs;
	
	private double totalCost;
	
	private String category;
	
	public Product(int id) {
		this.id = id;
	}
	
	public Product (int id, String ids, double totalCost, String category) {
		
		this.id = id;
		this.inventoryIDs = ids;
		this.totalCost = totalCost;
		this.category = category;
		
	}	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInventoryIDs() {
		return inventoryIDs;
	}

	public void setInventoryIDs(String inventoryIDs) {
		this.inventoryIDs = inventoryIDs;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void save () {
		if (id == 0) {
			MasterController.getInstance().getProductGateway().addProduct(this);
		}else {
			MasterController.getInstance().getProductGateway().updateProduct(this);
		}
	}
	
	public String toString () {
		String returnValue = "ID: " + id + ", totalCost: " + totalCost + ", idList: " + inventoryIDs + ", Category: " + category;
		
		return returnValue;
		
	}
	
}
