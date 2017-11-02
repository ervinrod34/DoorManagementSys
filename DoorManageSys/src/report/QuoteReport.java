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
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

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
	private Order order;
	private PDDocument doc;
	private String fileName, customerName, poNumber;
	private Date date;
	private PDPageContentStream stream;
	private PDPage currentPage, tempPage;
	private PDPageTree pages;
	private int y, quantity, orderNumber, pageNumber, productQuantity;
	private int newProductY, currentProductID;
	private double cost;
	private final int ORDER_X = 380, ORDER_Y = 680;
	private final int DATE_X = 377, DATE_Y = 644;
	private final int START_Y = 585;
	private final int QTY_X = 90, PRICE_X = 490, DESC_X = 135;
	private final int TOTAL_Y = 67, PAGE_BOTTOM = 110;
	private final int FIRSTPAGE_X = 60, FIRSTPAGE_Y = 45, FIRSTPAGE_WIDTH = 500, FIRSTPAGE_HEIGHT = 710;
	
	
	private PDImageXObject firstPage, nextPage;

	public QuoteReport (Order order) {
		
		this.order = order;
		
		doc = new PDDocument ();
		date = new Date ();
		tempPage = new PDPage ();
		
		try {
			firstPage = PDImageXObject.createFromFile("QuoteFirstPage.PNG", doc);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			nextPage = PDImageXObject.createFromFile("QuoteNextPage.PNG", doc);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		quote = order.getQuote();
		
		orderNumber = order.getId();
		poNumber = order.getCustomerPurchaseOrderNumber();
		customerName = order.getCustomerName();

		stream = null;
		
		pages = doc.getPages();
		
		currentPage = tempPage;
		
		pages.add(tempPage);
		
		tempPage = null;
		
		assignFileName ("");
		
		try {
			stream = new PDPageContentStream (doc, currentPage, PDPageContentStream.AppendMode.APPEND, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			stream.setFont(PDType1Font.HELVETICA, 16);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			setUpFirstPage ();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void setUpFirstPage () throws IOException {
		
		pageNumber = 1;
		
		System.out.println("Setting up document...");
		
		try {
			stream.drawImage(firstPage, FIRSTPAGE_X, FIRSTPAGE_Y, FIRSTPAGE_WIDTH, FIRSTPAGE_HEIGHT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		setStreamFont (12);
		stream.beginText();
		stream.newLineAtOffset(490, 717);
		stream.showText("Page " + pageNumber);
		stream.endText();
		
		for (Product product : quote.getProducts()) {
			cost += product.calculateTotalCost();
			quantity++;
		}
		
		setStreamFont (10);
		
		stream.beginText();
		stream.newLineAtOffset(QTY_X, TOTAL_Y);
		stream.showText(quantity + "");
		stream.endText();
		
		stream.beginText();
		stream.newLineAtOffset(PRICE_X, TOTAL_Y);
		stream.showText(" " + cost);
		stream.endText();

		stream.beginText();
		stream.newLineAtOffset(DATE_X, DATE_Y);
		stream.showText(date.toString());
		stream.endText();
		
		setStreamFont (14);
		
		stream.beginText();
		stream.newLineAtOffset(ORDER_X, ORDER_Y);
		stream.showText(orderNumber + "");
		stream.endText();	
		
		setStreamFont (12);
		
		stream.beginText();
		stream.newLineAtOffset(DESC_X + 10, ORDER_Y);
		stream.showText(customerName);
		stream.endText();
		
		stream.beginText();
		stream.newLineAtOffset(DESC_X + 50, DATE_Y);
		stream.showText(poNumber);
		stream.endText();
		
		
	}
	
	public void populateReport () throws IOException {
		
		System.out.println("Populating report...");
		
		StringBuffer price = null;
		
		y = START_Y;
		
		int items = 0;
		
		setStreamFont (10);
		
		boolean onNewProduct = true;
		
		for (Product product : quote.getProducts()) {
			
			items++;
			
			if (onNewProduct) {
				currentProductID = product.getId();
				productQuantity = 1;
			}
			
			else if (currentProductID == product.getId()) {
				productQuantity++;
				break; //We're never getting to the write quantity part because of this break statement
			}
			
			if (currentProductID != product.getId() || items == quote.getProducts().size()) {
				stream.beginText();
				stream.newLineAtOffset(QTY_X, newProductY);
				stream.showText(productQuantity + "");
				stream.endText();
				
				productQuantity = 1;
				currentProductID = product.getId();
				break;
			}
			
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
			
			newProductY = y;
			
			y -= 12;
			
			if (y < PAGE_BOTTOM) {
				setCurrentPage (getNewPage());
				setStreamPage (currentPage);
				setUpNextPage ();
				setStreamFont (10);
				y = START_Y -10;
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
				
				if (y < PAGE_BOTTOM) {
					setCurrentPage (getNewPage());
					setStreamPage (currentPage);
					setUpNextPage ();
					setStreamFont (10);
					y = START_Y -10;
				}
				
			}
			
			y -= 12;
			onNewProduct = false;
		}

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
			fileName += order.getId() + "";
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
	
	public void setUpNextPage () throws IOException {
		
		try {
			stream.drawImage(nextPage, FIRSTPAGE_X, FIRSTPAGE_Y, FIRSTPAGE_WIDTH, FIRSTPAGE_HEIGHT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		pageNumber ++;
		
		setStreamFont (12);
		stream.beginText();
		stream.newLineAtOffset(490, 717);
		stream.showText("Page " + pageNumber);
		stream.endText();
		
		
		
	}
	
	public PDPage getNewPage () {
		
		tempPage = new PDPage ();
		pages.add(tempPage);
		
		
		return tempPage;
	}
	
	public void setStreamFont (int size) throws IOException {
		stream.setFont(PDType1Font.HELVETICA, size);
	}
	
	
}
