package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public abstract class MasterGateway {

	protected Connection connection;
	protected MysqlDataSource database;
	
	protected Properties properties;
	protected FileInputStream propertiesFile;
	
	protected PreparedStatement preparedStatement;
	protected ResultSet resultSet;
	
	public MasterGateway() {
		this.setupDBProperties();
		this.setupDBCredentials();
		this.tryToConnectToDB();
	}
	
	private void setupDBProperties() {
		this.properties = new Properties();
		
		try {
			this.propertiesFile = new FileInputStream("database.properties");
		}
		catch (FileNotFoundException fileNotFoundException) {
			System.err.println("Properties File not found.");
		}
		
		try {
			this.properties.load(propertiesFile);
		}
		catch (IOException ioException) {
			System.err.println("Error reading from Input Stream.");
		}
	}
	
	private void setupDBCredentials() {
		this.database = new MysqlDataSource();
		
		this.database.setURL(properties.getProperty("MYSQL_DPM_DB_URL"));
		this.database.setUser(properties.getProperty("MYSQL_DPM_DB_UN"));
		this.database.setPassword(properties.getProperty("MYSQL_DPM_DB_PW"));
	}
	
	private void tryToConnectToDB() {
		try {
			this.connection = this.database.getConnection();
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace ();
		}
	}
	
	protected void resetPSandRS() {
		this.preparedStatement = null;
		this.resultSet = null;
	}
	
	protected void closePSandRS() throws SQLException{
		if(this.resultSet != null) {
			this.resultSet.close();
		}
		if(this.preparedStatement != null) {
			this.preparedStatement.close();
		}
	}
}
