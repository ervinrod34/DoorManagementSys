package user;

/**
 * The UserGateway class represents a database bridge or gateway object
 * that allows the connection of this connection and the Users table
 * from the database.
 */


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import application.*;

public class UsersGateway extends MasterGateway{

	/**
	 * Initialize a UsersGateway object. Reads a file containing
	 * the login properties or information to login into the database.
	 */
	public UsersGateway() {
		super();
	}
	
	/**
	 * Fetches the users from the Users table in the database. Returns
	 * a List containing fetched users.
	 * @return A List containing the users
	 */
	public List<DPMUser> getUsers() {
		resetPSandRS();
		List<DPMUser> users = new ArrayList<DPMUser>();
		
		try {
			preparedStatement = this.connection.prepareStatement("SELECT * FROM User"); //sql query
			resultSet = preparedStatement.executeQuery();
			
			//Grabs the record and creates a temporary user to be added to the List
			while(resultSet.next()) {
				DPMUser user = new DPMUser(resultSet.getInt("id"), resultSet.getString("userType"),
										   resultSet.getString("login"), resultSet.getString("password"),
										   resultSet.getString("name"), resultSet.getString("email"));
				users.add(user);
			}
			
			
		} catch(SQLException se) {
			se.printStackTrace();
		} finally {
			tryToClosePSandRS();
		}
		
		return users;
	}
}
