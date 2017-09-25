package inventory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class InventoryListController implements Initializable {

	@FXML private ListView<Inventory> inventoryListView;
	
	
	private ObservableList<Inventory> observableList;
	
	private List<Inventory> inventories;
	
	public InventoryListController() {
		this.inventories = new ArrayList<Inventory>();
	}
	
	@FXML private void handleInventoryList() {
		
	}
	
	public void initialize(URL loc, ResourceBundle rsc) {
		this.observableList = this.inventoryListView.getItems();
		for(Inventory inventory : this.inventories) {
			this.observableList.add(inventory);
		}
	}
}
