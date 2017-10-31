package application;

import login.*;
import user.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class RunDPM extends Application {
	
	/**
	 * Initialize a RunDPM object.
	 */
	public RunDPM() {
		
	}
	
	/**
	 * The application initialization method.
	 */
	public void init() {
		try {
			super.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is called when the application should stop.
	 */
	public void stop() {
		try {
			super.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The main beginning of the application where everything starts
	 * once the application is ready to start.
	 */
	public void start(Stage primaryStage) {
		try {
			
			//Loads the initial screen/view
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/login/Login_Page.fxml"));
			
			//Assigns a controller for the login page
			LoginController loginControl = new LoginController(new DPMUser());
			loader.setController(loginControl);

			Parent view = loader.load();
			
			MasterController.getInstance().setMainPane((BorderPane) view);
			MasterController.getInstance().setStage(primaryStage);
			
			Scene scene = new Scene(view);
			
			primaryStage.setTitle("Deansteel DPM v1.0");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Launches the args.
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
