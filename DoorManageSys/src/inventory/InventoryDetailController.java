package inventory;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import application.*;
import applicationhelper.PageTypes;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class InventoryDetailController implements Initializable {

	@FXML private Label dbID;
	@FXML private Label itemNumber;
	@FXML private Label manufacturer;
	@FXML private Label manufacturerID;
	@FXML private Label sizes;
	@FXML private Label colorCode;
	@FXML private Label otherInformation;
	@FXML private Label category;
	@FXML private Label unitOfMeasure;
	@FXML private Label actualCost;
	@FXML private Label sellingPrice;
	@FXML private Label taxable;
	@FXML private Label accountingCode;
	@FXML private Label quantity;
	@FXML private Label minQuantity;
	@FXML private Label maxQuantity;
	@FXML private Button editButton;
	@FXML private Button deleteButton;
	@FXML private ListView<String> vendorsList;
	
	private ObservableList<String> observableList;
	private Inventory inventory;
	
	public InventoryDetailController(Inventory inventory) {
		this.inventory = inventory;
	}
	
	@FXML private void handleInventoryDetail(ActionEvent ae) {
		Object source = ae.getSource();
		
		if(source == editButton) {
			MasterController.getInstance().setEditObject(this.inventory);
			MasterViewController.getInstance().changeView(PageTypes.INVENTORY_EDIT_PAGE);
			
		} else if(source == deleteButton) {
			MasterController.getInstance().getInventoryGateway().deleteInventory(this.inventory.getId());
			
			this.scheduleRefresh();
		}
	}
	
	private void scheduleRefresh() {
		Timer timer = new Timer();
					
			timer.schedule(new TimerTask() {
				public void run() {
					List<Inventory> allInventory = MasterController.getInstance().getInventoryGateway().getInventory();
					MasterController.getInstance().setInventoryListToDisplay(allInventory);
					MasterViewController.getInstance().changeView(PageTypes.INVENTORY_LIST_PAGE);
				}
			}, 2000);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		MasterController.getInstance().getRestriction().applyChangeInventoryRestriction(this.editButton);
		MasterController.getInstance().getRestriction().applyDeleteRestriction(this.deleteButton);
		
		if(this.inventory.getId() > 0) {
			this.dbID.setText(Integer.toString(this.inventory.getId()));
			this.itemNumber.setText(this.inventory.getItemNo());
			this.manufacturer.setText(this.inventory.getManufacturer());
			this.manufacturerID.setText(this.inventory.getManufacturerNo());
			this.sizes.setText(this.inventory.getSize());
			this.colorCode.setText(this.inventory.getColorCode());
			this.otherInformation.setText(this.inventory.getExtra());
			this.unitOfMeasure.setText(this.inventory.getUnitOfMeasure());
			this.category.setText(this.inventory.getCategory());
			this.actualCost.setText(Double.toString(this.inventory.getActualCost()));
			this.sellingPrice.setText(Double.toString(this.inventory.getSellingPrice()));
			this.displayTaxable();
			this.accountingCode.setText(this.inventory.getAccountingCode());
			this.quantity.setText(Integer.toString(this.inventory.getQuantity()));
			this.minQuantity.setText(Integer.toString(this.inventory.getMinQuantity()));
			this.maxQuantity.setText(Integer.toString(this.inventory.getMaxQuantity()));
			
			this.populateVendorList();
		}
	}
	
	private void populateVendorList() {
		List<String> vendors = Arrays.asList(inventory.getVendor().split(","));
		
		this.observableList = this.vendorsList.getItems();
		for(String vendor : vendors) {
			this.observableList.add(vendor);
		}
	}
	
	private void displayTaxable() {
		if(this.inventory.isTaxable()) {
			this.taxable.setText("Y");
		} else {
			this.taxable.setText("N");
		}
	}
}
