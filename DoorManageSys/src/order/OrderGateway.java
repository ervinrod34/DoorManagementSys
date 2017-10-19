package order;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import application.MasterController;
import inventory.Inventory;
import quoteproduct.*;
import blueprint.*;

public class OrderGateway {

	private Connection dbConnection;
	private Properties dbProperties;
	FileInputStream dbPropertiesFile;
	private MysqlDataSource database;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	
	public OrderGateway(){
		dbProperties = new Properties();
		setUpDBProperties();
		setUpDB();
		connectToDatabase();
	}
	
	private void setUpDBProperties(){
		
		try{
			dbPropertiesFile = new FileInputStream("database.properties");
		} catch (FileNotFoundException e){
			System.err.println("Properties File not found.");
		}
		
		try{
			dbProperties.load(dbPropertiesFile);
		} catch (IOException e){
			System.err.println("Error reading from Input Stream.");
		}
	}
	
	private void setUpDB(){
		
		database = new MysqlDataSource();
		
		database.setURL(dbProperties.getProperty("MYSQL_DPM_DB_URL"));
		database.setUser(dbProperties.getProperty("MYSQL_DPM_DB_UN"));
		database.setPassword(dbProperties.getProperty("MYSQL_DPM_DB_PW"));
	}
	
	private void connectToDatabase(){
		try{
			dbConnection = database.getConnection();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public List<Order> searchOrders(String search){
		List<Order> searchResults = new ArrayList<Order>();
		
		
		try{
			String query = "SELECT * FROM `Order` WHERE status LIKE ?";
			//String query = "SELECT * FROM `Order` WHERE "
			//		+ "id LIKE ? OR customerPurchaseOrderNumber LIKE ? OR " //id LIKE ? OR
			//		+ "customerName LIKE ? OR status LIKE ? OR dateOrdered LIKE ?";
			preparedStatement = dbConnection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

			
			//search = "%" + search + "%";
			//preparedStatement.setInt(1,Integer.valueOf(search));
			//preparedStatement.setString(2, search);
			//preparedStatement.setString(3, search);
			//preparedStatement.setString(4, search);
			//preparedStatement.setString(5, search);
			preparedStatement.setString(1, search);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){ // TODO: ADD PROPER BLUEPRINTS
				Quote quoteForOrder = MasterController.getInstance().getQuoteGateway().getQuoteByID(resultSet.getInt("quoteID"));
				Order order = new Order(resultSet.getInt("id"), quoteForOrder, 
						resultSet.getString("customerPurchaseOrderNumber"), 
						resultSet.getString("customerName"), 
						resultSet.getString("productCode"), 
						resultSet.getString("status"), 
						resultSet.getDate("dateOrdered"), 
						resultSet.getDate("targetShipping"), 
						resultSet.getDate("actualShipping"),
						new Blueprint(0),//new Blueprint(resultSet.getInt("blueprintID")),
						resultSet.getDouble("totalAmount"));
				
				searchResults.add(order);
			}
		} catch(SQLException sqlException){
			sqlException.printStackTrace();
		} finally {
			try{
				closePSandRS();
			} catch(SQLException sqlException){
				sqlException.printStackTrace();
			}
		}
		return searchResults;
	}

	public void addOrder(Order order) {
		
		StringBuffer sqlCommand = new StringBuffer ();
		
		sqlCommand.append("INSERT INTO `Order` (quoteID, customerPurchaseOrderNumber, customerName, "
				+ "productCode, status, dateOrdered, targetShipping, actualShipping, blueprintID, totalAmount)");
		sqlCommand.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); 
		preparedStatement = null;
		
		try {
			preparedStatement = dbConnection.prepareStatement(sqlCommand.toString(),
					PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, order.getQuote().getId());
			preparedStatement.setString(2, order.getCustomerPurchaseOrderNumber());
			preparedStatement.setString(3, order.getCustomerName());
			preparedStatement.setString(4, order.getProductCode());
			preparedStatement.setString(5, order.getStatus());
			preparedStatement.setDate(6, order.getDateOrdered());
			preparedStatement.setDate(7, order.getTargetShipping());
			preparedStatement.setDate(8, order.getActualShipping());
			preparedStatement.setInt(9, order.getBlueprint().getId());
			preparedStatement.setDouble(10, order.getTotalAmount());
			preparedStatement.execute();
			
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
	}

	public List<Order> getOrders() {
		List<Order> fullOrders = new ArrayList<Order>();
	
		try {
			preparedStatement = dbConnection.prepareStatement("SELECT * FROM `Order`");
			resultSet = preparedStatement.executeQuery();
		
			while(resultSet.next()) {
				Quote quoteForOrder = MasterController.getInstance().getQuoteGateway().getQuoteByID(resultSet.getInt("quoteID"));
				
				Order order = new Order(resultSet.getInt("id"), quoteForOrder, 
						resultSet.getString("customerPurchaseOrderNumber"), 
						resultSet.getString("customerName"), 
						resultSet.getString("productCode"), 
						resultSet.getString("status"), 
						resultSet.getDate("dateOrdered"), 
						resultSet.getDate("targetShipping"), 
						resultSet.getDate("actualShipping"),
						new Blueprint(0),
						resultSet.getDouble("totalAmount"));
			
				fullOrders.add(order);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				this.closePSandRS();
			} catch (SQLException se){
				se.printStackTrace();
			}
		}
		return fullOrders;
	}

	public void updateOrder(Order order) {
		StringBuffer sqlCommand = new StringBuffer ();
		sqlCommand.append("UPDATE `Order` SET quoteID=?, customerPurchaseOrderNumber=?, "
				+ "customerName=?, productCode=?, status=?, " 
				+ "dateOrdered=?, targetShipping=?, actualShipping=?, "
				+ "blueprintID=?, totalAmount=? WHERE id=?");
	
		preparedStatement = null;
		resultSet = null;
	
		try {
			preparedStatement = dbConnection.prepareStatement(sqlCommand.toString(), 
				PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, order.getQuote().getId());
			preparedStatement.setString(2, order.getCustomerPurchaseOrderNumber());
			preparedStatement.setString(3, order.getCustomerName());
			preparedStatement.setString(4, order.getProductCode());
			preparedStatement.setString(5, order.getStatus());
			preparedStatement.setDate(6, order.getDateOrdered());
			preparedStatement.setDate(7, order.getTargetShipping());
			preparedStatement.setDate(8, order.getActualShipping());
			preparedStatement.setInt(9, order.getBlueprint().getId());
			preparedStatement.setDouble(10, order.getTotalAmount());
			preparedStatement.setInt(11, order.getId());
			preparedStatement.execute();
			
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

	public void deleteOrder(int OrderID) {
		preparedStatement = null;
	
		try {
			preparedStatement = dbConnection.prepareStatement("DELETE from `Order` WHERE id=?",
					PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, OrderID);
			preparedStatement.execute();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public void closePSandRS() throws SQLException{
		if(this.resultSet != null){
			this.resultSet.close();
		}
		if(this.preparedStatement != null){
			this.preparedStatement.close();
		}
	}
}
