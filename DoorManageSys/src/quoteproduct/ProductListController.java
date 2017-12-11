package quoteproduct;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.*;
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

public class ProductListController implements Initializable {

	@FXML private TableView<ProductTableDisplay> productTable;
	@FXML private TableColumn<ProductTableDisplay, String> productIDCol;
	@FXML private TableColumn<ProductTableDisplay, String> productTotalCol;
	@FXML private TableColumn<ProductTableDisplay, String> productItemsCol;
	
	private ObservableList<ProductTableDisplay> observableList;
	
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
	
	@FXML private void handleProductTable(MouseEvent mouseEvent) {
		if((mouseEvent.getButton() == MouseButton.PRIMARY) &&
				(mouseEvent.getClickCount() == 2)) {
			Object selectedObject = productTable.getSelectionModel().selectedItemProperty().get();
			ProductTableDisplay selectedTableProduct = (ProductTableDisplay)selectedObject;
			if (selectedTableProduct != null) {
				Product selectedEditProduct = selectedTableProduct.getProduct();
				MasterController.getInstance().setEditObject(selectedEditProduct);
				MasterViewController.getInstance().changeView(PageTypes.PRODUCT_DETAIL_PAGE);
			}
		}
	}
	
	public void initialize(URL loc, ResourceBundle rsc) {
		
		productIDCol.setCellValueFactory(new PropertyValueFactory<ProductTableDisplay, String>("productID"));
		productTotalCol.setCellValueFactory(new PropertyValueFactory<ProductTableDisplay, String>("productTotal"));
		productItemsCol.setCellValueFactory(new PropertyValueFactory<ProductTableDisplay, String>("productItems"));
		
		observableList = FXCollections.observableArrayList();
		for(Product product : products) {
			ProductTableDisplay productDisplay = new ProductTableDisplay(product);
			productDisplay.setProduct(product);
			observableList.add(productDisplay);
		}
		
		productTable.setItems(observableList);
	}
}
