package applauncher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class DPMLauncher extends Application {

	private BorderPane mainPane;
	
	public DPMLauncher() {
		
	}
	
	public void init() {
		try {
			super.init();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		try {
			super.stop();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void start(Stage launcherStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/applauncher/Launcher_Page.fxml"));
		
		LauncherController launchControl = new LauncherController();
		loader.setController(launchControl);
		
		Parent view = loader.load();
		
		BorderPane main = new BorderPane();
		main.setCenter(view);
		
		mainPane = (BorderPane)main;
		
		Scene scene = new Scene(main);
		launcherStage.setScene(scene);
		launcherStage.setTitle("DPM Launcher beta version");
		launcherStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
