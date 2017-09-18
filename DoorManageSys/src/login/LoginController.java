package login;

/**
 * The LoginController class defines a controller class
 * for the login page of this application.
 * 
 * @author Team No Name Yet
 * @version 1.0
 */

import application.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import user.*;

public class LoginController {

	/**
	 * The login button
	 */
	@FXML private Button login;
	
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
			MasterController.getInstance().changeView(PageTypes.LANDING_PAGE, user);
		}
	}
}
