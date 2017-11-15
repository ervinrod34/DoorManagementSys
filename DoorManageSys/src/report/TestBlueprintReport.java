package report;

import java.io.IOException;
import java.util.ArrayList;

import blueprint.Blueprint;
import inventory.Inventory;
import order.Order;
import quoteproduct.Product;
import quoteproduct.Quote;

public class TestBlueprintReport {

	public static void main(String[] args) throws IOException {
		
		Order order = new Order ();
			
		Quote quote = new Quote ();
		
		order.setQuote(quote);
		order.setCustomerPurchaseOrderNumber("0004473R");
		order.setId(44553);
		order.setCustomerName("Richard Salcedo");
		
		Blueprint blueprint = new Blueprint ();
		
		blueprint.setDimension("36,84");
		blueprint.setFrame("3,3,3");
		blueprint.setHingeSpaces("12,30,30");
		blueprint.setStrikeHeight("42");
		blueprint.setInventoryNotes("Start");
		blueprint.setNotes("Start");
		
		order.setBlueprint(blueprint);
		
		ArrayList <Inventory> list = new ArrayList <Inventory> ();
		
		list.add(new Inventory (0, "JFHH388493", "STANLEY", "HEIU3384", "STANLEY", "43x84", "US32D", "", "ea", 10.0, 15.0, 50, 10, 100, "door", false, "3030"));
		quote.getProducts().add(new Product (1, list, 45));

		
		BlueprintReport report = new BlueprintReport (order);
		
		report.populateReport();
		report.save();
		report.close();

	}

}
