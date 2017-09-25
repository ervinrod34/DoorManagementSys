package inventory;

public class Inventory {

	private int id, quantity;
	private String manufacturer, partNo, vendor, size, colorCode, extra, unitOfMeasure, category;
	private double actualCost, sellingPrice;
	
	public Inventory (int id, String manufacturer, String partNo, String vendor, String size, String colorCode,
					  String extra, String unitOfMeasure, double actualCost, double sellingPrice, int quantity,
					  String category) {
		
		this.id = id;
		this.manufacturer = manufacturer;
		this.partNo = partNo;
		this.vendor = vendor;
		this.size = size;
		this.colorCode = colorCode;
		this.extra = extra;
		this.unitOfMeasure = unitOfMeasure;
		this.actualCost = actualCost;
		this.sellingPrice = sellingPrice;
		this.quantity = quantity;
		this.category = category;
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getColorCode() {
		return colorCode;
	}
	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getActualCost() {
		return actualCost;
	}
	public void setActualCost(double actualCost) {
		this.actualCost = actualCost;
	}
	public double getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	
	public String toString () {
		
		StringBuffer stringBuffer = new StringBuffer ();
		
		stringBuffer.append("id= " + id + ", manufacturer= " + manufacturer + ", partNo= " + partNo + ", vendor= " + vendor +
							", size= " + size + ", colorCode= " + colorCode + ", extra= " + extra + ", unitOfMeasure= " + unitOfMeasure +
							", actualCost= " + actualCost + ", sellingPrice= " + sellingPrice + ", quantity= " + quantity + ", category= " + category);
		
		return stringBuffer.toString();
		
	}
	
}
