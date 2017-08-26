package com.fh.bean.cluster;
/**
 * 服务器集群
 * @author ZhaiZhengqiang
 * @date 2016-11-03
 */
public class Cluster {
	
	private String clusterName;
	private String configuredState;
	private ClusterProtocol clusterProtocol;
	private String[] machineNames;
	
	public String getClusterName() {
		return clusterName;
	}
	
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	
	public String getConfiguredState() {
		return configuredState;
	}
	
	public void setConfiguredState(String configuredState) {
		this.configuredState = configuredState;
	}
	
	public ClusterProtocol getClusterProtocol() {
		return clusterProtocol;
	}
	
	public void setClusterProtocol(ClusterProtocol clusterProtocol) {
		this.clusterProtocol = clusterProtocol;
	}

	public String[] getMachineNames() {
		return machineNames;
	}

	public void setMachineNames(String[] machineNames) {
		this.machineNames = machineNames;
	}
	
	
}
