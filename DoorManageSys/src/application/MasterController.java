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
import inventory.*;
import login.*;
import product.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
	 * A reference to the Stage of this application
	 */
	private Stage stage;
	
	/**
	 * The current logged in user of this application
	 */
	private DPMUser user;
	
	/**
	 * The Gateway to the User table in the DB
	 */
	private UsersGateway usersGateway;
	
	/**
	 * The Gateway to the Inventory table in the Database
	 */
	private InventoryGateway inventoryGateway;
	
	private ProductGateway productGateway;
	
	/**
	 * The page that the user is trying to view
	 */
	private PageTypes desiredPage;
	
	/**
	 * Boolean whether user logged out
	 */
	private boolean loggedOut;
	
	/**
	 * A Object holder for an object the user wishes to edit
	 * Mainly used when passing an object from a DetailController into a 
	 * EditController
	 */
	private Object editObj;
	
	private List<Inventory> inventoryToDisplay;
	
	/**
	 * Initialize a MasterController object.
	 */
	private MasterController() {
		try {
			this.usersGateway = new UsersGateway();
			this.inventoryGateway = new InventoryGateway();
			this.productGateway = new ProductGateway ();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		loggedOut = false;
	}
	
	/**
	 * This is the main function that is called when the user
	 * wishes to change the current page.
	 * @param pageType The type of page
	 * @return
	 */
	public boolean changeView(PageTypes pageType) {
		desiredPage = pageType;
		Parent view = null;
		
		view = this.loadView();
		
		//Switching between Login and Landing
		if(desiredPage == PageTypes.LANDING_PAGE || desiredPage == PageTypes.LOGIN_PAGE) {
			this.setMainPane((BorderPane) view);
			Scene scene = new Scene(view);
			this.stage.setScene(scene);
			this.stage.show();
		
		//for List Page
		} else if(desiredPage == PageTypes.VIEW_USERS_PAGE || desiredPage == PageTypes.INVENTORY_LIST_PAGE) {
			mainPane.setCenter(view);
			mainPane.setRight(this.getEmptyRightPane());
		
		//for Detail Page
		} else if(desiredPage == PageTypes.INVENTORY_DETAIL_PAGE) {
			mainPane.setRight(view);
			
		//for Edit Page
		} else if(desiredPage == PageTypes.INVENTORY_EDIT_PAGE) {
			mainPane.setRight(view);
		}
		
		return true;
	}
	
	/**
	 * Loads the view by loading the requested page and assigning
	 * a Controller for it.
	 * @return A Parent object specifying a base class for all scene nodes
	 */
	public Parent loadView() {
		Parent view = null;
		FXMLLoader loader = null;
		
		
		switch(desiredPage) {
			case LANDING_PAGE:
				loader = new FXMLLoader(getClass().getResource("/landing/Landing_Page.fxml"));
				loader.setController(new LandingPageController());
				break;
				
			case LOGIN_PAGE:
				loader = new FXMLLoader(getClass().getResource("/login/Login_Page.fxml"));			
				loader.setController(new LoginController(new DPMUser()));
				break;
				
			case VIEW_USERS_PAGE:
				List<DPMUser> users = this.usersGateway.getUsers();
				loader = new FXMLLoader(getClass().getResource("/user/ViewUsers_Page.fxml"));
				loader.setController(new ViewUsersController(users));
				break;
				
			case INVENTORY_LIST_PAGE:
				loader = new FXMLLoader(getClass().getResource("/inventory/InventoryList_Page.fxml"));
				loader.setController(new InventoryListController(this.inventoryToDisplay));
				break;
				
			case INVENTORY_DETAIL_PAGE:
				loader = new FXMLLoader(getClass().getResource("/inventory/InventoryDetail_Page.fxml"));
				loader.setController(new InventoryDetailController((Inventory)this.editObj));
				break;
				
			case INVENTORY_EDIT_PAGE:
				loader = new FXMLLoader(getClass().getResource("/inventory/InventoryEdit_Page.fxml"));
				Inventory editInventory = (Inventory)this.editObj;
				
				if(editInventory.getId() > 0) {
					loader.setController(new InventoryEditController(editInventory));
				} else if(editInventory.getId() == 0) {
					loader.setController(new InventoryEditController(new Inventory()));
				}
				break;
				
			default:
				break;
			
		}
		
		try {
			view = loader.load();
		} catch(IOException io) {
			io.printStackTrace();
		}
		
		return view;
	}
	
	public boolean updateRightMenu(PageTypes pageType) {
		
		
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
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
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public UsersGateway getUsersGateway() {
		return usersGateway;
	}
	
	public DPMUser getUser() {
		return this.user;
	}
	
	public void setUser(DPMUser user) {
		this.user = user;
	}
	
	public void logoutPressed() {
		if(this.loggedOut == false) {
			this.loggedOut = true;
		}
	}
	
	public boolean isLogoutPressed() {
		return this.loggedOut;
	}

	public InventoryGateway getInventoryGateway() {
		return inventoryGateway;
	}

	public void setEditObject(Object obj) {
		this.editObj = obj;
	}
	
	public void setInventoryListToDisplay(List<Inventory> objects) {
		this.inventoryToDisplay = objects;
	}
	
	public AnchorPane getEmptyRightPane() {
		AnchorPane emptyAnchor = new AnchorPane();
		emptyAnchor.setPrefSize(466.0, 580.0);
		
		return emptyAnchor;
	}
}
