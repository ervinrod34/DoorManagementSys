package report;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

import order.Order;

public class BlueprintReport extends PDFReport {

	private Order order;
	private Date date;
	private PDImageXObject firstPage;
	private PDPageContentStream stream;
	private PDRectangle mediaBox;
	
	public BlueprintReport (Order order) throws IOException {
		
		this.order = order;
		date = new Date ();
		
		setDoc(new PDDocument ());
		setPages (getDoc().getPages());
		setCurrentPage (new PDPage (PDRectangle.A4));
		mediaBox = getCurrentPage().getMediaBox();
		mediaBox.setLowerLeftX(70);
		mediaBox.setLowerLeftY(70);
		getCurrentPage().setRotation(270);
		getPages().add (getCurrentPage());
		setPageNumber (1);
		setCoordinates (new HashMap <String, XYCoordinate> ());
		
		setStream (new PDPageContentStream (getDoc(), getCurrentPage(), PDPageContentStream.AppendMode.APPEND, false));
		
		stream = getStream();
		
		assignFileName ("Blueprint.pdf");
		
		firstPage = PDImageXObject.createFromFile("SingleDoorSide.PNG", getDoc());
		
		setUpFirstPage();
		
	}
	
	@Override
	public void setUpFirstPage() throws IOException {
		
		System.out.println("Setting up Blueprint...");
		
		setStreamFont(12);
		stream.drawImage(firstPage, mediaBox.getLowerLeftX(), mediaBox.getLowerLeftY(), 465, 710);
	}
	
	@Override
	public void populateReport() throws IOException {
		
		System.out.println("Populating Blueprint...");
		
		stream.beginText();
		stream.setTextMatrix(Matrix.getRotateInstance(-Math.PI/2, PDRectangle.A4.getWidth()/2, PDRectangle.A4.getHeight()/2));
		stream.newLineAtOffset(0, 0);
		//stream.showText("HHOAEIFHO");
		stream.endText();
		
		getStream().close();
	}

	//Not needed for this report
	@Override 
	public void setUpNextPage() {}
	
}
