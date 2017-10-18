package quoteproduct;

import application.*;
import inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class Product {

	private int id;

	private List<Inventory> inventories;
	
	private double totalCost;
	
	
	
	public Product(int id) {
		this.id = id;
		this.totalCost = 0.0;
		this.inventories = new ArrayList<Inventory>();
	}
	
	public Product (int id, List<Inventory> ids, double totalCost) {
		
		this.id = id;
		this.inventories = ids;
		this.totalCost = totalCost;
		
		
	}	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	

	public List<Inventory> getInventories() {
		return inventories;
	}

	public void setInventories(List<Inventory> inventories) {
		this.inventories = inventories;
	}

//	public void save () {
//		if (id == 0) {
//			MasterController.getInstance().getProductGateway().addProduct(this);
//		}else {
//			MasterController.getInstance().getProductGateway().updateProduct(this);
//		}
//	}
	
	public String toString () {
		String returnValue = "ID: " + id + ", totalCost: " + totalCost + ", idList: " + inventories + ", Category: ";
		
		return returnValue;
		
	}
	
}
