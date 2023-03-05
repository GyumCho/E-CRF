package nl.utwente.di.ecrf.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CRFmodel {
	
	private String v_num;
	private String date_of_creation;
	private int admin_id;
	
	// FIXME IF NEED BE
	private List<IncludedField> included;
	
	public CRFmodel() {
		
	}
	
	public CRFmodel(String v_num, String date_of_creation, int admin_id) {
		this.v_num = v_num;
		this.date_of_creation = date_of_creation;
		this.admin_id = admin_id;
	}
	
	public CRFmodel(String v_num, String date_of_creation, int admin_id, List<IncludedField> included) {
		this.v_num = v_num;
		this.date_of_creation = date_of_creation;
		this.admin_id = admin_id;
		this.included = included;
	}

	public List<IncludedField> getIncluded() {
		return included;
	}

	public void setIncluded(List<IncludedField> included) {
		this.included = included;
	}

	public String getV_num() {
		return v_num;
	}

	public void setV_num(String v_num) {
		this.v_num = v_num;
	}

	public String getDate_of_creation() {
		return date_of_creation;
	}

	public void setDate_of_creation(String date_of_creation) {
		this.date_of_creation = date_of_creation;
	}

	public int getAdmin_id() {
		return admin_id;
	}

	public void setAdmin_id(int admin_id) {
		this.admin_id = admin_id;
	}
	
}
