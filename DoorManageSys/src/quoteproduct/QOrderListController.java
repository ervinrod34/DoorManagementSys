package quoteproduct;

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
import order.Order;

public class QOrderListController implements Initializable {
	
	@FXML private TableView<QuoteTableDisplay> quoteTable;
	@FXML private TableColumn<QuoteTableDisplay, String> quotePurchaseNumCol;
	@FXML private TableColumn<QuoteTableDisplay, String> quoteCustomerNameCol;
	@FXML private TableColumn<QuoteTableDisplay, String> quoteStatusCol;
	@FXML private TableColumn<QuoteTableDisplay, String> quoteDateCol;
	@FXML private TableColumn<QuoteTableDisplay, String> quoteTotalCol;
	
	private ObservableList<QuoteTableDisplay> observableList;
	
	private List<Order> orders;
	
	@FXML private Button addButton;
	
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
	
	@FXML private void handleOrderTable(MouseEvent mouseEvent) {
		if((mouseEvent.getButton() == MouseButton.PRIMARY) &&
				(mouseEvent.getClickCount() == 2)) {
			Object selectedObject = quoteTable.getSelectionModel().selectedItemProperty().get();
			QuoteTableDisplay selectedQuote = (QuoteTableDisplay)selectedObject;
			if (selectedQuote != null) {
				MasterController.getInstance().setEditObject(selectedQuote.getOrder());
				MasterViewController.getInstance().changeView(PageTypes.QUOTE_DETAIL_PAGE);
			}
		}
	}
	
	public void initialize(URL loc, ResourceBundle rsc) {
		MasterController.getInstance().getRestriction().applyChangeOrderRestriction(this.addButton);
		
		quotePurchaseNumCol.setCellValueFactory(new PropertyValueFactory<QuoteTableDisplay, String>("quotePurchaseNum"));
		quoteCustomerNameCol.setCellValueFactory(new PropertyValueFactory<QuoteTableDisplay, String>("quoteCustomerName"));
		quoteStatusCol.setCellValueFactory(new PropertyValueFactory<QuoteTableDisplay, String>("quoteStatus"));
		quoteDateCol.setCellValueFactory(new PropertyValueFactory<QuoteTableDisplay, String>("quoteDate"));
		quoteTotalCol.setCellValueFactory(new PropertyValueFactory<QuoteTableDisplay, String>("quoteTotal"));
		
		observableList = FXCollections.observableArrayList();
		for(Order order : orders) {
			QuoteTableDisplay quoteDisplay = new QuoteTableDisplay(order);
			quoteDisplay.setOrder(order);
			observableList.add(quoteDisplay);
		}
		
		quoteTable.setItems(observableList);
	}
}
