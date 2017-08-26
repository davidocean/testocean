package com.fh.entity.ms.services;

import java.util.Date;

/**
 * GIS服务器配置
 * @author fujl
 * @date 2016-11-14 ++
 */
public class MsGisServerConfig {
	
	private int id;//主键id
		
	private String configname;//配置名称
	
	private String computername;//计算机名称
	
	private String intranetip;//内网IP
	
	private String intranetport;//内网端口
	
	private String adminname; //管理者
	
	private String adminpwd;//密码
	
	private String proxyservername;//代理计算机名称
	
	private String proxyip;//内网IP    
	
	private String proxyport;//内网端口
	
	private String zmemo;//备注
	
	private int enabled=1;
	
	private int sortcode=1;
	
	private int deletemark=0;

	private Date createdate=(new Date()); 
	
	private String createuserid;
	
	private String createusername;

	private Date modifydate;

	private String modifyuserid;
	
	private String modifyusername;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    //Enabled
	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
    //Sortcode
	public int getSortcode() {
		return sortcode;
	}

	public void setSortcode(int sortcode) {
		this.sortcode = sortcode;
	}
	//deletemark
	public int getDeletemark() {
		return deletemark;
	}

	public void setDeletemark(int deletemark) {
		this.deletemark = deletemark;
	}
	
    //configname
	public String getConfigname() {
		return configname;
	}

	public void setConfigname(String configname) {
		this.configname = configname;
	}
	//computername
	public String getComputername() {
		return computername;
	}

	public void setComputername(String computername) {
		this.computername = computername;
	}
	//intranetip
	public String getIntranetip() {
		return intranetip;
	}

	public void setIntranetip(String intranetip) {
		this.intranetip = intranetip;
	}
	
	//intranetport
	public String getIntranetport() {
		return intranetport;
	}

	public void setIntranetport(String intranetport) {
		this.intranetport = intranetport;
	}
    //adminname
	public String getAdminname() {
		return adminname;
	}

	public void setAdminname(String adminname) {
		this.adminname = adminname;
	}
    //adminpwd
	public String getAdminpwd() {
		return adminpwd;
	}

	public void setAdminpwd(String adminpwd) {
		this.adminpwd = adminpwd;
	}
    //proxyservername
	public String getProxyservername() {
		return proxyservername;
	}

	public void setProxyservername(String proxyservername) {
		this.proxyservername = proxyservername;
	}
    //proxyip
	public String getProxyip() {
		return proxyip;
	}

	public void setProxyip(String proxyip) {
		this.proxyip = proxyip;
	}
    //proxyport
	public String getProxyport() {
		return proxyport;
	}

	public void setProxyport(String proxyport) {
		this.proxyport = proxyport;
	}
    //zmemo
	public String getZmemo() {
		return zmemo;
	}

	public void setZmemo(String zmemo) {
		this.zmemo = zmemo;
	}
	//createdate
	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
    //modifydate
	public Date getModifydate() {
		return modifydate;
	}

	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}

	public String getCreateuserid() {
		return createuserid;
	}

	public void setCreateuserid(String createuserid) {
		this.createuserid = createuserid;
	}

	public String getCreateusername() {
		return createusername;
	}

	public void setCreateusername(String createusername) {
		this.createusername = createusername;
	}

	public String getModifyuserid() {
		return modifyuserid;
	}

	public void setModifyuserid(String modifyuserid) {
		this.modifyuserid = modifyuserid;
	}

	public String getModifyusername() {
		return modifyusername;
	}

	public void setModifyusername(String modifyusername) {
		this.modifyusername = modifyusername;
	}
}
