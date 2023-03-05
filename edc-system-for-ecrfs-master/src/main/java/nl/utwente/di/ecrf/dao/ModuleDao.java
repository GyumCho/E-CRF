package nl.utwente.di.ecrf.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import nl.utwente.di.ecrf.model.Mod;

public enum ModuleDao {

	instance;
	
	private Map<Integer, Mod> contentProvider = new HashMap<Integer, Mod>();
	
	// Necessary variables to establish a connection to the database
	Connection conn;
	Scanner sc = new Scanner(System.in);
	String dbuser = "dbuser";		// TODO: CHANGE THIS LINE
	String passwd = "dbpassword";		// TODO: CHANGE THIS LINE
	
	// Initialise the modules' dao
	private ModuleDao() {
		try {
			conn = DriverManager.getConnection(
				"jdbc:postgresql://bronto.ewi.utwente.nl:5432/"+dbuser,
				dbuser, passwd);
			conn.setAutoCommit(true);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			readModules();
			conn.close();
		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
	}
	
	// Subsidiary method to read modules and initialise the modules' dao
	private void readModules() {
		String query="SELECT * FROM dab_di19202b_260.module";
		try {
			Statement st = conn.createStatement();
			System.out.println("Query: "+query);
			ResultSet rs = st.executeQuery(query);
			while (rs.next())
			{
				contentProvider.put(rs.getInt(1), new Mod(rs.getInt(1), rs.getString(2), rs.getString(3)));
				// TODO: ERASE THIS LINE
				System.out.println("Module id: " + rs.getInt(1) + ", map size: " + contentProvider.size());
			}
			rs.close();
			st.close(); 
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
	}
	
	// Method that returns all modules
	public Map<Integer, Mod> getModules() {
		return contentProvider;
	}
	
	// Method that returns a module based on its id
	public Mod getModule(int id) {
		return contentProvider.get(id);
	}
	
}
