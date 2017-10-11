package quoteproduct;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

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
		
		public List<Quote> importListOfQuotesFromDB(String category) {
			List<Quote> quotes = new ArrayList<Quote>();
			//id, idList, totalCost, category
			
			
			
			return quotes;
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
