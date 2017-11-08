package applicationdialogs;

import application.MasterController;
import javafx.scene.control.Alert;
import user.*;

public class InfoDialogs extends Alert {

	private String userType;
	
	public InfoDialogs() {
		super(AlertType.ERROR);
		this.userType = MasterController.getInstance().getUser().getUserType().getType();
	}
	
	public void displayPageRestrictionError() {
		this.setHeaderText("Access Denied");
		this.setContentText(userType + " does not have permission to access this page.");
		
		this.showAndWait();
	}
	
	public void displayFeatureRestrictionError() {
		this.setHeaderText("Edit/Change Denied");
		this.setContentText(userType + " does not have permission to make changes to this item.");
		
		this.showAndWait();
	}
	
	public void displayBlueprintRestrictionError() {
		this.setHeaderText("Blueprint Access Denied");
		this.setContentText(userType + " does not have permission to this blueprint.");
		
		this.showAndWait();
	}
	
	public void displayInformationDialog(String message) {
		this.setHeaderText(null);
		this.setContentText(message);
		
		this.showAndWait();
	}
}
