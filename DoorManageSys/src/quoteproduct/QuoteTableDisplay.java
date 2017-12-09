package quoteproduct;

import javafx.beans.property.SimpleStringProperty;
import order.Order;

public class QuoteTableDisplay {

	private final SimpleStringProperty quotePurchaseNum;
	private final SimpleStringProperty quoteCustomerName;
	private final SimpleStringProperty quoteStatus;
	private final SimpleStringProperty quoteDate;
	private final SimpleStringProperty quoteTotal;
	
	private Order order;
	
	public QuoteTableDisplay(Order order) {
		this.quotePurchaseNum = new SimpleStringProperty(order.getCustomerPurchaseOrderNumber());
		this.quoteCustomerName = new SimpleStringProperty(order.getCustomerName());
		this.quoteStatus = new SimpleStringProperty(order.getStatus());
		this.quoteDate = new SimpleStringProperty(order.getDateOrdered().toString());
		this.quoteTotal = new SimpleStringProperty(String.valueOf(order.getTotalAmount()));
	}
	
	public String getQuotePurchaseNum() {
		return quotePurchaseNum.get();
	}
	
	public String getQuoteCustomerName() {
		return quoteCustomerName.get();
	}
	
	public String getQuoteStatus() {
		return quoteStatus.get();
	}
	
	public String getQuoteDate() {
		return quoteDate.get();
	}
	
	public String getQuoteTotal() {
		return quoteTotal.get();
	}
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
}
