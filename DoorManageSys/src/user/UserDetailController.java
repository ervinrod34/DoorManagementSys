package user;

import java.net.URL;
import java.util.ResourceBundle;

import application.MasterController;
import application.MasterViewController;
import applicationhelper.PageTypes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserDetailController implements Initializable{

	@FXML private Button editButton;
	@FXML private Button deleteButton;
	
	@FXML private Label name;
	@FXML private Label login;
	@FXML private Label email;
	@FXML private Label usertype;
	
	private DPMUser user;
	
	public UserDetailController(DPMUser user) {
		this.user = user;
	}
	
	@FXML private void handleUserDetail(ActionEvent action) {
		Object source = action.getSource();
		
		if(source == this.editButton) {
			MasterController.getInstance().setEditObject(this.user);
			MasterViewController.getInstance().changeView(PageTypes.USER_EDIT_PAGE);
			
		} else if(source == this.deleteButton) {
			MasterController.getInstance().getUsersGateway().deleteUser(this.user);
		}
	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		this.name.setText(this.user.getName());
		this.login.setText(this.user.getLogin());
		this.email.setText(this.user.getEmail());
		this.usertype.setText(this.user.getUserType().getType());
	}
}
