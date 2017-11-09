package login;

/**
 * The LoginController class defines a controller class
 * for the login page of this application.
 * 
 * @author Team No Name Yet
 * @version 1.0
 */

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.*;
import applicationhelper.PageTypes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import user.DPMUser;

public class LoginController implements Initializable {

	/**
	 * The login button
	 */
	@FXML private Button login;
	@FXML private TextField username;
	@FXML private PasswordField password;
	@FXML private Label notice;
	
	/**
	 * The list of users from the DB
	 */
	private List<DPMUser> users;
	
	/**
	 * The user that logged in
	 */
	private DPMUser user;
	
	/**
	 * Initializes a LoginController object with a DPMUser object.
	 * @param user The user who is logging in
	 */
	public LoginController(DPMUser user) {
		this.user = user;
	}
	
	/**
	 * Handles the user interaction with the GUI components in the
	 * login page.
	 * @param ae An ActionEvent
	 */
	@FXML private void handleLogin(ActionEvent ae) {
		Object source = ae.getSource();
		if(source == login) {
			this.attemptLogin();
		}
	}
	
	@FXML private void handleEnterKey(KeyEvent key) {
		if(key.getCode() == KeyCode.ENTER) {
			this.attemptLogin();
		}
	}

	public void attemptLogin() {
		if (users.stream().anyMatch(user -> user.getLogin().equalsIgnoreCase(username.getText()))) {
			for (DPMUser user : users) {
				if (user.getLogin().equalsIgnoreCase(username.getText())) {
					this.user = user;
					break;
				}
			}
			if (user.getPassword().equals(password.getText().toString())) {
				MasterController.getInstance().setUser(this.user);
				MasterViewController.getInstance().changeView(PageTypes.LANDING_PAGE);
			}
		}
		this.notice.setText("Incorrect Username or Password");
		notice.setVisible(true);
	}
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.users = MasterController.getInstance().getUsersGateway().getUsers();
		
		if(MasterController.getInstance().isLogoutPressed()) {
			this.notice.setText("You have logout successfully!");
			this.notice.setVisible(true);
			this.notice.setTextFill(Paint.valueOf("WHITE"));
		}
	}
}
