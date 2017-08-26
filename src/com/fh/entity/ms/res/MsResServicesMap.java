package com.fh.entity.ms.res;

import java.util.Date;

/**
 * 资源服务明细表
 * @author ZhaiZhengqiang
 * @date 2016-11-08
 */
public class MsResServicesMap {
	
	private int id;//主键
	
	private int classid;//MS_RES_SERVICES_CLASS ID
	
	private int serviceid;//  
	
	private String repairman;//维护管理员用户ID
	
	private Date repairdate;//维护的时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClassid() {
		return classid;
	}

	public void setClassid(int classid) {
		this.classid = classid;
	}

	public int getServiceid() {
		return serviceid;
	}

	public void setServiceid(int serviceid) {
		this.serviceid = serviceid;
	}

	public String getRepairman() {
		return repairman;
	}

	public void setRepairman(String repairman) {
		this.repairman = repairman;
	}

	public Date getRepairdate() {
		return repairdate;
	}

	public void setRepairdate(Date repairdate) {
		this.repairdate = repairdate;
	}
	
	
}
 