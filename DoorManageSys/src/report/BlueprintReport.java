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

import blueprint.Blueprint;
import order.Order;

public class BlueprintReport extends PDFReport {

	private Order order;
	private Date date;
	private PDImageXObject firstPage;
	private PDPageContentStream stream;
	private PDRectangle mediaBox;
	private String width, height, frameLeft, frameRight, frameTop;
	private String hingeSpaceTop, hingeSpaceMid, hingeSpaceBottom;
	private String strikeHeight;
	private HashMap <String, XYCoordinate> coordinate;
	
	public BlueprintReport (Order order) throws IOException {
		
		this.order = order;
		date = new Date ();
		
		setDoc(new PDDocument ());
		setPages (getDoc().getPages());
		setCurrentPage (new PDPage (PDRectangle.A4));
		mediaBox = getCurrentPage().getMediaBox();
		mediaBox.setLowerLeftX(70);
		mediaBox.setLowerLeftY(70);
		getCurrentPage().setRotation(90);
		getPages().add (getCurrentPage());
		setPageNumber (1);
		setCoordinates (new HashMap <String, XYCoordinate> ());
		coordinate = getCoordinates ();
		
		coordinate.put("width", new XYCoordinate (334, 448));
		coordinate.put("height", new XYCoordinate (191, 322));
		coordinate.put("frameLeft", new XYCoordinate (255,448));
		coordinate.put("frameRight", new XYCoordinate (410, 448));
		coordinate.put("frameTop", new XYCoordinate (425, 416));
		coordinate.put("hingeSpaceTop", new XYCoordinate (230, 416));
		coordinate.put("hingeSpaceMid", new XYCoordinate (230, 367));
		coordinate.put("hingeSpaceBottom", new XYCoordinate (230, 281));
		coordinate.put("strikeHeight", new XYCoordinate (455, 257));
		
		setStream (new PDPageContentStream (getDoc(), getCurrentPage(), PDPageContentStream.AppendMode.APPEND, false));
		
		stream = getStream();
		
		assignFileName ("Blueprint.pdf");
		
		firstPage = PDImageXObject.createFromFile("SingleDoorSide.PNG", getDoc());
		
		setUpFirstPage();
	}
	
	public void getValues () {
		
		Blueprint blueprint = order.getBlueprint();
		String[] sliced;
		
		sliced = blueprint.getDimension().split(",");
		width = sliced[0];
		height = sliced[1];
		
		sliced = blueprint.getFrame().split(",");
		frameLeft = sliced[0];
		frameRight = sliced[1];
		frameTop = sliced[2];
		
		sliced = blueprint.getHingeSpaces().split(",");
		hingeSpaceTop = sliced[0];
		hingeSpaceMid = sliced[1];
		hingeSpaceBottom = sliced[2];
		
		sliced = null;
		
		strikeHeight = blueprint.getStrikeHeight();
	}
	
	@Override
	public void setUpFirstPage() throws IOException {
		
		System.out.println("Setting up Blueprint...");
		
		setStreamFont(10);
		stream.drawImage(firstPage, mediaBox.getLowerLeftX(), mediaBox.getLowerLeftY(), 465, 710);
	}
	
	@Override
	public void populateReport() throws IOException {
		
		System.out.println("Populating Blueprint...");

		getValues();
		
		//Rotating the page goes clockwise
		//rotating this text matrix uses an angle. Think of the unit circle
		//must call setTextMatrix every fucking time. pretty dumb. Not sure 
		//how to set the stream to treat a rotated page as not rotated
		
		stream.beginText();		
		stream.setTextMatrix(Matrix.getRotateInstance(Math.PI/2, PDRectangle.A4.getWidth(), 0));		
		stream.newLineAtOffset(coordinate.get("height").getX(), coordinate.get("height").getY());
		stream.showText(height + "\"");
		stream.endText();
		
		stream.beginText();		
		stream.setTextMatrix(Matrix.getRotateInstance(Math.PI/2, PDRectangle.A4.getWidth(), 0));		
		stream.newLineAtOffset(coordinate.get("width").getX(), coordinate.get("width").getY());
		stream.showText(width + "\"");
		stream.endText();
		
		stream.beginText();		
		stream.setTextMatrix(Matrix.getRotateInstance(Math.PI/2, PDRectangle.A4.getWidth(), 0));		
		stream.newLineAtOffset(coordinate.get("frameLeft").getX(), coordinate.get("frameLeft").getY());
		stream.showText(frameLeft + "\"");
		stream.endText();
		
		stream.beginText();		
		stream.setTextMatrix(Matrix.getRotateInstance(Math.PI/2, PDRectangle.A4.getWidth(), 0));		
		stream.newLineAtOffset(coordinate.get("frameRight").getX(), coordinate.get("frameRight").getY());
		stream.showText(frameRight + "\"");
		stream.endText();
		
		stream.beginText();		
		stream.setTextMatrix(Matrix.getRotateInstance(Math.PI/2, PDRectangle.A4.getWidth(), 0));		
		stream.newLineAtOffset(coordinate.get("frameTop").getX(), coordinate.get("frameTop").getY());
		stream.showText(frameTop + "\"");
		stream.endText();
		
		stream.beginText();		
		stream.setTextMatrix(Matrix.getRotateInstance(Math.PI/2, PDRectangle.A4.getWidth(), 0));		
		stream.newLineAtOffset(coordinate.get("hingeSpaceTop").getX(), coordinate.get("hingeSpaceTop").getY());
		stream.showText(hingeSpaceTop + "\"");
		stream.endText();
		
		stream.beginText();		
		stream.setTextMatrix(Matrix.getRotateInstance(Math.PI/2, PDRectangle.A4.getWidth(), 0));		
		stream.newLineAtOffset(coordinate.get("hingeSpaceMid").getX(), coordinate.get("hingeSpaceMid").getY());
		stream.showText(hingeSpaceMid + "\"");
		stream.endText();
		
		stream.beginText();		
		stream.setTextMatrix(Matrix.getRotateInstance(Math.PI/2, PDRectangle.A4.getWidth(), 0));		
		stream.newLineAtOffset(coordinate.get("hingeSpaceBottom").getX(), coordinate.get("hingeSpaceBottom").getY());
		stream.showText(hingeSpaceBottom + "\"");
		stream.endText();
		
		stream.beginText();		
		stream.setTextMatrix(Matrix.getRotateInstance(Math.PI/2, PDRectangle.A4.getWidth(), 0));		
		stream.newLineAtOffset(coordinate.get("strikeHeight").getX(), coordinate.get("strikeHeight").getY());
		stream.showText(strikeHeight + "\"");
		stream.endText();
		
		getStream().close();
	}

	//Not needed for this report
	@Override 
	public void setUpNextPage() {}
	
}
