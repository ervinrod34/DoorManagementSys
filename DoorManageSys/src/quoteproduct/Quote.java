package quoteproduct;

import java.util.ArrayList;
import java.util.List;

import quoteproduct.*;

public class Quote {

	private int id;
	
	private List<Product> products;
	
	private double totalCost;
	
	public Quote() {
		this.id = 0;
		this.products = new ArrayList<Product>();
		this.totalCost = 0.0;
	}
	
	public Quote(int id, ArrayList<Product> products, double totalCost) {
		this.id = id;
		this.products = products;
		this.totalCost = totalCost;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	public void save() {
		if(this.id == 0) {
			//TODO: Insert to DB
		} else {
			//TODO: Update DB
		}
	}
	
	public String toString() {
		String returnValue = "";
		
		returnValue += this.id + " Total Cost: " +  this.totalCost;
		
		return returnValue;
	}
}
