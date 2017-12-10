package quoteproduct;

import javafx.beans.property.SimpleStringProperty;

public class ProductTableDisplay {

	private final SimpleStringProperty productID;
	private final SimpleStringProperty productTotal;
	private final SimpleStringProperty productItems;
	
	private Product product;
	
	public ProductTableDisplay(Product product) {
		this.productID = new SimpleStringProperty(String.valueOf(product.getId()));
		this.productTotal = new SimpleStringProperty(String.valueOf(product.getTotalCost()));
		this.productItems = new SimpleStringProperty(product.itemsToString());
	}
	
	public String getProductID() {
		return productID.get();
	}
	
	public String getProductTotal() {
		return productTotal.get();
	}
	
	public String getProductItems() {
		return productItems.get();
	}
	
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
}
