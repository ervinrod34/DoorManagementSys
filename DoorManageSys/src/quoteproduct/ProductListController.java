package quoteproduct;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.*;
import applicationhelper.PageTypes;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ProductListController implements Initializable {

	@FXML private ListView<Product> productListView;
	
	private ObservableList<Product> observableList;
	
	private List<Product> products;
	
	@FXML private Button addButton;
	
	public ProductListController(List<Product> products) {
		this.products = products;
	}
	
	@FXML private void handleProduct(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == addButton) {
			MasterController.getInstance().setEditObject(new Product());
			MasterViewController.getInstance().changeView(PageTypes.PRODUCT_EDIT_PAGE);
		}
	}
	
	@FXML private void handleProductList(MouseEvent mouseEvent) {
		if((mouseEvent.getButton() == MouseButton.PRIMARY) && (mouseEvent.getClickCount() == 2)) {
			Product selected = productListView.getSelectionModel().getSelectedItem();
			MasterController.getInstance().setEditObject(selected);
			MasterViewController.getInstance().changeView(PageTypes.PRODUCT_DETAIL_PAGE);
		}
	}
	
	public void initialize(URL loc, ResourceBundle rsc) {
		this.observableList = this.productListView.getItems();
		for(Product product : this.products) {
			this.observableList.add(product);
		}
	}
}
