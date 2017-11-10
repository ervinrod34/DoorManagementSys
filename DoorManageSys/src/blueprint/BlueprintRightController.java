package blueprint;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.MasterController;
import application.MasterViewController;
import applicationhelper.PageTypes;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import inventory.*;
import quoteproduct.*;
import order.*;

public class BlueprintRightController implements Initializable {
	
	@FXML private ChoiceBox<Product> productChoices;
	private ObservableList<Product> observableProducts;
	private List<Product> products;
	
	@FXML private ListView<Inventory> inventoryList;
	private ObservableList<Inventory> observableInventory;
	private List<Inventory> inventories;
	
	@FXML Button switchTemplate;
	@FXML Button openBlueprint;
	
	@FXML Label blueprintID;
	@FXML Label productID;
	
	private Order order;
	
	private int selectedProductIndex;
	
	public BlueprintRightController(Order order) {
		this.order = order;
		this.selectedProductIndex = 0;
	}
	
	@FXML private void handleButtons(ActionEvent ae) {
		Object source = ae.getSource();
		
		Product product = this.productChoices.getSelectionModel().getSelectedItem();
		MasterController.getInstance().setEditObject(product);
		
		if(source == switchTemplate) {
			MasterViewController.getInstance().switchTemplateIsPressed();
			MasterViewController.getInstance().changeView(PageTypes.BLUEPRINT_CENTER_PAGE);
		} else if(source == openBlueprint) {
			MasterViewController.getInstance().changeView(PageTypes.BLUEPRINT_CENTER_PAGE);
		}
	}
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(!this.order.getQuote().getProducts().isEmpty()) {
			this.productChoices.getSelectionModel().selectFirst();
		}
		
		this.populateProductChoices();
		this.displayInventoryOfProduct();
	}
	
	private void populateProductChoices() {
		if(!this.order.getQuote().getProducts().isEmpty()) {
			this.products = this.order.getQuote().getProducts();
			this.observableProducts = this.productChoices.getItems();
			for(Product product : this.products) {
				this.observableProducts.add(product);
			}
		}
	}
	
	private void displayInventoryOfProduct() {
		if(!this.products.isEmpty()) {
			this.inventories = this.products.get(this.selectedProductIndex).getInventories();
			this.observableInventory = this.inventoryList.getItems();
			for(Inventory item : this.inventories) {
				this.observableInventory.add(item);
			}
		}
	}
}
