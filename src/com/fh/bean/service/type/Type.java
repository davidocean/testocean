package com.fh.bean.service.type;

import java.util.List;

public class Type {

	private String Name;
	
	private String DisplayName;
	
	private String CLSID;
	
	private String Description;
	
	private String CfgFactoryPROGID;
	
	private String ObjectFactoryCLSID;
	
	private List<Properties> properties; 
	
	private List<Info> Info;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDisplayName() {
		return DisplayName;
	}

	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}

	public String getCLSID() {
		return CLSID;
	}

	public void setCLSID(String cLSID) {
		CLSID = cLSID;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getCfgFactoryPROGID() {
		return CfgFactoryPROGID;
	}

	public void setCfgFactoryPROGID(String cfgFactoryPROGID) {
		CfgFactoryPROGID = cfgFactoryPROGID;
	}

	public String getObjectFactoryCLSID() {
		return ObjectFactoryCLSID;
	}

	public void setObjectFactoryCLSID(String objectFactoryCLSID) {
		ObjectFactoryCLSID = objectFactoryCLSID;
	}
	
	public List<Info> getInfo() {
		return Info;
	}

	public void setInfo(List<Info> info) {
		Info = info;
	}

	public List<Properties> getProperties() {
		return properties;
	}

	public void setProperties(List<Properties> properties) {
		this.properties = properties;
	}

}
