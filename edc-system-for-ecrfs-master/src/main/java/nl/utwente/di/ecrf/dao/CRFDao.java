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

import nl.utwente.di.ecrf.model.AnsweredField;
import nl.utwente.di.ecrf.model.CRF;
import nl.utwente.di.ecrf.model.Field;

public enum CRFDao {

	instance;
	private Map<String, CRF> contentProvider = new HashMap<String, CRF>();
	
	// Necessary variables to establish a connection to the database
	Connection conn;
	Scanner sc = new Scanner(System.in);
	String dbuser = "dbname";       // TODO: CHANGE THIS LINE
	String passwd = "dbpasswd";		// TODO: CHANGE THIS LINE
		
	// Initialise the modules' dao
	private CRFDao() {
		
	}
	
	// Method that returns all forms
	public Map<String, CRF> getForms() {
		List<CRF> forms = new ArrayList<CRF>();
		try {
			conn = DriverManager.getConnection(
				"jdbc:postgresql://bronto.ewi.utwente.nl:5432/"+dbuser,
				dbuser, passwd);
			conn.setAutoCommit(true);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			forms = readForms(0, "");
			for(CRF f: forms) {
				f = getForm(f.getForm_id());
			}
			conn.close();
		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
		return contentProvider;
	}
	
	// Subsidiary method to read forms and initialise the forms' dao
	private List<CRF> readForms(int doctorId, String modelVersion) {
		List<CRF> forms = new ArrayList<CRF>();
		String query;
		if(doctorId != 0) { 
			query="SELECT * FROM dab_di19202b_260.\"eCRF_form\" WHERE doctor_id = " + doctorId;
		}
		else if(!modelVersion.equals("")) {
			query="SELECT * FROM dab_di19202b_260.\"eCRF_form\" WHERE model_v_num = '" + modelVersion + "';";
		}
		else {
			query="SELECT * FROM dab_di19202b_260.\"eCRF_form\"";
		}
		try {
			Statement st = conn.createStatement();
			System.out.println("Query: "+query);
			ResultSet rs = st.executeQuery(query);
			while (rs.next())
			{
				if(!forms.contains(new CRF(rs.getString(1)/*, rs.getString(2)*/, rs.getString(2), rs.getInt(3), rs.getInt(4)))) {
					forms.add(new CRF(rs.getString(1)/*, rs.getString(2)*/, rs.getString(2), rs.getInt(3), rs.getInt(4)));
				}
				// TODO: ERASE THIS LINE
				System.out.println("CRF id: " + rs.getString(1) + ", map size: " + contentProvider.size());
			}
			rs.close();
			st.close(); 
		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
		
		return forms;
	}
	
	// Method that returns a form based on its version number
	public CRF getForm(String formId) {
		if(contentProvider.containsKey(formId)) {
			return contentProvider.get(formId);
		}
		
		CRF f = new CRF();
		try {
			conn = DriverManager.getConnection(
				"jdbc:postgresql://bronto.ewi.utwente.nl:5432/"+dbuser,
				dbuser, passwd);
			conn.setAutoCommit(true);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			String query="SELECT * FROM dab_di19202b_260.\"eCRF_form\" f, dab_di19202b_260.answer a "
					+ "WHERE f.form_id = a.form_id AND f.form_id = '" + formId + "';";
			try {
				Statement st = conn.createStatement();
				System.out.println("Query: "+query);
				ResultSet rs = st.executeQuery(query);
				boolean flag = true;
				List<AnsweredField> ans = new ArrayList<AnsweredField>();
				while (rs.next())
				{
					if(flag) {
						f = new CRF(rs.getString(1)/*, rs.getString(2)*/, rs.getString(2), rs.getInt(3), rs.getInt(4), ans); 
						flag = false;
					}
					Field fld = FieldDao.instance.getField(rs.getInt(6));
					ans.add(new AnsweredField(fld, rs.getString(5), rs.getString(7), rs.getString(8)));
					// TODO: ERASE THIS LINE
					System.out.println("CRFmodel id: " + rs.getString(1) + ", map size: " + contentProvider.size());
				}
				contentProvider.put(f.getForm_id(), f);
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
		return contentProvider.get(formId);
	}
	
	// Method that returns all forms that a doctor has filled
	public List<CRF> getFormsByDoctorId(int doctorId) {
		List<CRF> finalforms = new ArrayList<CRF>();
		try {
			conn = DriverManager.getConnection(
				"jdbc:postgresql://bronto.ewi.utwente.nl:5432/"+dbuser,
				dbuser, passwd);
			conn.setAutoCommit(true);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			List<CRF> forms = readForms(doctorId, "");
			for(CRF f: forms) {
				f = getForm(f.getForm_id());
				finalforms.add(f);
			}
			conn.close();
		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
		return finalforms;
	}
	
	// Method that returns all forms that are based on a CRFmodel
	public List<CRF> getFormsByModelVersion(String modelVersion) {
		List<CRF> finalforms = new ArrayList<CRF>();
		try {
			conn = DriverManager.getConnection(
				"jdbc:postgresql://bronto.ewi.utwente.nl:5432/"+dbuser,
				dbuser, passwd);
			conn.setAutoCommit(true);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			List<CRF>forms = readForms(0, modelVersion);
			for(CRF f: forms) {
				f = getForm(f.getForm_id());
				finalforms.add(f);
			}
			conn.close();
		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
		return finalforms;
	}
	
	// Method that returns all the completed modules of a form
	public CRF getFormsModules(String formId) {
		CRF f = new CRF();
		try {
			conn = DriverManager.getConnection(
				"jdbc:postgresql://bronto.ewi.utwente.nl:5432/"+dbuser,
				dbuser, passwd);
			conn.setAutoCommit(true);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			String query = "SELECT * FROM dab_di19202b_260.fmd WHERE form_id = '" + formId + "';";
			try {
				Statement st = conn.createStatement();
				System.out.println("Query: "+query);
				ResultSet rs = st.executeQuery(query);
				List<List<String>> dates = new ArrayList<List<String>>();
				dates.add(new ArrayList<String>());
				dates.add(new ArrayList<String>());
				dates.add(new ArrayList<String>());
				while (rs.next())
				{
					switch(rs.getInt(2)) {
					case 1: {
						dates.get(0).add(rs.getString(3)); 
						break;
					}
					case 2: {
						dates.get(1).add(rs.getString(3)); 
						break;
					}
					default: {
						dates.get(2).add(rs.getString(3)); 
						break;
					}}
				}
				f = new CRF(formId, dates);
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
		
		return f;
	}
	
	// Method to retrieve the answers of a specific module at a specific date for a form
	public CRF getModulesAnswers(String formId, int moduleId, String date) {
		CRF f = new CRF();
		try {
			conn = DriverManager.getConnection(
				"jdbc:postgresql://bronto.ewi.utwente.nl:5432/"+dbuser,
				dbuser, passwd);
			conn.setAutoCommit(true);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			String query="SELECT a.field_id, a.form_id, a.answer_text, a.date, fr.model_v_num, fr.doctor_id, fr.patient_id " 
				+ "FROM dab_di19202b_260.\"eCRF_form\" fr, dab_di19202b_260.\"answer\" a, "
				+ "dab_di19202b_260.\"field\" f, dab_di19202b_260.\"section\" s, "
				+ "dab_di19202b_260.\"module\" m " 
				+ "WHERE fr.form_id = a.form_id AND a.field_id = f.field_id AND "
				+ "f.section_id = s.section_id AND s.module_id = m.module_id " 
				+ "AND a.form_id = '" + formId + "' AND m.module_id = " + moduleId + " AND a.date = to_date('" + date + "', 'YYYY-MM-DD');";
			try {
				Statement st = conn.createStatement();
				System.out.println("Query: "+query);
				ResultSet rs = st.executeQuery(query);
				boolean flag = true;
				List<AnsweredField> ans = new ArrayList<AnsweredField>();
				while (rs.next())
				{
					if(flag) {
						f = new CRF(rs.getString(2)/*, rs.getString(2)*/, rs.getString(5), rs.getInt(6), rs.getInt(7), ans); 
						flag = false;
					}
					Field field = FieldDao.instance.getField(rs.getInt(1));
					ans.add(new AnsweredField(field, rs.getString(2), rs.getString(3), rs.getString(4)));
				}
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
			
		
		return f;
	}
	
	// Method to add a new form in the forms' dao and the database
	public void addForm(CRF newForm) {
		contentProvider.put(newForm.getForm_id(), newForm);
		try {
			conn = DriverManager.getConnection(
				"jdbc:postgresql://bronto.ewi.utwente.nl:5432/"+dbuser,
				dbuser, passwd);
			conn.setAutoCommit(true);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			insertForm(newForm);
			insertAnsweredFields(newForm.getAnswers());
			conn.close();
		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
	}
	
	// Subsidiary method to insert a new model into the database
	private void insertForm(CRF newForm) {
		String query = "INSERT INTO dab_di19202b_260.\"eCRF_form\" VALUES "
				+ "('" + newForm.getForm_id() + "', '" + newForm.getModel_v_num() + "', " + newForm.getDoctor_id() 
						+ ", " + newForm.getPatient_id() + ");";
		// to_date('" + newForm.getDate_filled() + "', 'YYYY-MM-DD'),
		try {
			Statement st = conn.createStatement();
			System.out.println("Query: "+query);
			st.executeUpdate(query);
			contentProvider.put(newForm.getForm_id(), newForm);
			// TODO: ERASE THIS LINE
			System.out.println("New form inserted succesfully");
			st.close(); 
		} catch(SQLException e) {
			System.err.println("Oops: " + e.getMessage() );
			System.err.println("SQLState: " + e.getSQLState() );
		}
	}
	
	// Subsidiary method to insert all included fields of a newly created model into the database
	private void insertAnsweredFields(List<AnsweredField> formFields) {
		for(AnsweredField f: formFields) {
			String query = "INSERT INTO dab_di19202b_260.answer VALUES "
					+ "('" + f.getForm_id() + "', " + f.getField().getFieldid() + ", '"
							+ f.getAnswer_text() + "', to_date('" + f.getDate() + "', 'YYYY-MM-DD'));";
			
			try {
				Statement st = conn.createStatement();
				System.out.println("Query: "+query);
				st.executeUpdate(query);
				// TODO: ERASE THIS LINE;
				System.out.println("Field connected succesfully to the the form");
				st.close(); 
			} catch(SQLException e) {
				System.err.println("Oops: " + e.getMessage() );
				System.err.println("SQLState: " + e.getSQLState() );
			}
		}
	}
	
}