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

import nl.utwente.di.ecrf.model.CRFmodel;
import nl.utwente.di.ecrf.model.Field;
import nl.utwente.di.ecrf.model.IncludedField;

public enum CRFmodelDao {

	instance;
	
	private Map<String, CRFmodel> contentProvider = new HashMap<String, CRFmodel>();
	
	// Necessary variables to establish a connection to the database
	Connection conn;
	Scanner sc = new Scanner(System.in);
	String dbuser = "dbname";       // TODO: CHANGE THIS LINE
	String passwd = "dbpasswd";		// TODO: CHANGE THIS LINE
		
	// Initialise the modules' dao
	private CRFmodelDao() {
		
	}
		
	// Method that returns all models
	public Map<String, CRFmodel> getModels() {
		List<CRFmodel> models = new ArrayList<CRFmodel>();
		try {
			conn = DriverManager.getConnection(
				"jdbc:postgresql://bronto.ewi.utwente.nl:5432/"+dbuser,
				dbuser, passwd);
			conn.setAutoCommit(true);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			models = readModels();
			for(CRFmodel m: models) {
				m = getModel(m.getV_num());
			}
			conn.close();
		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
		return contentProvider;
	}
	
	// Subsidiary method to read modules and initialise the models' dao
	private List<CRFmodel> readModels() {
		List<CRFmodel> models = new ArrayList<CRFmodel>();
		String query="SELECT * FROM dab_di19202b_260.\"eCRF_model\"";
		try {
			Statement st = conn.createStatement();
			System.out.println("Query: "+query);
			ResultSet rs = st.executeQuery(query);
			while (rs.next())
			{
				if(!models.contains(new CRFmodel(rs.getString(1), rs.getString(2), rs.getInt(3)))) {
					models.add(new CRFmodel(rs.getString(1), rs.getString(2), rs.getInt(3)));
				}
				// TODO: ERASE THIS LINE
				System.out.println("CRFmodel id: " + rs.getInt(1) + ", map size: " + contentProvider.size());
			}
			rs.close();
			st.close(); 
		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
		
		return models;
	}
			
	// Method that returns a model based on its version number
	public CRFmodel getModel(String modelVersion) {
		if(contentProvider.containsKey(modelVersion)) {
			return contentProvider.get(modelVersion);
		}
		CRFmodel m = new CRFmodel();
		try {
			conn = DriverManager.getConnection(
				"jdbc:postgresql://bronto.ewi.utwente.nl:5432/"+dbuser,
				dbuser, passwd);
			conn.setAutoCommit(true);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			String query="SELECT * FROM dab_di19202b_260.\"eCRF_model\" m, dab_di19202b_260.includes i "
					+ "WHERE m.v_num = i.model_v_num AND m.v_num = '" + modelVersion + "';";
			try {
				Statement st = conn.createStatement();
				System.out.println("Query: "+query);
				ResultSet rs = st.executeQuery(query);
				boolean flag = true;
				List<IncludedField> in = new ArrayList<IncludedField>();
				while (rs.next())
				{
					if(flag) {
						m = new CRFmodel(rs.getString(1), rs.getString(2), rs.getInt(3), in); 
						flag = false;
					}
					Field f = FieldDao.instance.getField(rs.getInt(5));
					in.add(new IncludedField(f, rs.getBoolean(6), rs.getBoolean(7), rs.getString(8), rs.getString(4)));
					// TODO: ERASE THIS LINE
					System.out.println("CRFmodel id: " + rs.getInt(1) + ", map size: " + contentProvider.size());
				}
				contentProvider.put(m.getV_num(), m);
				rs.close();
				st.close(); 
			} catch(SQLException e) {
				System.err.println("Oops: " + e.getMessage() );
				System.err.println("SQLState: " + e.getSQLState() );
			}
			conn.close();
		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
		return contentProvider.get(modelVersion);
	}
	
	// Method that returns the latest created model
	public CRFmodel getLatestCreatedModel() {
		CRFmodel latest = new CRFmodel();
		try {
			conn = DriverManager.getConnection(
				"jdbc:postgresql://bronto.ewi.utwente.nl:5432/"+dbuser,
				dbuser, passwd);
			conn.setAutoCommit(true);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			String query="SELECT * FROM dab_di19202b_260.\"eCRF_model\" m, dab_di19202b_260.includes i "
					+ "WHERE m.v_num = i.model_v_num AND m.v_num = (SELECT v_num FROM dab_di19202b_260.\"eCRF_model\""
					+ "GROUP BY v_num ORDER BY date_created DESC LIMIT 1);";
			try {
				Statement st = conn.createStatement();
				System.out.println("Query: "+query);
				ResultSet rs = st.executeQuery(query);
				boolean flag = true;
				List<IncludedField> in = new ArrayList<IncludedField>();
				while (rs.next())
				{
					if(flag) {
						latest = new CRFmodel(rs.getString(1), rs.getString(2), rs.getInt(3), in);
						flag = false;
					}
					Field f = FieldDao.instance.getField(rs.getInt(5));
					in.add(new IncludedField(f, rs.getBoolean(6), rs.getBoolean(7), rs.getString(8), rs.getString(4)));
					// TODO: ERASE THIS LINE
					System.out.println("CRFmodel id: " + rs.getInt(5) + ", map size: " + contentProvider.size());
				}
				contentProvider.put(latest.getV_num(), latest);
				rs.close();
				st.close(); 
			} catch(SQLException e) {
				System.err.println("Oops: " + e.getMessage() );
				System.err.println("SQLState: " + e.getSQLState() );
			}
			conn.close();
		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
		return contentProvider.get(latest.getV_num());
	}
	
	// Method to add a new model in the models' dao and the database
	public void addModel(CRFmodel newModel) {
		contentProvider.put(newModel.getV_num(), newModel);
		try {
			conn = DriverManager.getConnection(
				"jdbc:postgresql://bronto.ewi.utwente.nl:5432/"+dbuser,
				dbuser, passwd);
			conn.setAutoCommit(true);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			insertModel(newModel);
			insertIncludedFields(newModel.getIncluded());
			conn.close();
		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
	}
	
	// Subsidiary method to insert a new model into the database
	private void insertModel(CRFmodel newModel) {
		String query = "INSERT INTO dab_di19202b_260.\"eCRF_model\" VALUES "
				+ "(" + newModel.getV_num() + ", to_date('" + newModel.getDate_of_creation() + "', 'YYYY-MM-DD'), "
						+ newModel.getAdmin_id() + ");";
		try {
			Statement st = conn.createStatement();
			System.out.println("Query: "+query);
			st.executeUpdate(query);
			contentProvider.put(newModel.getV_num(), newModel);
			// TODO: ERASE THIS LINE
			System.out.println("New model inserted succesfully");
			st.close(); 
		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
	}
	
	// Subsidiary method to insert all included fields of a newly created model into the database
	private void insertIncludedFields(List<IncludedField> modelFields) {
		for(IncludedField f: modelFields) {
			String query = "INSERT INTO dab_di19202b_260.includes VALUES "
					+ "(" + f.getModel_v_num() + ", " + f.getField().getFieldid() + ", "
							+ f.isIncluded() + ", " + f.isRequired() + ", '" + f.getPredef_value() + "');";
			
			try {
				Statement st = conn.createStatement();
				System.out.println("Query: "+query);
				st.executeUpdate(query);
				// TODO: ERASE THIS LINE;
				System.out.println("Field connected succesfully to the the model");
				st.close(); 
			} catch(SQLException e) {
				System.err.println("Oops: " + e.getMessage() );
				System.err.println("SQLState: " + e.getSQLState() );
			}
		}
	}
}
