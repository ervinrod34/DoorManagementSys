package quoteproduct;



import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import applicationdialogs.InfoDialogs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import inventory.*;

public class QuoteItemsListController implements Initializable {

	@FXML private TableView<ProductItemTableDisplay> productItemTable;
	@FXML private TableColumn<ProductItemTableDisplay, String> prodItemIDCol;
	@FXML private TableColumn<ProductItemTableDisplay, String> prodItemCategoryCol;
	@FXML private TableColumn<ProductItemTableDisplay, String> prodItemManufacturerCol;
	@FXML private TableColumn<ProductItemTableDisplay, String> prodItemManufacturerNumCol;
	@FXML private TableColumn<ProductItemTableDisplay, String> prodItemUoMCol;
	@FXML private TableColumn<ProductItemTableDisplay, String> prodItemSizeCol;
	@FXML private TableColumn<ProductItemTableDisplay, String> prodItemColorCol;
	@FXML private TableColumn<ProductItemTableDisplay, String> prodItemExtraInfoCol;
	
	private Product selectedProduct;
	private ObservableList<ProductItemTableDisplay> observableProductItems;
	private Inventory selectedProductItem;

	
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
	
	private ObservableList<InventoryTableDisplay> observableInventory;
	private List<Inventory> inventory;
	
	@FXML private Button deleteItem;
	
	public QuoteItemsListController(Product product, List<Inventory> inventory) {
		//TODO chech is product exist, user needs to select which product
		// user will be adding Inventory item to
		this.selectedProduct = product;
		this.inventory = inventory;
	}
	
	@FXML private void handleDelete(ActionEvent action) {
		Object source = action.getSource();
		if(source == deleteItem) {
			InfoDialogs dug = new InfoDialogs();
			int d = dug.displayDeleteConfirmation();
			if(d == 1) {
				this.tryToRemoveInventory(this.selectedProductItem);
			}
		}
	}
	
	private void tryToRemoveInventory(Inventory selected) {
		this.observableProductItems.remove(selected);
		//TODO when getInventories is implemented
		this.selectedProduct.getInventories().remove(selected);
	}
	
	@FXML private void handleProductTable(MouseEvent mouseEvent) {
		if(mouseEvent.getButton() == MouseButton.PRIMARY) {
			Object selectedTableObject = productItemTable.getSelectionModel().selectedItemProperty().get();
			ProductItemTableDisplay selectedTableItem = (ProductItemTableDisplay)selectedTableObject;
			if (selectedTableItem != null) {
				Inventory selectedProductItem = selectedTableItem.getProdInventoryItem();
				this.selectedProductItem = selectedProductItem;
			}
		}
	}
	
	@FXML private void handleInventoryTable(MouseEvent mouseEvent) {
		if((mouseEvent.getButton() == MouseButton.PRIMARY) &&
				(mouseEvent.getClickCount() == 2)) {
			Object selectedTableObject = inventoryTable.getSelectionModel().selectedItemProperty().get();
			InventoryTableDisplay selectedTableItem = (InventoryTableDisplay)selectedTableObject;
			if (selectedTableItem != null) {
				Inventory selectedInventoryItem = selectedTableItem.getInventoryItem();
				ProductItemTableDisplay newProductTableItem = new ProductItemTableDisplay(selectedInventoryItem);
				this.selectedProduct.getInventories().add(selectedInventoryItem);
				this.observableProductItems.add(newProductTableItem);
				this.selectedProduct.setTotalCost(this.selectedProduct.calculateTotalCost());
			}
			//TODO
			//Need to create an instance of the active "Right Pane" in MasterController to access
			//and update the product in the quote
			//MasterController.getInstance().getActiveRightPane().update(Object);
			//need to figure how to implement this, how to check for pane type
			//possibly implement .update() in MasterController that checks the type of pane and
			//Object being passed and call a more specified method built in the Active right pane controller
		}
	}
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		prodItemIDCol.setCellValueFactory(new PropertyValueFactory<ProductItemTableDisplay, String>("prodItemID"));
		prodItemCategoryCol.setCellValueFactory(new PropertyValueFactory<ProductItemTableDisplay, String>("prodItemCategory"));
		prodItemManufacturerCol.setCellValueFactory(new PropertyValueFactory<ProductItemTableDisplay, String>("prodItemManufacturer"));
		prodItemManufacturerNumCol.setCellValueFactory(new PropertyValueFactory<ProductItemTableDisplay, String>("prodItemManufacturerNum"));
		prodItemUoMCol.setCellValueFactory(new PropertyValueFactory<ProductItemTableDisplay, String>("prodItemUoM"));
		prodItemSizeCol.setCellValueFactory(new PropertyValueFactory<ProductItemTableDisplay, String>("prodItemSize"));
		prodItemColorCol.setCellValueFactory(new PropertyValueFactory<ProductItemTableDisplay, String>("prodItemColor"));
		prodItemExtraInfoCol.setCellValueFactory(new PropertyValueFactory<ProductItemTableDisplay, String>("prodItemExtraInfo"));
		
		observableProductItems = FXCollections.observableArrayList();
		for(Inventory productItem : selectedProduct.getInventories()) {
			ProductItemTableDisplay prodItemDisplay = new ProductItemTableDisplay(productItem);
			prodItemDisplay.setInventoryItem(productItem);
			observableProductItems.add(prodItemDisplay);
		}
		
		productItemTable.setItems(observableProductItems);
		
		
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
		
		observableInventory = FXCollections.observableArrayList();
		for(Inventory item : inventory) {
			InventoryTableDisplay itemDisplay = new InventoryTableDisplay(item);
			itemDisplay.setInventoryItem(item);
			observableInventory.add(itemDisplay);
		}
		
		inventoryTable.setItems(observableInventory);
	}

}
