package com.fh.bean.service;
/**
 * 服务发布
 * @author ZhaiZhengqiang
 * @date 2016-11-03
 */
public class ServicePublishBean {
	
	private String serviceName;
	private String type;
	private String description;
	private String capabilities;
	private String clusterName;
//	private String minInstancesPerNode;
//	private String maxInstancesPerNode;
//	private String maxWaitTime;
//	private String maxStartupTime;
//	private String maxIdleTime;
//	private String maxUsageTime;
//	private String recycleInterval;
//	private String loadBalancing;
//	private String isolationLevel;
	
	private Properties properties;
	
	public String getServiceName() {
		return serviceName;
	}
	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Properties getProperties() {
		return properties;
	}
	
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(String capabilities) {
		this.capabilities = capabilities;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

//	public String getMinInstancesPerNode() {
//		return minInstancesPerNode;
//	}
//
//	public void setMinInstancesPerNode(String minInstancesPerNode) {
//		this.minInstancesPerNode = minInstancesPerNode;
//	}
//
//	public String getMaxInstancesPerNode() {
//		return maxInstancesPerNode;
//	}
//
//	public void setMaxInstancesPerNode(String maxInstancesPerNode) {
//		this.maxInstancesPerNode = maxInstancesPerNode;
//	}
//
//	public String getMaxWaitTime() {
//		return maxWaitTime;
//	}
//
//	public void setMaxWaitTime(String maxWaitTime) {
//		this.maxWaitTime = maxWaitTime;
//	}
//
//	public String getMaxStartupTime() {
//		return maxStartupTime;
//	}
//
//	public void setMaxStartupTime(String maxStartupTime) {
//		this.maxStartupTime = maxStartupTime;
//	}
//
//	public String getMaxIdleTime() {
//		return maxIdleTime;
//	}
//
//	public void setMaxIdleTime(String maxIdleTime) {
//		this.maxIdleTime = maxIdleTime;
//	}
//
//	public String getMaxUsageTime() {
//		return maxUsageTime;
//	}
//
//	public void setMaxUsageTime(String maxUsageTime) {
//		this.maxUsageTime = maxUsageTime;
//	}
//
//	public String getRecycleInterval() {
//		return recycleInterval;
//	}
//
//	public void setRecycleInterval(String recycleInterval) {
//		this.recycleInterval = recycleInterval;
//	}
//
//	public String getLoadBalancing() {
//		return loadBalancing;
//	}
//
//	public void setLoadBalancing(String loadBalancing) {
//		this.loadBalancing = loadBalancing;
//	}
//
//	public String getIsolationLevel() {
//		return isolationLevel;
//	}
//
//	public void setIsolationLevel(String isolationLevel) {
//		this.isolationLevel = isolationLevel;
//	}
//	
	
}
