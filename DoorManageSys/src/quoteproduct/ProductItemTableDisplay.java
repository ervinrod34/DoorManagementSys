package quoteproduct;

import inventory.Inventory;
import javafx.beans.property.SimpleStringProperty;

public class ProductItemTableDisplay {

	private final SimpleStringProperty prodItemID;
	private final SimpleStringProperty prodItemCategory;
	private final SimpleStringProperty prodItemManufacturer;
	private final SimpleStringProperty prodItemManufacturerNum;
	private final SimpleStringProperty prodItemUoM;
	private final SimpleStringProperty prodItemSize;
	private final SimpleStringProperty prodItemColor;
	private final SimpleStringProperty prodItemExtraInfo;
	
	private Inventory inventoryItem;
	
	public ProductItemTableDisplay(Inventory inventoryItem) {
		this.prodItemID = new SimpleStringProperty(inventoryItem.getItemNo());
		this.prodItemCategory = new SimpleStringProperty(inventoryItem.getCategory());
		this.prodItemManufacturer = new SimpleStringProperty(inventoryItem.getManufacturer());
		this.prodItemManufacturerNum = new SimpleStringProperty(inventoryItem.getManufacturerNo());
		this.prodItemUoM = new SimpleStringProperty(inventoryItem.getUnitOfMeasure());
		this.prodItemSize = new SimpleStringProperty(inventoryItem.getSize());
		this.prodItemColor = new SimpleStringProperty(inventoryItem.getColorCode());
		this.prodItemExtraInfo = new SimpleStringProperty(inventoryItem.getExtra());
	}
	
	public String getProdItemID() {
		return prodItemID.get();
	}

	public String getProdItemCategory() {
		return prodItemCategory.get();
	}

	public String getProdItemManufacturer() {
		return prodItemManufacturer.get();
	}

	public String getProdItemManufacturerNum() {
		return prodItemManufacturerNum.get();
	}

	public String getProdItemUoM() {
		return prodItemUoM.get();
	}

	public String getProdItemSize() {
		return prodItemSize.get();
	}

	public String getProdItemColor() {
		return prodItemColor.get();
	}

	public String getProdItemExtraInfo() {
		return prodItemExtraInfo.get();
	}
	
	public Inventory getProdInventoryItem() {
		return inventoryItem;
	}

	public void setInventoryItem(Inventory inventoryItem) {
		this.inventoryItem = inventoryItem;
	}
}
