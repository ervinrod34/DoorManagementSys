package landing;

import application.*;

/**
 * The LandingPageController class defines a controllers class
 * for the landing page of this application. The Landing Page is the 
 * main page of this application.
 */

import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class LandingPageController {

	/**
	 * The logout button
	 */
	@FXML private Button logoutButton;
	
	/**
	 * The search button
	 */
	@FXML private Button searchButton;
	
	/**
	 * The textfield where the term to be searched is inputted
	 */
	@FXML private TextField searchTextField;
	
	/**
	 * The View Inventory menu item
	 */
	@FXML private MenuItem viewInventory;
	
	/**
	 * The View Users menu item
	 */
	@FXML private MenuItem viewUsers;
	
	/**
	 * Initializes a LandingPageController object.
	 */
	public LandingPageController() {
		
	}
	
	/**
	 * Handles the user interaction with the GUI components in the
	 * landing page.
	 * @param ae An ActionEvent
	 */
	@FXML private void handleLandingPage(ActionEvent ae) {
		Object source = ae.getSource();
		
		if(source == logoutButton) {
			Platform.exit(); //TEMPORARY just for demo
		}
		else if(source == viewUsers) {
			MasterController.getInstance().changeView(PageTypes.VIEW_USERS_PAGE, new Object());
		}
	}
	
}
