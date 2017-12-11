package application;

import java.io.IOException;
import java.util.List;

import applicationhelper.PageTypes;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import landing.LandingPageController;
import login.LoginController;
import inventory.*;
import order.*;
import quoteproduct.*;
import report.ReportsExportController;
import user.*;
import blueprint.*;

public class MasterViewController extends MasterController{

	private static MasterViewController instance = null;
	
	private boolean blueprintRightsIsOpen;
	
	private boolean switchTemplate;
	
	private MasterViewController() {
		this.blueprintRightsIsOpen = false;
		this.switchTemplate = false;
	}
	
	public static MasterViewController getInstance() {
		if(instance == null) {
			instance = new MasterViewController();
		}
		
		return instance;
	}
	
	/**
	 * This is the main function that is called when the user
	 * wishes to change the current page.
	 * @param pageType The type of page
	 */
	public boolean changeView(PageTypes pageType) {
		desiredPage = pageType;
		Parent view = null;
		
		view = this.loadView();
		
		//Switching between Login and Landing
		if(desiredPage == PageTypes.LANDING_PAGE || 
				desiredPage == PageTypes.LOGIN_PAGE) {
			MasterController.getInstance().setMainPane((BorderPane) view);
			Scene scene = new Scene(view);
			MasterController.getInstance().stage.setScene(scene);
			MasterController.getInstance().stage.show();
			
		//for List Page
		} else if(desiredPage == PageTypes.VIEW_USERS_PAGE || 
				desiredPage == PageTypes.INVENTORY_LIST_PAGE || 
				desiredPage == PageTypes.QORDER_LIST_PAGE || 
				desiredPage == PageTypes.ORDER_LIST_PAGE ||
				desiredPage == PageTypes.PRODUCT_LIST_PAGE || 
				desiredPage == PageTypes.QUOTEITEMS_LIST_PAGE ||
				desiredPage == PageTypes.INVENTORY_SEARCH_PAGE ||
				desiredPage == PageTypes.ORDER_SEARCH_PAGE ||
				desiredPage == PageTypes.QORDER_SEARCH_PAGE) {
			MasterController.getInstance().mainPane.setCenter(view);
			MasterController.getInstance().mainPane.setRight(this.getEmptyRightPane());
		
		//for Detail Page
		} else if(desiredPage == PageTypes.INVENTORY_DETAIL_PAGE || 
				desiredPage == PageTypes.QUOTE_DETAIL_PAGE ||
				desiredPage == PageTypes.PRODUCT_DETAIL_PAGE || 
				desiredPage == PageTypes.ORDER_DETAIL_PAGE ||
				desiredPage == PageTypes.USER_DETAIL_PAGE) {
			MasterController.getInstance().mainPane.setRight(view);
			this.blueprintRightsIsOpen = false;
			
		//for Edit Page
		} else if(desiredPage == PageTypes.INVENTORY_EDIT_PAGE || 
				desiredPage == PageTypes.QUOTE_EDIT_PAGE ||
				desiredPage == PageTypes.PRODUCT_EDIT_PAGE || 
				desiredPage == PageTypes.ORDER_EDIT_PAGE || 
				desiredPage == PageTypes.USER_EDIT_PAGE ||
				desiredPage == PageTypes.BLUEPRINT_RIGHT_PAGE) {
			MasterController.getInstance().mainPane.setRight(view);
			this.blueprintRightsIsOpen = false;
			
		//for Pages with right pane, no center
		} else if(desiredPage == PageTypes.REPORTS_EXPORT_PAGE) {
			MasterController.getInstance().mainPane.setCenter(this.getEmptyCenterPane());
			MasterController.getInstance().mainPane.setRight(view);
			this.blueprintRightsIsOpen = false;
			
		} else if(desiredPage == PageTypes.BLUEPRINT_CENTER_PAGE) {
			if(this.blueprintRightsIsOpen == false) {
				MasterController.getInstance().mainPane.setRight(openBlueprintRightPage());
				this.blueprintRightsIsOpen = true;
				MasterController.getInstance().mainPane.setCenter(view);
			} else {
				MasterController.getInstance().mainPane.setCenter(view);
			}
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
				
			case USER_DETAIL_PAGE:
				DPMUser viewUser = (DPMUser)MasterController.getInstance().editObj;
				
				loader = new FXMLLoader(getClass().getResource("/user/UserDetail_Page.fxml"));
				loader.setController(new UserDetailController(viewUser));
				break;
				
			case USER_EDIT_PAGE:
				DPMUser editUser = (DPMUser)MasterController.getInstance().editObj;
				loader = new FXMLLoader(getClass().getResource("/user/UserEdit_Page.fxml"));
				
				if(editUser.getId() > 0) {
					loader.setController(new UserEditController(editUser));
				} else if(editUser.getId() == 0) {
					loader.setController(new UserEditController(new DPMUser()));
				}
				break;
				
			case INVENTORY_LIST_PAGE:
				List<Inventory> allInventory = MasterController.getInstance().inventoryGateway.getInventory();
				
				loader = new FXMLLoader(getClass().getResource("/inventory/InventoryList_Page.fxml"));
				loader.setController(new InventoryListController(allInventory));
				break;
				
			case INVENTORY_DETAIL_PAGE:
				Inventory viewItem = (Inventory)MasterController.getInstance().editObj;
				
				loader = new FXMLLoader(getClass().getResource("/inventory/InventoryDetail_Page.fxml"));
				loader.setController(new InventoryDetailController(viewItem));
				break;
				
			case INVENTORY_SEARCH_PAGE:
				
				loader = new FXMLLoader(getClass().getResource("/inventory/InventoryList_Page.fxml"));
				loader.setController(new InventoryListController(MasterController.getInstance().getSearchedInventory()));
				break;
				
			case INVENTORY_EDIT_PAGE:
				Inventory editItem = (Inventory)MasterController.getInstance().editObj;
				loader = new FXMLLoader(getClass().getResource("/inventory/InventoryEdit_Page.fxml"));
				
				if(editItem.getId() > 0) {
					loader.setController(new InventoryEditController(editItem));
				} else if(editItem.getId() == 0) {
					loader.setController(new InventoryEditController(new Inventory()));
				}
				break;
				
			case ORDER_LIST_PAGE:
				List<Order> allOrders = MasterController.getInstance().orderGateway.getOrders();
				
				loader = new FXMLLoader(getClass().getResource("/order/OrderList_Page.fxml"));
				loader.setController(new OrderListController(allOrders));
				break;
				
			case ORDER_DETAIL_PAGE:
				Order viewOrder = (Order)MasterController.getInstance().editObj;
				
				loader = new FXMLLoader(getClass().getResource("/order/OrderDetail_Page.fxml"));
				loader.setController(new OrderDetailController(viewOrder));
				break;
				
			case ORDER_EDIT_PAGE:
				Order editOrder = (Order)MasterController.getInstance().editObj;
				loader = new FXMLLoader(getClass().getResource("/order/OrderEdit_Page.fxml"));
				
				if(editOrder.getId() > 0) {
					loader.setController(new OrderEditController(editOrder));
				} else if(editOrder.getId() == 0){
					loader.setController(new OrderEditController(new Order()));
				}
				break;
				
			case ORDER_SEARCH_PAGE:
				loader = new FXMLLoader(getClass().getResource("/order/OrderList_Page.fxml"));
				loader.setController(new OrderListController(MasterController.getInstance().getSearchedOrders()));
				break;
				
			case QUOTE_DETAIL_PAGE:
				Order quoteDetail = (Order)MasterController.getInstance().editObj;
				
				loader = new FXMLLoader(getClass().getResource("/quoteproduct/QuoteDetail_Page.fxml"));
				loader.setController(new QuoteDetailController(quoteDetail));
				break;
				
			case QUOTE_EDIT_PAGE:
				Order quoteEdit = (Order)MasterController.getInstance().editObj;
				loader = new FXMLLoader(getClass().getResource("/quoteproduct/QuoteEdit_Page.fxml"));
				
				if (quoteEdit.getId() > 0) {
					loader.setController(new QuoteEditController(quoteEdit));
				} else if (quoteEdit.getId() == 0) {
					loader.setController(new QuoteEditController(new Order()));
				}
				break;
				
			case QORDER_LIST_PAGE:
				List<Order> unfinishedOrders = MasterController.getInstance().orderGateway.searchOrders("Unfinished");
				
				loader = new FXMLLoader(getClass().getResource("/quoteproduct/QOrderList_Page.fxml"));
				loader.setController(new QOrderListController(unfinishedOrders));
				break;
				
			case QORDER_SEARCH_PAGE:				
				loader = new FXMLLoader(getClass().getResource("/quoteproduct/QOrderList_Page.fxml"));
				loader.setController(new QOrderListController(MasterController.getInstance().getSearchedQuotes()));
				break;
				
			case QUOTEITEMS_LIST_PAGE:
				loader = new FXMLLoader(getClass().getResource("/quoteproduct/QuoteItemsList_Page.fxml"));
				loader.setController(new QuoteItemsListController(
						MasterController.getInstance().productToDisplay, 
						MasterController.getInstance().inventoryToDisplay));
				break;
				
			case PRODUCT_LIST_PAGE:
				List<Product> allProducts = MasterController.getInstance().productGateway.importListOfProductsFromDB();
				
				loader = new FXMLLoader(getClass().getResource("/quoteproduct/ProductList_Page.fxml"));
				loader.setController(new ProductListController(allProducts));
				break;
				
			case PRODUCT_DETAIL_PAGE:
				Product viewProduct = (Product)MasterController.getInstance().editObj;
				
				loader = new FXMLLoader(getClass().getResource("/quoteproduct/ProductDetail_Page.fxml"));
				loader.setController(new ProductDetailController(viewProduct));
				break;
				
			case PRODUCT_EDIT_PAGE:
				Product editProduct = (Product)MasterController.getInstance().editObj;
				loader = new FXMLLoader(getClass().getResource("/quoteproduct/ProductEdit_Page.fxml"));
				
				if(editProduct.getId() > 0) {
					loader.setController(new ProductEditController(editProduct));
				} else if(editProduct.getId() == 0){
					loader.setController(new ProductEditController(new Product()));
				}
				break;
				
			case REPORTS_EXPORT_PAGE:	
				loader = new FXMLLoader(getClass().getResource("/report/ReportsExport_Page.fxml"));
				loader.setController(new ReportsExportController());
				break;
				
			case BLUEPRINT_CENTER_PAGE:
				Blueprint editBlueprint = new Blueprint();
				
				if(MasterController.getInstance().editObj instanceof Order) {
					Order order = (Order)MasterController.getInstance().editObj;
					int productID = order.getQuote().getProducts().get(0).getId();
					editBlueprint = MasterController.getInstance().getBlueprintGateway()
							.getBlueprintByProductID(productID);
				} else if(MasterController.getInstance().editObj instanceof Product) {
					Product product = (Product)MasterController.getInstance().editObj;
					editBlueprint = MasterController.getInstance().getBlueprintGateway()
							.getBlueprintByProductID(product.getId());
				}
				
				if(this.switchTemplate == false) {
					loader = new FXMLLoader(getClass().getResource("/blueprint/BlueprintOneDoor_Page.fxml"));
				} else {
					loader = new FXMLLoader(getClass().getResource("/blueprint/BlueprintTwoDoor_Page.fxml"));
				}
				loader.setController(new BlueprintDoorController(editBlueprint));
				
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
	
	private Parent openBlueprintRightPage() {
		Parent view = null;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/blueprint/BlueprintRight_Page.fxml"));
		Order order = (Order)MasterController.getInstance().editObj;
		loader.setController(new BlueprintRightController(order));
		
		try {
			view = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return view;
	}
	
	public void switchTemplateIsPressed() {
		if(this.switchTemplate == true) {
			this.switchTemplate = false;
		} else {
			this.switchTemplate = true;
		}
	}
	
	
	private AnchorPane getEmptyRightPane() {
		AnchorPane emptyRightAnchor = new AnchorPane();
		emptyRightAnchor.setPrefSize(466.0, 580.0);
		
		return emptyRightAnchor;
	}
	
	private AnchorPane getEmptyCenterPane() {
		AnchorPane emptyCenterAnchor = new AnchorPane();
		emptyCenterAnchor.setPrefSize(466.0, 580.0);
		
		return emptyCenterAnchor;
	}
	
	public void setDesiredPage (PageTypes page) {
		desiredPage = page;
	}
}
