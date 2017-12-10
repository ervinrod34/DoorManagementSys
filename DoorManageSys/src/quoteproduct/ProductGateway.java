package quoteproduct;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import application.MasterController;

import inventory.Inventory;
import application.*;

public class ProductGateway extends MasterGateway{

	public ProductGateway(){
		super();
	}
	
	public List<Product> importListOfProductsFromDB() {
		resetPSandRS();
		List<Product> products = new ArrayList<Product>();
		
		try {
			preparedStatement = this.connection.prepareStatement("SELECT * FROM Product "
					+ "WHERE category=?", PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, "product");
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Product product = new Product(resultSet.getInt("id"),
						this.parseCSVToInventory(resultSet.getString("idList")),
						resultSet.getDouble("totalCost"));
				
				products.add(product);
			}
			
		} catch (SQLException sqlException){
			sqlException.printStackTrace();
		}
		
		return products;
	}
	
	public Product getProductByID(int productID) {
		resetPSandRS();
		Product product = new Product();
		
		try {
			preparedStatement = this.connection.prepareStatement("SELECT * FROM Product "
					+ "WHERE id LIKE ? AND category='product'", PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, productID);
			
			resultSet = preparedStatement.executeQuery();
			
			if (!resultSet.isBeforeFirst())
				System.out.println("NO DATA FOR PRODUCT ID: '"+productID+"'");
			else {
				resultSet.next();
				product = new Product(resultSet.getInt("id"), 
						this.parseCSVToInventory(resultSet.getString("idList")),
						resultSet.getDouble("totalCost"));
			}
		} catch (SQLException sqlException){
			sqlException.printStackTrace();
		}
		
		return product;
	}
	
	public List<Inventory> parseCSVToInventory(String CSVid) {
		List<String> ids = new ArrayList<>();
		List<Inventory> items = new ArrayList<Inventory>();
		
		ids = Arrays.asList(CSVid.split(","));
		
		for(String id : ids) {
			items.add(MasterController.getInstance().getInventoryGateway().getInventoryByID(id));
		}
		
		return items;
	}
	
	public void insertNewProductRecord(Product product) {
		resetPSandRS();
		
		try {
			preparedStatement = this.connection.prepareStatement("INSERT INTO Product (id, idList, totalCost, category)"
					+ "VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, product.getId());
			preparedStatement.setString(2, this.parseProductIDsToCSV(product.getInventories()));
			preparedStatement.setDouble(3, product.getTotalCost());
			preparedStatement.setString(4, "product");
			preparedStatement.execute();
			
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			resultSet.next();
			product.setId(resultSet.getInt(1));
		} catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public String parseProductIDsToCSV(List<Inventory> items) {
		String CSV = "";
		
		for(Inventory item : items) {
			CSV += item.getItemNo() + ",";
		}
		
		CSV = CSV.substring(0, CSV.length() - 1);
		
		return CSV;
	}
	
	public void updateProductRecord(Product product) {
		resetPSandRS();
		
		try {
			preparedStatement = this.connection.prepareStatement("UPDATE Product SET id=?, idList=?, "
					+ "totalCost=?, category=? WHERE id=?", 
					PreparedStatement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setInt(1, product.getId());
			preparedStatement.setString(2, this.parseProductIDsToCSV(product.getInventories()));
			preparedStatement.setDouble(3, product.getTotalCost());
			preparedStatement.setString(4, "product");
			preparedStatement.setInt(5, product.getId());
			preparedStatement.execute();
			
		} catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public void deleteProductRecord(int id) {
		resetPSandRS();
		
		try {
			preparedStatement = this.connection.prepareStatement("DELETE FROM Product "
					+ "WHERE id=?");
			preparedStatement.setInt(1, id);
			
			preparedStatement.execute();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public void deleteProducts(List<Product> products) {
		if(products.size() > 0) {
			for(Product product : products) {
				this.deleteProductRecord(product.getId());
			}
		}
	}
}
