package quoteproduct;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import application.MasterController;
import inventory.Inventory;
import quoteproduct.*;

public class ProductGateway {
	
	private Connection connection;
	
	public ProductGateway(){
		this.connection = null;
		Properties prop = new Properties();
		FileInputStream fileStream = null;
		
		try{
			fileStream = new FileInputStream("database.properties");
			prop.load(fileStream);
			fileStream.close();
			
			MysqlDataSource info = new MysqlDataSource();
			info.setURL(prop.getProperty("MYSQL_DPM_DB_URL"));
			info.setUser(prop.getProperty("MYSQL_DPM_DB_UN"));
			info.setPassword(prop.getProperty("MYSQL_DPM_DB_PW"));
			
			this.connection = info.getConnection();
			
		} catch(IOException | SQLException e) {
			e.printStackTrace();
		}
		
	}//End Constructor
	
	public List<Product> importListOfProductsFromDB() {
		List<Product> products = new ArrayList<Product>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.connection.prepareStatement("SELECT * FROM Product "
					+ "WHERE category=?", PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, "product");
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Product product = new Product(rs.getInt("id"),
						this.parseCSVToInventory(rs.getString("idList")),
						rs.getDouble("totalCost"));
				
				products.add(product);
			}
			
		} catch (SQLException sqlException){
			sqlException.printStackTrace();
		}
		
		return products;
	}//end importList
	
	public Product getProductByID(int productID) {
		Product product = new Product();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.connection.prepareStatement("SELECT * FROM Product "
					+ "WHERE id LIKE ? AND category='product'", PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, productID);
			
			rs = ps.executeQuery();
			
			rs.next();
			product = new Product(rs.getInt("id"), 
					this.parseCSVToInventory(rs.getString("idList")),
					rs.getDouble("totalCost"));
			
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
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.connection.prepareStatement("INSERT INTO Product (id, idList, totalCost) "
					+ "VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, product.getId());
			ps.setString(2, this.parseProductIDsToCSV(product.getInventories()));
			ps.setDouble(3, product.getTotalCost());
			ps.setString(4, "quote");
			ps.executeQuery();
			
		} catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public String parseProductIDsToCSV(List<Inventory> products) {
		String CSV = "";
		
		for(Inventory product : products) {
			CSV += product.getId() + ",";
		}
		
		CSV = CSV.substring(0, CSV.length() - 1);
		
		return CSV;
	}
	
	public void updateProductRecord(Product product) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.connection.prepareStatement("UPDATE Product SET id=?, idList=?, "
					+ "totalCost=?, category=? WHERE id=?", 
					PreparedStatement.RETURN_GENERATED_KEYS);
			
			ps.setInt(1, product.getId());
			ps.setString(2, this.parseProductIDsToCSV(product.getInventories()));
			ps.setDouble(3, product.getTotalCost());
			ps.setString(4, "quote");
			ps.setInt(5, product.getId());
			ps.executeQuery();
			
		} catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public void deleteProductRecord(int id) {
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = this.connection.prepareStatement("DELETE FROM Product "
					+ "WHERE id=?");
			preparedStatement.setInt(1, id);
			
			preparedStatement.execute();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public void closePSandRS(PreparedStatement ps, ResultSet rs) throws SQLException {
		if(rs != null) {
			rs.close();
		}
		if(ps != null) {
			ps.close();
		}
	}

}
