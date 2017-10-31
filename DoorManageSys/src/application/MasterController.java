package application;

/**
 * The MasterController class defines a class that acts as a
 * global access point for all of the classes in this Application.
 * It is mainly responsible for storing states, values, and instances
 * for the application.
 * 
 * @author Team No Name Yet
 * @version 1.0
 */

import user.*;
import landing.*;
import inventory.*;
import login.*;
import order.*;
import quoteproduct.*;
import report.*;

import java.io.IOException;
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
	
	protected BorderPane mainPane;
	protected Stage stage;
	protected PageTypes desiredPage;
	private DPMUser user;
	
	protected UsersGateway usersGateway;
	protected InventoryGateway inventoryGateway;
	protected ProductGateway productGateway;
	protected OrderGateway orderGateway;
	protected QuoteGateway quoteGateway;
	
	/**
	 * Boolean whether user logged out
	 */
	private boolean loggedOut;
	
	/**
	 * A Object holder for an object the user wishes to edit
	 * Mainly used when passing an object from a DetailController into a 
	 * EditController
	 */
	protected Object editObj;
	protected Product productToDisplay;
	
	protected List<Inventory> inventoryToDisplay;
	protected List<Order> orderToDisplay;
	
	/**
	 * Initialize a MasterController object.
	 */
	public MasterController() {
		try {
			this.usersGateway = new UsersGateway();
			this.inventoryGateway = new InventoryGateway();
			this.productGateway = new ProductGateway();
			this.orderGateway = new OrderGateway();
			this.quoteGateway = new QuoteGateway();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		loggedOut = false;
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
	
	public Stage getStage() {
		return this.stage;
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
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
	
	public UsersGateway getUsersGateway() {
		return usersGateway;
	}
	
	public InventoryGateway getInventoryGateway() {
		return inventoryGateway;
	}
	
	public OrderGateway getOrderGateway(){
		return orderGateway;
	}
	
	public ProductGateway getProductGateway() {
		return productGateway;
	}
	
	public QuoteGateway getQuoteGateway() {
		return this.quoteGateway;
	}

	public void setEditObject(Object obj) {
		this.editObj = obj;
	}
	
	public void setInventoryListToDisplay(List<Inventory> objects) {
		this.inventoryToDisplay = objects;
	}
	
	public void setOrderListToDisplay(List<Order> objects) {
		this.orderToDisplay = objects;
	}
	
	public void setProductToDisplay(Product product) {
		this.productToDisplay = product;
	}
	
	public Product getProductToDisplay() {
		return this.productToDisplay;
	}
}
