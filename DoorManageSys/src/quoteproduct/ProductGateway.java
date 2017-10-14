package quoteproduct;

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

public class ProductGateway {

	private Connection dbConnection;
	private Properties dbProperties;
	FileInputStream dbPropertiesFile;
	private MysqlDataSource database;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	
	public ProductGateway () {
		
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
	
	public ArrayList <Product> getProducts () {
		ArrayList <Product> allProducts = new ArrayList <Product> ();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = dbConnection.prepareStatement("SELECT * FROM Product");
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Product product = new Product (rs.getInt("id"), rs.getString("idList"), rs.getDouble("totalCost"), rs.getString("category"));
				allProducts.add(product);
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		return allProducts;
		
	}
	
	public ArrayList <Product> searchProduct (String column, String value) {
		
		ArrayList <Product> matches = new ArrayList <Product> ();
		preparedStatement = null;
		resultSet = null;
		
		StringBuffer sqlCommand = new StringBuffer ();
		
		sqlCommand.append("SELECT * FROM Product WHERE " + column + "='" + value + "'");
		
		try {
			preparedStatement = dbConnection.prepareStatement(sqlCommand.toString());
			resultSet = preparedStatement.executeQuery();
		
			while (resultSet.next()) {
			
				Product product = new Product (resultSet.getInt("id"), resultSet.getString("idList"),
											   resultSet.getDouble("totalCost"), resultSet.getString("category"));
				
				matches.add(product);
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
	
	public void addProduct (Product product) {
		
		StringBuffer sqlCommand = new StringBuffer();
		
		sqlCommand.append("INSERT INTO Product (id, idList, totalCost, category) VALUES (?, ?, ?, ?)");
		
		preparedStatement = null;
		
		try {
			preparedStatement = dbConnection.prepareStatement(sqlCommand.toString(), preparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, product.getId());
			preparedStatement.setString(2, product.getInventoryIDs());
			preparedStatement.setDouble(3, product.getTotalCost());
			preparedStatement.setString(4, product.getCategory());
			preparedStatement.execute();
			
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public void updateProduct (Product product) {
		
		StringBuffer sqlCommand = new StringBuffer ();
		
		sqlCommand.append("UPDATE Product SET id=?, idList=?, totalCost=?, category=?, " + 
						  "WHERE id =?");
		
		preparedStatement = null;
		resultSet = null;
		
		try {
			preparedStatement = dbConnection.prepareStatement(sqlCommand.toString(), 
					PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, product.getId());
			preparedStatement.setString(2, product.getInventoryIDs());
			preparedStatement.setDouble(3, product.getTotalCost());
			preparedStatement.setString(4, product.getCategory());
			preparedStatement.execute();
			
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public void deleteProduct (int id) {
		
		StringBuffer sqlCommand = new StringBuffer ();
		
		sqlCommand.append("DELETE FROM Product WHERE id = '" + id + "'");
		
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
