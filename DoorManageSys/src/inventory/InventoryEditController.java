package inventory;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class InventoryEditController implements Initializable {

	@FXML private TextField itemNumberField;
	
	@FXML private TextField manufacturerField;
	
	@FXML private TextField partNumberField;
	
	@FXML private TextField sizesField;
	
	@FXML private TextField colorCodeField;
	
	@FXML private TextArea otherInfoField;
	
	@FXML private TextField unitOfMeasureField;
	
	@FXML private TextField actualCostField;
	
	@FXML private TextField sellingPriceField;
	
	@FXML private TextField accountingCodeField;
	
	@FXML private TextField vendorField;
	
	@FXML private TextField quantityField;
	
	@FXML private TextField minQuantityField;
	
	@FXML private Button addVendorButton;
	
	@FXML private Button saveButton;
	
	@FXML private Button cancelButton;
	
	@FXML private CheckBox taxable;
	
	@FXML private ListView<String> vendorsList;
	
	private ObservableList<String> observableList;
	
	private Inventory inventory;
	
	public InventoryEditController(Inventory inventory) {
		this.inventory = inventory;
	}
	
	@FXML public void handleInventoryEdit(ActionEvent ae) {
		
	}
	
	public void initialize(URL loc, ResourceBundle rsc) {
		if(this.inventory.getId() > 0) {
			this.itemNumberField.setText(this.inventory.getItemNo());
			this.manufacturerField.setText(this.inventory.getManufacturer());
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
	
	private String listViewToCSV() {
		String csvVendors = "";
		for(String vendor : this.vendorsList.getItems()) {
			csvVendors += vendor + ",";
		}
		csvVendors = csvVendors.substring(0, csvVendors.length() - 1);
		
		return csvVendors;
	}
	
	private Inventory getTextFromFields() {
		Inventory updatedInventory = new Inventory();
		
		updatedInventory.setId(this.inventory.getId());
		
		return updatedInventory;
	}
}
