package quoteproduct;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.MasterController;
import application.PageTypes;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import order.Order;

public class QuoteDetailController implements Initializable {
	
	@FXML private Label dbID;
	
	@FXML private Label quoteNumber;
	
	@FXML private Label quoteStatus;
	
	@FXML private Label purchaseOrderNumber;
	
	@FXML private Label customerName;
	
	@FXML private Label orderDate;
	
	@FXML private Label totalPrice;
	
	@FXML private Button editButton;
	
	@FXML private Button deleteButton;
	
	@FXML private ListView<String> productsList;
	
	private ObservableList<String> observableList;
	
	private Quote quote;
	
	private Order order;
	
	public QuoteDetailController(Order editObj) {
		this.order = editObj;
		this.quote = editObj.getQuote();
	}
	
	@FXML private void handleQuoteDetail(ActionEvent ae) {
		Object source = ae.getSource();
		
		if (source == editButton) {
			MasterController.getInstance().setEditObject(order);
			MasterController.getInstance().changeView(PageTypes.QUOTE_EDIT_PAGE);
		} else if (source == deleteButton) {
			MasterController.getInstance().getQuoteGateway().deleteQuoteRecord(quote.getId());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (quote.getId() > 0) {
			dbID.setText(Integer.toString(quote.getId()));
			quoteNumber.setText(Integer.toString(order.getQuote().getId()));
			quoteStatus.setText(order.getStatus());
			purchaseOrderNumber.setText(order.getCustomerPurchaseOrderNumber());
			customerName.setText(order.getCustomerName());
			orderDate.setText(order.getDateOrdered().toString());
			totalPrice.setText(Double.toString(quote.getTotalCost()));
			
			populateProductsList();
		}
	}
	
	private void populateProductsList() {
		List<Product> products = quote.getProducts();
		
		observableList = productsList.getItems();
		for (Product product : products) {
			observableList.add(product.toString());
		}
	}

}
