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
import quoteproduct.*;

public class QuoteGateway {

		private Connection connection;
		
		public QuoteGateway() {
			this.connection = null;
			Properties properties = new Properties();
			FileInputStream fileStream = null;
			
			try {
				fileStream = new FileInputStream("database.properties");
				properties.load(fileStream);
				fileStream.close();
				
				MysqlDataSource info = new MysqlDataSource();
				info.setURL(properties.getProperty("MYSQL_DPM_DB_URL"));
				info.setUser(properties.getProperty("MYSQL_DPM_DB_UN"));
				info.setPassword(properties.getProperty("MYSQL_DPM_DB_PW"));
				
				this.connection = info.getConnection();
				
			} catch(IOException | SQLException e) {
				e.printStackTrace();
			}
		}
		
		public List<Quote> importListOfQuotesFromDB() {
			List<Quote> quotes = new ArrayList<Quote>();
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try {
				ps = this.connection.prepareStatement("SELECT * FROM Product "
						+ "WHERE category=?", PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setString(1, "quote");
				
				rs = ps.executeQuery();
				
				while(rs.next()) {
					Quote quote = new Quote(rs.getInt("id"), 
							this.parseCSVToProducts(rs.getString("idList")),
							rs.getDouble("totalCost"));
					
					quotes.add(quote);
				}
				
			} catch (SQLException sqlException){
				sqlException.printStackTrace();
			}
			
			return quotes;
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
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try {
				ps = this.connection.prepareStatement("INSERT INTO Product (id, idList, totalCost, category) "
						+ "VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
				ps.setInt(1, quote.getId());
				ps.setString(2, this.parseProductIDsToCSV(quote.getProducts()));
				ps.setDouble(3, quote.getTotalCost());
				ps.setString(4, "quote");
				ps.executeQuery();
				
			} catch(SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}
		
		public void updateQuoteRecord(Quote quote) {
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try {
				ps = this.connection.prepareStatement("UPDATE Product SET id=?, idList=?, "
						+ "totalCost=?, category=? WHERE id=?", 
						PreparedStatement.RETURN_GENERATED_KEYS);
				
				ps.setInt(1, quote.getId());
				ps.setString(2, this.parseProductIDsToCSV(quote.getProducts()));
				ps.setDouble(3, quote.getTotalCost());
				ps.setString(4, "quote");
				ps.setInt(5, quote.getId());
				ps.executeQuery();
				
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
		
		/**
		 * Closes the PreparedStatement and ResultSet.
		 * @param ps The prepared statement
		 * @param rs The result set
		 * @throws SQLException
		 */
		public void closePSandRS(PreparedStatement ps, ResultSet rs) throws SQLException {
			if(rs != null) {
				rs.close();
			}
			if(ps != null) {
				ps.close();
			}
		}
}
