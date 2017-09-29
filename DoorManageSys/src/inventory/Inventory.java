package inventory;

import application.MasterController;

public class Inventory {

	private int id, quantity, minQuantity, maxQuantity;
	private String manufacturer, vendor, size, colorCode, extra, unitOfMeasure, category,
				   itemNo, manufacturerNo, accountingCode;
	private double actualCost, sellingPrice;
	private boolean taxable;
	
	public Inventory (int id, String itemNo, String manufacturer, String manufacturerNo, String vendor, String size, 
					  String colorCode, String extra, String unitOfMeasure, double actualCost, double sellingPrice,
					  int quantity, int minQuantity, int maxQuantity, String category, boolean taxable, String accountingCode) {
		
		this.id = id;
		this.itemNo = itemNo;
		this.manufacturer = manufacturer;
		this.manufacturerNo = manufacturerNo;
		this.vendor = vendor;
		this.size = size;
		this.colorCode = colorCode;
		this.extra = extra;
		this.unitOfMeasure = unitOfMeasure;
		this.actualCost = actualCost;
		this.sellingPrice = sellingPrice;
		this.quantity = quantity;
		this.minQuantity = minQuantity;
		this.maxQuantity = maxQuantity;
		this.category = category;
		this.taxable = taxable;
		this.accountingCode = accountingCode;
		
	}
	
	public Inventory () {
		id = 0;
		itemNo = "";
		manufacturer = "";
		manufacturerNo = "";
		vendor = "";
		size = "";
		colorCode = "";
		extra = "";
		unitOfMeasure = "";
		actualCost = 0;
		sellingPrice = 0;
		quantity = 0;
		minQuantity = 0;
		maxQuantity = 0;
		category = "";
		taxable = false;
		accountingCode = "";
	}
	
	public int getMinQuantity () {
		return minQuantity;
	}
	
	public void setMinQuantity (int minQuantity) {
		this.minQuantity = minQuantity;
	}
	
	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getManufacturerNo() {
		return manufacturerNo;
	}

	public void setManufacturerNo(String manufacturerNo) {
		this.manufacturerNo = manufacturerNo;
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
	public int getMaxQuantity() {
		return maxQuantity;
	}
	public void setMaxQuantity(int maxQuantity) {
		this.maxQuantity = maxQuantity;
	}
	public boolean isTaxable() {
		return taxable;
	}
	public void setTaxable(boolean taxable) {
		this.taxable = taxable;
	}
	public String getAccountingCode() {
		return accountingCode;
	}
	public void setAccountingCode(String accountingCode) {
		this.accountingCode = accountingCode;
	}
	
	public void save() {
		if (id == 0) {
			MasterController.getInstance().getInventoryGateway().addInventory(this);
		} else {
			MasterController.getInstance().getInventoryGateway().updateInventory(this);
		}
	}
	
	public String toString () { //TODO: MAKE THIS BETTER
		
		StringBuffer stringBuffer = new StringBuffer ();
		
		/*stringBuffer.append("id= " + id + ", itemNo= " + itemNo + ", manufacturer= " + manufacturer + ", manufacturerNo= " + manufacturerNo +
							", vendor= " + vendor + ", size= " + size + ", colorCode= " + colorCode + ", extra= " + extra + ", unitOfMeasure= " + 
							unitOfMeasure + ", actualCost= " + actualCost + ", sellingPrice= " + sellingPrice + ", quantity= " + quantity + 
							", minQuantity= " + minQuantity + ", maxQuantity" + maxQuantity + ", category= " + category +
							", taxable=" + taxable + ", accountingCode=" + accountingCode);*/
		
		stringBuffer.append(String.format("%-14s\t%-12s  %s  %s  ", itemNo, manufacturer, manufacturerNo, colorCode));
		if (!size.equals("")) {
			stringBuffer.append(size + "\t");
		} else {
			stringBuffer.append("\t\t");
		}
		if (!extra.equals("")) {
			stringBuffer.append(extra + "\t");
		} else {
			stringBuffer.append("\t\t\t");
		}
		stringBuffer.append(String.format("  || Curr. Qty.= %d || Cost= $%,.2f || Vendor(s): %s", quantity, actualCost, vendor));
		return stringBuffer.toString();
	}
	
}
