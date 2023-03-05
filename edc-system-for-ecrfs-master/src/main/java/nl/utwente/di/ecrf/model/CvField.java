package nl.utwente.di.ecrf.model;

public class CvField extends Field {

	private String measurement_unit;
	private int field_length;
	
	public CvField(int fieldid, String name, String tip, int version, String date_of_release, int sectionid) {
		super(fieldid, name, tip, version, date_of_release, sectionid);
	}
	
	public CvField(int fieldid, String name, String tip, int version, String date_of_release, 
			int sectionid, String mu, int fl) {
		super(fieldid, name, tip, version, date_of_release, sectionid);
		this.measurement_unit = mu;
		this.field_length = fl;
	}

	public String getMeasurement_unit() {
		return measurement_unit;
	}

	public void setMeasurement_unit(String measurement_unit) {
		this.measurement_unit = measurement_unit;
	}

	public int getField_length() {
		return field_length;
	}

	public void setField_length(int field_length) {
		this.field_length = field_length;
	}
	
}
