package user;

/**
 * The UserGateway class represents a database bridge or gateway object
 * that allows the connection of this connection and the Users table
 * from the database.
 */


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

public class UsersGateway {

	/**
	 * A Connection object
	 */
	private Connection connect;
	
	/**
	 * Initialize a UsersGateway object. Reads a file containing
	 * the login properties or information to login into the database.
	 */
	public UsersGateway() {
		this.connect = null;
		
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
			
			this.connect = info.getConnection();
			
		} catch(IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Fetches the users from the Users table in the database. Returns
	 * a List containing fetched users.
	 * @return A List containing the users
	 */
	public List<DPMUser> getUsers() {
		List<DPMUser> users = new ArrayList<DPMUser>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = this.connect.prepareStatement("SELECT * FROM User"); //sql query
			rs = ps.executeQuery();
			
			//Grabs the record and creates a temporary user to be added to the List
			while(rs.next()) {
				DPMUser user = new DPMUser(rs.getInt("id"), rs.getString("userType"),
										   rs.getString("login"), rs.getString("password"),
										   rs.getString("name"), rs.getString("email"));
				users.add(user);
			}
			
			
		} catch(SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				this.closePSandRS(ps, rs);
			} catch(SQLException se) {
				se.printStackTrace();
			}
		}
		
		return users;
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
