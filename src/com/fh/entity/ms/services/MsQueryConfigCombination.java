package com.fh.entity.ms.services;

import java.util.Date;

public class MsQueryConfigCombination {
	private int id;//主键id

	private String combinationname;//配置组合名称

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
	
    //combinationname
	public String getCombinationName() {
		return combinationname;
	}

	public void setCombinationName(String combinationname) {
		this.combinationname = combinationname;
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
