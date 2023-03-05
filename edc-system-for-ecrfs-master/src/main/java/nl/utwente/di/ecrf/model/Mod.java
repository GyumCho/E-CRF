package nl.utwente.di.ecrf.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Mod {
	
	private int moduleid;
	private String name;
	private String general_info;
	
	public Mod() {
		
	}

	public Mod(int moduleid, String name, String general_info) {
		this.moduleid = moduleid;
		this.name = name;
		this.general_info = general_info;
	}

	public int getModuleid() {
		return moduleid;
	}

	public void setModuleid(int moduleid) {
		this.moduleid = moduleid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGeneral_info() {
		return general_info;
	}

	public void setGeneral_info(String general_info) {
		this.general_info = general_info;
	}

}
