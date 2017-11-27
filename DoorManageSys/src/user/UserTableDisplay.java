package user;

import javafx.beans.property.SimpleStringProperty;

public class UserTableDisplay {

	private final SimpleStringProperty name;
	private final SimpleStringProperty username;
	private final SimpleStringProperty email;
	private final SimpleStringProperty usertype;
	
	private DPMUser userData;
	
	public UserTableDisplay(DPMUser user) {
		this.name = new SimpleStringProperty(user.getName());
		this.username = new SimpleStringProperty(user.getLogin());
		this.email = new SimpleStringProperty(user.getEmail());
		this.usertype = new SimpleStringProperty(user.getUserType().getType());
	}
	
	public String getName() {
		return this.name.get();
	}
	
	public void setName(DPMUser user) {
		this.name.set(user.getName());
	}
	
	public String getUsername() {
		return this.username.get();
	}
	
	public void setUsername(DPMUser user) {
		this.username.set(user.getLogin());
	}
	
	public String getEmail() {
		return this.email.get();
	}
	
	public void setEmail(DPMUser user) {
		this.email.set(user.getEmail());
	}
	
	public String getUsertype() {
		return this.usertype.get();
	}
	
	public void setUsertype(DPMUser user) {
		this.usertype.set(user.getUserType().getType());
	}
	
	public DPMUser getUserData() {
		return this.userData;
	}
	
	public void setUserData(DPMUser user) {
		this.userData = user;
	}
}
