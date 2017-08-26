/**  
 * Copyright © Jes All rights reserved.
 *
 * @Title: MsServicesLogMergedPK.java
 * @Prject: OceanServer
 * @author: Jes  
 * @date: 2016年11月26日 下午5:53:43
 * @version: V1.0  
 */
package com.fh.entity.ms.logs;

/**
 * @author Jes
 * 		
 */
public class MsServicesLogMergedPK {
	
	private long serviceid;// '服务id'
	private String username;// '服务名称'
	private String year;// '服务访问日期中的‘年’'
	private String month;// '服务访问日期中的‘月’'
	private String day;// '服务访问日期中的‘日’'
	private String servicetype;// '服务类型'
	private String remotehostip;// '客户端访问IP'
	
	public long getServiceid() {
		return serviceid;
	}
	
	public void setServiceid(long serviceid) {
		this.serviceid = serviceid;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getMonth() {
		return month;
	}
	
	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getDay() {
		return day;
	}
	
	public void setDay(String day) {
		this.day = day;
	}
	
	public String getServicetype() {
		return servicetype;
	}
	
	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}
	
	public String getRemotehostip() {
		return remotehostip;
	}
	
	public void setRemotehostip(String remotehostip) {
		this.remotehostip = remotehostip;
	}
	
}
