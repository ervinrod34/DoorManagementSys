package applicationdialogs;

import java.util.Optional;

import application.MasterController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
	
	public int displaySaveConfirmation() {
		this.setHeaderText("You are about to save this product.");
		this.setContentText("Do you want to continue?");
		Optional<ButtonType> result = this.showAndWait();
		if(result.get() == ButtonType.OK) {
			return 1;
		}else {
			return 0;
		}
	}
	
	public int displayDeleteConfirmation() {
		this.setHeaderText("You are about to delete this product.");
		this.setContentText("Do you want to continue?");
		Optional<ButtonType> result = this.showAndWait();
		if(result.get() == ButtonType.OK) {
			return 1;
		}else {
			return 0;
		}
	}
}
