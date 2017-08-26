package com.fh.entity.ms.services;

import com.fh.entity.ms.BaseClass;

public class MsStatConfigDetail extends BaseClass{
	  public int ID;
	  public int StaticsId;
	  public String StaticObjName;
	  public String ServiceName;
	  public String LayerName;
	  public int LayerID;
	  public String FieldName;
	  public String FieldAlias;
	  public String WhereConditon;
	  public String GroupFieldName;
	  

	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getStaticsId() {
		return StaticsId;
	}
	public void setStaticsId(int staticsId) {
		StaticsId = staticsId;
	}
	public String getStaticObjName() {
		return StaticObjName;
	}
	public void setStaticObjName(String staticObjName) {
		StaticObjName = staticObjName;
	}
	public String getServiceName() {
		return ServiceName;
	}
	public void setServiceName(String serviceName) {
		ServiceName = serviceName;
	}
	public String getLayerName() {
		return LayerName;
	}
	public void setLayerName(String layerName) {
		LayerName = layerName;
	}
	public int getLayerID() {
		return LayerID;
	}
	public void setLayerID(int layerID) {
		LayerID = layerID;
	}
	public String getFieldName() {
		return FieldName;
	}
	public void setFieldName(String fieldName) {
		FieldName = fieldName;
	}
	public String getFieldAlias() {
		return FieldAlias;
	}
	public void setFieldAlias(String fieldAlias) {
		FieldAlias = fieldAlias;
	}
	public String getWhereConditon() {
		return WhereConditon;
	}
	public void setWhereConditon(String whereConditon) {
		WhereConditon = whereConditon;
	}

	public String getGroupFieldName() {
		return GroupFieldName;
	}
	public void setGroupFieldName(String groupFieldName) {
		GroupFieldName = groupFieldName;
	}


}
