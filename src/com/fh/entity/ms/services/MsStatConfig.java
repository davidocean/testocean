package com.fh.entity.ms.services;

import java.util.Date;

import com.fh.entity.ms.BaseClass;

/**
 * 要素统计服务配置
 * @author fujl
 * @date 2016-11-20 ++
 */
public class MsStatConfig  extends BaseClass{
	public int ID ;
	public String StaticsName;
	public String StaticsType;
	public int StaticsTypeVal;
	public String classificationName;
	public int classificationId;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getStaticsName() {
		return StaticsName;
	}
	public void setStaticsName(String staticsName) {
		StaticsName = staticsName;
	}
	public String getStaticsType() {
		return StaticsType;
	}
	public void setStaticsType(String staticsType) {
		StaticsType = staticsType;
	}
	public int getStaticsTypeVal() {
		return StaticsTypeVal;
	}
	public void setStaticsTypeVal(int staticsTypeVal) {
		StaticsTypeVal = staticsTypeVal;
	}
	public String getClassificationName() {
		return classificationName;
	}
	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}
	public int getClassificationId() {
		return classificationId;
	}
	public void setClassificationId(int classificationId) {
		this.classificationId = classificationId;
	}

}
