package order;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import application.*;
import quoteproduct.*;
import blueprint.*;

public class OrderGateway extends MasterGateway {
	
	public OrderGateway(){
		super();
	}
	
	public List<Order> searchOrders(String search){
		resetPSandRS();
		List<Order> searchResults = new ArrayList<Order>();
		
		try{
			String query = "SELECT * FROM `Order` WHERE status LIKE ?";
			preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, search);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
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
		resetPSandRS();
		StringBuffer sqlCommand = new StringBuffer ();
		
		sqlCommand.append("INSERT INTO `Order` (quoteID, customerPurchaseOrderNumber, customerName, "
				+ "productCode, status, dateOrdered, targetShipping, actualShipping, blueprintID, totalAmount)");
		sqlCommand.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); 
		
		try {
			preparedStatement = connection.prepareStatement(sqlCommand.toString(),
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
		resetPSandRS();
		List<Order> fullOrders = new ArrayList<Order>();
	
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM `Order`");
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
		resetPSandRS();
		StringBuffer sqlCommand = new StringBuffer ();
		sqlCommand.append("UPDATE `Order` SET quoteID=?, customerPurchaseOrderNumber=?, "
				+ "customerName=?, productCode=?, status=?, " 
				+ "dateOrdered=?, targetShipping=?, actualShipping=?, "
				+ "blueprintID=?, totalAmount=? WHERE id=?");
	
		try {
			preparedStatement = connection.prepareStatement(sqlCommand.toString(), 
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
		try {
			preparedStatement = connection.prepareStatement("DELETE from `Order` WHERE id=?",
					PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, OrderID);
			preparedStatement.execute();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
}
