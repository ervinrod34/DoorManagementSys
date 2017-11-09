package report;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public abstract class PDFReport implements Report {

	private PDDocument doc;
	private PDPageContentStream stream;
	private PDPage currentPage, tempPage;
	private PDPageTree pages;
	private HashMap <String, XYCoordinate> coordinates;
	private int pageNumber;
	private String fileName;
	
	public PDDocument getDoc () {return doc;}
	public void setDoc (PDDocument doc) {this.doc = doc;}
	
	public PDPageContentStream getStream() {return stream;}
	public void setStream(PDPageContentStream stream) {this.stream = stream;}
	public PDPage getCurrentPage() {return currentPage;}
	public void setCurrentPage(PDPage currentPage) {this.currentPage = currentPage;}
	public PDPage getTempPage() {return tempPage;}
	public void setTempPage(PDPage tempPage) {this.tempPage = tempPage;}
	public PDPageTree getPages() {return pages;}
	public void setPages(PDPageTree pages) {this.pages = pages;}
	public HashMap<String, XYCoordinate> getCoordinates() {return coordinates;}
	public void setCoordinates(HashMap<String, XYCoordinate> coordinates) {this.coordinates = coordinates;}
	public int getPageNumber () {return pageNumber;}
	public void setPageNumber (int pageNumber) {this.pageNumber = pageNumber;}
	
	public void setStreamPage (PDPage page) throws IOException {

		stream.close();
		stream = new PDPageContentStream (doc, page, PDPageContentStream.AppendMode.APPEND, false);
	}
	
	public PDPage getNewPage () {

		tempPage = new PDPage ();
		pages.add(tempPage);
		return tempPage;
	}
	
	public void setStreamFont (int size) throws IOException {
		stream.setFont(PDType1Font.HELVETICA, size);
	}
	
	public void save () throws IOException {
		
		try {
			doc.save(System.getProperty("user.home") + "\\Desktop\\" + fileName);
			System.out.println("Save successful.");
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			System.err.print("Save unsuccessful");
		}
	}
	
	public void close () throws IOException {
		System.out.println("Closing document...");
		doc.close();
	}
	
	public void assignFileName (String fileName) {
		this.fileName = fileName;
	}

}
