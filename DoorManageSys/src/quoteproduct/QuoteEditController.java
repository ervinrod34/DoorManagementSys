package quoteproduct;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

import application.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
	
	private ChangeListener<Product> selectionListener = new ChangeListener<Product>() {
		@Override
		public void changed(ObservableValue<? extends Product> arg0, Product arg1, Product arg2) {
			MasterController.getInstance().setProductToDisplay(products.getSelectionModel().getSelectedItem());
			MasterViewController.getInstance().changeView(PageTypes.QUOTEITEMS_LIST_PAGE);
			MasterViewController.getInstance().changeView(PageTypes.QUOTE_EDIT_PAGE);
		}
	};

	public QuoteEditController(Order order) {
		this.order = order;
		quote = order.getQuote();
		observableList = FXCollections.observableList(quote.getProducts());
	}
	
	@FXML public void handleQuoteEdit(ActionEvent ae) {
		Object source = ae.getSource();
		
		if (source == cancelQuoteButton) {
			for(int i = 0; i < products.getItems().size(); i++) {
				if(products.getItems().get(i).getId() == 0) {
					products.getItems().remove(i);
					i--;
				}
			}
			MasterViewController.getInstance().changeView(PageTypes.QORDER_LIST_PAGE);
			MasterController.getInstance().setEditObject(this.order);
			MasterViewController.getInstance().changeView(PageTypes.QUOTE_DETAIL_PAGE);
		} else if (source == saveQuoteButton) {
			this.updateQuoteObject();
			this.quote.saveProducts();
			this.quote.save();
			this.order.save();
			
			MasterViewController.getInstance().changeView(PageTypes.QORDER_LIST_PAGE);
			MasterController.getInstance().setEditObject(this.order);
			MasterViewController.getInstance().changeView(PageTypes.QUOTE_DETAIL_PAGE);
		} else if (source == createNewProductButton) {
			Product newProduct = new Product();
			products.getItems().add(newProduct);
			MasterController.getInstance().setProductToDisplay(newProduct);
			MasterViewController.getInstance().changeView(PageTypes.QUOTEITEMS_LIST_PAGE);
			MasterViewController.getInstance().changeView(PageTypes.QUOTE_EDIT_PAGE);
		} else if (source == addDoorButton) {
			MasterController.getInstance().setInventoryListToDisplay(
					MasterController.getInstance().getInventoryGateway().searchInventory("category", "Door"));
			MasterViewController.getInstance().changeView(PageTypes.QUOTEITEMS_LIST_PAGE);
			MasterViewController.getInstance().changeView(PageTypes.QUOTE_EDIT_PAGE);
		} else if (source == addFrameButton) {
			MasterController.getInstance().setInventoryListToDisplay(
					MasterController.getInstance().getInventoryGateway().searchInventory("category", "Frame"));
			MasterViewController.getInstance().changeView(PageTypes.QUOTEITEMS_LIST_PAGE);
			MasterViewController.getInstance().changeView(PageTypes.QUOTE_EDIT_PAGE);
		} else if (source == addHingeButton) {
			MasterController.getInstance().setInventoryListToDisplay(
					MasterController.getInstance().getInventoryGateway().searchInventory("category", "Hinge"));
			MasterViewController.getInstance().changeView(PageTypes.QUOTEITEMS_LIST_PAGE);
			MasterViewController.getInstance().changeView(PageTypes.QUOTE_EDIT_PAGE);
		} else if (source == addLockButton) {
			MasterController.getInstance().setInventoryListToDisplay(
					MasterController.getInstance().getInventoryGateway().searchInventory("category", "Lock"));
			MasterViewController.getInstance().changeView(PageTypes.QUOTEITEMS_LIST_PAGE);
			MasterViewController.getInstance().changeView(PageTypes.QUOTE_EDIT_PAGE);
		} else if (source == addExtrasButton) {
			MasterController.getInstance().setInventoryListToDisplay(
					MasterController.getInstance().getInventoryGateway().searchInventoryForExtras());
			MasterViewController.getInstance().changeView(PageTypes.QUOTEITEMS_LIST_PAGE);
			MasterViewController.getInstance().changeView(PageTypes.QUOTE_EDIT_PAGE);
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
			updatedOrder.getQuote().setTotalCost(
					updatedOrder.getQuote().calculateTotalCost());
		}
		
		order = updatedOrder;
		quote = updatedOrder.getQuote();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (order.getId() > 0) {
			quoteNumber.setText(Integer.toString(order.getQuote().getId()));
			quoteStatus.setText(order.getStatus());
			purchaseOrderNumber.setText(order.getCustomerPurchaseOrderNumber());
			customerName.setText(order.getCustomerName());
			orderDate.setText(order.getDateOrdered().toString());
			quote.setTotalCost(quote.calculateTotalCost());
			totalPrice.setText(Double.toString(quote.getTotalCost()));
		}
		products.setItems(observableList);
		//TODO: fix on empty product list (new quotes)
		products.getSelectionModel().select(MasterController.getInstance().getProductToDisplay());
		products.getSelectionModel().selectedItemProperty().addListener(selectionListener);
	}

}
