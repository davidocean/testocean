package com.fh.entity.ms.services;

import java.util.Date;
/**
 * 服务注册
 * @author ZhaiZhengqiang
 * @date 2016-11-04
 */
public class MsServicesReg {
	
	private int id;//主键id
	
	private int classid;//服务分类id
	
	private int resinfoid;//资源ID
	
	private int resorgid;
	
	private String servicename;//服务名称
	
	private String servicefullname;
	
	private String servicetype;//注册服务类型
	
	private String folder;//发布目录
	
	private String fullpermission = "0"; //大权限
	
	private String clustercode;//集群代码
	
	private String transferurl;//转发访问地址
	
	private String nativeurl;//服务访问地址     
	
	private int status = 0;//运行状态
	
	private int ifpublish = 1;//服务是否公开 0不公开1公开
	
	private int snap;//服务概要图
	
	private String registerdman;//服务注册者ID
	
	private Date registerddate;//服务注册时间
	
	private String repairman;//最后操作者
	
	private Date repairdate;//最后操作时间
	
	private String description;
	
	private String servicecache;//服务缓存
	
	private String memo;
	
	private String thumbnail;//缩略图
	
	private int count = 0;//访问量

	private String  parentname;//父类名称
	
	private String version;//版本号
	
	private String desversion;//版本备注
	

	public String getDesversion() {
		return desversion;
	}

	public void setDesversion(String desversion) {
		this.desversion = desversion;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getParentname() {
		return parentname;
	}

	public void setParentid(String parentname) {
		this.parentname = parentname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getResinfoid() {
		return resinfoid;
	}

	public void setResinfoid(int resinfoid) {
		this.resinfoid = resinfoid;
	}

	public int getResorgid() {
		return resorgid;
	}

	public void setResorgid(int resorgid) {
		this.resorgid = resorgid;
	}

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

	public String getServicetype() {
		return servicetype;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}

	public String getFullpermission() {
		return fullpermission;
	}

	public void setFullpermission(String fullpermission) {
		this.fullpermission = fullpermission;
	}

	public String getClustercode() {
		return clustercode;
	}

	public void setClustercode(String clustercode) {
		this.clustercode = clustercode;
	}

	public String getTransferurl() {
		return transferurl;
	}

	public void setTransferurl(String transferurl) {
		this.transferurl = transferurl;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIfpublish() {
		return ifpublish;
	}

	public void setIfpublish(int ifpublish) {
		this.ifpublish = ifpublish;
	}

	public int getSnap() {
		return snap;
	}

	public void setSnap(int snap) {
		this.snap = snap;
	}

	public String getRegisterdman() {
		return registerdman;
	}

	public void setRegisterdman(String registerdman) {
		this.registerdman = registerdman;
	}

	public String getRepairman() {
		return repairman;
	}

	public void setRepairman(String repairman) {
		this.repairman = repairman;
	}
	
	public Date getRegisterddate() {
		return registerddate;
	}

	public void setRegisterddate(Date registerddate) {
		this.registerddate = registerddate;
	}

	public Date getRepairdate() {
		return repairdate;
	}

	public void setRepairdate(Date repairdate) {
		this.repairdate = repairdate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getClassid() {
		return classid;
	}

	public void setClassid(int classid) {
		this.classid = classid;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getServicecache() {
		return servicecache;
	}

	public void setServicecache(String servicecache) {
		this.servicecache = servicecache;
	}

	public String getNativeurl() {
		return nativeurl;
	}

	public void setNativeurl(String nativeurl) {
		this.nativeurl = nativeurl;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
