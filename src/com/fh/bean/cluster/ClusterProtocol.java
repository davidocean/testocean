package com.fh.bean.cluster;
/**
 * 服务器集群
 * @author ZhaiZhengqiang
 * @date 2016-11-03
 */
public class ClusterProtocol {

	private String type;
	private String tcpClusterPort;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getTcpClusterPort() {
		return tcpClusterPort;
	}
	
	public void setTcpClusterPort(String tcpClusterPort) {
		this.tcpClusterPort = tcpClusterPort;
	}
	
	
}
