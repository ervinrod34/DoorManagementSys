package quoteproduct;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

import application.MasterController;
import application.PageTypes;
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
			MasterController.getInstance().changeView(PageTypes.QORDER_LIST_PAGE);
			for(int i = 0; i < products.getItems().size(); i++) {
				if(products.getItems().get(i).getId() == 0) {
					products.getItems().remove(i);
					i--;
				}
			}
			MasterController.getInstance().setEditObject(this.order);
			MasterController.getInstance().changeView(PageTypes.QUOTE_DETAIL_PAGE);
		} else if (source == saveQuoteButton) {
			this.updateQuoteObject();
			this.order.save();
			
			MasterController.getInstance().changeView(PageTypes.QORDER_LIST_PAGE);
			MasterController.getInstance().setEditObject(this.order);
			MasterController.getInstance().changeView(PageTypes.QUOTE_DETAIL_PAGE);
		} else if (source == createNewProductButton) {
			Product newProduct = new Product();
			products.getItems().add(newProduct);
			products.getSelectionModel().selectLast();
			MasterController.getInstance().setProductToDisplay(newProduct);
			MasterController.getInstance().changeView(PageTypes.QUOTEITEMS_LIST_PAGE);
			MasterController.getInstance().changeView(PageTypes.QUOTE_EDIT_PAGE);
		} else if (source == addDoorButton) {
			MasterController.getInstance().setInventoryListToDisplay(
					MasterController.getInstance().getInventoryGateway().searchInventory("category", "Door"));
			MasterController.getInstance().changeView(PageTypes.QUOTEITEMS_LIST_PAGE);
			MasterController.getInstance().changeView(PageTypes.QUOTE_EDIT_PAGE);
		} else if (source == addFrameButton) {
			MasterController.getInstance().setInventoryListToDisplay(
					MasterController.getInstance().getInventoryGateway().searchInventory("category", "Frame"));
			MasterController.getInstance().changeView(PageTypes.QUOTEITEMS_LIST_PAGE);
			MasterController.getInstance().changeView(PageTypes.QUOTE_EDIT_PAGE);
		} else if (source == addHingeButton) {
			MasterController.getInstance().setInventoryListToDisplay(
					MasterController.getInstance().getInventoryGateway().searchInventory("category", "Hinge"));
			MasterController.getInstance().changeView(PageTypes.QUOTEITEMS_LIST_PAGE);
			MasterController.getInstance().changeView(PageTypes.QUOTE_EDIT_PAGE);
		} else if (source == addLockButton) {
			MasterController.getInstance().setInventoryListToDisplay(
					MasterController.getInstance().getInventoryGateway().searchInventory("category", "Lock"));
			MasterController.getInstance().changeView(PageTypes.QUOTEITEMS_LIST_PAGE);
			MasterController.getInstance().changeView(PageTypes.QUOTE_EDIT_PAGE);
		} else if (source == addExtrasButton) {
			MasterController.getInstance().setInventoryListToDisplay(
					MasterController.getInstance().getInventoryGateway().searchInventoryForExtras());
			MasterController.getInstance().changeView(PageTypes.QUOTEITEMS_LIST_PAGE);
			MasterController.getInstance().changeView(PageTypes.QUOTE_EDIT_PAGE);
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
			quoteNumber.setText(Integer.toString(order.getQuote().getId()));
			quoteStatus.setText(order.getStatus());
			purchaseOrderNumber.setText(order.getCustomerPurchaseOrderNumber());
			customerName.setText(order.getCustomerName());
			orderDate.setText(order.getDateOrdered().toString());
			products.setItems(observableList);
			products.getSelectionModel().selectFirst();
			totalPrice.setText(Double.toString(quote.getTotalCost()));
		}
	}

}
