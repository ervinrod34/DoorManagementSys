package report;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
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
	private int y, totalQuantity, orderNumber, pageNumber;
	private double totalCost;
	private final int ORDER_X = 380, ORDER_Y = 680;
	private final int DATE_X = 377, DATE_Y = 644;
	private final int START_Y = 585;
	private final int QTY_X = 90, PRICE_X = 490, DESC_X = 135;
	private final int TOTAL_Y = 67, PAGE_BOTTOM = 120;
	private final int FIRSTPAGE_X = 60, FIRSTPAGE_Y = 45, FIRSTPAGE_WIDTH = 500, FIRSTPAGE_HEIGHT = 710;
	private StringBuffer price;
	private HashMap <Integer, Integer> productQuantity;
	private HashMap <Integer, Integer> inventoryQuantity;
	private ArrayList <Product> differentProducts;
	private ArrayList <Inventory> differentInventory;
	private PDImageXObject firstPage, nextPage;

	public QuoteReport (Order order) throws IOException {
		
		this.order = order;
		
		doc = new PDDocument ();
		date = new Date ();
		tempPage = new PDPage ();
		firstPage = PDImageXObject.createFromFile("C:\\Program Files\\DPM\\pdf-templates\\QuoteFirstPage.PNG", doc);
		nextPage = PDImageXObject.createFromFile("C:\\Program Files\\DPM\\pdf-templates\\QuoteNextPage.PNG", doc);	
		quote = order.getQuote();
		orderNumber = order.getId();
		poNumber = order.getCustomerPurchaseOrderNumber();
		customerName = order.getCustomerName();
		differentProducts = new ArrayList <Product> ();
		
		stream = null;
		pages = doc.getPages();
		currentPage = tempPage;
		pages.add(tempPage);
		tempPage = null;
		
		assignFileName ("");
		productQuantity = new HashMap <Integer, Integer> ();
		stream = new PDPageContentStream (doc, currentPage, PDPageContentStream.AppendMode.APPEND, false);
		stream.setFont(PDType1Font.HELVETICA, 16);
		setUpFirstPage ();
		
		
	}
	
	public void setUpFirstPage () throws IOException {
		
		pageNumber = 1;
		
		System.out.println("Setting up document...");
		stream.drawImage(firstPage, FIRSTPAGE_X, FIRSTPAGE_Y, FIRSTPAGE_WIDTH, FIRSTPAGE_HEIGHT);
		setStreamFont (12);
		stream.beginText();
		stream.newLineAtOffset(490, 717);
		stream.showText("Page " + pageNumber);
		stream.endText();
		
		for (Product product : quote.getProducts()) {
			totalCost += product.calculateTotalCost();
			totalQuantity++;
			
			if (productQuantity.containsKey(product.getId())) {
				productQuantity.put(product.getId(), productQuantity.get(product.getId()) + 1);
			}
			else {
				productQuantity.put(product.getId(), 1);
				differentProducts.add(product);
			}
		}
		
		setStreamFont (10);

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
		y = START_Y;
		setStreamFont (10);
		int unit = 0;
		
		for (Product product : differentProducts) {
			
			inventoryQuantity = new HashMap <Integer, Integer> ();
			differentInventory = new ArrayList <Inventory> ();
			unit++;
			
			for (Inventory inventory : product.getInventories()) {
				if (inventoryQuantity.containsKey(inventory.getId()))
					inventoryQuantity.put(inventory.getId(), inventoryQuantity.get(inventory.getId()) + 1);
				else {
					inventoryQuantity.put(inventory.getId(), 1);
					differentInventory.add(inventory);
				}
			}
			
			skipLine();
			
			stream.beginText();
			stream.newLineAtOffset(DESC_X, y);
			stream.showText("Unit # " + unit);
			stream.endText();
			
			skipLine();
			
			for (Inventory inventory : differentInventory) {
				
				stream.beginText();
				stream.newLineAtOffset(QTY_X, y);
				stream.showText(inventoryQuantity.get(inventory.getId()) + "");
				stream.endText();
				
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
				
				skipLine();
			}
			
			stream.beginText();
			stream.newLineAtOffset(DESC_X, y);
			stream.showText("PRICE PER UNIT # " + unit);
			stream.endText();
			
			stream.beginText();
			stream.newLineAtOffset(PRICE_X, y);
			price = new StringBuffer ();
			price.append(String.format("$%,.2f", product.calculateTotalCost()));
			stream.showText(price.toString());
			stream.endText();
			
			skipLine();
			
			stream.beginText();
			stream.newLineAtOffset(QTY_X, y);
			stream.showText(productQuantity.get(product.getId()) + "");
			stream.endText();
			
			stream.beginText();
			stream.newLineAtOffset(PRICE_X, y);
			stream.showText("       x" + productQuantity.get(product.getId()));
			stream.endText();
			
			stream.beginText();
			stream.newLineAtOffset(DESC_X, y);
			stream.showText("QUANTITY OF UNIT # " + unit);
			stream.endText();
			
			skipLine();
			
			stream.beginText();
			stream.newLineAtOffset(PRICE_X, y);
			stream.showText("-------------");
			stream.endText();
			
			skipLine();
			
			stream.beginText();
			stream.newLineAtOffset(DESC_X, y);
			stream.showText("TOTAL");
			stream.endText();
			
			stream.beginText();
			stream.newLineAtOffset(PRICE_X, y);
			price = new StringBuffer ();
			price.append(String.format("$%,.2f", product.calculateTotalCost()*productQuantity.get(product.getId())));
			stream.showText(price.toString());
			stream.endText();
			
			skipLine();
			
			stream.beginText();
			stream.newLineAtOffset(DESC_X, y);
			stream.showText("------------------------------------------------------------------------------------------------------");
			stream.endText();
			
			y -= 12;
			skipLine();
			
		}
		
		stream.beginText();
		stream.newLineAtOffset(DESC_X, y);
		stream.showText("OTHER CHARGES: FREIGHT");
		stream.endText();
		
		stream.beginText();
		stream.newLineAtOffset(PRICE_X, y);
		stream.showText("$0.00");
		stream.endText();

		skipLine();
		
		stream.beginText();
		stream.newLineAtOffset(DESC_X, y);
		stream.showText("GRAND TOTAL");
		stream.endText();
		
		stream.beginText();
		stream.newLineAtOffset(PRICE_X, y);
		price = new StringBuffer ();
		price.append(String.format("$%,.2f", totalCost));
		stream.showText(price.toString());
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
			fileName += order.getId() + "";
			fileName += ".pdf";
		} else {
			fileName = inputtedName + ".pdf";
		}
	}
	
	public void setStreamPage (PDPage page) throws IOException {

		stream.close();
		stream = new PDPageContentStream (doc, page, PDPageContentStream.AppendMode.APPEND, false);
	}
	
	public void setCurrentPage (PDPage page) {
		currentPage = page;
	}
	
	public void setUpNextPage () throws IOException {

		stream.drawImage(nextPage, FIRSTPAGE_X, FIRSTPAGE_Y, FIRSTPAGE_WIDTH, FIRSTPAGE_HEIGHT);
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
	
	public void skipLine () throws IOException {
		y -= 12;
		if (y < PAGE_BOTTOM) {
			setCurrentPage (getNewPage());
			setStreamPage (currentPage);
			setUpNextPage ();
			setStreamFont (10);
			y = START_Y -10;
		}
	}
	
}
