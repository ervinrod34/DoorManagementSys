package blueprint;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import application.*;
import quoteproduct.Product;

public class BlueprintGateway extends MasterGateway{

	public BlueprintGateway() {
		super();
	}
	
	public Blueprint getBlueprintByProductID(int productID) {
		resetPSandRS();
		Blueprint blueprint = new Blueprint();
		
		try {
			preparedStatement = this.connection.prepareStatement("SELECT * "
					+ "FROM Blueprint WHERE productID=?");
			preparedStatement.setInt(1, productID);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				blueprint = new Blueprint(resultSet.getInt("id"), productID, 
						resultSet.getString("dimension"), 
						resultSet.getString("strikeheight"),
						resultSet.getString("frame"), 
						resultSet.getString("hingespaces"),
						resultSet.getString("notes"), 
						resultSet.getString("inventorynotes"));
			}
		} catch(SQLException e) {
			tryToClosePSandRS();
		}
		
		return blueprint;
	}

	public void insertBlueprint(Blueprint blueprint) {
		resetPSandRS();
		
		try {
			preparedStatement = this.connection.prepareStatement(
					"INSERT INTO Blueprint (id, productID, dimension, "
					+ "strikeheight, frame, hingespaces, notes, inventorynotes) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			
			preparedStatement.setInt(1, blueprint.getId());
			preparedStatement.setInt(2, blueprint.getProductID());
			preparedStatement.setString(3, blueprint.getDimension());
			preparedStatement.setString(4, blueprint.getStrikeHeight());
			preparedStatement.setString(5, blueprint.getFrame());
			preparedStatement.setString(6, blueprint.getHingeSpaces());
			preparedStatement.setString(7, blueprint.getNotes());
			preparedStatement.setString(8, blueprint.getInventoryNotes());
			
			preparedStatement.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateBlueprint(Blueprint blueprint) {
		resetPSandRS();
		
		try {
			preparedStatement = this.connection.prepareStatement(
					"UPDATE Blueprint SET productID=?, dimension=?, "
					+ "strikeheight=?, frame=?, hingespaces=?, notes=?,"
					+ "inventorynotes=? WHERE blueprintID=?");
			
			preparedStatement.setInt(1, blueprint.getProductID());
			preparedStatement.setString(2, blueprint.getDimension());
			preparedStatement.setString(3, blueprint.getStrikeHeight());
			preparedStatement.setString(4, blueprint.getFrame());
			preparedStatement.setString(5, blueprint.getHingeSpaces());
			preparedStatement.setString(6, blueprint.getNotes());
			preparedStatement.setString(7, blueprint.getInventoryNotes());
			preparedStatement.setInt(8, blueprint.getId());
			
			preparedStatement.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteBlueprint(Blueprint blueprint) {
		resetPSandRS();
		
		try {
			preparedStatement = this.connection.prepareStatement("DELETE FROM Blueprint WHERE id=?",
					PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, blueprint.getId());
			preparedStatement.execute();
		} catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public boolean checkIfBlueprintIsAssignedToProduct(Product product) {
		Blueprint blueprintResult = this.getBlueprintByProductID(product.getId());
		
		if(blueprintResult.getId() == 0) {
			return false;
		} else {
			return true;
		}
	}
}
