package landing;

/**
 * The LandingPageController class defines a controllers class
 * for the landing page of this application. The Landing Page is the 
 * main page of this application.
 */


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.*;
import applicationhelper.*;
import applicationhelper.PageTypes;
import inventory.*;

import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import order.Order;

public class LandingPageController implements Initializable {
	
	@FXML private Label inventory;
	@FXML private Label orders;
	@FXML private Label quote;
	@FXML private Label reports;
	@FXML private Label users;
	@FXML private Label logout;
	@FXML private Label userIdentifierLabel;
	
	/**
	 * The search button
	 */
	@FXML private Button searchButton;
	
	/**
	 * The textfield where the term to be searched is inputted
	 */
	@FXML private TextField searchTextField;
	
	/**
	 * Initializes a LandingPageController object.
	 */
	public LandingPageController() {
		
	}
	
	/**
	 * Handles the user interaction with the the menu labels in the
	 * Landing page.
	 * @param ae An ActionEvent
	 */
	@FXML private void handleMenu(MouseEvent me) {
		Object source = me.getSource();
		
		if(source == logout) {
			MasterController.getInstance().logoutPressed();
			MasterViewController.getInstance().changeView(PageTypes.LOGIN_PAGE);
			
		} else if(source == inventory) {
			this.applyEffectOnMenuLabel(inventory);
			this.changeViewOnLabelClick(inventory, PageTypes.INVENTORY_LIST_PAGE);
			
		} else if(source == orders) {
			this.applyEffectOnMenuLabel(orders);
			
			if(me.getButton() == MouseButton.PRIMARY) {
				if(MasterController.getInstance().getRestriction().applyOrdersTabRestriction()) {
					this.changeViewOnLabelClick(orders, PageTypes.ORDER_LIST_PAGE);
				}
			}
			
		} else if(source == quote) {
			this.applyEffectOnMenuLabel(quote);
			
			if(me.getButton() == MouseButton.PRIMARY) {
				if(MasterController.getInstance().getRestriction().applyQuotesTabRestriction()) {
					this.changeViewOnLabelClick(quote, PageTypes.QORDER_LIST_PAGE);
				}
			}
			
		} else if(source == reports) { 
			this.applyEffectOnMenuLabel(reports);
			this.changeViewOnLabelClick(reports, PageTypes.REPORTS_EXPORT_PAGE);
			
		} else if(source == users) {
			this.applyEffectOnMenuLabel(users);
			this.changeViewOnLabelClick(users, PageTypes.VIEW_USERS_PAGE);
		} 
	}
	
	@FXML private void handleSearch(ActionEvent ae) {
		
		PageTypes currentPage = MasterViewController.getInstance().getCurrentPage();
		String searchKey = this.searchTextField.getText();
		Object source = ae.getSource();
		
		if(source == searchButton) {
			
			switch (currentPage) {
			
			case INVENTORY_LIST_PAGE:
				
				List<Inventory> inventorySearch = MasterController.getInstance().getInventoryGateway().searchInventory(searchKey);
				MasterController.getInstance().setSearchedInventory(inventorySearch);
				MasterViewController.getInstance().changeView(PageTypes.INVENTORY_SEARCH_PAGE);
				MasterViewController.getInstance().setDesiredPage(PageTypes.INVENTORY_LIST_PAGE);
				break;
				
			case ORDER_LIST_PAGE:
				
				List <Order> orderSeach = MasterController.getInstance().getOrderGateway().searchOrderByName(searchKey);
				MasterController.getInstance().setSearchedOrders(orderSeach);
				MasterViewController.getInstance().changeView(PageTypes.ORDER_SEARCH_PAGE);
				MasterViewController.getInstance().setDesiredPage(PageTypes.ORDER_LIST_PAGE);
				break;
				
			case QORDER_LIST_PAGE:
				
				List <Order> quoteSearch = MasterController.getInstance().getOrderGateway().searchQuoteByName(searchKey);
				MasterController.getInstance().setSearchedQuotes(quoteSearch);
				MasterViewController.getInstance().changeView(PageTypes.QORDER_SEARCH_PAGE);
				MasterViewController.getInstance().setDesiredPage(PageTypes.QORDER_LIST_PAGE);
				break;

			default:
				break;	
			}			
		}	
	}

	public void initialize(URL location, ResourceBundle resources) {
		MasterController.getInstance().getRestriction().applyUsersTabRestriction(this.users);
		
		this.userIdentifierLabel.setText("Welcome " + MasterController.getInstance().getUser().getName());
		
	}
	
	private void applyEffectOnMenuLabel(Label label) {
		label.setOnMouseEntered(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent e) {
		        label.setScaleX(1.5);
		        label.setScaleY(1.5);
		    }
		});
		
		label.setOnMouseExited(new EventHandler<MouseEvent>() {
		    @Override
		    public void handle(MouseEvent e) {
		        label.setScaleX(1.0);
		        label.setScaleY(1.0);
		    }
		});
	}
	
	private void changeViewOnLabelClick(Label label, PageTypes pageType) {
		label.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				MasterViewController.getInstance().changeView(pageType);
			}
		});
	}
}
