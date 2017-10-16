package order;

import java.sql.Date;

import application.MasterController;
import blueprint.Blueprint;
import quote.Quote;

public class Order {

	//object member variables
	private int id;
	
	private Quote quote;
	private Blueprint blueprint;
	
	private String customerPurchaseOrderNumber;
	private String customerName;
	private String productCode;
	private String status;
	
	private Date dateOrdered;
	private Date targetShipping;
	private Date actualShipping;
	
	private Double totalAmount;
	
	//constructors
	public Order(){
		this.quote = new Quote(0);
		this.blueprint = new Blueprint(0);
		this.customerPurchaseOrderNumber = "";
		this.customerName = "";
		this.productCode = "";
		this.status = "";
		this.dateOrdered = null;
		this.targetShipping = null;
		this.actualShipping = null;
		this.totalAmount = 0.0;
	}
	
	public Order(int quoteId, int blueprintId, String cPON, String cName, 
			String pCode, String status, Date dateOrdered, Date target, Date actual, 
			Double totalAmount){
		this();
		this.customerPurchaseOrderNumber = cPON;
		this.customerName = cName;
		this.productCode = pCode;
		this.status = status;
		this.dateOrdered = dateOrdered;
		this.targetShipping = target;
		this.actualShipping = actual;
		this.totalAmount = totalAmount;
		this.quote = getQuoteById(quoteId);
		this.blueprint = getBlueprintById(blueprintId);
	}
	
	public Order(int id, int quoteId, int blueprintId, String cPON, String cName, 
			String pCode, String status, Date dateOrdered, Date target, Date actual, 
			Double totalAmount){
		this();
		this.id = id;
		this.customerPurchaseOrderNumber = cPON;
		this.customerName = cName;
		this.productCode = pCode;
		this.status = status;
		this.dateOrdered = dateOrdered;
		this.targetShipping = target;
		this.actualShipping = actual;
		this.totalAmount = totalAmount;
		this.quote = getQuoteById(quoteId);
		this.blueprint = getBlueprintById(blueprintId);
}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	/*public boolean checkId(int id){
		if(id >= 0)
			return true;
		else
			return false;
	}*/

	public Quote getQuote() {
		return quote;
	}

	public void setQuote(Quote quote) {
		this.quote = quote;
	}
	
	public int getQuoteId(){
		return quote.getId();
	}

	public Blueprint getBlueprint() {
		return blueprint;
	}
	
	public int getBlueprintId(){
		return blueprint.getId();
	}

	public void setBlueprint(Blueprint blueprint) {
		this.blueprint = blueprint;
	}

	public String getCustomerPurchaseOrderNumber() {
		return customerPurchaseOrderNumber;
	}

	public void setCustomerPurchaseOrderNumber(String customerPurchaseOrderNumber) {
		this.customerPurchaseOrderNumber = customerPurchaseOrderNumber;
	}
	//check for other things too?
	/*public boolean checkCustomerPurchaseOrderNumber(String customerPurchaseOrderNumber) {
		if(customerPurchaseOrderNumber.length() > 0)
			return true;
		else
			return false;
	}*/

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	/*public boolean checkCustomerName(String customerName) {
		if(customerName.length() > 0)
			return true;
		else
			return false;
	}*/
	

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	/*public boolean checkProductCode(String productCode) {
		if(productCode.length() > 0)
			return true;
		else
			return false;
	}*/

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	/*public boolean checkStatus(String status) {
		if(status.length() > 0)
			return true;
		else
			return false;
	}*/

	public Date getDateOrdered() {
		return dateOrdered;
	}

	public void setDateOrdered(Date dateOrdered) {
		this.dateOrdered = dateOrdered;
	}
	
	/*public boolean checkDateOrdered(Date dateOrdered) {
		if(dateOrdered != null)
			return true;
		else
			return false;
	}*/

	public Date getTargetShipping() {
		return targetShipping;
	}

	public void setTargetShipping(Date targetShipping) {
		this.targetShipping = targetShipping;
	}
	
	/*public boolean checkTargetShipping(Date targetShipping) {
		if(targetShipping != null)
			return true;
		else
			return false;
	}*/

	public Date getActualShipping() {
		return actualShipping;
	}

	public void setActualShipping(Date actualShipping) {
		this.actualShipping = actualShipping;
	}
	
	/*public boolean checkActualShipping(Date actalShipping) {
		if(actualShipping != null)
			return true;
		else
			return false;
	}*/

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	/*public boolean checkTotalAmount(Double totalAmount) {
		if(totalAmount >= 0.0)
			return true;
		else
			return false;
	}*/
	
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
