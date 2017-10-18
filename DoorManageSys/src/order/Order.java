package order;

import java.sql.Date;

import application.MasterController;
import blueprint.Blueprint;
import quoteproduct.*;

public class Order {

	private int id;
	
	private Quote quote;
	
	private String customerPurchaseOrderNumber;
	private String customerName;
	private String productCode;
	private String status;
	
	private Date dateOrdered;
	private Date targetShipping;
	private Date actualShipping;
	
	private Blueprint blueprint;
	
	private Double totalAmount;
	
	@SuppressWarnings("deprecation") //Needs to change this type later on
	public Order(){
		this.id = 0;
		this.quote = new Quote();
		this.customerPurchaseOrderNumber = "";
		this.customerName = "";
		this.productCode = "";
		this.status = "";
		this.dateOrdered = new Date(2000, 01, 01); //yyyy-MM-dd
		this.targetShipping = new Date(2000, 01, 01);
		this.actualShipping = new Date(2000, 01, 01);
		this.blueprint = new Blueprint(0);
		this.totalAmount = 0.0;
	}
	
	public Order(int id, Quote quote, String cPON, String cName, 
			String pCode, String status, Date dateOrdered, Date target, Date actual, 
			Blueprint blueprint, Double totalAmount){
		this.id = id;
		this.quote = quote;
		this.customerPurchaseOrderNumber = cPON;
		this.customerName = cName;
		this.productCode = pCode;
		this.status = status;
		this.dateOrdered = dateOrdered;
		this.targetShipping = target;
		this.actualShipping = actual;
		this.blueprint = blueprint;
		this.totalAmount = totalAmount;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}

	public String getCustomerPurchaseOrderNumber() {
		return customerPurchaseOrderNumber;
	}

	public void setCustomerPurchaseOrderNumber(String customerPurchaseOrderNumber) {
		this.customerPurchaseOrderNumber = customerPurchaseOrderNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDateOrdered() {
		return dateOrdered;
	}

	public void setDateOrdered(Date dateOrdered) {
		this.dateOrdered = dateOrdered;
	}

	public Date getTargetShipping() {
		return targetShipping;
	}

	public void setTargetShipping(Date targetShipping) {
		this.targetShipping = targetShipping;
	}

	public Date getActualShipping() {
		return actualShipping;
	}

	public void setActualShipping(Date actualShipping) {
		this.actualShipping = actualShipping;
	}

	public Blueprint getBlueprint() {
		return blueprint;
	}

	public void setBlueprint(Blueprint blueprint) {
		this.blueprint = blueprint;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void save() {
		if (id == 0) {
			MasterController.getInstance().getOrderGateway().addOrder(this);
		} else {
			MasterController.getInstance().getOrderGateway().updateOrder(this);
		}
	}
	
	public String toString(){
		return "";
	}
	
}
