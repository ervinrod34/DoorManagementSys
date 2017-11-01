package report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import customer.Customer;
import inventory.Inventory;
import order.Order;
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
	private String fileName, customerName, poNumber;
	private Date date;
	private PDPageContentStream stream;
	private PDPage currentPage;
	private PDPageTree pages;
	private int y, quantity, orderNumber;
	private double cost;
	private final int ORDER_X = 380, ORDER_Y = 680;
	private final int DATE_X = 375, DATE_Y = 644;
	private final int START_Y = 585;
	private final int QTY_X = 90, PRICE_X = 490, DESC_X = 135;
	private final int FINAL_Y = 67, PAGE_BOTTOM = 110;

	public QuoteReport (Order order) {
		
		date = new Date ();
		
		quote = order.getQuote();
		
		orderNumber = order.getId();
		poNumber = order.getCustomerPurchaseOrderNumber();
		customerName = order.getCustomerName();

		stream = null;
		
		try {
			doc = PDDocument.load(new File ("BlankQuote.pdf"));
		} catch (InvalidPasswordException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		pages = doc.getPages();
		currentPage = pages.get(0);
		
		assignFileName ("");
		
		try {
			stream = new PDPageContentStream (doc, currentPage, PDPageContentStream.AppendMode.APPEND, false);
			System.out.println("stream set to first page");
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
		stream.newLineAtOffset(ORDER_X, ORDER_Y);
		stream.showText(orderNumber + "");
		stream.endText();
		
		System.out.println("should have printed orderNumber.");
		
		stream.setFont(PDType1Font.HELVETICA, 10);
		stream.beginText();
		stream.newLineAtOffset(DATE_X, DATE_Y);
		stream.showText(date.toString());
		stream.endText();
		
		System.out.println("Should have printed Date.");
		
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
			
			if (y < PAGE_BOTTOM) {
				setCurrentPage (getNewPage());
				setStreamPage (currentPage);
				y = START_Y;
			}
			
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
			System.out.println("Save successful.");
		} catch(FileNotFoundException e) {
			System.err.print("Save unsuccessful");
		}
	}
	
	public void close () throws IOException {
		System.out.println("Closing document...");
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
	
	public void setStreamPage (PDPage page) {
		try {
			stream.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			stream = new PDPageContentStream (doc, page, PDPageContentStream.AppendMode.APPEND, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setCurrentPage (PDPage page) {
		currentPage = page;
	}
	
	public PDPage getNewPage () {
		PDDocument tempDoc = null;
		PDPage tempPage = null;
		
		try {
			tempDoc = PDDocument.load(new File ("QuoteNextPage.pdf"));
		} catch (InvalidPasswordException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		tempPage = tempDoc.getPage(0);
		doc.getPages().add(tempPage);
		
		return tempPage;
	}
	
	
}
