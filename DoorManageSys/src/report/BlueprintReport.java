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

import application.MasterController;
import blueprint.Blueprint;
import order.Order;
import quoteproduct.*;

public class BlueprintReport extends PDFReport {

	private Product product;
	private Date date;
	private PDImageXObject firstPage;
	private PDPageContentStream stream;
	private PDRectangle mediaBox;
	private String width, height, frameLeft, frameRight, frameTop;
	private String hingeSpaceTop, hingeSpaceMid, hingeSpaceBottom;
	private String strikeHeight, formattedDate;
	private int numHinges;
	private HashMap <String, XYCoordinate> coordinate;
	
	public BlueprintReport (Product product) throws IOException {
		
		this.product = product;
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
		coordinate.put("frameTop", new XYCoordinate (427, 416));
		coordinate.put("hingeSpaceTop", new XYCoordinate (230, 416));
		coordinate.put("hingeSpaceMid", new XYCoordinate (230, 367));
		coordinate.put("hingeSpaceBottom", new XYCoordinate (230, 281));
		coordinate.put("strikeHeight", new XYCoordinate (455, 257));
		coordinate.put("formattedDate", new XYCoordinate (309, 113));
		coordinate.put("customerName", new XYCoordinate (410, 85));
		coordinate.put("numHinges", new XYCoordinate (575, 107));
		
		setStream (new PDPageContentStream (getDoc(), getCurrentPage(), PDPageContentStream.AppendMode.APPEND, false));
		
		stream = getStream();
		
		assignFileName ("Blueprint.pdf");
		
		firstPage = PDImageXObject.createFromFile("SingleDoorSide2.PNG", getDoc());
		
		setUpFirstPage();
	}
	
	public void getValues () {
		
		Blueprint blueprint = MasterController.getInstance().getBlueprintGateway().getBlueprintByProductID(this.product.getId());
		System.out.println("Blueprint: " + blueprint);
		
		
		String[] sliced;
		
		sliced = blueprint.getDimension().split("x");
		width = sliced[0];
		height = sliced[1];
		
		frameLeft = blueprint.getFrame();
		frameRight = blueprint.getFrame();
		frameTop = blueprint.getFrame();
		
		sliced = blueprint.getHingeSpaces().split(",");
		hingeSpaceTop = sliced[0];
		hingeSpaceMid = sliced[1];
		hingeSpaceBottom = sliced[2];
		numHinges = sliced.length;
		
		sliced = null;
		
		strikeHeight = blueprint.getStrikeHeight();
		
		StringBuilder sb = new StringBuilder ();
		
		sb.append(date.toString().substring(4, 7) + " ");
		sb.append(date.toString().substring(8, 10) + ", ");
		sb.append(date.toString().substring(24, 28));
		
		formattedDate = sb.toString();		
	}
	
	@Override
	public void setUpFirstPage() throws IOException {
		
		System.out.println("Setting up Blueprint...");
		setTextSize(10);
		stream.drawImage(firstPage, mediaBox.getLowerLeftX(), mediaBox.getLowerLeftY(), 465, 710);
	}
	
	@Override
	public void populateReport() throws IOException {
		
		System.out.println("Populating Blueprint...");

		getValues();
		
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
		
		stream.beginText();		
		stream.setTextMatrix(Matrix.getRotateInstance(Math.PI/2, PDRectangle.A4.getWidth(), 0));		
		stream.newLineAtOffset(coordinate.get("numHinges").getX(), coordinate.get("numHinges").getY());
		stream.showText(numHinges + "");
		stream.endText();
		
		setTextSize (9);
		stream.beginText();		
		stream.setTextMatrix(Matrix.getRotateInstance(Math.PI/2, PDRectangle.A4.getWidth(), 0));		
		stream.newLineAtOffset(coordinate.get("formattedDate").getX(), coordinate.get("formattedDate").getY());
		stream.showText(formattedDate);
		stream.endText();
		
		setTextSize (10);
		stream.beginText();		
		stream.setTextMatrix(Matrix.getRotateInstance(Math.PI/2, PDRectangle.A4.getWidth(), 0));		
		stream.newLineAtOffset(coordinate.get("customerName").getX(), coordinate.get("customerName").getY());
		stream.showText("");
		stream.endText();
		
		getStream().close();
	}

	//Not needed for this report
	@Override 
	public void setUpNextPage() {}
	
}
