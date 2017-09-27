package inventory;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import application.MasterController;
import application.PageTypes;
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
	
	@FXML private Label unitOfMeasure;
	
	@FXML private Label actualCost;
	
	@FXML private Label sellingPrice;
	
	@FXML private Label taxable;
	
	@FXML private Label accountingCode;
	
	@FXML private Label quantity;
	
	@FXML private Label minQuantity;
	
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
			MasterController.getInstance().changeView(PageTypes.INVENTORY_EDIT_PAGE);
			
		} else if(source == deleteButton) {
			MasterController.getInstance().getInventoryGateway().deleteInventory(this.inventory.getId());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(this.inventory.getId() > 0) {
			this.dbID.setText(Integer.toString(this.inventory.getId()));
			this.itemNumber.setText(this.inventory.getItemNo());
			this.manufacturer.setText(this.inventory.getManufacturer());
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
}
