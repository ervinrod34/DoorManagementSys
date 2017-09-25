package inventory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

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
	
	public ArrayList <Inventory> searchInventory (String category) {
		
		ArrayList <Inventory> matches = new ArrayList <Inventory> ();
		preparedStatement = null;
		resultSet = null;
		
		StringBuffer sqlCommand = new StringBuffer ();
		
		sqlCommand.append("SELECT * FROM Inventory WHERE category='");
		sqlCommand.append(category);
		sqlCommand.append("'");
		
		try {
			preparedStatement = dbConnection.prepareStatement(sqlCommand.toString());
			resultSet = preparedStatement.executeQuery();
		
			while (resultSet.next()) {
			
				Inventory inventory = new Inventory (resultSet.getInt("id"), resultSet.getString("manufacturer"), resultSet.getString("partNo"),
													 resultSet.getString("vendor"), resultSet.getString("size"), resultSet.getString("colorCode"),
													 resultSet.getString("extra"), resultSet.getString("unitOfMeasure"), resultSet.getDouble("actualCost"),
													 resultSet.getDouble("sellingPrice"), resultSet.getInt("quantity"), resultSet.getString("category"));
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
		
		sqlCommand.append("INSERT INTO Inventory (id, manufacturer, partNo, vendor, size, colorCode, extra, unitOfMeasure, actualCost, sellingPrice, quantity, category) VALUES ('"); 
		sqlCommand.append (inventory.getId() + "', '" + inventory.getManufacturer() + "', '" + inventory.getPartNo() + "', '" + inventory.getVendor() + "', '" +
						   inventory.getSize() + "', '" + inventory.getColorCode() + "', '" + inventory.getExtra() + "', '" + inventory.getUnitOfMeasure() + "', '" +
						   inventory.getActualCost() + "', '" + inventory.getSellingPrice() + "', '" + inventory.getQuantity() + "', '" + inventory.getCategory() + "')");
		
		preparedStatement = null;
		
		try {
			preparedStatement = dbConnection.prepareStatement(sqlCommand.toString());
			preparedStatement.execute();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
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


















