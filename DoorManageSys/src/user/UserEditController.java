package user;

import java.net.URL;
import java.util.ResourceBundle;

import com.mysql.fabric.xmlrpc.base.Array;

import application.MasterController;
import application.MasterViewController;
import applicationdialogs.InfoDialogs;
import applicationhelper.PageTypes;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class UserEditController implements Initializable {

	@FXML private Button saveButton;
	@FXML private Button cancelButton;
	
	@FXML private TextField username;
	@FXML private TextField email;
	@FXML private TextField firstName;
	@FXML private TextField lastName;
	
	@FXML private PasswordField password;
	
	@FXML ChoiceBox<UserTypes> usertypeChoice;
	@FXML ObservableList<UserTypes> observableTypes;
	
	private DPMUser user;
	
	public UserEditController(DPMUser user) {
		this.user = user;
	}
	
	@FXML public void handleUserEdit(ActionEvent action) {
		Object source = action.getSource();
		
		if(source == cancelButton) {
			MasterController.getInstance().setEditObject(this.user);
			MasterViewController.getInstance().changeView(PageTypes.USER_DETAIL_PAGE);
		} else if(source == saveButton) {
			this.updateUserObject();
			
			if(this.checkIfUserSelectedAType()) {
				this.user.save();
				
				MasterViewController.getInstance().changeView(PageTypes.VIEW_USERS_PAGE);
				MasterController.getInstance().setEditObject(this.user);
				MasterViewController.getInstance().changeView(PageTypes.USER_DETAIL_PAGE);
			} else {
				InfoDialogs info = new InfoDialogs();
				info.displayInformationDialog("Please select a User type.");
			}
		}
	}
	
	private boolean checkIfUserSelectedAType() {
		UserTypes userType = this.usertypeChoice.getSelectionModel().getSelectedItem();
		
		if(userType == UserTypes.NEW) {
			return false;
		} else {
			return true;
		}
	}
	
	public void initialize(URL location, ResourceBundle resources) {
		this.observableTypes = this.usertypeChoice.getItems();
		this.populateChoicesWithUserTypes();
		this.usertypeChoice.setTooltip(new Tooltip("Select a user type."));
		this.usertypeChoice.setValue(this.user.getUserType());
		
		if(this.user.getId() > 0) {
			this.splitName();
			this.username.setText(this.user.getLogin());
			this.password.setText(this.user.getPassword());
			this.email.setText(this.user.getEmail());
			
		}
	}
	
	private void splitName() {
		String[] names = this.user.getName().split(" ");
		this.firstName.setText(names[0]);
		this.lastName.setText(names[1]);
	}
	
	private void populateChoicesWithUserTypes() {
		for(UserTypes type : UserTypes.values()) {
			this.observableTypes.add(type);
		}
		
		this.observableTypes.remove(0);
	}
	
	private void updateUserObject() {
		DPMUser updatedUser = new DPMUser();
		
		updatedUser.setId(this.user.getId());
		updatedUser.setName(this.firstName.getText() + " " + this.lastName.getText());
		updatedUser.setLogin(this.username.getText());
		updatedUser.setPassword(this.password.getText());
		updatedUser.setEmail(this.email.getText());
		updatedUser.setUserType(this.usertypeChoice.getSelectionModel().getSelectedItem());
		
		this.user = updatedUser;
	}
}
