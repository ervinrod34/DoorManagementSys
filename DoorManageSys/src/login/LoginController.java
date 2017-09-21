package login;



import java.util.List;

/**
 * The LoginController class defines a controller class
 * for the login page of this application.
 * 
 * @author Team No Name Yet
 * @version 1.0
 */
import application.MasterController;
import application.PageTypes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import user.DPMUser;

public class LoginController {

	/**
	 * The login button
	 */
	@FXML private Button login;
	@FXML private TextField username;
	@FXML private PasswordField password;
	@FXML private Label notice;
	
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
		List<DPMUser> userList = MasterController.getInstance().getUsersGateway().getUsers();
		if(source == login) {
			if (userList.stream().anyMatch(user -> user.getLogin().equalsIgnoreCase(username.getText()))) {
				for (DPMUser user : userList) {
					if (user.getLogin().equalsIgnoreCase(username.getText())) {
						this.user = user;
						break;
					}
				}
				if (user.getPassword().equals(password.getText()))
					MasterController.getInstance().changeView(PageTypes.LANDING_PAGE, user);
			}
			notice.setVisible(true);
		}
	}
}
