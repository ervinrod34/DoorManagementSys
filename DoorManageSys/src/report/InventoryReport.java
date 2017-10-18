package report;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import inventory.Inventory;


/**
 * To generate report you must call populateReport
 * this InventoryReport object must be saved and closed.
 * @author Richard Salcedo
 *
 */
public class InventoryReport {

	private ArrayList <Inventory> inventory;
	private PDDocument doc;
	private PDPageTree page;
	private PDPage currentPage;
	private PDPageContentStream stream;
	private Date date;
	private File saveFile;
	private int x, y;
	private final int MAX_PER_PAGE = 40, START_X = 70, START_Y = 660;
	
	public InventoryReport (ArrayList <Inventory> inventory) throws IOException {
		
		this.inventory = inventory;
		
		try {
			doc = PDDocument.load(new File ("BlankInventoryPage.pdf"));
		} catch (InvalidPasswordException e) {e.printStackTrace();}
		
		page = doc.getPages();
		
		currentPage = page.get(0);
		
		date = new Date ();
		
		System.out.println(date);
		
		String fileName = "Inventory";
		fileName += date.toString().substring(4, 7);
		fileName += date.toString().substring(8, 10);
		fileName += date.toString().substring(24, 28);
		fileName += ".pdf";
		
		saveFile = new File (fileName);

		stream = new PDPageContentStream (doc, currentPage, PDPageContentStream.AppendMode.APPEND, false);
		stream.setFont(PDType1Font.HELVETICA, 9);

		x = START_X;
		y = START_Y;
		
	}
	
	public void populateReport () throws IOException {
		
		int count = 1;
		
		for (Inventory inventory : inventory) {
			
			if (((count % MAX_PER_PAGE) == 1) && count != 1) {
				setCurrentPage (getNewPage ());
				setStreamPage (currentPage);
				setStreamFont ();
				x = 70;
				y  = 660;
			}
			
			stream.beginText();				
			stream.newLineAtOffset(x, y);
			stream.showText(inventory.getItemNo());
			stream.endText();
			
			x += 65;
			stream.beginText();
			stream.newLineAtOffset(x, y);
			stream.showText(inventory.getManufacturer());
			stream.endText();
			
			x += 100;
			stream.beginText();
			stream.newLineAtOffset(x, y);
			stream.showText(inventory.getUnitOfMeasure());
			stream.endText();
			
			x += 75;
			stream.beginText();			
			stream.newLineAtOffset(x, y);
			stream.showText(inventory.getMinQuantity() + "");
			stream.endText();
			
			x += 70;
			stream.beginText();			
			stream.newLineAtOffset(x, y);
			stream.showText(inventory.getMaxQuantity() + "");
			stream.endText();
			
			x += 67;
			stream.beginText();			
			stream.newLineAtOffset(x, y);
			stream.showText(inventory.getCategory());
			stream.endText();
			
			x += 70;
			stream.beginText();			
			stream.newLineAtOffset(x, y);
			stream.showText(inventory.getQuantity() + "");
			stream.endText();
			
			count ++;
			x = 70;
			y -= 12;
			
		}
		
		stream.close();
		
	}
	
	public void setStreamFont () throws IOException {
		stream.setFont(PDType1Font.HELVETICA, 9);
	}
	
	public void save () throws IOException {
		doc.save(saveFile);
	}
	
	public void close () throws IOException {
		doc.close();
	}
	
	public void setCurrentPage (PDPage page) {
		currentPage = page;
	}
	
	public void setStreamPage (PDPage page) {
		try {
			stream.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			stream = new PDPageContentStream (doc, currentPage, PDPageContentStream.AppendMode.APPEND, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public PDPage getNewPage () {
		PDDocument tempDoc = null;
		PDPage tempPage = null;
		
		try {
			tempDoc = PDDocument.load(new File ("BlankInventoryPage.pdf"));
		} catch (InvalidPasswordException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		tempPage = tempDoc.getPage(0);
		page.add(tempPage);
		
		return tempPage;
	}
	
}


































