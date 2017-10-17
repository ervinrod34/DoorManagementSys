package order;

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

import application.MasterController;
import inventory.Inventory;
import quoteproduct.*;
import blueprint.*;

public class OrderGateway {

	private Connection dbConnection;
	private Properties dbProperties;
	private FileInputStream dbPropertiesFile;
	private MysqlDataSource database;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;
	
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
		
		database.setURL(dbProperties.getProperty("MYSQL_DPM_URL"));
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
	
	/*public ArrayList<Order> searchOrders(String search){
		ArrayList<Order> searchResults = new ArrayList<Order>();
		
		try{
			String query = "SELECT * FROM Order WHERE "
					+ "customerPurchaseOrderNumber LIKE ? OR customerName LIKE ? "
					+ "OR productCode LIKE ? OR status LIKE ?";
			preparedStatement = dbConnection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			
			search = "%" + search + "%";
			preparedStatement.setString(1, search);
			preparedStatement.setString(2, search);
			preparedStatement.setString(3, search);
			preparedStatement.setString(4, search);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				Order order = new Order(resultSet.getInt("id"), resultSet.getInt("quoteID"), resultSet.getInt("blueprintID"), 
						resultSet.getString("customerPurchaseOrderNumber"), resultSet.getString("customerName"), 
						resultSet.getString("productCode"), resultSet.getString("status"), resultSet.getDate("dateOrdered"), 
						resultSet.getDate("targetShipping"), resultSet.getDate("actualShipping"), resultSet.getDouble("totalAmount"));
				
				searchResults.add(order);
			}
		} catch(SQLException sqlException){
			sqlException.printStackTrace();
		} finally {
			try{
				closePSandRS (preparedStatement, resultSet);
			} catch(SQLException sqlException){
				sqlException.printStackTrace();
			}
		}
		
		return searchResults;
	}*/

	public void addOrder(Order order) {
		
		StringBuffer sqlCommand = new StringBuffer ();
		
		sqlCommand.append("INSERT INTO Order (quoteID, customerPurchaseOrderNumber, customerName, "
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

	public List<Order> getOrder() {
		List<Order> fullOrders = new ArrayList<Order>();
		PreparedStatement ps = null;
		ResultSet rs = null;
	
		try {
			ps = dbConnection.prepareStatement("SELECT * FROM Order");
			rs = ps.executeQuery();
		
			while(rs.next()) {
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
				this.closePSandRS(ps, rs);
			} catch (SQLException se){
				se.printStackTrace();
			}
		}
		return fullOrders;
	}

	public void updateOrder(Order order) {
		StringBuffer sqlCommand = new StringBuffer ();
		sqlCommand.append("UPDATE Order SET quoteID=?, customerPurchaseOrderNumber=?, "
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
			preparedStatement = dbConnection.prepareStatement("DELETE from Order WHERE id=?",
					PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, OrderID);
			preparedStatement.execute();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

/*	public Order getOrderById(int id){
		Order order = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = dbConnection.prepareStatement("SELECT * from Order where id=?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while(rs.next()){
				order = new Order(rs.getInt("id"), rs.getInt("quoteId"), rs.getInt("blueprintId"), 
					 rs.getString("customerPurchaseOrderNumber"), rs.getString("customerName"), 
					 rs.getString("productCode"), rs.getString("status"), rs.getDate("dateOrdered"), 
					 rs.getDate("targetShipping"), rs.getDate("actualShipping"), rs.getDouble("totalAmount"));
			}
		} catch(SQLException e){
			e.printStackTrace();
		} finally {
			try{
				this.closePSandRS(ps, rs);
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
	return order;
	}*/
	
	public void closePSandRS(PreparedStatement ps, ResultSet rs) throws SQLException{
		if(rs != null){
			rs.close();
		}
		if(ps != null){
			ps.close();
		}
	}
}
