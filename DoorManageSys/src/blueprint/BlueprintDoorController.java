package blueprint;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.MasterController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import quoteproduct.Product;
import report.BlueprintReport;

public class BlueprintDoorController implements Initializable {
	
	@FXML private TextField width;
	@FXML private TextField height;
	
	@FXML private TextField hingeTopGap;
	@FXML private TextField hingeGapOne;
	@FXML private TextField hingeGapTwo;
	
	@FXML private TextField strikeHeight;
	
	@FXML private TextField frame;
	@FXML private Label frameOne;
	@FXML private Label frameTwo;
	@FXML private Label frameThree;
	
	@FXML private TextArea notes;
	@FXML private TextArea inventoryNotes;
	
	@FXML private Button saveBlueprint;
	@FXML private Button createReport;
	
	private Blueprint blueprint;
	private Product product;
	
	public BlueprintDoorController(Blueprint blueprint, Product product) {
		this.blueprint = blueprint;
		this.product = product;
	}
	
	@FXML private void handleBlueprint(ActionEvent ae) {
		Object source = ae.getSource();
		
		if(source == saveBlueprint) {
			this.updateBlueprint();
			this.blueprint.save();
			System.out.println("Saved blueprint: " + this.blueprint.toString() + "\n" + this.product.getId());
		} else if(source == createReport) {
			try {
				this.updateBlueprint();
				this.blueprint.save();
				BlueprintReport blueprintReport = new BlueprintReport(this.product);
				blueprintReport.assignFileName("Blueprint_Product" + this.product.getId());
				blueprintReport.populateReport();
				blueprintReport.save();
				blueprintReport.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.assignFrameLabelsAutoUpdate();
		this.setDimensionToFields();
		this.setHingeValuesToFields();
		this.strikeHeight.setText(this.blueprint.getStrikeHeight());
		
		this.frame.setText(this.blueprint.getFrame());
		this.assignValueToFrameLabels();
		
		this.notes.setText(this.blueprint.getNotes());
		this.inventoryNotes.setText(this.blueprint.getInventoryNotes());
	}
	
	private void setDimensionToFields() {
		if(this.blueprint.getDimension().contains("x")) {
			String[] dimension = this.blueprint.getDimension().split("x");
			this.width.setText(dimension[0]);
			this.height.setText(dimension[1]);
		} else {
			this.width.setText("");
			this.height.setText("");
		}
	}
	
	private void setHingeValuesToFields() {
		if(this.blueprint.getHingeSpaces().contains(",")) {
			String[] hinges = this.blueprint.getHingeSpaces().split(",");
			this.hingeTopGap.setText(hinges[0]);
			this.hingeGapOne.setText(hinges[1]);
			this.hingeGapTwo.setText(hinges[2]);
		} else {
			this.hingeTopGap.setText("");
			this.hingeGapOne.setText("");
			this.hingeGapTwo.setText("");
		}
	}
	
	private void assignFrameLabelsAutoUpdate() {
		this.frame.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent arg0) {
				assignValueToFrameLabels();
			}
		});
	}
	
	public void assignValueToFrameLabels() {
		this.frameOne.setText(this.frame.getText());
		this.frameTwo.setText(this.frame.getText());
		this.frameThree.setText(this.frame.getText());
	}
	
	public void updateBlueprint() {
		Blueprint saveBlueprint = new Blueprint(this.blueprint.getId());
		
		saveBlueprint.setProductID(this.product.getId());
		saveBlueprint.setDimension(this.width.getText() + "x" + this.height.getText());
		saveBlueprint.setStrikeHeight(this.strikeHeight.getText());
		saveBlueprint.setFrame(this.frame.getText());
		saveBlueprint.setHingeSpaces(this.hingeTopGap.getText() + "," + 
				this.hingeGapOne.getText() + "," + this.hingeGapTwo.getText());
		saveBlueprint.setNotes(this.notes.getText());
		saveBlueprint.setInventoryNotes(this.inventoryNotes.getText());
		this.blueprint = saveBlueprint;
	}
}
