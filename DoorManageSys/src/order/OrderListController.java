package order;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.MasterController;
import application.MasterViewController;
import applicationhelper.PageTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class OrderListController implements Initializable{
	
	@FXML private TableView<OrderTableDisplay> orderTable;
	@FXML private TableColumn<OrderTableDisplay, String> orderIDCol;
	@FXML private TableColumn<OrderTableDisplay, String> orderCustomerNameCol;
	@FXML private TableColumn<OrderTableDisplay, String> orderPurchaseNumCol;
	@FXML private TableColumn<OrderTableDisplay, String> orderDateCol;
	@FXML private TableColumn<OrderTableDisplay, String> orderTargetShippingCol;
	@FXML private TableColumn<OrderTableDisplay, String> orderProductCodeCol;
	@FXML private TableColumn<OrderTableDisplay, String> orderStatusCol;
	@FXML private TableColumn<OrderTableDisplay, String> orderTotalCol;
	
	private ObservableList<OrderTableDisplay> observableList;
	
	private List<Order> orders;
	
	@FXML private Button addButton;
	
	public OrderListController(List<Order> orders){
		this.orders = orders;
	}
	
	@FXML private void handleOrder(ActionEvent ae){
		Object source = ae.getSource();
		if(source == addButton){
			MasterController.getInstance().setEditObject(new Order());
			MasterViewController.getInstance().changeView(PageTypes.ORDER_EDIT_PAGE);
		}
	}
	
	@FXML private void handleOrderTable(MouseEvent mouseEvent){
		if((mouseEvent.getButton() == MouseButton.PRIMARY) &&
				(mouseEvent.getClickCount() == 2)) {
			Object selectedObject = orderTable.getSelectionModel().selectedItemProperty().get();
			OrderTableDisplay selectedOrder = (OrderTableDisplay)selectedObject;
			if (selectedOrder != null) {
				MasterController.getInstance().setEditObject(selectedOrder.getOrder());
				MasterViewController.getInstance().changeView(PageTypes.ORDER_DETAIL_PAGE);
			}
		}
	}
	
	public void initialize(URL loc, ResourceBundle rsc){
		MasterController.getInstance().getRestriction().applyChangeOrderRestriction(this.addButton);
		
		orderIDCol.setCellValueFactory(new PropertyValueFactory<OrderTableDisplay, String>("orderID"));
		orderCustomerNameCol.setCellValueFactory(new PropertyValueFactory<OrderTableDisplay, String>("orderCustomerName"));
		orderPurchaseNumCol.setCellValueFactory(new PropertyValueFactory<OrderTableDisplay, String>("orderPurchaseNum"));
		orderDateCol.setCellValueFactory(new PropertyValueFactory<OrderTableDisplay, String>("orderDate"));
		orderTargetShippingCol.setCellValueFactory(new PropertyValueFactory<OrderTableDisplay, String>("orderTargetShipping"));
		orderProductCodeCol.setCellValueFactory(new PropertyValueFactory<OrderTableDisplay, String>("orderProductCode"));
		orderStatusCol.setCellValueFactory(new PropertyValueFactory<OrderTableDisplay, String>("orderStatus"));
		orderTotalCol.setCellValueFactory(new PropertyValueFactory<OrderTableDisplay, String>("orderTotal"));
		
		observableList = FXCollections.observableArrayList();
		for(Order order : orders){
			OrderTableDisplay orderDisplay = new OrderTableDisplay(order);
			orderDisplay.setOrder(order);
			observableList.add(orderDisplay);
		}
		
		orderTable.setItems(observableList);
	}
}
