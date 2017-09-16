package controllers;

import application.*;
import objects.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LoginController {

	@FXML private Button login;
	
	private DPMUser user;
	
	public LoginController(DPMUser user) {
		this.user = user;
	}
	
	@FXML private void handleLogin(ActionEvent ae) {
		Object source = ae.getSource();
		
		if(source == login) {
			MasterController.getInstance().changeView(ViewTypes.LANDING_PAGE, user);
		}
	}
}
