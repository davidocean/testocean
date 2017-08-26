/**  
 * Copyright © Jes All rights reserved.
 *
 * @Title: MsServicesLogMonitor.java
 * @Prject: OceanServer
 * @author: Jes  
 * @date: 2016年11月20日 下午9:08:37
 * @version: V1.0  
 */
package com.fh.entity.ms.logs;

import java.util.Date;

/**
 * @author Jes 服务访问日志（服务监控）
 * 		
 */
public class MsServicesLogMonitor {
	
	private int logid; // 日志ID
	private int requestid; // 请求ID
	private Date requesttime; // 请求时间
	private String token; // 安全token
	private String requesturi; // 请求URI
	private String remotehostip; // 远程访问客户端IP
	private String remotehostport; // 远程访问客户端端口
	private String localhostip; // 本地服务器IP
	private String localhostport; // 本地服务器端口
	private String servicename; // 服务名称
	private String servicetype; // 服务类型
	private String method; // 服务调用方法
	private String param; // 服务调用请求参数
	private String memo; // 备注
	private String requser; // 用户名
	private int serviceid; // 服务ID
	private int accesstime; // 访问所耗时间
	private int httpstatus; // 服务返回状态码
	private String fromserver; // 服务请求来源
	
	public int getLogid() {
		return logid;
	}
	
	public void setLogid(int logid) {
		this.logid = logid;
	}
	
	public int getRequestid() {
		return requestid;
	}
	
	public void setRequestid(int requestid) {
		this.requestid = requestid;
	}
	
	public Date getRequesttime() {
		return requesttime;
	}
	
	public void setRequesttime(Date requesttime) {
		this.requesttime = requesttime;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getRequesturi() {
		return requesturi;
	}
	
	public void setRequesturi(String requesturi) {
		this.requesturi = requesturi;
	}
	
	public String getRemotehostip() {
		return remotehostip;
	}
	
	public void setRemotehostip(String remotehostip) {
		this.remotehostip = remotehostip;
	}
	
	public String getRemotehostport() {
		return remotehostport;
	}
	
	public void setRemotehostport(String remotehostport) {
		this.remotehostport = remotehostport;
	}
	
	public String getLocalhostip() {
		return localhostip;
	}
	
	public void setLocalhostip(String localhostip) {
		this.localhostip = localhostip;
	}
	
	public String getLocalhostport() {
		return localhostport;
	}
	
	public void setLocalhostport(String localhostport) {
		this.localhostport = localhostport;
	}
	
	public String getServicename() {
		return servicename;
	}
	
	public void setServicename(String servicename) {
		this.servicename = servicename;
	}
	
	public String getServicetype() {
		return servicetype;
	}
	
	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}
	
	public String getMethod() {
		return method;
	}
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getParam() {
		return param;
	}
	
	public void setParam(String param) {
		this.param = param;
	}
	
	public String getMemo() {
		return memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getRequser() {
		return requser;
	}
	
	public void setRequser(String requser) {
		this.requser = requser;
	}
	
	public int getServiceid() {
		return serviceid;
	}
	
	public void setServiceid(int serviceid) {
		this.serviceid = serviceid;
	}
	
	public int getAccesstime() {
		return accesstime;
	}
	
	public void setAccesstime(int accesstime) {
		this.accesstime = accesstime;
	}
	
	public int getHttpstatus() {
		return httpstatus;
	}
	
	public void setHttpstatus(int httpstatus) {
		this.httpstatus = httpstatus;
	}
	
	public String getFromserver() {
		return fromserver;
	}
	
	public void setFromserver(String fromserver) {
		this.fromserver = fromserver;
	}
	
}
