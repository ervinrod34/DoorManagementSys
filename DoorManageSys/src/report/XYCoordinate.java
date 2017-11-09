package report;

public class XYCoordinate {

	private int x, y;
	
	public XYCoordinate (int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX () {return x;}
	public int getY () {return y;}
	public void setX (int x) {this.x = x;}
	public void setY (int y) {this.y = y;}
}
