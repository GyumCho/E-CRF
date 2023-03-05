package nl.utwente.di.ecrf.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import nl.utwente.di.ecrf.model.Section;

public enum SectionDao {

	instance;
	
	private Map<Integer, Section> contentProvider = new HashMap<Integer, Section>();
	
	// Necessary variables to establish a connection to the database
	Connection conn;
	Scanner sc = new Scanner(System.in);
	String dbuser = "dbuser";		// TODO: CHANGE THIS LINE
	String passwd = "dbpassword";		// TODO: CHANGE THIS LINE
	
	// Initialise the modules' dao
	private SectionDao() {
		try {
			conn = DriverManager.getConnection(
				"jdbc:postgresql://bronto.ewi.utwente.nl:5432/"+dbuser,
				dbuser, passwd);
			conn.setAutoCommit(true);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			readSections();
			conn.close();
		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
	}
	
	// Subsidiary method to read modules and initialise the modules' dao
	private void readSections() {
		String query="SELECT * FROM dab_di19202b_260.section";
		try {
			Statement st = conn.createStatement();
			System.out.println("Query: "+query);
			ResultSet rs = st.executeQuery(query);
			while (rs.next())
			{
				contentProvider.put(rs.getInt(1), new Section(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
				// TODO: ERASE THIS LINE
				System.out.println("Section id: " + rs.getInt(1) + ", map size: " + contentProvider.size());
			}
			rs.close();
			st.close(); 
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
	}
	
	// Method that returns all modules
	public Map<Integer, Section> getSections() {
		return contentProvider;
	}
	
	// Method that returns all sections of a module based on the module id
	public List<Section> getModuleSections(int moduleid) {
		List<Section> sections = new ArrayList<Section>();
		for (java.util.Map.Entry<Integer, Section> mapElement : contentProvider.entrySet()) {   
            Section section = ((Section)mapElement.getValue());
            if(section.getModuleid() == moduleid) {
            	sections.add(section);
            }
        }
		
		return sections;
	}
	
	// Method that returns a section based on its id
	public Section getSection(int sectionid) {
		return contentProvider.get(sectionid);
	}
	
}
