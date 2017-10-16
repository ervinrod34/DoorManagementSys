package order;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class OrderEditController implements Initializable {

	@FXML private TextField quoteNumberField;
	
	@FXML private TextField blueprintNumberField;
	
	@FXML private TextField customerPurchaseOrderNumberField;
	
	@FXML private TextField customerNameField;
	
	@FXML private TextField productCodeField;
	
	@FXML private TextField statusField;
	
	@FXML private DatePicker dateOrderedPicker;
	
	@FXML private DatePicker targetShippingPicker;
	
	@FXML private DatePicker actualShippingPicker;
	
	@FXML private TextField totalAmountField;
	
	@FXML private Button saveButton;
	
	@FXML private Button cancelButton;
	
	private Order order;
	
	public OrderEditController(Order order) {
		this.order = order;
	}
	
	@FXML public void handleOrderEdit(ActionEvent ae) {
		Object source = ae.getSource();
		
		if(source == cancelButton) {
			MasterController.getInstance().setEditObject(this.order);
			MasterController.getInstance().changeView(PageTypes.ORDER_EDIT_PAGE);
		} else if(source == saveButton) {
			this.updateOrderObject();
			this.order.save();
			
			//After saving, change the screen into detail of the updated inventory
			MasterController.getInstance().changeView(PageTypes.ORDER_LIST_PAGE);
			
			MasterController.getInstance().setEditObject(this.order);
			MasterController.getInstance().changeView(PageTypes.ORDER_DETAIL_PAGE);
		}
	}
	
	public void initialize(URL loc, ResourceBundle rsc) {
		if(this.order.getId() > 0) {
			this.quoteNumberField.setText(Integer.toString(this.order.getQuoteId()));
			this.blueprintNumberField.setText(Integer.toString(this.order.getBlueprintId()));
			this.customerPurchaseOrderNumberField.setText(this.order.getCustomerPurchaseOrderNumber());
			this.customerNameField.setText(this.order.getCustomerName());
			this.productCodeField.setText(this.order.getProductCode());
			this.statusField.setText(this.order.getStatus());
			this.dateOrderedPicker.setValue(this.order.getDateOrdered());
			this.targetShippingPicker.setValue(this.order.getTargetShipping());
			this.actualShippingPicker.setValue(this.order.getActualShipping());
			this.totalAmountField.setText(Double.toString(this.order.getTotalAmount()));
			
		}
	}
	
	public void updateOrderObject() {
		Order updatedOrder = new Order(this.order.getId(),
									 Integer.parseInt(this.quoteNumberField.getText()),
									 Integer.parseInt(this.blueprintNumberField.getText()),
									 this.customerPurchaseOrderNumberField.getText(),
									 this.customerNameField.getText(),
									 this.productCodeField.getText(),
									 this.statusField.getText(),
									 this.dateOrderedPicker.getValue(),
									 this.targetShippingPicker.getValue(),
									 this.actualShippingPicker.getValue(),
									 Double.parseDouble(this.totalAmountField.getText()),
		this.order = updatedOrder;
	}
}

