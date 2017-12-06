package inventory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.MasterController;
import application.MasterViewController;
import applicationhelper.PageTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class InventoryListController implements Initializable {

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
	
	private List<Inventory> inventory;
	
	@FXML private Button addButton;
	
	public InventoryListController(List<Inventory> inventory) {
		this.inventory = inventory;
	}
	
	@FXML private void handleInventory(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == addButton) {
			MasterController.getInstance().setEditObject(new Inventory());
			MasterViewController.getInstance().changeView(PageTypes.INVENTORY_EDIT_PAGE);
		}
	}
	
	@FXML private void handleInventoryTable(MouseEvent mouseEvent) {
		if((mouseEvent.getButton() == MouseButton.PRIMARY) &&
				(mouseEvent.getClickCount() == 2)) {
			Object selectedObject = inventoryTable.getSelectionModel().selectedItemProperty().get();
			InventoryTableDisplay selectedItem = (InventoryTableDisplay)selectedObject;
			if (selectedItem != null) {
				MasterController.getInstance().setEditObject(selectedItem.getInventoryItem());
				MasterViewController.getInstance().changeView(PageTypes.INVENTORY_DETAIL_PAGE);
			}
		}
	}
	
	public void initialize(URL loc, ResourceBundle rsc) {
		MasterController.getInstance().getRestriction().applyChangeInventoryRestriction(this.addButton);
		
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
		for(Inventory item : inventory) {
			InventoryTableDisplay itemDisplay = new InventoryTableDisplay(item);
			itemDisplay.setInventoryItem(item);
			observableList.add(itemDisplay);
		}
		
		inventoryTable.setItems(observableList);
	}
}
