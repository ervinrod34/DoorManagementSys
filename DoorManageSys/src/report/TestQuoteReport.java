package report;

import java.io.IOException;
import java.util.ArrayList;

import inventory.Inventory;
import quoteproduct.Product;
import quoteproduct.Quote;

public class TestQuoteReport {

	public static void main(String[] args) throws IOException {
		
		Quote quote = new Quote ();
		
		ArrayList <Inventory> list = new ArrayList <Inventory> ();
		
		for (int i = 0; i < 3; i++) 
			list.add(new Inventory (0, "JFHH388493", "STANLEY", "HEIU3384", "STANLEY", "4.5x5.5", "US32D", "", "ea", 10.0, 15.0, 50, 10, 100, "Hinge", false, "3030"));
		
		for (int i = 0; i < 6; i++)
			quote.getProducts().add(new Product (15, list, 25.99));
		
		QuoteReport report = new QuoteReport (quote);
		
		report.populateReport();
		report.save();
		report.close();

	}

}
