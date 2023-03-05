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
import java.util.Map.Entry;
import java.util.Scanner;

import nl.utwente.di.ecrf.model.CheckboxField;
import nl.utwente.di.ecrf.model.CvField;
import nl.utwente.di.ecrf.model.Field;
import nl.utwente.di.ecrf.model.FreetextField;

public enum FieldDao {

	instance;
	
	private Map<Integer, Field> contentProvider = new HashMap<Integer, Field>();
	private Map<Integer, Field> fieldsContentProvider = new HashMap<Integer, Field>();
	private Map<Integer, CheckboxField> chfieldsContentProvider = new HashMap<Integer, CheckboxField>();
	private Map<Integer, CvField> cvfieldsContentProvider = new HashMap<Integer, CvField>();
	private Map<Integer, FreetextField> ftfieldsContentProvider = new HashMap<Integer, FreetextField>();
	
	// Necessary variables to establish a connection to the database
	Connection conn;
	Scanner sc = new Scanner(System.in);
	String dbuser = "dbuser";		// TODO: CHANGE THIS LINE
	String passwd = "dbpassword";		// TODO: CHANGE THIS LINE
	
	// Initialise the modules' dao
	private FieldDao() {
		try {
			conn = DriverManager.getConnection(
				"jdbc:postgresql://bronto.ewi.utwente.nl:5432/"+dbuser,
				dbuser, passwd);
			conn.setAutoCommit(true);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			readFields();
			conn.close();
		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
	}
	
	// Subsidiary method to read modules and initialise the modules' dao
	private void readFields() {
		HashMap<Integer, String> tables = new HashMap<Integer, String>();
		tables.put(0, "checkbox_field");
		tables.put(1, "cv_field");
		tables.put(2, "freetext_field");
		tables.put(3, "date_field");
		HashMap<Integer, String> attr = new HashMap<Integer, String>();
		attr.put(0, "ch_id");
		attr.put(1, "cv_id");
		attr.put(2, "ft_id");
		attr.put(3, "d_id");
		
		try {
			Statement st = conn.createStatement();
			ResultSet rs = null;
			for(int i = 0; i < 4; i++) {
				String query="SELECT * "
						+ "FROM dab_di19202b_260.field f, dab_di19202b_260." + tables.get(i) + " t "
						+ "WHERE f.field_id = t." + attr.get(i) ;
				
				System.out.println("Query: "+query);
				rs = st.executeQuery(query);
				while (rs.next()) {
					contentProvider.put(rs.getInt(1), new Field(rs.getInt(1), rs.getString(2), rs.getString(6),  
							rs.getInt(3), rs.getString(4), rs.getInt(5)));
					// TODO: ERASE THIS LINE
					System.out.println("Field id: " + rs.getInt(1) + ", map size: " + contentProvider.size());
					
					switch(i) {
					case 0:  
						chfieldsContentProvider.put(rs.getInt(1), new CheckboxField(rs.getInt(1), rs.getString(2), 
								rs.getString(6), rs.getInt(3), rs.getString(4), rs.getInt(5), 
								rs.getString(8), rs.getInt(9)));
						// TODO: ERASE THIS LINE
						System.out.println("CheckboxField id: " + rs.getInt(1) + ", map size: " + chfieldsContentProvider.size());
						break;
					case 1:
						cvfieldsContentProvider.put(rs.getInt(1), new CvField(rs.getInt(1), rs.getString(2), 
								rs.getString(6), rs.getInt(3), rs.getString(4), rs.getInt(5), 
								rs.getString(8), rs.getInt(9)));
						// TODO: ERASE THIS LINE
						System.out.println("CvField id: " + rs.getInt(1) + ", map size: " + cvfieldsContentProvider.size());
						break;
					case 2:
						ftfieldsContentProvider.put(rs.getInt(1), new FreetextField(rs.getInt(1), rs.getString(2), 
								rs.getString(6), rs.getInt(3), rs.getString(4), rs.getInt(5), rs.getString(8)));
						// TODO: ERASE THIS LINE - CHANGE THE FIELDS TO THEIR CONSTRUCTORS
						System.out.println("FreetextField id: " + rs.getInt(1) + ", map size: " + ftfieldsContentProvider.size());
						break;
					case 3:
						fieldsContentProvider.put(rs.getInt(1), new Field(rs.getInt(1), rs.getString(2), 
								rs.getString(6), rs.getInt(3), rs.getString(4), rs.getInt(5)));
						// TODO: ERASE THIS LINE - CHANGE THE FIELDS TO THEIR CONSTRUCTORS
						System.out.println("DateField id: " + rs.getInt(1) + ", map size: " + fieldsContentProvider.size());
						break;
					}
					
				}
			}
			rs.close();
			st.close(); 
  		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
	}
	
	// Method that returns all fields
	public Map<Integer, Field> getfields() {
		return contentProvider;
	}
	
	// Method that returns all fields of a section based on the section id
	public List<Field> getSectionFields(int sectionid) {
		List<Field> fields = new ArrayList<Field>();
		for (Entry<Integer, Field> mapElement : contentProvider.entrySet()) {   
            Field field = ((Field)mapElement.getValue());
            if(field.getSectionid() == sectionid) {
            	fields.add(findField(mapElement.getKey()));	
            }
        }
		
		return fields;
	}
	
	// Method that returns a section based on its id
	public Field getField(int fieldid) {
		return findField(fieldid);
	}
	
	private Field findField(int key) { 
		if(fieldsContentProvider.containsKey(key)) {
			return fieldsContentProvider.get(key);
		}
		else if(chfieldsContentProvider.containsKey(key)) {
			return chfieldsContentProvider.get(key);
		}
		else if(cvfieldsContentProvider.containsKey(key)) {
			return cvfieldsContentProvider.get(key);
		}
		return ftfieldsContentProvider.get(key);
	}
	
}
