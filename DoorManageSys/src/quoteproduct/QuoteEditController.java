package quoteproduct;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import order.Order;

public class QuoteEditController implements Initializable {
	
	@FXML private TextField quoteNumber;
	
	@FXML private TextField quoteStatus;
	
	@FXML private TextField purchaseOrderNumber;
	
	@FXML private TextField customerName;
	
	@FXML private TextField orderDate;
	
	@FXML private Button createNewProductButton;
	
	@FXML private Button addDoorButton;
	
	@FXML private Button addFrameButton;
	
	@FXML private Button addHingeButton;
	
	@FXML private Button addLockButton;
	
	@FXML private Button addExtrasButton;
	
	@FXML private Button saveQuoteButton;
	
	@FXML private Button cancelQuoteButton;
	
	@FXML private Label totalPrice;

	@FXML private ChoiceBox<Product> products;
	
	private ObservableList<Product> observableList;
	
	private Quote quote;
	
	private Order order;

	public QuoteEditController(Order order) {
		this.order = order;
		quote = order.getQuote();
		observableList = FXCollections.observableList(quote.getProducts());
	}
	
	@FXML public void handleQuoteEdit(ActionEvent ae) {
		Object source = ae.getSource();
		
		if (source == cancelQuoteButton) {
			
		} else if (source == saveQuoteButton) {
			
		} else if (source == createNewProductButton) {
			
		} else if (source == addDoorButton) {
			
		} else if (source == addFrameButton) {
			
		} else if (source == addHingeButton) {
			
		} else if (source == addLockButton) {
			
		} else if (source == addExtrasButton) {
			
		}
	}
	
	public void updateQuoteObject() {
		Order updatedOrder = new Order();
		
		updatedOrder.setId(order.getId());
		updatedOrder.getQuote().setId(quote.getId());
		
		if (quoteNumber.getText().length() == 0) {
			updatedOrder.getQuote().setId(0);
		} else {
			updatedOrder.getQuote().setId(Integer.parseInt(quoteNumber.getText()));
		}
		
		if (quoteStatus.getText().length() == 0) {
			updatedOrder.setStatus("Unfinished");
		} else {
			updatedOrder.setStatus(quoteStatus.getText());
		}
		
		if (purchaseOrderNumber.getText().length() == 0) {
			updatedOrder.setCustomerPurchaseOrderNumber("");
		} else {
			updatedOrder.setCustomerPurchaseOrderNumber(purchaseOrderNumber.getText());
		}
		
		if (customerName.getText().length() == 0) {
			updatedOrder.setCustomerName("");
		} else {
			updatedOrder.setCustomerName(customerName.getText());
		}
		
		if (orderDate.getText().length() == 0) {
			updatedOrder.setDateOrdered(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		} else {
			updatedOrder.setDateOrdered(java.sql.Date.valueOf(orderDate.getText()));
		}
		
		if (!products.getItems().isEmpty()) {
			updatedOrder.getQuote().setProducts(products.getItems());
		}
		
		order = updatedOrder;
		quote = updatedOrder.getQuote();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (quote.getId() > 0) {
			quoteNumber.setText(Integer.toString(order.getQuoteId()));
			quoteStatus.setText(order.getStatus());
			purchaseOrderNumber.setText(order.getCustomerPurchaseOrderNumber());
			customerName.setText(order.getCustomerName());
			orderDate.setText(order.getDateOrdered().toString());
			products.setItems(observableList);
			totalPrice.setText(Double.toString(quote.getTotalCost()));
		}
	}

}
