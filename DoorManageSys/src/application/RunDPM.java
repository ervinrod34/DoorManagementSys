package application;

/**
 * 
 */
// TODO: remove this comment

import controllers.*;
import objects.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class RunDPM extends Application {
	
	public RunDPM() {
		
	}
	
	public void init() {
		try {
			super.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		try {
			super.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void start(Stage primaryStage) {
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Login_Page.fxml"));
			
			LoginController loginControl = new LoginController(new DPMUser());
			loader.setController(loginControl);

			Parent view = loader.load();
			
			MasterController.getInstance().setMainPane((BorderPane) view);
			
			Scene scene = new Scene(view);
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
