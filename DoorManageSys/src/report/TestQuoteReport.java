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
		
		for (int i = 0; i < 15; i++) {
			list.add(new Inventory (0, "JFHH388493", "STANLEY", "HEIU3384", "STANLEY", "4.5x5.5", "US32D", "", "ea", 10.0, 15.0, 50, 10, 100, "Hinge", false, "3030"));
		}
		
		for (int i = 0; i < 15; i++) {
			quote.getProducts().add(new Product (15, list, 45));
		}
		
		for (int i = 0; i < 8; i++) {
			quote.getProducts().add(new Product (190, list, 45));
		}
		
		for (int i = 0; i < 25; i++) {
			quote.getProducts().add(new Product (11, list, 45));
		}
		
		QuoteReport report = new QuoteReport (order);
		
		report.populateReport();
		report.save();
		report.close();

	}

}
