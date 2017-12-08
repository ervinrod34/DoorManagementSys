package user;

import application.MasterController;

/**
 * The DPMUSer class defines a DPMUser object which
 * represents a user for this Door Production Manager application.
 * 
 * @author Team No Name Yet
 * @version 1.0
 */

public class DPMUser {

	/**
	 * The id of this user
	 */
	private int id;
	
	/**
	 * The user type
	 */
	private UserTypes userType;
	
	/**
	 * The login of this user
	 */
	private String login;
	
	/**
	 * The password of this user
	 */
	private String password;
	
	/**
	 * The name of this user
	 */
	private String name;
	
	/**
	 * The email of this user
	 */
	private String email;
	
	private UserHelper helper = new UserHelper();

	/**
	 * Initialize a DPMUser object using default values.
	 */
	public DPMUser() {
		this.id = 0;
		this.userType = UserTypes.NEW;
		this.login = "";
		this.password = "";
		this.name = "";
		this.email = "";
	}
	
	/**
	 * Initialize a DPMUser object using predefined values.
	 * @param id An int representing an id
	 * @param type A UserTypes representing a user type user type
	 * @param login A String representing a login information
	 * @param pw A String representing a password
	 * @param name A String representing a name
	 * @param email A String representing an email
	 */
	public DPMUser(int id, String type, String login, String pw, String name, String email) {
		this.id = id;
		this.userType = helper.parseStringToUserType(type);
		this.login = login;
		this.password = pw;
		this.name = name;
		this.email = email;
	}
	
	/**
	 * Returns the id of this User.
	 * @return An int specifying the id
	 */
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Returns the usertype.
	 * @return A UserTypes specifying the userType
	 */
	public UserTypes getUserType() {
		return this.userType;
	}

	/**
	 * Changes the userType.
	 * @param userType A UserTypes representing the new userType
	 */
	public void setUserType(UserTypes userType) {
		this.userType = userType;
	}

	/**
	 * Returns the login information.
	 * @return A String specifying the login
	 */
	public String getLogin() {
		return this.login;
	}

	/**
	 * Changes the login information.
	 * @param login A String representing the new login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Returns the password.
	 * @return A String specifying the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Changes the password.
	 * @param password A String representing the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns the name.
	 * @return A String specifying the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Changes the name.
	 * @param name A String representing the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the email address.
	 * @return A String specifying the email address
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Changes the email address.
	 * @param email A String specifying the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void save() {
		if(id == 0) {
			MasterController.getInstance().getUsersGateway().insertUser(this);
		} else {
			MasterController.getInstance().getUsersGateway().updateUser(this);
		}
	}
	
	/**
	 * Returns a String representation of this DPMUser object.
	 * @return A String containing the name, login, and id
	 */
	public String toString() {
		String returnString = "";
		
		returnString += this.name;
		returnString += "\tUsername: " + this.login + " ";
		returnString += "ID: " + this.id;
		
		return returnString;
	}
}
