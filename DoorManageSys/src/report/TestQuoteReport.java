package report;

import java.io.IOException;
import java.util.ArrayList;
import inventory.Inventory;
import quoteproduct.Product;
import quoteproduct.Quote;
import order.Order;

public class TestQuoteReport {

	public static void main(String[] args) throws IOException {
		
		Order order = new Order ();
		
		Quote quote = new Quote ();
		
		order.setQuote(quote);
		order.setCustomerPurchaseOrderNumber("0004473R");
		order.setId(44553);
		order.setCustomerName("Richard Salcedo");
		
		ArrayList <Inventory> list = new ArrayList <Inventory> ();
		ArrayList <Inventory> list2 = new ArrayList <Inventory> ();
		ArrayList <Inventory> list3 = new ArrayList <Inventory> ();
		
		Inventory inventory1 = new Inventory (0, "JFHH388493", "STANLEY", "HEIU3384", "STANLEY", "4.5x5.5", "US32D", "", "ea", 10.0, 28.98, 50, 10, 100, "Hinge", false, "3030");
		Inventory inventory2 = new Inventory (22, "FNdD22-3", "STANLEY", "FN20070", "STANLEY", "4.5x5.5", "US32D", "", "ea", 10.0, 78.99, 50, 10, 100, "Lock", false, "3030");
		Inventory inventory3 = new Inventory (13, "FNJA333882", "STANLEY", "FNN88-002", "STANLEY", "4.5x5.5", "US32D", "", "ea", 10.0, 35.49, 50, 10, 100, "Window", false, "3030");
		
		for (int i = 0; i < 15; i++) {
			list.add(inventory1);
		}
		
		for (int i = 0; i < 22; i++) {
			list.add(inventory2);
		}
		
		for (int i = 0; i < 7; i++) {
			list.add(inventory3);
		}
		
		for (int i = 0; i < 20; i++) {
			list2.add(inventory1);
		}
		
		for (int i = 0; i < 14; i++) {
			list2.add(inventory2);
		}
		
		for (int i = 0; i < 9; i++) {
			list2.add(inventory3);
		}
		
		for (int i = 0; i < 11; i++) {
			list3.add(inventory1);
		}
		
		for (int i = 0; i < 18; i++) {
			list3.add(inventory2);
		}
		
		for (int i = 0; i < 10; i++) {
			list3.add(inventory3);
		}
		
		
		
		for (int i = 0; i < 15; i++) {
			quote.getProducts().add(new Product (15, list, 45));
		}
		
		for (int i = 0; i < 8; i++) {
			quote.getProducts().add(new Product (190, list2, 45));
		}
		
		for (int i = 0; i < 25; i++) {
			quote.getProducts().add(new Product (11, list3, 45));
		}
		
		QuoteReport report = new QuoteReport (order);
		
		report.populateReport();
		report.save();
		report.close();

	}

}
