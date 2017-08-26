package com.fh.entity.ms.user;

import java.util.Date;

/**
 * 资源服务用户权限表
 * @author ZhaiZhengqiang
 * @date 2016-11-08
 */
public class MsUserSecurity {
	
	private int id;//主键id
	
	private String userid;//用户id关联
	
	private String rolename;
	
	private String serviceid;//MS_SERVICES_REG ID
	
	private String description;//权限的简单描述信息
	
	private String ifmanagement;
	
	private Date updatedate;//维护的时间
	
	private String updateman;//登录用户ID
	
	private String memo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getServiceid() {
		return serviceid;
	}

	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIfmanagement() {
		return ifmanagement;
	}

	public void setIfmanagement(String ifmanagement) {
		this.ifmanagement = ifmanagement;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

	public String getUpdateman() {
		return updateman;
	}

	public void setUpdateman(String updateman) {
		this.updateman = updateman;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
