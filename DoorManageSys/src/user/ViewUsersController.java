package user;

/**
 * The ViewusersController class defines a controller object
 * for the ViewUsers_Page.
 * 
 * @author Team No Name Yet
 * @version 1.0
 */

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class ViewUsersController implements Initializable {
	
	/**
	 * The ListView in the ViewUsers_Page
	 */
	@FXML private ListView<DPMUser> usersListView;
	
	/**
	 * An observable list which allows the users to be displayed
	 */
	private ObservableList<DPMUser> observableList;
	
	/**
	 * The list of users
	 */
	private List<DPMUser> users;
	
	/**
	 * Initialize a ViewUsersController object.
	 * @param users
	 */
	public ViewUsersController(List<DPMUser> users) {
		this.users = users;
	}
	
	/**
	 * Handles the user interaction with the GUI components in the
	 * view users page.
	 * @param ae An ActionEvent
	 */
	@FXML private void handleViewUsers(ActionEvent ae) {
		
	}
	
	/**
	 * Initializes the controller's components with data.
	 */
	public void initialize(URL loc, ResourceBundle rsc) {
		this.observableList = this.usersListView.getItems();
		for(DPMUser user : this.users) {
			this.observableList.add(user);
		}
	}
}
