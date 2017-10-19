package applauncher;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class LauncherController{

	@FXML private Button launch;
	@FXML private Button exit;
	@FXML private ProgressBar progressBar;
	@FXML private Label status;
	
	private static Timer timer;
	
	private static float progress;
	
	public LauncherController() {
		this.timer = new Timer();
	}
	
	@FXML private void handleLaunch(ActionEvent action) {
		Object source = action.getSource();
		
		if(source == exit) {
			Platform.exit();
		} else if(source == launch) {
			this.status.setText("Launching...");
			this.startLaunching();
			
			
			this.status.setText("Done!");
		}
	}

	private void startLaunching() {
		this.progressBar.setProgress(0f);
		this.timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				if(progress < 1.0f) {
					progress += 0.2f;
					progressBar.setProgress(progress);
				} else {
					progressBar.setProgress(progress);
					timer.cancel();
					launchJarFile();
					Platform.exit();
				}
			}
		}, 0, 1000);
	}
	
	public void launchJarFile() {
		try {
			Runtime.getRuntime().exec("cmd /c \"C:\\Program Files\\DPM\\launch.bat\"");
		} catch(IOException exception) {
			exception.printStackTrace();
		}
	}
}
