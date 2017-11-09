package quoteproduct;

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

public class ProductEditController implements Initializable {

	@FXML private TextField productNumberField;
	
	@FXML private TextField totalCostField;
	
	@FXML private TextField inventoryItemsField;

	@FXML private Button saveButton;
	
	@FXML private Button cancelButton;
	
	private Product product;
	
	public ProductEditController(Product product) {
		this.product = product;
	}
	
	@FXML public void handleProductEdit(ActionEvent ae) {
		Object source = ae.getSource();
		
		if(source == cancelButton) {
			MasterController.getInstance().setEditObject(this.product);
			MasterViewController.getInstance().changeView(PageTypes.PRODUCT_DETAIL_PAGE);
			
		} else if(source == saveButton) {
			this.updateProductObject();
			this.product.save();
			
			//After saving, change the screen into detail of the updated inventory
			MasterViewController.getInstance().changeView(PageTypes.PRODUCT_LIST_PAGE);
			
			MasterController.getInstance().setEditObject(this.product);
			MasterViewController.getInstance().changeView(PageTypes.PRODUCT_DETAIL_PAGE);
		}
	}
	
	public void initialize(URL loc, ResourceBundle rsc) {
		if(this.product.getId() > 0) {
			this.productNumberField.setText(Integer.toString(this.product.getId()));
			this.totalCostField.setText(Double.toString(this.product.getTotalCost()));
			//this.inventoryItemsField.setText(this.product.getInventories());
		}
	}
	
	public void updateProductObject() {
		Product updatedProduct = new Product();
		
		updatedProduct.setId(this.product.getId());
		
		/*if(this.inventoryItemsField.getText().length() == 0) {
			updatedProduct.setInventories("");
		} else {
			updatedProduct.setInventories(this.inventoryItemsField.getText());
		}*/
		
		if(this.totalCostField.getText().length() == 0) {
			updatedProduct.setTotalCost(0.0);
		} else {
			updatedProduct.setTotalCost(Double.parseDouble(this.totalCostField.getText()));
		}
		
		this.product = updatedProduct;
	}
	
}
