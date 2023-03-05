package nl.utwente.di.ecrf.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Section {
	
	private int sectionid;
	private String name;
	private String tip;
	private int sequence_no;
	private int moduleid;
	
	public Section() {
		
	}

	public Section(int sectionid, String name, String tip, int sequence_no, int moduleid) {
		this.sectionid = sectionid;
		this.name = name;
		this.tip = tip;
		this.sequence_no = sequence_no;
		this.moduleid = moduleid;
	}

	public int getSectionid() {
		return sectionid;
	}

	public void setSectionid(int sectionid) {
		this.sectionid = sectionid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public int getSequence_no() {
		return sequence_no;
	}

	public void setSequence_no(int sequence_no) {
		this.sequence_no = sequence_no;
	}

	public int getModuleid() {
		return moduleid;
	}

	public void setModuleid(int moduleid) {
		this.moduleid = moduleid;
	}
	
}
