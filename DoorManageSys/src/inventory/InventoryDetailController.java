package inventory;

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

public class InventoryDetailController implements Initializable {

	@FXML private Label dbID;
	
	@FXML private Label itemNumber;
	
	@FXML private Label manufacturer;
	
	@FXML private Label manufacturerID;
	
	@FXML private Label sizes;
	
	@FXML private Label colorCode;
	
	@FXML private Label otherInformation;
	
	@FXML private Label unitOfMeasure;
	
	@FXML private Label actualCost;
	
	@FXML private Label sellingPrice;
	
	@FXML private Label taxable;
	
	@FXML private Label accoundtingCode;
	
	@FXML private Button editButton;
	
	@FXML private Button deleteButton;
	
	@FXML private ListView<String> vendorsList;
	
	private ObservableList<String> observableList;
	
	private List<String> vendors;
	
	private Inventory inventory;
	
	public InventoryDetailController(Inventory inventory) {
		this.inventory = inventory;
	}
	
	@FXML private void handleInventoryDetail(ActionEvent ae) {
		Object source = ae.getSource();
		
		if(source == editButton) {
			MasterController.getInstance().setEditObject(this.inventory);
			MasterController.getInstance().changeView(PageTypes.INVENTORY_EDIT_PAGE);
			
		} else if(source == deleteButton) {
			//MasterController.getInstance().getInventoryGateway().delete(this.inventory);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	private void populateVendorList() {
		this.observableList = this.vendorsList.getItems();
		//for(String vendor : this.inventory) {
		//	this.observableList.add(vendor);
		//}
	}
}
