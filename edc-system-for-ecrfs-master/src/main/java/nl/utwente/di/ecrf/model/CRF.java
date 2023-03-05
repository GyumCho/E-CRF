package nl.utwente.di.ecrf.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CRF {
	
	private String form_id;
	//private String date_filled;
	private String model_v_num;
	private int doctor_id;
	private int patient_id;
	
	//FIXME IF NEED BE
	private List<AnsweredField> answers;
	private List<List<String>> modulesDates;
	
	public CRF() {
		
	}

	public CRF(String form_id/*, String date_filled*/, String model_v_num, int doctor_id, int patient_id) {
		this.form_id = form_id;
		//this.date_filled = date_filled;
		this.model_v_num = model_v_num;
		this.doctor_id = doctor_id;
		this.patient_id = patient_id;
	}
	
	public CRF(String form_id/*, String date_filled*/, String model_v_num, int doctor_id, int patient_id, 
			List<AnsweredField> answers) {
		this.form_id = form_id;
		//this.date_filled = date_filled;
		this.model_v_num = model_v_num;
		this.doctor_id = doctor_id;
		this.patient_id = patient_id;
		this.answers = answers;
	}
	
	public CRF(String form_id, List<List<String>> modulesDates) {
		this.form_id = form_id;
		this.modulesDates = modulesDates;
	}

	public List<AnsweredField> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnsweredField> answers) {
		this.answers = answers;
	}	

	public List<List<String>> getModules() {
		return modulesDates;
	}

	public void setModules(List<List<String>> modulesDates) {
		this.modulesDates = modulesDates;
	}

	public String getForm_id() {
		return form_id;
	}

	public void setForm_id(String form_id) {
		this.form_id = form_id;
	}

	/*public String getDate_filled() {
		return date_filled;
	}

	public void setDate_filled(String date_filled) {
		this.date_filled = date_filled;
	}*/

	public String getModel_v_num() {
		return model_v_num;
	}

	public void setModel_v_num(String model_v_num) {
		this.model_v_num = model_v_num;
	}

	public int getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}

	public int getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}

}
