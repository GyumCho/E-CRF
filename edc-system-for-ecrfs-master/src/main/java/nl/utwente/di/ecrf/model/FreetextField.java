package nl.utwente.di.ecrf.model;

public class FreetextField extends Field {
	
	private String measurement_unit;

	public FreetextField(int fieldid, String name, String tip, int version, String date_of_release, int sectionid) {
		super(fieldid, name, tip, version, date_of_release, sectionid);
	}
	
	public FreetextField(int fieldid, String name, String tip, int version, String date_of_release, 
			int sectionid, String mu) {
		super(fieldid, name, tip, version, date_of_release, sectionid);
		this.measurement_unit = mu;
	}

	public String getMeasurement_unit() {
		return measurement_unit;
	}

	public void setMeasurement_unit(String measurement_unit) {
		this.measurement_unit = measurement_unit;
	}
	
}
