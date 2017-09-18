package application;

/**
 * The MasterController class defines a controller that acts as a
 * global access point for all of the classes in this Application.
 * It is single controller that handle the changing of screens, views,
 * or pages.
 * 
 * @author Team No Name Yet
 * @version 1.0
 */

import user.*;
import landing.*;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;


public class MasterController {

	/**
	 * An instance of this class
	 */
	private static MasterController instance = null;
	
	/**
	 * The main pane of this application
	 */
	private BorderPane mainPane;
	
	/**
	 * The Gateway to the User table in the DB
	 */
	private UsersGateway usersGateway;
	
	/**
	 * The page that the user is trying to view
	 */
	private PageTypes desiredPage;
	
	/**
	 * Initialize a MasterController object.
	 */
	private MasterController() {
		try {
			usersGateway = new UsersGateway();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This is the main function that is called when the user
	 * wishes to change the current page.
	 * @param pageType The type of page
	 * @param obj
	 * @return
	 */
	public boolean changeView(PageTypes pageType, Object obj) {
		FXMLLoader loader = null;
		desiredPage = pageType;
		Parent view = null;
		
		loader = this.loadView();
		
		try {
			view = loader.load();
		} catch(IOException io) {
			io.printStackTrace();
		}
		
		mainPane.setCenter(view);
		return true;
	}
	
	public FXMLLoader loadView() {
		FXMLLoader loader = null;
		
		if(desiredPage == PageTypes.LANDING_PAGE) {
			loader = new FXMLLoader(getClass().getResource("/landing/Landing_Page.fxml"));
			loader.setController(new LandingPageController());
			
		} else if(desiredPage == PageTypes.VIEW_USERS_PAGE) {
			List<DPMUser> users = this.usersGateway.getUsers();
			loader = new FXMLLoader(getClass().getResource("/user/ViewUsers_Page.fxml"));
			loader.setController(new ViewUsersController(users));;
		}
		
		return loader;
	}
	
	public static MasterController getInstance() {
		if(instance == null) {
			instance = new MasterController();
		}
		
		return instance;
	}
	
	public BorderPane getMainPane() {
		return this.mainPane;
	}
	
	public void setMainPane(BorderPane mainPane) {
		this.mainPane = mainPane;
	}
	
}
