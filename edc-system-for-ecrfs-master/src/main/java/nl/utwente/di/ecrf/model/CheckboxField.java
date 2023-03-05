package nl.utwente.di.ecrf.model;

public class CheckboxField extends Field {
	
	// TODO: check how this can be converted into a list of Strings
	private String possible_values;
	private int num_of_possible_values;
	
	public CheckboxField(int fieldid, String name, String tip, int version, String date_of_release, int sectionid) {
		super(fieldid, name, tip, version, date_of_release, sectionid);
	}
	
	public CheckboxField(int fieldid, String name, String tip, int version, String date_of_release, 
			int sectionid, String pv, int npv) {
		super(fieldid, name, tip, version, date_of_release, sectionid);
		this.possible_values = pv;
		this.num_of_possible_values = npv;
	}

	public String getPossible_values() {
		return possible_values;
	}

	public void setPossible_values(String possible_values) {
		this.possible_values = possible_values;
	}

	public int getNum_of_possible_values() {
		return num_of_possible_values;
	}

	public void setNum_of_possible_values(int num_of_possible_values) {
		this.num_of_possible_values = num_of_possible_values;
	}
	
}
