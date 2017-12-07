package order;

import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import application.*;
import applicationdialogs.InfoDialogs;
import applicationhelper.PageTypes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import quoteproduct.*;
import blueprint.*;

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
	
	@FXML private TextField actualCostField;
	
	@FXML private Button saveButton;
	
	@FXML private Button cancelButton;
	
	private Order order;
	
	public OrderEditController(Order order) {
		this.order = order;
		System.out.println("Total amount " + this.order.getTotalAmount());
	}
	
	@FXML public void handleOrderEdit(ActionEvent ae) {
		Object source = ae.getSource();
		
		if(source == cancelButton) {
			MasterController.getInstance().setEditObject(this.order);
			MasterViewController.getInstance().changeView(PageTypes.ORDER_DETAIL_PAGE);
		} else if(source == saveButton) {
			InfoDialogs dug = new InfoDialogs();
			int d = dug.displaySaveConfirmation();
			if(d == 1) {
				this.updateOrderObject();
				this.order.save();
				
				//After saving, change the screen into detail of the updated inventory
				MasterViewController.getInstance().changeView(PageTypes.ORDER_LIST_PAGE);
				
				MasterController.getInstance().setEditObject(this.order);
				MasterViewController.getInstance().changeView(PageTypes.ORDER_DETAIL_PAGE);
			}
		}
	}
	
	public void initialize(URL loc, ResourceBundle rsc) {
		if(this.order.getId() > 0) {
			this.quoteNumberField.setText(Integer.toString(this.order.getQuote().getId()));
			this.blueprintNumberField.setText(Integer.toString(this.order.getBlueprint().getId()));
			this.customerPurchaseOrderNumberField.setText(this.order.getCustomerPurchaseOrderNumber());
			this.customerNameField.setText(this.order.getCustomerName());
			this.productCodeField.setText(this.order.getProductCode());
			this.statusField.setText(this.order.getStatus());
			this.dateOrderedPicker.setValue(this.convertDateToLocalDate(this.order.getDateOrdered()));
			this.targetShippingPicker.setValue(this.convertDateToLocalDate(this.order.getTargetShipping()));
			this.actualShippingPicker.setValue(this.convertDateToLocalDate(this.order.getActualShipping()));
			System.out.println("Total Amount " + this.order.getTotalAmount());
			this.actualCostField.setText(this.formatAmount(this.order.getTotalAmount()));
		}
	}
	
	private String formatAmount(double amountToFormat) {
		String formattedAmount = "";
		DecimalFormat amountFormat = new DecimalFormat(".##");

		amountFormat.setRoundingMode(RoundingMode.UP);
		formattedAmount = amountFormat.format(amountToFormat);
		System.out.print("Formatted amount " + formattedAmount);
		return formattedAmount;
		
	}
	
	public void updateOrderObject() {
		Order updatedOrder = new Order(this.order.getId(),
								new Quote(),
								this.customerPurchaseOrderNumberField.getText(),
								this.customerNameField.getText(),
								this.productCodeField.getText(),
								this.statusField.getText(),
								java.sql.Date.valueOf(this.dateOrderedPicker.getValue()),
								java.sql.Date.valueOf(this.targetShippingPicker.getValue()),
								java.sql.Date.valueOf(this.actualShippingPicker.getValue()),
								new Blueprint(0),
								Double.parseDouble(this.actualCostField.getText()));
		
		this.order = updatedOrder;
	}
	
	public LocalDate convertDateToLocalDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date utilDate = null;
		try {
			utilDate = dateFormat.parse(date.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		LocalDate localDate = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		return localDate;
	}
}

