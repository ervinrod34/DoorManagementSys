package inventory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.media.jfxmedia.logging.Logger;

import application.MasterController;
import application.PageTypes;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class InventoryListController implements Initializable {

	@FXML private ListView<Inventory> inventoryListView;
	
	private ObservableList<Inventory> observableList;
	
	private List<Inventory> inventories;
	
	@FXML private Button addButton;
	
	public InventoryListController(List<Inventory> inventories) {
		this.inventories = new ArrayList<Inventory>();
		
		//for testing - DELETE this later
		Inventory data1 = new Inventory();
		data1.setManufacturer("STANLEY");
		this.inventories.add(data1);
	}
	
	@FXML private void handleInventory(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == addButton) {
			MasterController.getInstance().setEditObject(new Inventory());
			MasterController.getInstance().changeView(PageTypes.INVENTORY_EDIT_PAGE);
		}
	}
	
	@FXML private void handleInventoryList(MouseEvent mouseEvent) {
		if((mouseEvent.getButton() == MouseButton.PRIMARY) && (mouseEvent.getClickCount() == 2)) {
			Inventory selected = inventoryListView.getSelectionModel().getSelectedItem();
			MasterController.getInstance().setEditObject(selected);
			MasterController.getInstance().changeView(PageTypes.INVENTORY_DETAIL_PAGE);
		}
	}
	
	public void initialize(URL loc, ResourceBundle rsc) {
		this.observableList = this.inventoryListView.getItems();
		for(Inventory inventory : this.inventories) {
			this.observableList.add(inventory);
		}
	}
}
