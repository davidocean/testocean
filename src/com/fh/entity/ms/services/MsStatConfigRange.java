package com.fh.entity.ms.services;

import com.fh.entity.ms.BaseClass;

public class MsStatConfigRange extends BaseClass{
	public int ID;
	public int StaticsId;
	public int Configdetailid;
	public String Rangename;
	public double Value1;
	public double Value2;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	
	
	public void setConfigdetailid(int Configdetailid) {
		this.Configdetailid = Configdetailid;
	}
	
	public int getConfigdetailid() {
		return Configdetailid;
	}
	

	public void setRangename(String Rangename) {
		this.Rangename = Rangename;
	}
	
	public String getRangename() {
		return Rangename;
	}
	
	
	public int getStaticsId() {
		return StaticsId;
	}
	public void setStaticsId(int staticsId) {
		StaticsId = staticsId;
	}
	public double getValue1() {
		return Value1;
	}
	public void setValue1(double value1) {
		Value1 = value1;
	}
	public double getValue2() {
		return Value2;
	}
	public void setValue2(double value2) {
		Value2 = value2;
	}

}
