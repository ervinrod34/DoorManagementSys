package application;

import java.io.IOException;
import java.util.List;

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

public class MasterViewController extends MasterController{

	private static MasterViewController instance = null;
	
	private MasterViewController() {

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
				desiredPage == PageTypes.QUOTEITEMS_LIST_PAGE) {
			MasterController.getInstance().mainPane.setCenter(view);
			MasterController.getInstance().mainPane.setRight(this.getEmptyRightPane());
		
		//for Detail Page
		} else if(desiredPage == PageTypes.INVENTORY_DETAIL_PAGE || 
				desiredPage == PageTypes.QUOTE_DETAIL_PAGE ||
				desiredPage == PageTypes.ORDER_DETAIL_PAGE) {
			MasterController.getInstance().mainPane.setRight(view);
			
		//for Edit Page
		} else if(desiredPage == PageTypes.INVENTORY_EDIT_PAGE || 
				desiredPage == PageTypes.QUOTE_EDIT_PAGE ||
				desiredPage == PageTypes.ORDER_EDIT_PAGE) {
			MasterController.getInstance().mainPane.setRight(view);
			
		//for Pages with right pane, no center
		} else if(desiredPage == PageTypes.REPORTS_EXPORT_PAGE) {
			MasterController.getInstance().mainPane.setCenter(this.getEmptyCenterPane());
			MasterController.getInstance().mainPane.setRight(view);
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
				List<Inventory> allInventory = MasterController.getInstance().inventoryGateway.getInventory();
				
				loader = new FXMLLoader(getClass().getResource("/inventory/InventoryList_Page.fxml"));
				loader.setController(new InventoryListController(allInventory));
				break;
				
			case INVENTORY_DETAIL_PAGE:
				Inventory viewItem = (Inventory)MasterController.getInstance().editObj;
				
				loader = new FXMLLoader(getClass().getResource("/inventory/InventoryDetail_Page.fxml"));
				loader.setController(new InventoryDetailController(viewItem));
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
				
			case QUOTEITEMS_LIST_PAGE:
				loader = new FXMLLoader(getClass().getResource("/quoteproduct/QuoteItemsList_Page.fxml"));
				loader.setController(new QuoteItemsListController(
						MasterController.getInstance().productToDisplay, 
						MasterController.getInstance().inventoryToDisplay));
				break;
				
			case REPORTS_EXPORT_PAGE:	
				loader = new FXMLLoader(getClass().getResource("/report/ReportsExport_Page.fxml"));
				loader.setController(new ReportsExportController());
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
}
