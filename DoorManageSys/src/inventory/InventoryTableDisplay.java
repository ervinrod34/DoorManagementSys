package inventory;

import javafx.beans.property.SimpleStringProperty;

public class InventoryTableDisplay {

	private final SimpleStringProperty itemID;
	private final SimpleStringProperty itemCategory;
	private final SimpleStringProperty itemManufacturer;
	private final SimpleStringProperty itemManufacturerNum;
	private final SimpleStringProperty itemUoM;
	private final SimpleStringProperty itemSize;
	private final SimpleStringProperty itemColor;
	private final SimpleStringProperty itemExtraInfo;
	private final SimpleStringProperty itemCurrQuantity;
	private final SimpleStringProperty itemMinQuantity;
	private final SimpleStringProperty itemMaxQuantity;
	
	private Inventory inventoryItem;
	
	public InventoryTableDisplay(Inventory inventoryItem) {
		this.itemID = new SimpleStringProperty(inventoryItem.getItemNo());
		this.itemCategory = new SimpleStringProperty(inventoryItem.getCategory());
		this.itemManufacturer = new SimpleStringProperty(inventoryItem.getManufacturer());
		this.itemManufacturerNum = new SimpleStringProperty(inventoryItem.getManufacturerNo());
		this.itemUoM = new SimpleStringProperty(inventoryItem.getUnitOfMeasure());
		this.itemSize = new SimpleStringProperty(inventoryItem.getSize());
		this.itemColor = new SimpleStringProperty(inventoryItem.getColorCode());
		this.itemExtraInfo = new SimpleStringProperty(inventoryItem.getExtra());
		this.itemCurrQuantity = new SimpleStringProperty(String.valueOf(inventoryItem.getQuantity()));
		this.itemMinQuantity = new SimpleStringProperty(String.valueOf(inventoryItem.getMinQuantity()));
		this.itemMaxQuantity = new SimpleStringProperty(String.valueOf(inventoryItem.getMaxQuantity()));
	}

	public String getItemID() {
		return itemID.get();
	}

	public String getItemCategory() {
		return itemCategory.get();
	}

	public String getItemManufacturer() {
		return itemManufacturer.get();
	}

	public String getItemManufacturerNum() {
		return itemManufacturerNum.get();
	}

	public String getItemUoM() {
		return itemUoM.get();
	}

	public String getItemSize() {
		return itemSize.get();
	}

	public String getItemColor() {
		return itemColor.get();
	}

	public String getItemExtraInfo() {
		return itemExtraInfo.get();
	}

	public String getItemCurrQuantity() {
		return itemCurrQuantity.get();
	}

	public String getItemMinQuantity() {
		return itemMinQuantity.get();
	}

	public String getItemMaxQuantity() {
		return itemMaxQuantity.get();
	}

	public Inventory getInventoryItem() {
		return inventoryItem;
	}

	public void setInventoryItem(Inventory inventoryItem) {
		this.inventoryItem = inventoryItem;
	}
	
}
