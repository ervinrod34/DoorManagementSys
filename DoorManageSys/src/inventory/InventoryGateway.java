package inventory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import quoteproduct.Quote;

public class InventoryGateway {

	private Connection dbConnection;
	private Properties dbProperties;
	FileInputStream dbPropertiesFile;
	private MysqlDataSource database;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	
	public InventoryGateway () {
		
		dbProperties = new Properties ();
		
		setUpDBProperties ();
		setUpDB ();
		connectToDatabase ();
		
	}
	
	private void setUpDBProperties () {
		
		try {
			dbPropertiesFile = new FileInputStream ("database.properties");
		}
		catch (FileNotFoundException fileNotFoudnException) {
			System.err.println("Properties File not found.");
		}
		
		try {
			dbProperties.load(dbPropertiesFile);
		}
		catch (IOException e) {
			System.err.println("Error reading from Input Stream.");
		}
	}
	
	private void setUpDB () {
		database = new MysqlDataSource ();
		
		database.setURL(dbProperties.getProperty("MYSQL_DPM_DB_URL"));
		database.setUser(dbProperties.getProperty("MYSQL_DPM_DB_UN"));
		database.setPassword(dbProperties.getProperty("MYSQL_DPM_DB_PW"));
	}

	private void connectToDatabase () {
		
		try {
			dbConnection = database.getConnection();
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace ();
		}
	}
	
	public ArrayList<Inventory> searchInventory(String search) {
		ArrayList<Inventory> searchResults = new ArrayList<Inventory>();
		
		try {
			String query = "SELECT * FROM Inventory WHERE "
					+ "manufacturer LIKE ? OR manufacturerNo LIKE ? OR size LIKE ? OR "
					+ "colorCode LIKE ? OR extra LIKE ?";
			preparedStatement = dbConnection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			
			search = "%" + search + "%";
			preparedStatement.setString(1, search);
			preparedStatement.setString(2, search);
			preparedStatement.setString(3, search);
			preparedStatement.setString(4, search);
			preparedStatement.setString(5, search);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Inventory inventory = new Inventory (resultSet.getInt("id"), resultSet.getString("itemNo"), resultSet.getString("manufacturer"),
						resultSet.getString("manufacturerNo"), resultSet.getString("vendor"), resultSet.getString("size"),
						resultSet.getString("colorCode"), resultSet.getString("extra"), resultSet.getString("unitOfMeasure"),
						resultSet.getDouble("actualCost"), resultSet.getDouble("sellingPrice"), resultSet.getInt("quantity"),
						resultSet.getInt("minQuantity"), resultSet.getInt("maxQuantity"), resultSet.getString("category"),
						resultSet.getBoolean("taxable"), resultSet.getString("accountingCode"));
				
				searchResults.add(inventory);
			}
			
		} catch(SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				closePSandRS (preparedStatement, resultSet);
			}
			catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}
		
		return searchResults;
	}
	
	public ArrayList <Inventory> searchInventory (String column, String value) {
		
		ArrayList <Inventory> matches = new ArrayList <Inventory> ();
		preparedStatement = null;
		resultSet = null;
		
		StringBuffer sqlCommand = new StringBuffer ();
		
		sqlCommand.append("SELECT * FROM Inventory WHERE " + column + "='" + value + "'");
		
		try {
			preparedStatement = dbConnection.prepareStatement(sqlCommand.toString());
			resultSet = preparedStatement.executeQuery();
		
			while (resultSet.next()) {
			
				Inventory inventory = new Inventory (resultSet.getInt("id"), resultSet.getString("itemNo"), resultSet.getString("manufacturer"),
						resultSet.getString("manufacturerNo"), resultSet.getString("vendor"), resultSet.getString("size"),
						resultSet.getString("colorCode"), resultSet.getString("extra"), resultSet.getString("unitOfMeasure"),
						resultSet.getDouble("actualCost"), resultSet.getDouble("sellingPrice"), resultSet.getInt("quantity"),
						resultSet.getInt("minQuantity"), resultSet.getInt("maxQuantity"), resultSet.getString("category"),
						resultSet.getBoolean("taxable"), resultSet.getString("accountingCode"));
				
				matches.add(inventory);
			}
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		finally {
			try {
				closePSandRS (preparedStatement, resultSet);
			}
			catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}
		
		return matches;
	}
	
	public void addInventory (Inventory inventory) {
		
		StringBuffer sqlCommand = new StringBuffer ();
		
		sqlCommand.append("INSERT INTO Inventory (id, itemNo, manufacturer, manufacturerNo, vendor, size, colorCode, extra, " +
						  "unitOfMeasure, actualCost, sellingPrice, quantity, minQuantity, maxQuantity, category, taxable, " +
						  "accountingCode)");
		sqlCommand.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); 
		preparedStatement = null;
		
		try {
			preparedStatement = dbConnection.prepareStatement(sqlCommand.toString(),
					PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, inventory.getId());
			preparedStatement.setString(2, inventory.getItemNo());
			preparedStatement.setString(3, inventory.getManufacturer());
			preparedStatement.setString(4, inventory.getManufacturerNo());
			preparedStatement.setString(5, inventory.getVendor());
			preparedStatement.setString(6, inventory.getSize());
			preparedStatement.setString(7, inventory.getColorCode());
			preparedStatement.setString(8, inventory.getExtra());
			preparedStatement.setString(9, inventory.getUnitOfMeasure());
			preparedStatement.setDouble(10, inventory.getActualCost());
			preparedStatement.setDouble(11, inventory.getSellingPrice());
			preparedStatement.setInt(12, inventory.getQuantity());
			preparedStatement.setInt(13, inventory.getMinQuantity());
			preparedStatement.setInt(14, inventory.getMaxQuantity());
			preparedStatement.setString(15, inventory.getCategory());
			preparedStatement.setBoolean(16, inventory.isTaxable());
			preparedStatement.setString(17, inventory.getAccountingCode());
			
			preparedStatement.execute();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
	}
	
	public List<Inventory> getInventory() {
		List<Inventory> fullInventory = new ArrayList<Inventory>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = dbConnection.prepareStatement("SELECT * FROM Inventory");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Inventory inventory = new Inventory (rs.getInt("id"), rs.getString("itemNo"), rs.getString("manufacturer"),
						 rs.getString("manufacturerNo"), rs.getString("vendor"), rs.getString("size"),
						 rs.getString("colorCode"), rs.getString("extra"), rs.getString("unitOfMeasure"),
						 rs.getDouble("actualCost"), rs.getDouble("sellingPrice"), rs.getInt("quantity"),
						 rs.getInt("minQuantity"), rs.getInt("maxQuantity"), rs.getString("category"),
						 rs.getBoolean("taxable"), rs.getString("accountingCode"));
				
				fullInventory.add(inventory);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				this.closePSandRS(ps, rs);
			} catch (SQLException se){
				se.printStackTrace();
			}
		}
		return fullInventory;
	}

	public void updateInventory (Inventory inventory) {
		
		StringBuffer sqlCommand = new StringBuffer ();
		
		sqlCommand.append("UPDATE Inventory SET id=?, itemNo=?, manufacturer=?, manufacturerNo=?, " + 
											   "vendor=?, size=?, colorCode=?, extra=?, unitOfMeasure=?, " + 
											   "actualCost=?, sellingPrice=?, quantity=?, minQuantity=?, " +
											   "maxQuantity=?, category=?, taxable=?, accountingCode=? " + 
											   "WHERE id =?");
		
		preparedStatement = null;
		resultSet = null;
		
		try {
			preparedStatement = dbConnection.prepareStatement(sqlCommand.toString(), 
					PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, inventory.getId());
			preparedStatement.setString(2, inventory.getItemNo());
			preparedStatement.setString(3, inventory.getManufacturer());
			preparedStatement.setString(4, inventory.getManufacturerNo());
			preparedStatement.setString(5, inventory.getVendor());
			preparedStatement.setString(6, inventory.getSize());
			preparedStatement.setString(7, inventory.getColorCode());
			preparedStatement.setString(8, inventory.getExtra());
			preparedStatement.setString(9, inventory.getUnitOfMeasure());
			preparedStatement.setDouble(10, inventory.getActualCost());
			preparedStatement.setDouble(11, inventory.getSellingPrice());
			preparedStatement.setInt(12, inventory.getQuantity());
			preparedStatement.setInt(13, inventory.getMinQuantity());
			preparedStatement.setInt(14, inventory.getMaxQuantity());
			preparedStatement.setString(15, inventory.getCategory());
			preparedStatement.setBoolean(16, inventory.isTaxable());
			preparedStatement.setString(17, inventory.getAccountingCode());
			preparedStatement.setInt(18, inventory.getId());
			
			preparedStatement.execute();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
	}
	
	public void deleteInventory (int id) {
		
		StringBuffer sqlCommand = new StringBuffer ();
		
		sqlCommand.append("DELETE FROM Inventory WHERE id = '" + id + "'");
		
		preparedStatement = null;
		
		try {
			preparedStatement = dbConnection.prepareStatement(sqlCommand.toString());
			preparedStatement.execute();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public Inventory getInventoryByID(String itemNo) {
		Inventory inventory = new Inventory();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.dbConnection.prepareStatement("SELECT * FROM Inventory "
					+ "WHERE itemNo LIKE ?", PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, itemNo);
			
			rs = ps.executeQuery();
			rs.next();
			inventory = new Inventory(rs.getInt("id"), rs.getString("itemNo"), rs.getString("manufacturer"),
					 rs.getString("manufacturerNo"), rs.getString("vendor"), rs.getString("size"),
					 rs.getString("colorCode"), rs.getString("extra"), rs.getString("unitOfMeasure"),
					 rs.getDouble("actualCost"), rs.getDouble("sellingPrice"), rs.getInt("quantity"),
					 rs.getInt("minQuantity"), rs.getInt("maxQuantity"), rs.getString("category"),
					 rs.getBoolean("taxable"), rs.getString("accountingCode"));
			
		} catch (SQLException sqlException){
			sqlException.printStackTrace();
		}
		
		return inventory;
	}
	
	public void closePSandRS (PreparedStatement ps, ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (ps!= null) {
			ps.close();
		}
	}
}


















