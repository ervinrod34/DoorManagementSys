package quoteproduct;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import application.*;
import applicationdialogs.InfoDialogs;
import applicationhelper.PageTypes;
import inventory.Inventory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ProductDetailController implements Initializable {

	@FXML private Label productNumber;
	@FXML private Label totalCost;
	@FXML private Button editButton;
	@FXML private Button deleteButton;
	@FXML private ListView<String> inventoryItemsList;
	
	private ObservableList<String> observableList;
	private Product product;
	
	public ProductDetailController(Product product) {
		this.product = product;
	}
	
	@FXML private void handleProductDetail(ActionEvent ae) {
		
		Object source = ae.getSource();
		
		if(source == editButton) {
			MasterController.getInstance().setEditObject(this.product);
			MasterViewController.getInstance().changeView(PageTypes.PRODUCT_EDIT_PAGE);
			
		} else if(source == deleteButton) {
			
			InfoDialogs dug = new InfoDialogs();
			int d = dug.displayDeleteConfirmation();
			if(d == 1) {
				MasterController.getInstance().getProductGateway().deleteProductRecord(this.product.getId());
			
			}
		}
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(this.product.getId() > 0) {
			this.productNumber.setText(Integer.toString(this.product.getId()));
			this.totalCost.setText(Double.toString(this.product.getTotalCost()));
			
			this.populateInventoryItemsList();
		}
	}
	
	private void populateInventoryItemsList() {
		List<Inventory> inventories = product.getInventories();
		
		this.observableList = this.inventoryItemsList.getItems();
		for(Inventory inventoryItem : inventories) {
			this.observableList.add(inventoryItem.toString());
		}
	}
}
