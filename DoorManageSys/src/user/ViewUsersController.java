package user;

/**
 * The ViewusersController class defines a controller object
 * for the ViewUsers_Page.
 * 
 * @author Team No Name Yet
 * @version 1.0
 */

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.MasterController;
import application.MasterViewController;
import applicationhelper.PageTypes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ViewUsersController implements Initializable {
	
	@FXML private TableView<UserTableDisplay> usersTable;
	@FXML private TableColumn<UserTableDisplay, String> nameCol;
	@FXML private TableColumn<UserTableDisplay, String> usernameCol;
	@FXML private TableColumn<UserTableDisplay, String> emailCol;
	@FXML private TableColumn<UserTableDisplay, String> usertypeCol;
	@FXML private Button addButton;
	
	/**
	 * An observable list which allows the users to be displayed
	 */
	private ObservableList<UserTableDisplay> observableList;
	
	/**
	 * The list of users
	 */
	private List<DPMUser> users;
	
	/**
	 * Initialize a ViewUsersController object.
	 * @param users
	 */
	public ViewUsersController(List<DPMUser> users) {
		this.users = users;
	}
	
	/**
	 * Handles the user interaction with the GUI components in the
	 * view users page.
	 */
	@FXML private void handleViewUsers(MouseEvent mouseEvent) {
		if((mouseEvent.getButton() == MouseButton.PRIMARY) && 
				(mouseEvent.getClickCount() == 2)) {
			Object selectedObject = usersTable.getSelectionModel().selectedItemProperty().get();
			UserTableDisplay selectedUser = (UserTableDisplay)selectedObject;
			if(selectedUser != null) {
				MasterController.getInstance().setEditObject(selectedUser.getUserData());
				MasterViewController.getInstance().changeView(PageTypes.USER_DETAIL_PAGE);
			}
		}
	}
	
	@FXML private void handleAddUser(ActionEvent action) {
		Object source = action.getSource();
		if(source == addButton) {
			MasterController.getInstance().setEditObject(new DPMUser());
			MasterViewController.getInstance().changeView(PageTypes.USER_EDIT_PAGE);
		}
	}
	
	/**
	 * Initializes the controller's components with data.
	 */
	public void initialize(URL loc, ResourceBundle rsc) {		
		nameCol.setCellValueFactory(new PropertyValueFactory<UserTableDisplay, String>("name"));
		usernameCol.setCellValueFactory(new PropertyValueFactory<UserTableDisplay, String>("username"));
		emailCol.setCellValueFactory(new PropertyValueFactory<UserTableDisplay, String>("email"));
		usertypeCol.setCellValueFactory(new PropertyValueFactory<UserTableDisplay, String>("usertype"));
		
		this.observableList = FXCollections.observableArrayList();
		for(DPMUser user : users) {
			UserTableDisplay userDisplay = new UserTableDisplay(user);
			userDisplay.setUserData(user);
			this.observableList.add(userDisplay);
		}
		
		this.usersTable.setItems(this.observableList);
	}
}
