package inventory;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.*;

public class InventoryGateway extends MasterGateway{
	
	public InventoryGateway () {
		super();
	}
	
	public ArrayList<Inventory> searchInventory(String search) {
		resetPSandRS();
		ArrayList<Inventory> searchResults = new ArrayList<Inventory>();
		
		try {
			String query = "SELECT * FROM Inventory WHERE "
					+ "manufacturer LIKE ? OR manufacturerNo LIKE ? OR size LIKE ? OR "
					+ "colorCode LIKE ? OR extra LIKE ?";
			preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			
			search = "%" + search + "%";
			preparedStatement.setString(1, search);
			preparedStatement.setString(2, search);
			preparedStatement.setString(3, search);
			preparedStatement.setString(4, search);
			preparedStatement.setString(5, search);
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Inventory inventory = new Inventory (resultSet.getInt("id"), resultSet.getString("itemNo"), resultSet.getString("manufacturer"),
						resultSet.getString("manufacturerNo"), resultSet.getString("vendor"), resultSet.getString("size"),
						resultSet.getString("colorCode"), resultSet.getString("extra"), resultSet.getString("unitOfMeasure"),
						resultSet.getDouble("actualCost"), resultSet.getDouble("sellingPrice"), resultSet.getInt("quantity"),
						resultSet.getInt("minQuantity"), resultSet.getInt("maxQuantity"), resultSet.getString("category"),
						resultSet.getBoolean("taxable"), resultSet.getString("accountingCode"));
				
				searchResults.add(inventory);
			}
			
		} catch(SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				closePSandRS();
			}
			catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}
		
		return searchResults;
	}
	
	public ArrayList <Inventory> searchInventory (String column, String value) {
		resetPSandRS();
		ArrayList <Inventory> matches = new ArrayList <Inventory>();
		StringBuffer sqlCommand = new StringBuffer ();
		
		sqlCommand.append("SELECT * FROM Inventory WHERE " + column + "='" + value + "'");
		
		try {
			preparedStatement = connection.prepareStatement(sqlCommand.toString());
			resultSet = preparedStatement.executeQuery();
		
			while (resultSet.next()) {
			
				Inventory inventory = new Inventory (resultSet.getInt("id"), resultSet.getString("itemNo"), resultSet.getString("manufacturer"),
						resultSet.getString("manufacturerNo"), resultSet.getString("vendor"), resultSet.getString("size"),
						resultSet.getString("colorCode"), resultSet.getString("extra"), resultSet.getString("unitOfMeasure"),
						resultSet.getDouble("actualCost"), resultSet.getDouble("sellingPrice"), resultSet.getInt("quantity"),
						resultSet.getInt("minQuantity"), resultSet.getInt("maxQuantity"), resultSet.getString("category"),
						resultSet.getBoolean("taxable"), resultSet.getString("accountingCode"));
				
				matches.add(inventory);
			}
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		finally {
			try {
				closePSandRS();
			}
			catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}
		
		return matches;
	}
	
	public ArrayList <Inventory> searchInventoryForExtras() {
		resetPSandRS();
		ArrayList <Inventory> matches = new ArrayList <Inventory> ();
		
		try {
			String query = "SELECT * FROM Inventory WHERE NOT category='Door' "
					+ "AND NOT category='Frame' AND NOT category='Hinge' "
					+ "AND NOT category='Lock'";
			preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			resultSet = preparedStatement.executeQuery();
		
			while (resultSet.next()) {
				Inventory inventory = new Inventory (resultSet.getInt("id"), resultSet.getString("itemNo"), resultSet.getString("manufacturer"),
						resultSet.getString("manufacturerNo"), resultSet.getString("vendor"), resultSet.getString("size"),
						resultSet.getString("colorCode"), resultSet.getString("extra"), resultSet.getString("unitOfMeasure"),
						resultSet.getDouble("actualCost"), resultSet.getDouble("sellingPrice"), resultSet.getInt("quantity"),
						resultSet.getInt("minQuantity"), resultSet.getInt("maxQuantity"), resultSet.getString("category"),
						resultSet.getBoolean("taxable"), resultSet.getString("accountingCode"));
				
				matches.add(inventory);
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} finally {
			try {
				closePSandRS();
			}
			catch (SQLException sqlException) {
				sqlException.printStackTrace();
			}
		}
		
		return matches;
	}

	public void addInventory (Inventory inventory) {
		resetPSandRS();
		StringBuffer sqlCommand = new StringBuffer ();
		
		sqlCommand.append("INSERT INTO Inventory (id, itemNo, manufacturer, manufacturerNo, vendor, size, colorCode, extra, " +
						  "unitOfMeasure, actualCost, sellingPrice, quantity, minQuantity, maxQuantity, category, taxable, " +
						  "accountingCode)");
		sqlCommand.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"); 
		
		try {
			preparedStatement = connection.prepareStatement(sqlCommand.toString(),
					PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, inventory.getId());
			preparedStatement.setString(2, inventory.getItemNo());
			preparedStatement.setString(3, inventory.getManufacturer());
			preparedStatement.setString(4, inventory.getManufacturerNo());
			preparedStatement.setString(5, inventory.getVendor());
			preparedStatement.setString(6, inventory.getSize());
			preparedStatement.setString(7, inventory.getColorCode());
			preparedStatement.setString(8, inventory.getExtra());
			preparedStatement.setString(9, inventory.getUnitOfMeasure());
			preparedStatement.setDouble(10, inventory.getActualCost());
			preparedStatement.setDouble(11, inventory.getSellingPrice());
			preparedStatement.setInt(12, inventory.getQuantity());
			preparedStatement.setInt(13, inventory.getMinQuantity());
			preparedStatement.setInt(14, inventory.getMaxQuantity());
			preparedStatement.setString(15, inventory.getCategory());
			preparedStatement.setBoolean(16, inventory.isTaxable());
			preparedStatement.setString(17, inventory.getAccountingCode());
			
			preparedStatement.execute();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
	}
	
	public List<Inventory> getInventory() {
		resetPSandRS();
		List<Inventory> fullInventory = new ArrayList<Inventory>();
		
		try {
			preparedStatement = connection.prepareStatement("SELECT * FROM Inventory");
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Inventory inventory = new Inventory (resultSet.getInt("id"), resultSet.getString("itemNo"), resultSet.getString("manufacturer"),
						 resultSet.getString("manufacturerNo"), resultSet.getString("vendor"), resultSet.getString("size"),
						 resultSet.getString("colorCode"), resultSet.getString("extra"), resultSet.getString("unitOfMeasure"),
						 resultSet.getDouble("actualCost"), resultSet.getDouble("sellingPrice"), resultSet.getInt("quantity"),
						 resultSet.getInt("minQuantity"), resultSet.getInt("maxQuantity"), resultSet.getString("category"),
						 resultSet.getBoolean("taxable"), resultSet.getString("accountingCode"));
				
				fullInventory.add(inventory);
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {
				closePSandRS();
			} catch (SQLException se){
				se.printStackTrace();
			}
		}
		return fullInventory;
	}

	public void updateInventory (Inventory inventory) {
		resetPSandRS();
		StringBuffer sqlCommand = new StringBuffer ();
		
		sqlCommand.append("UPDATE Inventory SET id=?, itemNo=?, manufacturer=?, manufacturerNo=?, " + 
											   "vendor=?, size=?, colorCode=?, extra=?, unitOfMeasure=?, " + 
											   "actualCost=?, sellingPrice=?, quantity=?, minQuantity=?, " +
											   "maxQuantity=?, category=?, taxable=?, accountingCode=? " + 
											   "WHERE id =?");
		
		try {
			preparedStatement = connection.prepareStatement(sqlCommand.toString(), 
					PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, inventory.getId());
			preparedStatement.setString(2, inventory.getItemNo());
			preparedStatement.setString(3, inventory.getManufacturer());
			preparedStatement.setString(4, inventory.getManufacturerNo());
			preparedStatement.setString(5, inventory.getVendor());
			preparedStatement.setString(6, inventory.getSize());
			preparedStatement.setString(7, inventory.getColorCode());
			preparedStatement.setString(8, inventory.getExtra());
			preparedStatement.setString(9, inventory.getUnitOfMeasure());
			preparedStatement.setDouble(10, inventory.getActualCost());
			preparedStatement.setDouble(11, inventory.getSellingPrice());
			preparedStatement.setInt(12, inventory.getQuantity());
			preparedStatement.setInt(13, inventory.getMinQuantity());
			preparedStatement.setInt(14, inventory.getMaxQuantity());
			preparedStatement.setString(15, inventory.getCategory());
			preparedStatement.setBoolean(16, inventory.isTaxable());
			preparedStatement.setString(17, inventory.getAccountingCode());
			preparedStatement.setInt(18, inventory.getId());
			
			preparedStatement.execute();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
	}
	
	public void deleteInventory (int id) {
		resetPSandRS();
		StringBuffer sqlCommand = new StringBuffer ();
		
		sqlCommand.append("DELETE FROM Inventory WHERE id = '" + id + "'");
		
		try {
			preparedStatement = connection.prepareStatement(sqlCommand.toString());
			preparedStatement.execute();
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}
	
	public Inventory getInventoryByID(String itemNo) {
		resetPSandRS();
		Inventory inventory = new Inventory();
		
		try {
			preparedStatement = this.connection.prepareStatement("SELECT * FROM Inventory "
					+ "WHERE itemNo LIKE ?", PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, itemNo);
			
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.isBeforeFirst())
				System.out.println("NO DATA");
			else {
				resultSet.next();
				inventory = new Inventory(resultSet.getInt("id"), resultSet.getString("itemNo"), resultSet.getString("manufacturer"),
						 resultSet.getString("manufacturerNo"), resultSet.getString("vendor"), resultSet.getString("size"),
						 resultSet.getString("colorCode"), resultSet.getString("extra"), resultSet.getString("unitOfMeasure"),
						 resultSet.getDouble("actualCost"), resultSet.getDouble("sellingPrice"), resultSet.getInt("quantity"),
						 resultSet.getInt("minQuantity"), resultSet.getInt("maxQuantity"), resultSet.getString("category"),
						 resultSet.getBoolean("taxable"), resultSet.getString("accountingCode"));
			}
		} catch (SQLException sqlException){
			sqlException.printStackTrace();
		}
		
		return inventory;
	}
}


















