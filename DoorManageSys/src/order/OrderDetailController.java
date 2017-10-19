package order;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import application.MasterController;
import application.PageTypes;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class OrderDetailController implements Initializable {

	@FXML private Label orderNumber;
	
	@FXML private Label quoteNumber;
	
	@FXML private Label blueprintNumber;
	
	@FXML private Label customerPurchaseOrderNumber;
	
	@FXML private Label customerName;
	
	@FXML private Label productCode;
	
	@FXML private Label status;
	
	@FXML private DatePicker dateOrdered;
	
	@FXML private DatePicker targetShipping;
	
	@FXML private DatePicker actualShipping;
	
	@FXML private Label actualCost;
	
	@FXML private Button editButton;
	
	@FXML private Button deleteButton;
	
	private Order order;
	
	public OrderDetailController(Order order) {
		this.order = order;
	}
	
	@FXML private void handleOrderDetail(ActionEvent ae) {
		Object source = ae.getSource();
		
		if(source == editButton) {
			MasterController.getInstance().setEditObject(this.order);
			MasterController.getInstance().changeView(PageTypes.ORDER_EDIT_PAGE);
			
		} else if(source == deleteButton) {
			MasterController.getInstance().getInventoryGateway().deleteInventory(this.order.getId());
			
			//Go back to this later
			MasterController.getInstance().changeView(PageTypes.ORDER_LIST_PAGE);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(this.order.getId() > 0) {
			this.orderNumber.setText(Integer.toString(this.order.getId()));
			this.quoteNumber.setText(this.order.getQuote().toString());
			this.blueprintNumber.setText(this.order.getBlueprint().toString());
			this.customerPurchaseOrderNumber.setText(this.order.getCustomerPurchaseOrderNumber());
			this.customerName.setText(this.order.getCustomerName());
			this.productCode.setText(this.order.getProductCode());
			this.status.setText(this.order.getStatus());
			
			this.dateOrdered.setValue(this.convertDateToLocalDate(this.order.getDateOrdered()));
			this.targetShipping.setValue(this.convertDateToLocalDate(this.order.getTargetShipping()));
			this.actualShipping.setValue(this.convertDateToLocalDate(this.order.getActualShipping()));
			
			this.actualCost.setText(Double.toString(this.order.getTotalAmount()));
			
		}
	}
	
	public LocalDate convertDateToLocalDate(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		return localDate;
	}
}

