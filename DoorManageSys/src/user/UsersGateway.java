package user;
import java.sql.PreparedStatement;

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
	
	public List<DPMUser> searchUsers(String searchKey) {
		List<DPMUser> users = new ArrayList<DPMUser>();
		
		try {
			preparedStatement = this.connection.prepareStatement("SELECT * FROM User WHERE "
					+ "name LIKE ? OR login LIKE ?");
			searchKey = "%" + searchKey + "%";
			preparedStatement.setString(1, searchKey);
			preparedStatement.setString(2, searchKey);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				DPMUser user = new DPMUser(resultSet.getInt("id"), resultSet.getString("userType"),
						resultSet.getString("login"), resultSet.getString("password"),
						resultSet.getString("name"), resultSet.getString("email"));
				
				users.add(user);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	public void insertUser(DPMUser user) {
		resetPSandRS();
		
		try {
			preparedStatement = this.connection.prepareStatement("INSERT INTO "
					+ "User (id, userType, login, password, name, email) "
					+ "VALUES (?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, user.getId());
			preparedStatement.setString(2, user.getUserType().getType());
			preparedStatement.setString(3, user.getLogin());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setString(5, user.getName());
			preparedStatement.setString(6, user.getEmail());
			
			preparedStatement.execute();
		} catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public void updateUser(DPMUser user) {
		resetPSandRS();
		
		try {
			preparedStatement = this.connection.prepareStatement("UPDATE User " + 
					"SET id=?, userType=?, login=?, password=?, name=?, email=? " + 
					"WHERE id=?", PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, user.getId());
			preparedStatement.setString(2, user.getUserType().getType());
			preparedStatement.setString(3, user.getLogin());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setString(5, user.getName());
			preparedStatement.setString(6, user.getEmail());
			preparedStatement.setInt(7, user.getId());
			
			preparedStatement.execute();
		} catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public void deleteUser(DPMUser user) {
		resetPSandRS();
		
		try {
			preparedStatement = this.connection.prepareStatement("DELETE FROM User id=?");
			preparedStatement.execute();
		} catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
}
