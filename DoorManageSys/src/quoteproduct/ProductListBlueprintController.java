package quoteproduct;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.*;
import applicationdialogs.InfoDialogs;
import applicationhelper.PageTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ProductListBlueprintController implements Initializable {

	@FXML private TableView<ProductTableDisplay> productTable;
	@FXML private TableColumn<ProductTableDisplay, String> productIDCol;
	@FXML private TableColumn<ProductTableDisplay, String> productTotalCol;
	@FXML private TableColumn<ProductTableDisplay, String> productItemsCol;
	
	private ObservableList<ProductTableDisplay> observableList;
	
	private List<Product> products;
	
	public ProductListBlueprintController(List<Product> products) {
		this.products = products;
	}
	
	@FXML private void handleProduct(ActionEvent action) {
		
	}
	
	@FXML private void handleProductTable(MouseEvent mouseEvent) {
		if((mouseEvent.getButton() == MouseButton.PRIMARY) &&
				(mouseEvent.getClickCount() == 2)) {
			Object selectedObject = productTable.getSelectionModel().selectedItemProperty().get();
			ProductTableDisplay selectedTableProduct = (ProductTableDisplay)selectedObject;
			if (selectedTableProduct != null) {
				Product selectedEditProduct = selectedTableProduct.getProduct();
				InfoDialogs dialog = new InfoDialogs();
				dialog.setAlertType(AlertType.INFORMATION);
				
				if(MasterController.getInstance().getBlueprintGateway().checkIfBlueprintIsAssignedToProduct(selectedEditProduct)) {
					MasterController.getInstance().setSelectedProductForBlueprint(selectedTableProduct.getProduct());
					dialog.displayInformationDialog("Product selected.");
				} else {
					dialog.displayInformationDialog("This product currently has no blueprint.");
				}
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
