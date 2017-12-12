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
import inventory.*;
import order.*;
import quoteproduct.*;
import blueprint.*;

import java.util.List;

import applicationhelper.*;
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
	protected BlueprintGateway blueprintGateway;
	
	/**
	 * Boolean whether user logged out
	 */
	private boolean loggedOut;
	
	private UserRestrictor restriction;
	
	/**
	 * A Object holder for an object the user wishes to edit
	 * Mainly used when passing an object from a DetailController into a 
	 * EditController
	 */
	protected Object editObj;
	protected Object prevEditObj;
	
	protected Product selectedProductForBlueprint;
	
	protected Product productToDisplay;
	protected List<Inventory> inventoryToDisplay;
	protected List<Order> orderToDisplay;
	
	protected Product prevProductToDisplay;
	protected List<Inventory> prevInventoryToDisplay;
	protected List<Order> prevOrderToDisplay;
	
	protected List <Inventory> searchedInventory;
	protected List <Order> searchedOrders;
	protected List <Order> searchedQuotes;
	protected Order selectedOrder;
	
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
			this.blueprintGateway = new BlueprintGateway();
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
	
	public UserRestrictor getRestriction() {
		this.restriction = new UserRestrictor(this.user.getUserType());
		return this.restriction;
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
	
	public BlueprintGateway getBlueprintGateway() {
		return this.blueprintGateway;
	}

	public void setEditObject(Object obj) {
		this.prevEditObj = this.editObj;
		this.editObj = obj;
	}
	
	public void setInventoryListToDisplay(List<Inventory> objects) {
		this.prevInventoryToDisplay = this.inventoryToDisplay;
		this.inventoryToDisplay = objects;
	}
	
	public void setOrderListToDisplay(List<Order> objects) {
		this.prevOrderToDisplay = this.orderToDisplay;
		this.orderToDisplay = objects;
	}
	
	public void setProductToDisplay(Product product) {
		this.prevProductToDisplay = this.productToDisplay;
		this.productToDisplay = product;
	}
	
	public Product getProductToDisplay() {
		return this.productToDisplay;
	}
	
	public PageTypes getCurrentPage () {
		return desiredPage;
	}
	
	public List<Inventory> getInventoryToDisplay () {
		return inventoryToDisplay;
	}

	public List<Inventory> getSearchedInventory() {
		return searchedInventory;
	}

	public void setSearchedInventory(List<Inventory> searchedInventory) {
		this.searchedInventory = searchedInventory;
	}

	public List<Order> getSearchedOrders() {
		return searchedOrders;
	}

	public void setSearchedOrders(List<Order> searchedOrders) {
		this.searchedOrders = searchedOrders;
	}

	public List<Order> getSearchedQuotes() {
		return searchedQuotes;
	}

	public void setSearchedQuotes(List<Order> searchedQuotes) {
		this.searchedQuotes = searchedQuotes;
	}

	public Order getSelectedOrder() {
		return selectedOrder;
	}

	public void setSelectedOrder(Order selectedOrder) {
		this.selectedOrder = selectedOrder;
	}
	
	public void setSelectedProductForBlueprint(Product product) {
		this.selectedProductForBlueprint = product;
	}
	
	public Product getSelectedProductForBlueprint() {
		return this.selectedProductForBlueprint;
	}
}
