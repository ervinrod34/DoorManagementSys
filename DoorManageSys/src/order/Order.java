package order;

import java.time.LocalDateTime;

import application.*;
import blueprint.Blueprint;
import quoteproduct.Product;

public class Order {
	
	//object member variables
	private int id;
	private Product product;
	private Blueprint blueprint;
	//create product and blueprint objects
	private String customerName, status;
	private double orderDollarAmount;
	private LocalDateTime orderDate; //change
	
	//constructors
	public Order(){
		this.id = 0;
		this.product = new Product(0);
		this.blueprint = new Blueprint(0);
		this.customerName = "";
		this.status = "";
		this.orderDollarAmount = 0;
		this.orderDate = null;
	}
	
	public Order(int id, String cName, String status, double orderAmount, 
			Product product, Blueprint blueprint, LocalDateTime oD){
		this();
		this.customerName = cName;
		this.status = status;
		this.orderDollarAmount = orderAmount;
		this.product = product;
		this.blueprint = blueprint;
		this.orderDate = oD;
	}

	//getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Blueprint getBlueprint() {
		return blueprint;
	}

	public void setBlueprint(Blueprint blueprint) {
		this.blueprint = blueprint;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getOrderDollarAmount() {
		return orderDollarAmount;
	}

	public void setOrderDollarAmount(double orderDollarAmount) {
		this.orderDollarAmount = orderDollarAmount;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}
	
	//toString
	public String toString(){
		String stringReturn = "Order: " + this.id + " Customer: " + this.customerName + " Amount: " +
				this.orderDollarAmount + " Date: " + this.orderDate + " Status: " + this.status;
		return stringReturn;
	}
	
	//check for incorrect formatting
	public boolean checkID(int id){
		if (id >= 0)
			return true;
		else
			return false;
	}
	
	public boolean checkOrderID(int oID){
		if (oID >= 0)
			return true;
		else
			return false;
	}
	
	public boolean checkCustomerName(String cN){
		int length = cN.length();
		if (0 < length && length <= 255)
			return true;
		else
			return false;
	}
	
	public boolean checkStatus(String s){
		int length = s.length();
		if (0 < length && length <= 255)
			return true;
		else
			return false;
	}
	
	public boolean checkOrderDollarAmount(double oda){
		if (oda >= 0)
			return true;
		else
			return false;
	}
	
	public boolean checkProductID(int pID){
		if (pID >= 0)
			return true;
		else
			return false;
	}
	
	public boolean checkBlueprintID(int bpID){
		if (bpID >= 0)
			return true;
		else
			return false;
	}
	
	/*public boolean checkOrderDate(LocalDateTime od){
		
	}*/
}
