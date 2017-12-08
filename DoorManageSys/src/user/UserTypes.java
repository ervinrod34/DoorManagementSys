package user;

/**
 * The UserTypes class represents an enumeration of the
 * different types of users for Door Production Management
 * application.
 * 
 * @author Team No Name Yet
 * @version 1.0
 */

public enum UserTypes {

	NEW("New"),
	SALES("Sales"),
	ACCOUNTING("Accounting"),
	PURCHASING("Purchasing"),
	ENGINEER("Engineer"),
	INVENTORYMANAGER("Inventory Manager"),
	ADMIN("Administrator");
	
	private final String type;
	
	private UserTypes(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
}
