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
			MasterController.getInstance().changeView(PageTypes.INVENTORY_EDIT_PAGE);
		} else if(source == saveButton) {
			this.updateInventoryObject();
			this.inventory.save();
			
			//After saving, change the screen into detail of the updated inventory
			MasterController.getInstance().changeView(PageTypes.INVENTORY_LIST_PAGE);
			
			MasterController.getInstance().setEditObject(this.inventory);
			MasterController.getInstance().changeView(PageTypes.INVENTORY_DETAIL_PAGE);
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
		}
	}
	
	public void updateInventoryObject() {
		Inventory updatedInventory = new Inventory(this.inventory.getId(),
									 this.itemNumberField.getText(),
									 this.manufacturerField.getText(),
									 this.partNumberField.getText(),
									 this.vendorsField.getText(),
									 this.sizesField.getText(),
									 this.colorCodeField.getText(),
									 this.otherInfoField.getText(),
									 this.unitOfMeasureField.getText(),
									 Double.parseDouble(this.actualCostField.getText()),
									 Double.parseDouble(this.sellingPriceField.getText()),
									 Integer.parseInt(this.quantityField.getText()),
									 Integer.parseInt(this.minQuantityField.getText()),
									 Integer.parseInt(this.maxQuantityField.getText()),
									 "default Category",
									 this.taxable.isSelected(),
									 this.accountingCodeField.getText());
		
		this.inventory = updatedInventory;
	}
}
