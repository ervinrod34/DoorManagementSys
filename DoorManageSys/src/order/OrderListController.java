package order;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class OrderListController implements Initializable{
	
	@FXML private ListView<Order> orderListView;
	
	private ObservableList<Order> observableList;
	
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
	
	@FXML private void handleOrderList(MouseEvent mouseEvent){
		if((mouseEvent.getButton() == MouseButton.PRIMARY) && (mouseEvent.getClickCount() == 2)) {
			Order selected = orderListView.getSelectionModel().getSelectedItem();
			MasterController.getInstance().setEditObject(selected);
			MasterViewController.getInstance().changeView(PageTypes.ORDER_DETAIL_PAGE);
		}
	}
	
	public void initialize(URL loc, ResourceBundle rsc){
		this.observableList = this.orderListView.getItems();
		for(Order order : this.orders){
			this.observableList.add(order);
		}
	}
}
