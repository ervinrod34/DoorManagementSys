package order;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

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
import quoteproduct.Product;

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
			MasterController.getInstance().getProductGateway().deleteProducts(this.order.getQuote().getProducts());
			MasterController.getInstance().getQuoteGateway().deleteQuoteRecord(this.order.getQuote().getId());
			MasterController.getInstance().getOrderGateway().deleteOrder(this.order.getId());
			
			//Go back to this later
			this.scheduleRefresh();
		}
	}
	
	private void scheduleRefresh() {
		Timer timer = new Timer();
					
			timer.scheduleAtFixedRate(new TimerTask() {
				public void run() {
					List<Order> allOrders = MasterController.getInstance().getOrderGateway().getOrders();
					MasterController.getInstance().setOrderListToDisplay(allOrders);
					MasterController.getInstance().changeView(PageTypes.ORDER_LIST_PAGE);
					timer.cancel();
				}
			}, 1000, 1000);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(this.order.getId() > 0) {
			this.orderNumber.setText(Integer.toString(this.order.getId()));
			this.quoteNumber.setText(Integer.toString(this.order.getQuote().getId()));
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

