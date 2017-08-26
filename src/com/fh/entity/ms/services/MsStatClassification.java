package com.fh.entity.ms.services;

import com.fh.entity.ms.BaseClass;

public class MsStatClassification extends BaseClass {
	
	  public int id;
	  public String name;

	  public int getID() {
			return this.id;
	  }
	  public void setID(int id) {
			this.id = id;
	  }
		
	  public String getName() {
			return this.name;
	  }
	  public void setName(String name) {
			this.name = name;
	  }
}
