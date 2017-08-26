package com.fh.entity.ms.stat;

public class MsServicesStat {
	private  String servicename;
    
	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}

	public String getServicefullname() {
		return servicefullname;
	}

	public void setServicefullname(String servicefullname) {
		this.servicefullname = servicefullname;
	}

	public int getAccesscount() {
		return accesscount;
	}

	public void setAccesscount(int accesscount) {
		this.accesscount = accesscount;
	}

	private  String servicefullname;
     
    private int  accesscount;

}
