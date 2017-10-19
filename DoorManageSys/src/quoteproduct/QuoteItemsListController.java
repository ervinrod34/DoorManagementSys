package quoteproduct;



import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import inventory.*;

public class QuoteItemsListController implements Initializable {

	@FXML private ListView<Inventory> productContents;
	private ObservableList<Inventory> observableContents;
	private List<Inventory> contents;
	private Inventory selectedContent;
	
	@FXML private ListView<Inventory> searchResults;
	private ObservableList<Inventory> observableResults;
	private List<Inventory> results;
	
	@FXML private Button deleteItem;
	
	private Product selectedProduct;
	
	private List<Inventory> searchedItems;
	
	public QuoteItemsListController(Product product, List<Inventory> searched) {
		//TODO chech is product exist, user needs to select which product
		// user will be adding Inventory item to
		this.selectedProduct = product;
		this.searchedItems = searched;
	}
	
	@FXML private void handleDelete(ActionEvent action) {
		Object source = action.getSource();
		if(source == deleteItem) {
			this.tryToRemoveInventory(this.selectedContent);
		}
	}
	
	private void tryToRemoveInventory(Inventory selected) {
		this.observableContents.remove(selected);
		//TODO when getInventories is implemented
		//this.selectedProduct.getInventories().remove(selected);
	}
	
	@FXML private void handleProductContents(MouseEvent mouseEvent) {
		if(mouseEvent.getButton() == MouseButton.PRIMARY) {
			this.selectedContent = this.productContents.getSelectionModel().getSelectedItem();
		}
	}
	
	@FXML private void handleSearchResults(MouseEvent mouseEvent) {
		if((mouseEvent.getButton() == MouseButton.PRIMARY) && (mouseEvent.getClickCount() == 2)) {
			Inventory selectedSearchedItem = this.searchResults.getSelectionModel().getSelectedItem();
			
			//TODO
			//this.selectedProduct.getInventories().add(selectedSearchedItem);
			//this.observableContents.add(selectedSearchedItem);
			
			//Need to create an instance of the active "Right Pane" in MasterController to access
			//and update the product in the quote
			//MasterController.getInstance().getActiveRightPane().update(Object);
			//need to figure how to implement this, how to check for pane type
			//possibly implement .update() in MasterController that checks the type of pane and
			//Object being passed and call a more specified method built in the Active right pane controller
		}
	}
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.observableContents = this.productContents.getItems();
		for(Inventory content : this.selectedProduct.getInventories()) {
			this.observableContents.add(content);
		}
		
		
		this.observableResults = this.searchResults.getItems();
		for(Inventory search : this.searchedItems) {
			this.observableResults.add(search);
		}
	}

}
