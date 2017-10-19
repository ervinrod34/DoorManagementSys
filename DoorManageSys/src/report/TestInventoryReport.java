package report;

import java.io.IOException;
import java.util.ArrayList;

import inventory.Inventory;

/**
 * This is a class that tests the InventoryReport. When we use the InventoryReport object, we
 * must call populateReport(), save(), and close(). We can pass it the arrayList from getInventory()
 * from the InventoryGateway
 * @author Richard Salcedo
 *
 */
public class TestInventoryReport {

	public static void main(String[] args) throws IOException {

		ArrayList <Inventory> list = new ArrayList <Inventory> ();
		
		for (int i = 0; i < 50; i++) {
			list.add(new Inventory (0, "JFHH388493", "STANLEY", "HEIU3384", "STANLEY", "4.5x5.5", "US32D", "", "ea", 10.0, 15.0, 50, 10, 100, "Hinge", false, "3030"));
		}
		
		InventoryReport report = new InventoryReport (list);
		
		report.populateReport();
		report.save();
		report.close();
	}

}
