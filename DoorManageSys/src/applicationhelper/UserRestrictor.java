package applicationhelper;

import user.*;
import applicationdialogs.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserRestrictor {
	
	private UserTypes userType;
	
	private InfoDialogs dialog;
	
	public UserRestrictor() {
		this.userType = null;
		this.dialog = new InfoDialogs();
	}
	
	public UserRestrictor(UserTypes type) {
		this.userType = type;
		this.dialog = new InfoDialogs();
	}
	
	public void setUserTypeForRestrictor(UserTypes type) {
		this.userType = type;
	}
	
	public void applyUsersTabRestriction(Label tab) {
		if(this.userType != UserTypes.ADMIN) {
			tab.setVisible(false);
		}
	}
	
	public boolean applyOrdersTabRestriction() {
		if(this.userType == UserTypes.INVENTORYMANAGER) {
			this.dialog.displayPageRestrictionError();
			return false;
		} else {
			return true;
		}
	}
	
	public boolean applyQuotesTabRestriction() {
		if((this.userType == UserTypes.ACCOUNTING) ||
				(this.userType == UserTypes.ENGINEER) ||
				(this.userType == UserTypes.INVENTORYMANAGER) ||
				(this.userType == UserTypes.PURCHASING)) {
			this.dialog.displayPageRestrictionError();
			return false;
		} else {
			return true;
		}
	}
	
	public boolean applyInventoryReportRestriction() {
		if((this.userType == UserTypes.SALES) ||
				(this.userType == UserTypes.ACCOUNTING) ||
				(this.userType == UserTypes.ENGINEER)) {
			this.dialog.displayBlueprintRestrictionError();
			return false;
		} else {
			return true;
		}
	}
	
	public boolean applyQuoteBlueprintReportRestriction() {
		if((this.userType == UserTypes.ACCOUNTING) ||
				(this.userType == UserTypes.INVENTORYMANAGER) ||
				(this.userType == UserTypes.PURCHASING)) {
			this.dialog.displayBlueprintRestrictionError();
			
			return false;
		} else {
			return true;
		}
	}
	
	public void applyChangeInventoryRestriction(Button button) {
		if((this.userType == UserTypes.ACCOUNTING) || 
				(this.userType == UserTypes.ENGINEER) ||
				(this.userType == UserTypes.SALES)) {
			button.setVisible(false);
		}
	}
	
	public void applyChangeOrderRestriction(Button button) {
		if((this.userType == UserTypes.ACCOUNTING) ||
				(this.userType == UserTypes.ENGINEER) ||
				(this.userType == UserTypes.INVENTORYMANAGER) ||
				(this.userType == UserTypes.PURCHASING)) {
			button.setVisible(false);
		}
	}
	
	public void applyDeleteRestriction(Button button) {
		if(this.userType != UserTypes.ADMIN) {
			button.setVisible(false);
		}
	}
	
	public void applyOrderDeleteRestriction(Button button) {
		if((this.userType != UserTypes.ACCOUNTING) ||
				(this.userType != UserTypes.ENGINEER) ||
				(this.userType != UserTypes.INVENTORYMANAGER) ||
				(this.userType != UserTypes.PURCHASING)) {
			button.setVisible(false);
		}
	}
}
