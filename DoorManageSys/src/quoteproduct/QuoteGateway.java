package quoteproduct;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import application.MasterController;
import application.*;

public class QuoteGateway extends MasterGateway{
		
		public QuoteGateway() {
			super();
		}
		
		public List<Quote> importListOfQuotesFromDB() {
			resetPSandRS();
			List<Quote> quotes = new ArrayList<Quote>();
			
			try {
				preparedStatement = this.connection.prepareStatement("SELECT * FROM Product "
						+ "WHERE category=?", PreparedStatement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, "quote");
				
				resultSet = preparedStatement.executeQuery();
				
				while(resultSet.next()) {
					Quote quote = new Quote(resultSet.getInt("id"), 
							this.parseCSVToProducts(resultSet.getString("idList")),
							resultSet.getDouble("totalCost"));
					
					quotes.add(quote);
				}
				
			} catch (SQLException sqlException){
				sqlException.printStackTrace();
			}
			
			return quotes;
		}
		
		public Quote getQuoteByID(int quoteID) {
			resetPSandRS();
			Quote quote = new Quote();
			
			try {
				preparedStatement = this.connection.prepareStatement("SELECT * FROM Product "
						+ "WHERE id LIKE ? AND category='quote'", PreparedStatement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(1, quoteID);
				
				resultSet = preparedStatement.executeQuery();
				
				if (!resultSet.isBeforeFirst())
					System.out.println("NO DATA FOR QUOTE ID: '"+quoteID+"'");
				else {
					resultSet.next();
					quote = new Quote(resultSet.getInt("id"), 
							this.parseCSVToProducts(resultSet.getString("idList")),
							resultSet.getDouble("totalCost"));
				}
			} catch (SQLException sqlException){
				sqlException.printStackTrace();
			}
			
			return quote;
		}
		
		public List<Product> parseCSVToProducts(String CSVid) {
			List<String> ids = new ArrayList<>();
			List<Product> products = new ArrayList<Product>();
			
			ids = Arrays.asList(CSVid.split(","));
			
			for(String id : ids) {
				products.add(MasterController.getInstance().getProductGateway().getProductByID(Integer.parseInt(id)));
			}
			
			return products;
		}
		
		public void insertNewQuoteRecord(Quote quote) {
			resetPSandRS();
			
			try {
				preparedStatement = this.connection.prepareStatement("INSERT INTO Product (id, idList, totalCost, category)"
						+ "VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(1, quote.getId());
				preparedStatement.setString(2, this.parseProductIDsToCSV(quote.getProducts()));
				preparedStatement.setDouble(3, quote.getTotalCost());
				preparedStatement.setString(4, "quote");
				preparedStatement.execute();
				
				ResultSet resultSet = preparedStatement.getGeneratedKeys();
				resultSet.next();
				quote.setId(resultSet.getInt(1));
			} catch(SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}
		
		public void updateQuoteRecord(Quote quote) {
			resetPSandRS();
			
			try {
				preparedStatement = this.connection.prepareStatement("UPDATE Product SET id=?, idList=?, "
						+ "totalCost=?, category=? WHERE id=?", 
						PreparedStatement.RETURN_GENERATED_KEYS);
				
				preparedStatement.setInt(1, quote.getId());
				preparedStatement.setString(2, this.parseProductIDsToCSV(quote.getProducts()));
				preparedStatement.setDouble(3, quote.getTotalCost());
				preparedStatement.setString(4, "quote");
				preparedStatement.setInt(5, quote.getId());
				preparedStatement.execute();
				
			} catch(SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}
		
		public String parseProductIDsToCSV(List<Product> products) {
			String CSV = "";
			
			for(Product product : products) {
				CSV += product.getId() + ",";
			}
			
			CSV = CSV.substring(0, CSV.length() - 1);
			
			return CSV;
		}
		
		public void deleteQuoteRecord(int id) {
			resetPSandRS();
			
			try {
				preparedStatement = this.connection.prepareStatement("DELETE FROM Product "
						+ "WHERE id=? AND category='quote'", PreparedStatement.RETURN_GENERATED_KEYS);
				preparedStatement.setInt(1, id);
				
				preparedStatement.execute();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}
}
