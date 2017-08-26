package com.fh.bean.service;

import net.sf.json.JSONObject;

public class Test {

	public static void main(String[] args) {
		ServicePublishBean bean = new ServicePublishBean();
		bean.setServiceName("world1");
		bean.setType("MapServer");
		bean.setDescription("my map service");
		bean.setCapabilities("map,query,data");
		bean.setClusterName("default");
		Properties pro = new Properties();
		pro.setVirtualCacheDir("http://119.29.52.71:6080//arcgis/server/arcgiscache");
		pro.setFilePath("c:\\oceandata\\world\\world.msd");
		pro.setVirtualOutputDir("http://119.29.52.71:6080/arcgis/server/arcgisoutput");
		pro.setOutputDir("C:\\arcgisserver\\arcgisoutput");
		pro.setIsCached("false");
		pro.setIgnoreCache("false");
		bean.setProperties(pro);
		JSONObject json = JSONObject.fromObject(bean); 
		System.out.println(json);
	}
}
