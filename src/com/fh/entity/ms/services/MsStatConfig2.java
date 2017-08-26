package com.fh.entity.ms.services;

import java.util.Date;

/**
 * 要素统计服务配置
 * @author fujl
 * @date 2016-11-20 ++
 */
public class MsStatConfig2 {
	private int id;//主键id

	private String configname;//配置名称
	
	private String servicename;//服务名称
	
	private String layername;//图层名称
	
	private int layerid;//图层ID
	
	private String filedname; //字段名称
	
	private int xyz;//xyz轴
	
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
	//servicename
	public String getServicename() {
		return servicename;
	}

	public void setServicename(String servicename) {
		this.servicename = servicename;
	}
	//layername
	public String getLayername() {
		return layername;
	}

	public void setLayername(String layername) {
		this.layername = layername;
	}
	
	//layerid
	public int getLayerid() {
		return layerid;
	}

	public void setLayerid(int layerid) {
		this.layerid = layerid;
	}
    //filedname
	public String getFiledname() {
		return filedname;
	}

	public void setFiledname(String filedname) {
		this.filedname = filedname;
	}
    //xyz
	public int getXYZ() {
		return xyz;
	}

	public void setXYZ(int xyz) {
		this.xyz = xyz;
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
