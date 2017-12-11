package report;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.MasterController;
import application.MasterViewController;
import applicationhelper.PageTypes;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import inventory.*;

public class ReportsExportController implements Initializable{

	@FXML private ChoiceBox<ReportTypes> reportsSelection;
	private ObservableList<ReportTypes> observableReports;
	
	@FXML private TextField fileNameField;
	private String fileName;
	
	@FXML private Button export;
	
	@FXML private void handleExport(ActionEvent ae) {
		Object source = ae.getSource();
		ReportTypes reportType = this.reportsSelection.getSelectionModel().getSelectedItem();
		this.fileName = this.fileNameField.getText();
		
		if((source == export) && (reportType == ReportTypes.INVENTORY)) {
			this.exportInventoryReport();
		}
		
		else if (source == export && (reportType == ReportTypes.QUOTE)) {
			this.exportQuoteReport();
		}
		
		else if (source == export && (reportType == ReportTypes.BLUEPRINT)) {
			this.exportBlueprintReport();
		}
	}
	
	public void exportInventoryReport() {
		try {
			InventoryReport inventoryReport = new InventoryReport(this.getInventoryRecords(), this.fileName);
			inventoryReport.populateReport();
			inventoryReport.save();
			inventoryReport.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void exportQuoteReport () {
		
		try {
			QuoteReport quoteReport = new QuoteReport (MasterController.getInstance().getSelectedOrder());
			quoteReport.assignFileName(fileName);
			quoteReport.populateReport();
			quoteReport.save();
			quoteReport.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void exportBlueprintReport () {

		try {
			BlueprintReport blueprintReport = new BlueprintReport (MasterController.getInstance().getSelectedOrder());
			blueprintReport.assignFileName(fileName);
			blueprintReport.populateReport();
			blueprintReport.save();
			blueprintReport.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private List<Inventory> getInventoryRecords() {
		List<Inventory> inventories = MasterController.getInstance().getInventoryGateway().getInventory();
		
		return inventories;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.observableReports = this.reportsSelection.getItems();
		this.observableReports.add(ReportTypes.INVENTORY);
		this.observableReports.add(ReportTypes.QUOTE);
		this.observableReports.add(ReportTypes.BLUEPRINT);
		reportsSelection.getSelectionModel().selectedIndexProperty().addListener(new ReportTypeSelectionListener ());
	}
	
	private class ReportTypeSelectionListener implements ChangeListener <Number> {

		@Override
		public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
			
			switch (newValue.intValue()) {
				
			case 1:
				MasterViewController.getInstance().changeView(PageTypes.QUOTE_REPORT_PAGE);
				break;
				
			case 2:
				MasterViewController.getInstance().changeView(PageTypes.BLUEPRINT_REPORT_PAGE);
				break;
			}
			
		}		
	}
}
