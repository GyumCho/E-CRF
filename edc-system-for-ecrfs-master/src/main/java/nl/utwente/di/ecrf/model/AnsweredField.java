package nl.utwente.di.ecrf.model;

public class AnsweredField {

	private Field field;
	private String form_id;
	private String answer_text;
	
	private String date;
	
	public AnsweredField() {
		
	}
	
	public AnsweredField(Field field, String form_id, String answer_text, String date) {
		this.field = field;
		this.form_id = form_id;
		this.answer_text = answer_text;
		
		this.date = date;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public String getForm_id() {
		return form_id;
	}

	public void setForm_id(String form_id) {
		this.form_id = form_id;
	}

	public String getAnswer_text() {
		return answer_text;
	}

	public void setAnswer_text(String answer_text) {
		this.answer_text = answer_text;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}
