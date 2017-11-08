package inventory;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import application.*;
import applicationhelper.PageTypes;
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
	
	@FXML private TextField vendorsField;

	@FXML private TextField quantityField;
	
	@FXML private TextField minQuantityField;
	
	@FXML private TextField maxQuantityField;
	
	@FXML private TextField categoryField;
	
	@FXML private Button saveButton;
	
	@FXML private Button cancelButton;
	
	@FXML private CheckBox taxable;
	
	private Inventory inventory;
	
	public InventoryEditController(Inventory inventory) {
		this.inventory = inventory;
	}
	
	@FXML public void handleInventoryEdit(ActionEvent ae) {
		Object source = ae.getSource();
		
		if(source == cancelButton) {
			MasterController.getInstance().setEditObject(this.inventory);
			MasterViewController.getInstance().changeView(PageTypes.INVENTORY_DETAIL_PAGE);
			
		} else if(source == saveButton) {
			this.updateInventoryObject();
			this.inventory.save();
			
			//After saving, change the screen into detail of the updated inventory
			MasterViewController.getInstance().changeView(PageTypes.INVENTORY_LIST_PAGE);
			
			MasterController.getInstance().setEditObject(this.inventory);
			MasterViewController.getInstance().changeView(PageTypes.INVENTORY_DETAIL_PAGE);
		}
	}
	
	public void initialize(URL loc, ResourceBundle rsc) {
		if(this.inventory.getId() > 0) {
			this.itemNumberField.setText(this.inventory.getItemNo());
			this.manufacturerField.setText(this.inventory.getManufacturer());
			this.partNumberField.setText(this.inventory.getManufacturerNo());
			this.sizesField.setText(this.inventory.getSize());
			this.colorCodeField.setText(this.inventory.getColorCode());
			this.otherInfoField.setText(this.inventory.getExtra());
			this.unitOfMeasureField.setText(this.inventory.getUnitOfMeasure());
			this.actualCostField.setText(Double.toString(this.inventory.getActualCost()));
			this.sellingPriceField.setText(Double.toString(this.inventory.getSellingPrice()));
			this.accountingCodeField.setText(this.inventory.getAccountingCode());
			this.quantityField.setText(Integer.toString(this.inventory.getQuantity()));
			this.minQuantityField.setText(Integer.toString(this.inventory.getMinQuantity()));
			this.maxQuantityField.setText(Integer.toString(this.inventory.getMaxQuantity()));
			this.taxable.setSelected(this.inventory.isTaxable());
			this.vendorsField.setText(this.inventory.getVendor());
			this.categoryField.setText(this.inventory.getCategory());
		}
	}
	
	public void updateInventoryObject() {
		Inventory updatedInventory = new Inventory();
		
		updatedInventory.setId(this.inventory.getId());
		
		if(this.itemNumberField.getText().length() == 0) {
			updatedInventory.setItemNo("");
		} else {
			updatedInventory.setItemNo(this.itemNumberField.getText());
		}
		
		if(this.manufacturerField.getText().length() == 0) {
			updatedInventory.setManufacturer("");
		} else {
			updatedInventory.setManufacturer(this.manufacturerField.getText());
		}
		
		if(this.partNumberField.getText().length()== 0) {
			updatedInventory.setManufacturerNo("");
		} else {
			updatedInventory.setManufacturerNo(this.partNumberField.getText());
		}

		if(this.vendorsField.getText().length() == 0) {
			updatedInventory.setVendor("");
		} else {
			updatedInventory.setVendor(this.vendorsField.getText());
		}
		
		if(this.sizesField.getText().length() == 0) {
			updatedInventory.setSize("");
		} else {
			updatedInventory.setSize(this.sizesField.getText());
		}

		if(this.colorCodeField.getText().length() == 0) {
			updatedInventory.setColorCode("");
		} else {
			updatedInventory.setColorCode(this.colorCodeField.getText());
		}
		
		if(this.otherInfoField.getText().length() == 0) {
			updatedInventory.setExtra("");
		} else {
			updatedInventory.setExtra(this.otherInfoField.getText());
		}
		
		if(this.unitOfMeasureField.getText().length() == 0) {
			updatedInventory.setUnitOfMeasure("");
		} else {
			updatedInventory.setUnitOfMeasure(this.unitOfMeasureField.getText());
		}
		
		if(this.actualCostField.getText().length() == 0) {
			updatedInventory.setActualCost(0.0);
		} else {
			updatedInventory.setActualCost(Double.parseDouble(this.actualCostField.getText()));
		}
		
		if(this.sellingPriceField.getText().length() == 0) {
			updatedInventory.setSellingPrice(0.0);
		} else {
			updatedInventory.setSellingPrice(Double.parseDouble(this.sellingPriceField.getText()));
		}
									 
		if(this.quantityField.getText().length() == 0) {
			updatedInventory.setQuantity(0);
		} else {
			updatedInventory.setQuantity(Integer.parseInt(this.quantityField.getText()));
		}
		
		if(this.minQuantityField.getText().length() == 0) {
			updatedInventory.setMinQuantity(0);
		} else {
			updatedInventory.setMinQuantity(Integer.parseInt(this.minQuantityField.getText()));
		}
		
		if(this.maxQuantityField.getText().length() == 0) {
			updatedInventory.setMaxQuantity(0);
		} else {
			updatedInventory.setMaxQuantity(Integer.parseInt(this.maxQuantityField.getText()));
		}
		
		if(this.categoryField.getText().length() == 0) {
			updatedInventory.setCategory("");
		} else {
			updatedInventory.setCategory(this.categoryField.getText());
		}
		
		if(this.accountingCodeField.getText().length() == 0) {
			updatedInventory.setAccountingCode("");
		} else {
			updatedInventory.setAccountingCode(this.accountingCodeField.getText());
		}
		
		updatedInventory.setTaxable(this.taxable.isSelected());
		
		this.inventory = updatedInventory;
	}
	
}
