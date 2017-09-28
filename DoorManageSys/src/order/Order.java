package order;

import java.time.LocalDateTime;

import application.*;
import blueprint.Blueprint;
import product.Product;

public class Order {
	
	//object member variables
	private int id, orderID, productID, blueprintID;
	private Product p;
	private Blueprint bp;
	//create product and blueprint objects
	private String customerName, status;
	private double orderDollarAmount;
	private LocalDateTime orderDate; //change
	
	//constructors
	public Order(){
		this.orderID = 0;
		this.productID = 0;
		this.p = new Product(productID, 0, 0, 0, 0, 0, "", "", "");
		this.blueprintID = 0;
		this.bp = new Blueprint(blueprintID);
		this.customerName = "";
		this.status = "";
		this.orderDollarAmount = 0;
		this.orderDate = null;
	}
	
	public Order(int oID, String cName, String status, double orderAmount,
			int pID, int bpID, LocalDateTime oD){
		this();
		this.orderID = oID;
		this.customerName = cName;
		this.status = status;
		this.orderDollarAmount = orderAmount;
		this.productID = pID;
		//this.p = 
		this.blueprintID = bpID;
		//this.bp = 
		this.orderDate = oD;
	}
	
	public Order(int id, int oID, String cName, String status, double orderAmount,
			int pID, int bpID, LocalDateTime oD){
		this();
		this.id = id;
		this.orderID = oID;
		this.customerName = cName;
		this.status = status;
		this.orderDollarAmount = orderAmount;
		this.productID = pID;
		//this.p = 
		this.blueprintID = bpID;
		//this.bp = 
		this.orderDate = oD;
	}

	//getters and setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public int getBlueprintID() {
		return blueprintID;
	}

	public void setBlueprintID(int blueprintID) {
		this.blueprintID = blueprintID;
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
		String s = "Order: " + this.orderID + " Customer: " + this.customerName + " Amount: " +
				this.orderDollarAmount + " Date: " + this.orderDate + " Status: " + this.status;
		return s;
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
