package quoteproduct;

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
import order.Order;

public class QOrderListController implements Initializable {
	
	@FXML private ListView<Order> orderListView;
	
	@FXML private Button addButton;
	
	private ObservableList<Order> observableList;
	
	private List<Order> orders;
	
	public QOrderListController(List<Order> orders) {
		this.orders = orders;
	}
	
	@FXML private void handleOrder(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == addButton) {
			MasterController.getInstance().setEditObject(new Order());
			MasterViewController.getInstance().changeView(PageTypes.QUOTE_EDIT_PAGE);
		}
	}
	
	@FXML private void handleOrderList(MouseEvent mouseEvent) {
		if((mouseEvent.getButton() == MouseButton.PRIMARY) && (mouseEvent.getClickCount() == 2)) {
			Order selected = orderListView.getSelectionModel().getSelectedItem();
			MasterController.getInstance().setEditObject(selected);
			MasterViewController.getInstance().changeView(PageTypes.QUOTE_DETAIL_PAGE);
		}
	}
	
	public void initialize(URL loc, ResourceBundle rsc) {
		this.observableList = this.orderListView.getItems();
		for(Order order : this.orders) {
			this.observableList.add(order);
		}
	}
}
