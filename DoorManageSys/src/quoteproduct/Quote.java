package quoteproduct;

import java.util.ArrayList;
import java.util.List;

import application.MasterController;
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
	
	public Quote(int id, List<Product> list, double totalCost) {
		this.id = id;
		this.products = list;
		this.totalCost = totalCost;
	}
	
	public Double calculateTotalCost() {
		Double totalCost = 0.0;
		for(Product product : this.products) {
			totalCost += product.getTotalCost();
		}
		return totalCost;
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
		if(this.totalCost == 0) {
			this.totalCost = calculateTotalCost();
		}
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}
	
	public void saveProducts() {
		for(Product product : this.products) {
			product.save();
		}
	}
	
	public void save() {
		if(this.id == 0) {
			MasterController.getInstance().getQuoteGateway().insertNewQuoteRecord(this);
		} else {
			MasterController.getInstance().getQuoteGateway().updateQuoteRecord(this);
		}
	}
	
	public String toString() {
		String returnValue = "";
		
		returnValue += this.id + " Total Cost: " +  this.totalCost;
		
		return returnValue;
	}
}
