package quoteproduct;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.print.attribute.AttributeSet;
import javax.swing.JFormattedTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import application.*;
import applicationdialogs.InfoDialogs;
import applicationhelper.PageTypes;
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
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;
import order.Order;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;

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
			InfoDialogs dug = new InfoDialogs();
			int d = dug.displaySaveConfirmation();
			if(d == 1) {
				this.updateQuoteObject();
				this.quote.saveProducts();
				this.quote.save();
				this.order.save();
				this.quote.subtractQuantity();
			}
			
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
	
	private Pattern validDoubleText = Pattern.compile("-?((\\d*)|(\\d+\\.\\d*))");
	
	
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
		customerName.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("[a-zA-Z]*")) {
		        	customerName.setText(newValue.replaceAll("[^a-zA-Z]", ""));
		        }
		    }
		});
		
		quoteStatus.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("[a-zA-Z]*")) {
		        	quoteStatus.setText(newValue.replaceAll("[^a-zA-Z]", ""));
		        }
		    }
		});
		
		purchaseOrderNumber.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("(\\w*)")) {
		        	purchaseOrderNumber.setText(newValue.replaceAll("[^\\w]", ""));
		        }
		    }
		});
		
		orderDate.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("[\\w\\-\\.\\'\"]*")) {
		        	orderDate.setText(newValue.replaceAll("[^\\w\\-\\.\\'\"]", ""));
		        }
		    }
		});
		
		
		products.setItems(observableList);
		//TODO: fix on empty product list (new quotes)
		products.getSelectionModel().select(MasterController.getInstance().getProductToDisplay());
		products.getSelectionModel().selectedItemProperty().addListener(selectionListener);
		
	}

}

