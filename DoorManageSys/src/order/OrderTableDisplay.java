package order;

import javafx.beans.property.SimpleStringProperty;

public class OrderTableDisplay {

	private final SimpleStringProperty orderID;
	private final SimpleStringProperty orderCustomerName;
	private final SimpleStringProperty orderPurchaseNum;
	private final SimpleStringProperty orderDate;
	private final SimpleStringProperty orderTargetShipping;
	private final SimpleStringProperty orderProductCode;
	private final SimpleStringProperty orderStatus;
	private final SimpleStringProperty orderTotal;
	
	private Order order;
	
	public OrderTableDisplay(Order order) {
		this.orderID = new SimpleStringProperty(String.valueOf(order.getId()));
		this.orderCustomerName = new SimpleStringProperty(order.getCustomerName());
		this.orderPurchaseNum = new SimpleStringProperty(order.getCustomerPurchaseOrderNumber());
		this.orderDate = new SimpleStringProperty(order.getDateOrdered().toString());
		this.orderTargetShipping = new SimpleStringProperty(order.getProductCode());
		this.orderProductCode = new SimpleStringProperty(order.getProductCode());
		this.orderStatus = new SimpleStringProperty(String.valueOf(order.getStatus()));
		this.orderTotal = new SimpleStringProperty(String.valueOf(order.getTotalAmount()));
	}
	
	public String getOrderID() {
		return orderID.get();
	}
	
	public String getOrderCustomerName() {
		return orderCustomerName.get();
	}
	
	public String getOrderPurchaseNum() {
		return orderPurchaseNum.get();
	}
	
	public String getOrderDate() {
		return orderDate.get();
	}
	
	public String getOrderTargetShipping() {
		return orderTargetShipping.get();
	}
	
	public String getOrderProductCode() {
		return orderProductCode.get();
	}
	
	public String getOrderStatus() {
		return orderStatus.get();
	}
	
	public String getOrderTotal() {
		return orderTotal.get();
	}
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}

}
