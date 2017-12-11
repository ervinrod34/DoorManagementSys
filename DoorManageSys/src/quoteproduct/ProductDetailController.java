package quoteproduct;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.MasterController;
import application.MasterViewController;
import applicationdialogs.InfoDialogs;
import applicationhelper.PageTypes;
import inventory.Inventory;
import inventory.InventoryTableDisplay;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProductDetailController implements Initializable {

	@FXML private Label productNumber;
	@FXML private Label totalCost;
	@FXML private Button editButton;
	@FXML private Button deleteButton;
	
	@FXML private TableView<InventoryTableDisplay> inventoryTable;
	@FXML private TableColumn<InventoryTableDisplay, String> itemIDCol;
	@FXML private TableColumn<InventoryTableDisplay, String> itemCategoryCol;
	@FXML private TableColumn<InventoryTableDisplay, String> itemManufacturerCol;
	@FXML private TableColumn<InventoryTableDisplay, String> itemManufacturerNumCol;
	@FXML private TableColumn<InventoryTableDisplay, String> itemUoMCol;
	@FXML private TableColumn<InventoryTableDisplay, String> itemSizeCol;
	@FXML private TableColumn<InventoryTableDisplay, String> itemColorCol;
	@FXML private TableColumn<InventoryTableDisplay, String> itemExtraInfoCol;
	@FXML private TableColumn<InventoryTableDisplay, String> itemCurrQuantityCol;
	@FXML private TableColumn<InventoryTableDisplay, String> itemMinQuantityCol;
	@FXML private TableColumn<InventoryTableDisplay, String> itemMaxQuantityCol;
	
	private ObservableList<InventoryTableDisplay> observableList;
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
		
		itemIDCol.setCellValueFactory(new PropertyValueFactory<InventoryTableDisplay, String>("itemID"));
		itemCategoryCol.setCellValueFactory(new PropertyValueFactory<InventoryTableDisplay, String>("itemCategory"));
		itemManufacturerCol.setCellValueFactory(new PropertyValueFactory<InventoryTableDisplay, String>("itemManufacturer"));
		itemManufacturerNumCol.setCellValueFactory(new PropertyValueFactory<InventoryTableDisplay, String>("itemManufacturerNum"));
		itemUoMCol.setCellValueFactory(new PropertyValueFactory<InventoryTableDisplay, String>("itemUoM"));
		itemSizeCol.setCellValueFactory(new PropertyValueFactory<InventoryTableDisplay, String>("itemSize"));
		itemColorCol.setCellValueFactory(new PropertyValueFactory<InventoryTableDisplay, String>("itemColor"));
		itemExtraInfoCol.setCellValueFactory(new PropertyValueFactory<InventoryTableDisplay, String>("itemExtraInfo"));
		itemCurrQuantityCol.setCellValueFactory(new PropertyValueFactory<InventoryTableDisplay, String>("itemCurrQuantity"));
		itemMinQuantityCol.setCellValueFactory(new PropertyValueFactory<InventoryTableDisplay, String>("itemMinQuantity"));
		itemMaxQuantityCol.setCellValueFactory(new PropertyValueFactory<InventoryTableDisplay, String>("itemMaxQuantity"));
		
		observableList = FXCollections.observableArrayList();
		for(Inventory item : inventories) {
			InventoryTableDisplay itemDisplay = new InventoryTableDisplay(item);
			itemDisplay.setInventoryItem(item);
			observableList.add(itemDisplay);
		}
		
		inventoryTable.setItems(observableList);
	}
}
