package com.fh.entity.ms.res;

import java.util.List;

/**
 * 服务分类
 * @author ZhaiZhengqiang
 * @date 2016-11-06
 */
public class MsResServicesClass {
	
	private String id;//主键id
	
	private String parentid;//上级节点代码
	
	private String classname;//节点描述
	
	private String classtype;//节点代码值
	
	private Integer sort;//排序
	
	private String memo;//预留
	
	private String target;
	
	private MsResServicesClass msResServicesClass;
	
	private List<MsResServicesClass> subMsResServicesClass;
	
	private boolean hasMsResServicesClass = false;
	
	private String treeurl;
	
	private String icon;
	
	public MsResServicesClass(){
		
	}
	
	public MsResServicesClass(String id, String classname) {
		this.id = id;
		this.classname = classname;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getClasstype() {
		return classtype;
	}

	public void setClasstype(String classtype) {
		this.classtype = classtype;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public MsResServicesClass getMsResServicesClass() {
		return msResServicesClass;
	}

	public void setMsResServicesClass(MsResServicesClass msResServicesClass) {
		this.msResServicesClass = msResServicesClass;
	}

	public List<MsResServicesClass> getSubMsResServicesClass() {
		return subMsResServicesClass;
	}

	public void setSubMsResServicesClass(List<MsResServicesClass> subMsResServicesClass) {
		this.subMsResServicesClass = subMsResServicesClass;
	}

	public boolean isHasMsResServicesClass() {
		return hasMsResServicesClass;
	}

	public void setHasMsResServicesClass(boolean hasMsResServicesClass) {
		this.hasMsResServicesClass = hasMsResServicesClass;
	}

	public String getTreeurl() {
		return treeurl;
	}

	public void setTreeurl(String treeurl) {
		this.treeurl = treeurl;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	
	
}
