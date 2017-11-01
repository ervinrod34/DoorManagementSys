package report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import customer.Customer;
import inventory.Inventory;
import quoteproduct.Product;
import quoteproduct.Quote;

/**
 * Must be populated, saved, and closed
 * call assignFileName on this object to change save file name before calling save
 * @author TeamNoNameYet
 *
 */

public class QuoteReport {
	
	private Quote quote;
	private PDDocument doc;
	private String fileName;
	private Date date;
	private PDPageContentStream stream;
	private int x, y, quantity;
	private double cost;
	private final int ID_X = 380, ID_Y = 680;
	private final int DATE_X = 375, DATE_Y = 644;
	private final int START_Y = 585;
	private final int QTY_X = 90, PRICE_X = 490, DESC_X = 135;
	private final int FINAL_Y = 67;
	
	//Quote should have customer, this is temp
	private Customer customer;

	public QuoteReport (Quote quote) {
		
		this.quote = quote;
		date = new Date ();
		stream = null;
		
		try {
			doc = PDDocument.load(new File ("BlankQuote.pdf"));
		} catch (InvalidPasswordException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assignFileName ("");
		
		try {
			stream = new PDPageContentStream (doc, doc.getPage(0), PDPageContentStream.AppendMode.APPEND, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			stream.setFont(PDType1Font.HELVETICA, 16);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void populateReport () throws IOException {
		
		StringBuffer price = null;
		
		quantity = 0;
		cost = 0;
		
		stream.beginText();
		stream.newLineAtOffset(ID_X, ID_Y);
		stream.showText(quote.getId() + "");
		stream.endText();
		
		stream.setFont(PDType1Font.HELVETICA, 10);
		stream.beginText();
		stream.newLineAtOffset(DATE_X, DATE_Y);
		stream.showText(date.toString());
		stream.endText();
		
		y = START_Y;
		
		stream.setFont(PDType1Font.HELVETICA, 10);
		
		for (Product product : quote.getProducts()) {
			
			quantity++;
			stream.beginText();
			stream.newLineAtOffset(QTY_X, y);
			stream.showText("1");
			stream.endText();
			
			stream.beginText();
			stream.newLineAtOffset(DESC_X, y);
			stream.showText("**Product Details Listed Below");
			stream.endText();
			
			stream.beginText();
			stream.newLineAtOffset(PRICE_X, y);
			price = new StringBuffer ();
			price.append(String.format("$%.2f", product.calculateTotalCost()));
			stream.showText(price.toString());
			stream.endText();
			
			cost += product.calculateTotalCost();
			
			y -= 12;
			
			for (Inventory inventory : product.getInventories()) {
				
				stream.beginText();
				stream.newLineAtOffset(DESC_X, y);
				stream.showText(inventory.getItemNo() + " " + inventory.getManufacturer() + " " + inventory.getCategory() + " " + inventory.getSize());
				stream.endText();
				
				price = new StringBuffer ();
				price.append(String.format("$%.2f", inventory.getSellingPrice()));
				
				stream.beginText();
				stream.newLineAtOffset(PRICE_X, y);
				stream.showText(price.toString());
				stream.endText();
				
				y -= 12;
				
			}
			
			y -= 12;
		}
		
		stream.beginText();
		stream.newLineAtOffset(QTY_X, FINAL_Y);
		stream.showText(quantity + "");
		stream.endText();
		
		stream.beginText();
		stream.newLineAtOffset(PRICE_X, FINAL_Y);
		stream.showText(" " + cost);
		stream.endText();
		
		
		stream.close();
		
	}
	
	public void save () throws IOException {
		
		try {
			doc.save(System.getProperty("user.home") + "\\Desktop\\" + fileName);
		} catch(FileNotFoundException e) {
			System.err.print("Save unsuccessful");
		}
	}
	
	public void close () throws IOException {
		doc.close();
	}
	
	public void assignFileName(String inputtedName) {
		
		if (inputtedName.length() == 0) {
			fileName = "Quote Number ";
			fileName += quote.getId() + "";
			fileName += ".pdf";
		} else {
			fileName = inputtedName + ".pdf";
		}
	}
	
	public void setCustomer (Customer customer) {
		this.customer = customer;
	}
	
	
	
}
