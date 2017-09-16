package controllers;

import java.io.IOException;

import application.*;
import gateways.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class MasterController {

	private static MasterController instance = null;
	
	private BorderPane mainPane;
	
	private UsersGateway usersGateway;
	
	private ViewTypes desiredView;
	
	private MasterController() {
		try {
			usersGateway = new UsersGateway();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public boolean changeView(ViewTypes viewType, Object obj) {
		FXMLLoader loader = null;
		desiredView = viewType;
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
		
		if(desiredView == ViewTypes.LANDING_PAGE) {
			loader = new FXMLLoader(getClass().getResource("/views/Landing_Page.fxml"));
			loader.setController(new LandingPageController());
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
