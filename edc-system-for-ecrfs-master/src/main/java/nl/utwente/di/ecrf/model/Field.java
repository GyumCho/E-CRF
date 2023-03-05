package nl.utwente.di.ecrf.model;

import javax.xml.bind.annotation.XmlRootElement;

// NOTE: THE SUBCLASSES WORK WITH OR WITHOUT THE XmlElement NOTATION
@XmlRootElement
public class Field {

	private int fieldid;
	private String name;
	private String tip;
	private int version;
	private String date_of_release;
	private int sectionid;
	
	public Field() {
		
	}

	public Field(int fieldid, String name, String tip, int version, String date_of_release, int sectionid) {
		this.fieldid = fieldid;
		this.name = name;
		this.version = version;
		this.date_of_release = date_of_release;
		this.sectionid = sectionid;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getDate_of_release() {
		return date_of_release;
	}

	public void setDate_of_release(String date_of_release) {
		this.date_of_release = date_of_release;
	}

	public int getSectionid() {
		return sectionid;
	}

	public void setSectionid(int sectionid) {
		this.sectionid = sectionid;
	}

	public int getFieldid() {
		return fieldid;
	}

	public void setFieldid(int fieldid) {
		this.fieldid = fieldid;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}
	
}
