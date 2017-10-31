package user;

/**
 * The UserHelper class defines a helper class for the User object. Includes
 * methods that can be used by the User object.
 *  
 * @author Ervin Rodriguez
 * @version 1.0
 */

public class UserHelper{

	public UserTypes parseStringToUserType(String type) {
		UserTypes userType = null;
		
		switch(type) {
			case "Admin":
				userType = UserTypes.ADMIN;
				break;
				
			case "Sales":
				userType = UserTypes.ADMIN;
				break;
				
			case "Accounting":
				userType = UserTypes.ACCOUNTING;
				break;
				
			case "Purchasing":
				userType = UserTypes.PURCHASING;
				break;
				
			case "Engineering":
				userType = UserTypes.ENGINEERING;
				break;
				
			case "InventoryManager":
				userType = UserTypes.INVENTORYMANAGER;
				break;	
		}
		return userType;
	}
}
