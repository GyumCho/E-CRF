package nl.utwente.di.ecrf.model;

public class IncludedField {
	
	private Field field;
	private boolean included;
	private boolean required;
	private String predef_value;
	private String model_v_num;
	
	public IncludedField() {
		
	}
	
	public IncludedField(Field field, boolean included, boolean required, String predef_value, String model_v_num) {
		this.field = field;
		this.included = included;
		this.required = required;
		this.predef_value = predef_value;
		this.model_v_num = model_v_num;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public boolean isIncluded() {
		return included;
	}

	public void setIncluded(boolean included) {
		this.included = included;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getPredef_value() {
		return predef_value;
	}

	public void setPredef_value(String predef_value) {
		this.predef_value = predef_value;
	}

	public String getModel_v_num() {
		return model_v_num;
	}

	public void setModel_v_num(String model_v_num) {
		this.model_v_num = model_v_num;
	}
	
}
