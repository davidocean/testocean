/**  
 * Copyright © Jes All rights reserved.
 *
 * @Title: MsServicesLogMerged.java
 * @Prject: OceanServer
 * @author: Jes  
 * @date: 2016年11月26日 下午5:53:27
 * @version: V1.0  
 */
package com.fh.entity.ms.logs;

import java.util.Date;

/**
 * @author Jes
 * 		
 */
public class MsServicesLogMerged {
	
	private MsServicesLogMergedPK pkid;// composite id.
	private String hour;// '服务访问日期中的‘小时’'
	private String servicename;
	private long accesstimeconsuming;// '服务访问平均耗时'
	private long accesssuccess;// '服务访问成功次数'
	private long accessfailure;// '服务访问失败次数'
	private long accesscount;// '服务访问总次数'
	private Date lastupdatetime;// '服务日志最后更新时间'
	private String memo;// '备注'
	private String userfullname;// '用户显示名称'
	private String servicefullname;// '服务显示名称'
	private Date accessdate;// '服务访问时间'
	
	public MsServicesLogMergedPK getPkid() {
		return pkid;
	}
	
	public void setPkid(MsServicesLogMergedPK pkid) {
		this.pkid = pkid;
	}
	
	public String getHour() {
		return hour;
	}
	
	public void setHour(String hour) {
		this.hour = hour;
	}
	
	public String getServicename() {
		return servicename;
	}
	
	public void setServicename(String servicename) {
		this.servicename = servicename;
	}
	
	public long getAccesstimeconsuming() {
		return accesstimeconsuming;
	}
	
	public void setAccesstimeconsuming(long accesstimeconsuming) {
		this.accesstimeconsuming = accesstimeconsuming;
	}
	
	public long getAccesssuccess() {
		return accesssuccess;
	}
	
	public void setAccesssuccess(long accesssuccess) {
		this.accesssuccess = accesssuccess;
	}
	
	public long getAccessfailure() {
		return accessfailure;
	}
	
	public void setAccessfailure(long accessfailure) {
		this.accessfailure = accessfailure;
	}
	
	public long getAccesscount() {
		return accesscount;
	}
	
	public void setAccesscount(long accesscount) {
		this.accesscount = accesscount;
	}
	
	public Date getLastupdatetime() {
		return lastupdatetime;
	}
	
	public void setLastupdatetime(Date lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getUserfullname() {
		return userfullname;
	}
	
	public void setUserfullname(String userfullname) {
		this.userfullname = userfullname;
	}
	
	public String getServicefullname() {
		return servicefullname;
	}
	
	public void setServicefullname(String servicefullname) {
		this.servicefullname = servicefullname;
	}
	
	public Date getAccessdate() {
		return accessdate;
	}
	
	public void setAccessdate(Date accessdate) {
		this.accessdate = accessdate;
	}
	
}
